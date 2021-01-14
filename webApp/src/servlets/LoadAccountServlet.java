package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.*;
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
 * input:
 *      date : Date
 *      amount : Double
 * Output:
 *     DefaultDto:
 *          isSucceed : true/false
 *          errorMessage : string
 */

@WebServlet(name = "LoadAccountServlet", urlPatterns = "/loadAccount")
public class LoadAccountServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        PrintWriter out = response.getWriter();
        OrderDiscountsDto orderDiscountsDto = new OrderDiscountsDto();
        DefaultDto defaultDto = new DefaultDto();

        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        Double amountParameter = requestData.get(AMOUNT).getAsDouble();
        String dateAsStringFromParameter = requestData.get(DATE).getAsString();
        Date dateParameter = null;
        try {
            dateParameter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAsStringFromParameter);
        } catch (ParseException e) {
            defaultDto.setSucceed(false);
            defaultDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(defaultDto));
        }
        Customer customer = (Customer) engineManager.getUserByName(SessionUtils.getUsername(request));

        try {
            synchronized (getServletContext()) {
                engineManager.loadAccount(customer, dateParameter, amountParameter);
            }
            defaultDto.setSucceed(true);
            out.append(GsonUtils.toJson(defaultDto));
        } catch (IllegalArgumentException e) {
            defaultDto.setSucceed(false);
            defaultDto.setErrorMessage(e.getMessage());
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
