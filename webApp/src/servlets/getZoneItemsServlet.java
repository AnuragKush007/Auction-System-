package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.ItemDto;
import dto.ZoneItemsDto;
import sdm.engine.CutomSDMClasses.EngineManager;
import sdm.engine.CutomSDMClasses.Item;
import sdm.engine.CutomSDMClasses.Utils;
import utils.GsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static constants.Constants.ZONE_NAME;

/**
 * input:
 *      zoneName: string
 * Output:
 *     ZoneItemsDto:
 *          isSucceed: true/false
 *          Set<ItemDto> items:
 *                id: Integer
 *                name: string
 *                purchaseCategory: string
 *                availableIn: Integer
 *                averagePrice: Double
 *                totalSells: Double
 *          errorMessage : String
 */

@WebServlet(name = "getZoneItemsServlet", urlPatterns = "/getZoneItems")
public class getZoneItemsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        String zoneNameParameter = requestData.get(ZONE_NAME).getAsString();
        Set<ItemDto> itemDtos = new HashSet<>();
        ZoneItemsDto zoneItemsDto = new ZoneItemsDto();
        try {
            Map<Integer, Item> IdToItemsInZone = engineManager.getIdToItemsByZoneName(zoneNameParameter);

            for(Item item : IdToItemsInZone.values()){
                ItemDto itemDto = new ItemDto();
                itemDto.setId(item.getID());
                itemDto.setName(item.getName());
                itemDto.setPurchaseCategory(item.getPurchaseCategory().getPurchaseCategoryStr());
                itemDto.setAvailableIn(item.getAvailableIn().size());
                itemDto.setAveragePrice(Utils.twoNumbersAfterDot(item.getAveragePrice()));
                itemDto.setTotalSells(Utils.twoNumbersAfterDot(item.getNumberOfSales()));
                itemDtos.add(itemDto);
            }
            zoneItemsDto.setItems(itemDtos);
            zoneItemsDto.setSucceed(true);
            out.append(GsonUtils.toJson(zoneItemsDto));
        } catch (IllegalArgumentException e){
            zoneItemsDto.setSucceed(false);
            zoneItemsDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(zoneItemsDto));
        } catch (Exception e){
            zoneItemsDto.setSucceed(false);
            zoneItemsDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(zoneItemsDto));
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
