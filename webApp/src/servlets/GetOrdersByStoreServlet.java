package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.OrderDto;
import dto.OrderItemDto;
import dto.StoreOrdersDto;
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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static constants.Constants.*;

/**
 * input:
 *      zoneName: string
 *      storeId: integer
 * Output:
 *     StoreOrdersDto:
 *          isSucceed : true/false
 *          Set<OrderDto> orders:
 *                id: Integer
 *                date: Date
 *                customerName: string
 *                location: string [x,y]
 *                numOfStores: Integer
 *                numOfItems: Integer
 *                totalItemsPrice: Double
 *                totalDeliveryPrice: Double
 *                totalOrderPrice: Double
 *                Set<OrderItemDto> items:
 *                        id: Integer
 *                        name: string
 *                        parchaseCategory: string
 *                        quantity: Double
 *                        pricePerUnit: Double
 *                        totalPrice: Double
 *                        isChosenOffer: boolean
 *          errorMessage: String
 */
@WebServlet(name = "GetOrdersByStoreServlet", urlPatterns = "/getOrdersByStore")
public class GetOrdersByStoreServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        String storeNameParameter = requestData.get(STORE_NAME).getAsString();
        String zoneNameParameter = requestData.get(ZONE_NAME).getAsString();
        StoreOrdersDto storeOrdersDto = new StoreOrdersDto();
        StoreOwner storeOwner = (StoreOwner) engineManager.getUserByName(SessionUtils.getUsername(request));
        Set<Order> orders = null;
        Store store = null;
        if(engineManager.getStoreByStoreNameAndZoneName(storeNameParameter, zoneNameParameter).getOwner() != storeOwner){
            storeOrdersDto.setSucceed(false);
            storeOrdersDto.setErrorMessage("You're not the store owner\n");
            out.append(GsonUtils.toJson(storeOrdersDto));
        }
        else{
            try{
                synchronized (getServletContext()) {
                    store = engineManager.getStoreByStoreNameAndZoneName(storeNameParameter, zoneNameParameter);
                    orders = new HashSet<Order>(store.getOrders().values());
                }
                Set<OrderDto> orderDtoSet = new HashSet<>();
                for (Order order : orders){
                    SubOrder subOrder = order.getSubOrderByStoreId(store.getID());
                    OrderDto orderDto = new OrderDto();
                    orderDto.setId(order.getID());
                    orderDto.setDate(order.getDate());
                    orderDto.setCustomerName(order.getCustomer().getName());
                    orderDto.setLocation(getStoreLocationAsString(order.getLocation()));
                    orderDto.setNumOfItems(subOrder.getTotalItems());
                    orderDto.setTotalDeliveryPrice(Utils.twoNumbersAfterDot(subOrder.getDeliveryPrice()));
                    orderDto.setTotalItemsPrice(Utils.twoNumbersAfterDot(subOrder.getItemsTotalPrice()));
                    orderDto.setTotalOrderPrice(Utils.twoNumbersAfterDot(subOrder.getTotalPrice()));
                    orderDto.setItems(getOrderItemsDto(subOrder, engineManager));
                    orderDtoSet.add(orderDto);
                }
                storeOrdersDto.setOrders(orderDtoSet);
                storeOrdersDto.setSucceed(true);
                out.append(GsonUtils.toJson(storeOrdersDto));
            } catch (NullPointerException e){
                storeOrdersDto.setSucceed(false);
                storeOrdersDto.setErrorMessage("No orders have yet been posted for the store " + store.getName() + "\n");
                out.append(GsonUtils.toJson(storeOrdersDto));
            } catch (IllegalArgumentException e){
                storeOrdersDto.setSucceed(false);
                storeOrdersDto.setErrorMessage(e.getMessage());
                out.append(GsonUtils.toJson(storeOrdersDto));
            } catch (Exception e){
                storeOrdersDto.setSucceed(false);
                storeOrdersDto.setErrorMessage(e.getMessage());
                out.append(GsonUtils.toJson(storeOrdersDto));
            }
        }
    }

    protected String getStoreLocationAsString(Pair<Integer,Integer> location){
        return "[" + location.getKey() + "," + location.getValue() + "]";
    }

    protected Set<OrderItemDto> getOrderItemsDto(SubOrder subOrder, EngineManager engine){
        Set<OrderItemDto> itemDtoSet = new HashSet<>();
        for(Integer itemId : subOrder.getItemIdToAmount().keySet()){    /**All items from original order*/
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(itemId);
            Item item = engine.getItemByItemId(itemId);
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
                Item currentItem = engine.getItemByItemId(offer.getItemId());
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
