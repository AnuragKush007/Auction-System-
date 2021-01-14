package servlets;

import dto.UploadFileDto;
import sdm.engine.CutomSDMClasses.EngineManager;
import utils.GsonUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static constants.Constants.*;

/**
 * input:
 *     file: xml file
 *
 * Output:
 *     UploadFileDto:
 *              isSucceed : true/false
 *              errorMessage : String
 */

@WebServlet(name = "UploadFileServlet", urlPatterns = "/uploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        UploadFileDto uploadDto = new UploadFileDto();
        String usernameFromSession = SessionUtils.getUsername(request);
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        PrintWriter out = response.getWriter();
        Part xmlPart = request.getPart(FILE);
        InputStream fileContent = xmlPart.getInputStream();
        try {
            //engineManager.addUser("dan", Constants.ROLE_STOREOWNER); //TODO: remove line
            engineManager.loadFromXmlFile(fileContent, usernameFromSession);
            uploadDto.setSucceed(true);
            out.append(GsonUtils.toJson(uploadDto));
        } catch (JAXBException e){
            uploadDto.setSucceed(false);
            uploadDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(uploadDto));
        }catch (FileNotFoundException e){
            uploadDto.setSucceed(false);
            uploadDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(uploadDto));
        }catch (Exception e) {
            uploadDto.setSucceed(false);
            uploadDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(uploadDto));
        } //TODO: handle all exceptions
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

