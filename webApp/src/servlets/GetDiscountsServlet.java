package servlets;

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
import java.util.*;


/**
 * input:
 *
 * Output:
 *     OrderDiscountsDto :
 *            isSucceed: true/false
 *            Set<DiscountDto> discounts:
 *                   each DiscountDto:
 *                            storeName: string
 *                            name: String
 *                            ifYouBuyId: Integer (itemId)
 *                            ifYouBuyName: string (itemName)
 *                            ifYouBuyQuantity: Double
 *                            operator: string (irrelevant/one-of/all-or-nothing)
 *                            SetOfferDto> offers:
 *                                      each OfferDto:
 *                                              thenYouGetId: Integer (itemId)
 *                                              thenYouGetName: string (itemName)
 *                                              quantity: Double
 *                                              additionalPrice: Integer (for one item!!)
 *            errorMessage: String
 */
@WebServlet(name = "GetDiscountsServlet", urlPatterns = "/getDiscounts")
public class GetDiscountsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        PrintWriter out = response.getWriter();
        OrderDiscountsDto orderDiscountsDto = new OrderDiscountsDto();
        Set<DiscountDto> discountDtos = new HashSet<>();

        Customer customer = (Customer)engineManager.getUserByName(usernameFromSession);
        try{
            synchronized (getServletContext()) {
                Map<Store, ArrayList<Discount>> discounts = engineManager.newOrder_GetDiscounts(customer);
                for (Store store : discounts.keySet()) {
                    for (Discount discount : store.getAllDiscounts()) {
                        DiscountDto discountDto = new DiscountDto();
                        discountDto.setName(discount.getName());
                        discountDto.setStoreName(store.getName());
                        discountDto.setStoreId(store.getID());
                        discountDto.setIfYouBuyId(discount.getItemId());
                        discountDto.setIfYouBuyName(engineManager.newOrder_getZone(customer).getItemById(discount.getItemId()).getName());
                        discountDto.setIfYouBuyQuantity(Utils.twoNumbersAfterDot(discount.getItemQuantity()));
                        discountDto.setOperator(discount.getOperator().getDiscountOperatorStr());
                        discountDto.setOffers(addDiscountOffers(discount, engineManager, customer));
                        discountDtos.add(discountDto);
                    }
                }
            }
            orderDiscountsDto.setDiscounts(discountDtos);
            orderDiscountsDto.setSucceed(true);
            out.append(GsonUtils.toJson(orderDiscountsDto));
        } catch (IllegalArgumentException e){
            orderDiscountsDto.setSucceed(false);
            orderDiscountsDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(orderDiscountsDto));
        } catch (Exception e){
            orderDiscountsDto.setSucceed(false);
            orderDiscountsDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(orderDiscountsDto));
        }
    }

    protected Set<OfferDto> addDiscountOffers(Discount discount, EngineManager engine, Customer customer){
        Set<OfferDto> offers = new HashSet<>();
        for(Offer offer :  discount.getOffers()){
            OfferDto offerDto = new OfferDto();
            offerDto.setThenYouGetId(offer.getItemId());
            offerDto.setAdditionalPrice(offer.getAdditionalPrice());
            offerDto.setQuantity(Utils.twoNumbersAfterDot(offer.getQuantity()));
            offerDto.setThenYouGetName(engine.newOrder_getZone(customer).getItemById(offer.getItemId()).getName());
            offers.add(offerDto);
        }
        return offers;
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
