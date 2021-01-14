package servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static constants.Constants.*;

/**
 *  Input:
 *      zoneName: string
 *      orderType: string - static/dynamic
 *      storeName: string // if dynamic then = null
 *      xLocation = Integer
 *      yLocation = Integer
 *      date: Date
 *      array itemIdToAmount:
 *                  itemId: integer
 *                  quantity: double

 *  output:
 *      OrderSubSummaryDto:
 *          isSucceed - true/false
 *          Set<OrderStoreDto>:
 *                  isSucceed: true/false
 *                  id: Integer
 *                  name: String
 *                  location: String [x,y]
 *                  distanceFromCustomer: Double
 *                  ppk: Integer
 *                  deliveryCost: Double
 *                  itemsTypeQuantity: Integer
 *                  itemsCost: Double
 *                  errorMessage: String
 *          errorMassage - string
 */

@WebServlet(name = "CheckoutServlet", urlPatterns = "/checkout")
public class CheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        String zoneNameParameter = requestData.get(ZONE_NAME).getAsString();
        String storeNameParameter = requestData.get(STORE_NAME).getAsString();
        String orderTypeParameter = requestData.get(ORDER_TYPE).getAsString();
        Integer xLocationParameter = requestData.get(X_LOCATION).getAsInt();
        Integer yLocationParameter = requestData.get(Y_LOCATION).getAsInt();
        String dateAsStringFromParameter = requestData.get(DATE).getAsString();
        OrderSubSummaryDto orderSummaryDto = new OrderSubSummaryDto();
        Date dateParameter = null;
        try {
            dateParameter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAsStringFromParameter);
        } catch (ParseException e) {
            orderSummaryDto.setSucceed(false);
            orderSummaryDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(orderSummaryDto));
        }

        JsonArray itemIdsToAmountJson = requestData.get(ITEM_ID_TO_AMOUNT).getAsJsonArray();
        Map<Integer, Double> itemIdsToAmountParameter = new HashMap<>();
        for(JsonElement item : itemIdsToAmountJson ){
            JsonObject itemJson = item.getAsJsonObject();
            int itemId = itemJson.get(ID).getAsInt();
            Double quantity = itemJson.get(QUANTITY).getAsDouble();
            itemIdsToAmountParameter.put(itemId, quantity);
        };

        Map<Item, Double> itemsToAmount = convertToItemsAndAmount(itemIdsToAmountParameter, engineManager, zoneNameParameter);
        Customer customer = (Customer) engineManager.getUserByName(SessionUtils.getUsername(request));
        try {
            engineManager.newOrder_Create(customer);
            engineManager.newOrder_SetZone(zoneNameParameter, customer);
            engineManager.newOrder_SetLocation(new Pair<>(xLocationParameter, yLocationParameter), customer);
            engineManager.newOrder_SetDate(dateParameter, customer);
            engineManager.newOrder_SetType(orderTypeParameter, customer);

            if (orderTypeParameter.equals("static")) {
                Store store = engineManager.getStoreByStoreNameAndZoneName(storeNameParameter, zoneNameParameter);
                engineManager.newOrder_SetStore(store.getID(), customer);
            }
            engineManager.newOrder_SaveItemsSelection(itemsToAmount, customer);
        } catch (Exception e) {
            engineManager.newOrder_delete(customer);
            orderSummaryDto.setSucceed(false);
            orderSummaryDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(orderSummaryDto));
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
                storeDto.setSucceed(true);
                storesDtos.add(storeDto);
            }
            orderSummaryDto.setStores(storesDtos);
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

    protected String getStoreLocationAsString(Pair<Integer,Integer> location){
        return "[" + location.getKey() + "," + location.getValue() + "]";
    }

    protected Map<Item, Double> convertToItemsAndAmount(Map<Integer, Double> itemIdsAndAmount, EngineManager engine, String zoneName){
        Map<Item, Double> itemsAndAmount = new HashMap<>();
        for (Integer itemId : itemIdsAndAmount.keySet()){
            itemsAndAmount.put(engine.getZoneByName(zoneName).getItemById(itemId), itemIdsAndAmount.get(itemId));
        }
        return itemsAndAmount;
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
