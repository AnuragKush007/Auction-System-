package servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.DefaultDto;
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
 *      zoneName: string
 *      storeName: string
 *      xLocation = Integer
 *      yLocation = Integer
 *      ppk: Integer
 *      array itemIdToPrice:
 *                  itemId: integer
 *                  price: double
 *  Output:
 *      DefaultDto :
 *          isSucceed : true/false
 *          errorMassage : string
 */

@WebServlet(name = "AddNewStoreServlet", urlPatterns = "/addNewStore")
public class AddNewStoreServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        String zoneNameParameter = requestData.get(ZONE_NAME).getAsString();
        String storeNameParameter = requestData.get(STORE_NAME).getAsString();
        Integer xLocationParameter = requestData.get(X_LOCATION).getAsInt();
        Integer yLocationParameter = requestData.get(Y_LOCATION).getAsInt();
        Integer ppkParameter = requestData.get(PPK).getAsInt();
        DefaultDto defaultDto = new DefaultDto();

        JsonArray itemIdsToPriceJson = requestData.get(ITEM_ID_TO_PRICE).getAsJsonArray();
        Map<Integer, Double> itemIdsToPriceParameter = new HashMap<>();
        for (JsonElement item : itemIdsToPriceJson) {
            JsonObject itemJson = item.getAsJsonObject();
            int itemId = itemJson.get(ID).getAsInt();
            Double price = itemJson.get(PRICE).getAsDouble();
            itemIdsToPriceParameter.put(itemId, price);
        }

        StoreOwner storeOwner = (StoreOwner) engineManager.getUserByName(SessionUtils.getUsername(request));
        try {
            synchronized (getServletContext()) {
                engineManager.addNewStore(zoneNameParameter, storeOwner, storeNameParameter,
                        new Pair<>(xLocationParameter, yLocationParameter), ppkParameter, itemIdsToPriceParameter);
            }
            defaultDto.setSucceed(true);
            out.append(GsonUtils.toJson(defaultDto));
        } catch (Exception e) {
            defaultDto.setSucceed(false);
            defaultDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(defaultDto));
        }
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
