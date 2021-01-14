package servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.DefaultDto;
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
import java.util.stream.Collectors;

import static constants.Constants.*;

/**
 * input:
 *     array feedbacks:
 *              storeName : String
 *              score : integer
 *              text : String
 * Output:
 *     DefaultDto:
 *          isSucceed : true/false
 *          errorMessage : string
 */

@WebServlet(name = "SendFeedbacksServlet", urlPatterns = "/sendFeedbacks")
public class SendFeedbacksServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        PrintWriter out = response.getWriter();
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        JsonArray feedbacksArray = requestData.get(FEEDBACKS).getAsJsonArray();
        DefaultDto answer = new DefaultDto();

        try{
            synchronized (getServletContext()) {
                Customer customer = (Customer) engineManager.getUserByName(usernameFromSession);
                for(JsonElement element : feedbacksArray){
                    JsonObject feedback = element.getAsJsonObject();
                    String storeNameParameter = feedback.get(STORE_NAME).getAsString();
                    Integer scoreParameter = feedback.get(SCORE).getAsInt();
                    String textParameter = feedback.get(TEXT).getAsString();
                    engineManager.newOrder_addFeedback(storeNameParameter, scoreParameter, textParameter, customer);
                }
            }
            answer.setSucceed(true);
            out.append(GsonUtils.toJson(answer));
        } catch (IllegalArgumentException e){
            answer.setSucceed(true);
            answer.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(answer));
        } catch (Exception e){
            answer.setSucceed(true);
            answer.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(answer));
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
