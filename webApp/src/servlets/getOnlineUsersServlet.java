package servlets;

import dto.UserDto;
import sdm.engine.CutomSDMClasses.EngineManager;
import sdm.engine.CutomSDMClasses.User;
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
 *     Set<UserDto>:
 *            isSucceed: true/false
 *            userName: string
 *            userRole: string
 *            errorMessage : String
 */

@WebServlet(name = "getOnlineUsersServlet", urlPatterns = "/getOnlineUsers")
public class getOnlineUsersServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        Set<UserDto> usersDtos = new HashSet<>();

        Map<Integer, User> idToUsers = engineManager.getIdToUsers();
        for(User user : idToUsers.values()){
            UserDto userDto = new UserDto();
            userDto.setUserName(user.getName());
            userDto.setUserRole(user.getClass().getSimpleName());
            userDto.setSucceed(true);
            usersDtos.add(userDto);
        }

        try (PrintWriter out = response.getWriter()){
            out.append(GsonUtils.toJson(usersDtos));
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
