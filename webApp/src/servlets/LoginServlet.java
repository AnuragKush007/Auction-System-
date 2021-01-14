package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import constants.Constants;
import dto.DefaultDto;
import sdm.engine.CutomSDMClasses.EngineManager;
import utils.GsonUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constants.Constants.IS_OWNER;
import static constants.Constants.USERNAME;
import static sdm.engine.CutomSDMClasses.Constants.ROLE_CUSTOMER;
import static sdm.engine.CutomSDMClasses.Constants.ROLE_STOREOWNER;

/**
 * input:
 *     "userName" : String
 *     "isOwner" : true / false
 * Output:
 *     DefaultDto :
 *          isSucceed : true/false
 *          errorMassage : string
 */

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String usernameFromSession = SessionUtils.getUsername(request);
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        DefaultDto defaultDto = new DefaultDto();
        PrintWriter out = response.getWriter();
        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();

        if (usernameFromSession == null) {
            //user is not logged in yet
            String usernameFromParameter = requestData.get(USERNAME).getAsString();
            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
                defaultDto.setSucceed(false);
                out.append(GsonUtils.toJson(defaultDto));
            } else {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim();

                synchronized (this) {
                    if (engineManager.isUserExists(usernameFromParameter)) {
                        defaultDto.setSucceed(false);
                        out.append(GsonUtils.toJson(defaultDto));
                    } else {
                        engineManager.addUser(usernameFromParameter, requestData.get(IS_OWNER).getAsString());
                        request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);
                        defaultDto.setSucceed(true);
                        out.append(GsonUtils.toJson(defaultDto));
                    }
                }
            }
        } else {
            if (!usernameFromSession.equals(requestData.get(USERNAME).getAsString().trim()) ||
                    (requestData.get(IS_OWNER).getAsString().equals(ROLE_CUSTOMER) &&
                            engineManager.getUserByName(usernameFromSession).getClass().getSimpleName().equals("StoreOwner"))
                    || (requestData.get(IS_OWNER).getAsString().equals(ROLE_STOREOWNER) &&
                    engineManager.getUserByName(usernameFromSession).getClass().getSimpleName().equals("Customer"))){
                defaultDto.setSucceed(false);
                out.append(GsonUtils.toJson(defaultDto));
            }
            else {
                //user is already logged in
                defaultDto.setSucceed(true);
                out.append(GsonUtils.toJson(defaultDto));
            }
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
