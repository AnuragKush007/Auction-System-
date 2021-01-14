package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.FeedbackDto;
import dto.FeedbacksDto;
import sdm.engine.CutomSDMClasses.EngineManager;
import sdm.engine.CutomSDMClasses.Feedback;
import sdm.engine.CutomSDMClasses.StoreOwner;
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
 *      zoneName : string
 * Output:
 *      FeedbacksDto:
 *          isSucceed : true/false
 *          Set<FeedbackDto> feedbacks:
 *                      storeName : String
 *                      customerName : string
 *                      date : Date
 *                      score : integer
 *                      text : String
 *          errorMessage : string
 */

@WebServlet(name = "GetFeedbacksByStoreOwnerServlet", urlPatterns = "/getFeedbacksByStoreOwner")
public class GetFeedbacksByStoreOwnerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        PrintWriter out = response.getWriter();
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        String zoneName = requestData.get(ZONE_NAME).getAsString();
        FeedbacksDto feedbacksDto = new FeedbacksDto();
        Set<Feedback> feedbacks = new HashSet<>();

        try{
            synchronized (getServletContext()) {
                StoreOwner storeOwner = (StoreOwner) engineManager.getUserByName(SessionUtils.getUsername(request));
                feedbacks = engineManager.getFeedbacksByStoreOwnerNameAndZoneName(usernameFromSession, zoneName);
            }
            if(!feedbacks.isEmpty()) {
                Set<FeedbackDto> feedbackDtoSet = new HashSet<>();
                for (Feedback feedback : feedbacks) {
                    FeedbackDto feedbackDto = new FeedbackDto();
                    feedbackDto.setStoreName(feedback.getStoreName());
                    feedbackDto.setCustomerName(feedback.getCustomerName());
                    feedbackDto.setDate(feedback.getDate());
                    feedbackDto.setScore(feedback.getScore());
                    feedbackDto.setText(feedback.getText());
                    feedbackDtoSet.add(feedbackDto);
                }
                feedbacksDto.setFeedbacks(feedbackDtoSet);
                feedbacksDto.setSucceed(true);
                out.append(GsonUtils.toJson(feedbacksDto));
            }else {
                feedbacksDto.setSucceed(false);
                out.append(GsonUtils.toJson(feedbacksDto));
            }
        } catch (IllegalArgumentException e){
            feedbacksDto.setSucceed(false);
            feedbacksDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(feedbacksDto));
        } catch (Exception e){
            feedbacksDto.setSucceed(false);
            feedbacksDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(feedbacksDto));
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
