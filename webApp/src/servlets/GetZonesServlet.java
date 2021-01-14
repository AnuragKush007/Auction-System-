package servlets;

import dto.ZoneDto;
import sdm.engine.CutomSDMClasses.EngineManager;
import sdm.engine.CutomSDMClasses.Utils;
import sdm.engine.CutomSDMClasses.Zone;
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

/**
 * input:
 *
 * Output:
 *     Set<ZoneDto> :
 *            isSucceed: true/false
 *            zoneName: string
 *            zoneOwner: string
 *            availableItems: Integer
 *            availableStores: Integer
 *            totalOrders: Integer
 *            averageOrdersPrice: Double
 *            errorMessage : String
 */

@WebServlet(name = "GetZonesServlet", urlPatterns = "/getZones")
public class GetZonesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        Set<ZoneDto> zoneDtos = new HashSet<>();


        Map<String, Zone> nameToZones = engineManager.getNameToZone();
        for(Zone zone : nameToZones.values()){
            ZoneDto zoneDto = new ZoneDto();
            zoneDto.setZoneName(zone.getName());
            zoneDto.setZoneOwner(zone.getOwner().getName());
            zoneDto.setAvailableItems(zone.getNumOfItems());
            zoneDto.setAvailableStores(zone.getNumOfStores());
            zoneDto.setTotalOrders(zone.getNumOfOrders());
            zoneDto.setAverageOrdersPrice(Utils.twoNumbersAfterDot(zone.getAverageOrdersPrice()));
            zoneDto.setSucceed(true);
            zoneDtos.add(zoneDto);
        }
        try (PrintWriter out = response.getWriter()){
            out.append(GsonUtils.toJson(zoneDtos));
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
