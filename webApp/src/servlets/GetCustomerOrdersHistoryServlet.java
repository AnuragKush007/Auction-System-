package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.*;
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

/**
 * input:
 *
 * Output:
 *     OrdersHistoryDto:
 *          isSucceed : true/false
 *          Set<OrderDto> orders:
 *                id: Integer
 *                date: Date
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
 *                        storeName: string
 *                        storeId: Integer
 *                        quantity: Double
 *                        pricePerUnit: Double
 *                        totalPrice: Double
 *                        isChosenOffer: boolean
 *          errorMessage: String
 */

@WebServlet(name = "GetCustomerOrdersHistoryServlet", urlPatterns = "/getCustomerOrdersHistory")
public class GetCustomerOrdersHistoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        OrdersHistoryDto ordersHistoryDto = new OrdersHistoryDto();
        Customer customer = (Customer) engineManager.getUserByName(SessionUtils.getUsername(request));
        Set<Order> orders = null;

        try {
            synchronized (getServletContext()) {
                orders = new HashSet<Order>(engineManager.getOrdersByCustomer(customer));
            }
            Set<OrderDto> orderDtoSet = new HashSet<>();
            for (Order order : orders){
                OrderDto orderDto = new OrderDto();
                orderDto.setId(order.getID());
                orderDto.setDate(order.getDate());
                orderDto.setLocation(getStoreLocationAsString(order.getLocation()));
                orderDto.setNumOfItems(order.getTotalItems());
                orderDto.setNumOfStores(order.getSubOrders().size());
                orderDto.setTotalDeliveryPrice(Utils.twoNumbersAfterDot(order.getDeliveryPrice()));
                orderDto.setTotalItemsPrice(Utils.twoNumbersAfterDot(order.getItemsTotalPrice()));
                orderDto.setTotalOrderPrice(Utils.twoNumbersAfterDot(order.getTotalOrderPrice()));
                orderDto.setItems(getOrderItems(order, engineManager));
                orderDtoSet.add(orderDto);
            }
            ordersHistoryDto.setOrders(orderDtoSet);
            ordersHistoryDto.setSucceed(true);
            out.append(GsonUtils.toJson(ordersHistoryDto));
        } catch (NullPointerException e){
            ordersHistoryDto.setSucceed(false);
            ordersHistoryDto.setErrorMessage("No orders have yet been posted for the customer " + customer.getName() + "\n");
            out.append(GsonUtils.toJson(ordersHistoryDto));
        } catch (IllegalArgumentException e){
            ordersHistoryDto.setSucceed(false);
            ordersHistoryDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(ordersHistoryDto));
        } catch (Exception e){
            ordersHistoryDto.setSucceed(false);
            ordersHistoryDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(ordersHistoryDto));
        }
    }

    protected String getStoreLocationAsString(Pair<Integer,Integer> location){
        return "[" + location.getKey() + "," + location.getValue() + "]";
    }

    protected Set<OrderItemDto> getOrderItems(Order order, EngineManager engineManager){
        Set<OrderItemDto> items = new HashSet<>();
        for (SubOrder subOrder : order.getSubOrders().values()){
            items.addAll(getOrderItemsDto(subOrder, engineManager));
        }
        return items;
    }

    protected Set<OrderItemDto> getOrderItemsDto(SubOrder subOrder, EngineManager engine){
        Set<OrderItemDto> itemDtoSet = new HashSet<>();
        for(Integer itemId : subOrder.getItemIdToAmount().keySet()){    /**All items from original order*/
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(itemId);
            Item item = engine.getItemByItemId(itemId);
            itemDto.setName(item.getName());
            itemDto.setPurchaseCategory(item.getPurchaseCategory().getPurchaseCategoryStr());
            itemDto.setStoreId(subOrder.getStore().getID());
            itemDto.setStoreName(subOrder.getStore().getName());
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
                itemDto1.setStoreId(subOrder.getStore().getID());
                itemDto1.setStoreName(subOrder.getStore().getName());
                itemDto1.setQuantity(Utils.twoNumbersAfterDot(offer.getQuantity()));
                itemDto1.setPricePerUnit((Utils.twoNumbersAfterDot((double) offer.getAdditionalPrice())));
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
