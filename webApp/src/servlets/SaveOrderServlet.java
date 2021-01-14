package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.OrderItemDto;
import dto.OrderStoreDto;
import dto.OrderSummaryDto;
import javafx.util.Pair;
import sdm.engine.CutomSDMClasses.*;
import utils.GsonUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static constants.Constants.*;

/**
 *  Input:
 *      isApproved : true/false
 *  output:
 *      OrderSummaryDto:
 *          isSucceed - true/false
 *          Set<OrderStoreDto> stores:
 *                  isSucceed: true/false
 *                  id: Integer
 *                  name: String
 *                  location: String [x,y]
 *                  distanceFromCustomer: Double
 *                  ppk: Integer
 *                  deliveryCost: Double
 *                  itemsTypeQuantity: Integer
 *                  itemsCost: Double
 *                  Set<OrderItemDto> items:
 *                              id: Integer
 *                              name: String
 *                              purchaseCategory: string
 *                              Quantity: Double
 *                              pricePerUnit: Double
 *                              totalPrice: Double
 *                              isChosenOffer: Boolean
 *                  errorMessage: String
 *          totalItemsPrice - Double
 *          totalDeliveriesPrice - Double
 *          totalOrderPrice - Double
 *          errorMassage - string
 */

@WebServlet(name = "SaveOrderServlet", urlPatterns = "/saveOrder")
public class SaveOrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        String isApprovedParameter = requestData.get(IS_APPROVED).getAsString();
        OrderSummaryDto orderSummaryDto = new OrderSummaryDto();

        Customer customer = (Customer) engineManager.getUserByName(SessionUtils.getUsername(request));

        synchronized (getServletContext()) {
            try {
                if(isApprovedParameter.equals("true"))
                    engineManager.newOrder_Finish(customer);
            } catch (Exception e) {
                orderSummaryDto.setSucceed(false);
                orderSummaryDto.setErrorMessage(e.getMessage());
                out.append(GsonUtils.toJson(orderSummaryDto));
            }
        }
        Set<OrderStoreDto> storesDtos = new HashSet<>();
        try {
            Map<Integer, SubOrder> subOrders = engineManager.newOrder_getSubOrders(customer);
            for (SubOrder subOrder : subOrders.values()) {
                OrderStoreDto storeDto = new OrderStoreDto();
                storeDto.setId(subOrder.getStore().getID());
                storeDto.setDeliveryCost(Utils.twoNumbersAfterDot(subOrder.getDeliveryPrice()));
                storeDto.setDistanceFromCustomer(Utils.twoNumbersAfterDot(subOrder.getDistanceFromCustomer()));
                storeDto.setItemsCost(Utils.twoNumbersAfterDot(subOrder.getItemsTotalPrice()));
                storeDto.setItemsTypeQuantity(subOrder.getItemsTypeQuantity());
                storeDto.setLocation(getStoreLocationAsString(subOrder.getStore().getLocation()));
                storeDto.setName(subOrder.getStore().getName());
                storeDto.setPpk(subOrder.getStore().getPPK());
                storeDto.setItems(getOrderItemsDto(subOrder, engineManager, customer));
                storeDto.setSucceed(true);
                storesDtos.add(storeDto);
            }
            orderSummaryDto.setStores(storesDtos);
            orderSummaryDto.setTotalDeliveriesPrice(engineManager.newOrder_getOrderByCustomer(customer).getDeliveryPrice());
            orderSummaryDto.setTotalItemsPrice(engineManager.newOrder_getOrderByCustomer(customer).getItemsTotalPrice());
            orderSummaryDto.setTotalOrderPrice(engineManager.newOrder_getOrderByCustomer(customer).getTotalOrderPrice());
            orderSummaryDto.setSucceed(true);
            out.append(GsonUtils.toJson(orderSummaryDto));
        } catch (IllegalArgumentException e) {
            orderSummaryDto.setSucceed(false);
            orderSummaryDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(orderSummaryDto));
        } catch (Exception e) {
            orderSummaryDto.setSucceed(false);
            orderSummaryDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(orderSummaryDto));
        }
    }

    protected Set<OrderItemDto> getOrderItemsDto(SubOrder subOrder, EngineManager engine, Customer customer){
        Set<OrderItemDto> itemDtoSet = new HashSet<>();
        for(Integer itemId : subOrder.getItemIdToAmount().keySet()){    /**All items from original order*/
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(itemId);
            Item item = engine.newOrder_getZone(customer).getItemById(itemId);
            itemDto.setName(item.getName());
            itemDto.setPurchaseCategory(item.getPurchaseCategory().getPurchaseCategoryStr());
            itemDto.setQuantity(Utils.twoNumbersAfterDot(subOrder.getItemAmount(itemId)));
            itemDto.setPricePerUnit(Utils.twoNumbersAfterDot(subOrder.getItemPricePerUnit(itemId)));
            itemDto.setTotalPrice(Utils.twoNumbersAfterDot(subOrder.getItemTotalPrice(itemId)));
            itemDto.setChosenOffer(false);
            itemDtoSet.add(itemDto);
        }
        for(Integer itemId : subOrder.getChosenOffers().keySet()) {   /**All items from chosen offers*/
            for (Offer offer : subOrder.getChosenOffers().get(itemId)) {
                OrderItemDto itemDto1 = new OrderItemDto();
                Item currentItem = engine.newOrder_getZone(customer).getItemById(offer.getItemId());
                itemDto1.setId(currentItem.getID());
                itemDto1.setName(currentItem.getName());
                itemDto1.setPurchaseCategory(currentItem.getPurchaseCategory().getPurchaseCategoryStr());
                itemDto1.setQuantity(Utils.twoNumbersAfterDot(offer.getQuantity()));
                itemDto1.setPricePerUnit(Utils.twoNumbersAfterDot((double) offer.getAdditionalPrice()));
                itemDto1.setTotalPrice(Utils.twoNumbersAfterDot(itemDto1.getPricePerUnit() * itemDto1.getQuantity()));
                itemDto1.setChosenOffer(true);
                itemDtoSet.add(itemDto1);
            }
        }
        return itemDtoSet;
    }

    protected String getStoreLocationAsString(Pair<Integer,Integer> location){
        return "[" + location.getKey() + "," + location.getValue() + "]";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
