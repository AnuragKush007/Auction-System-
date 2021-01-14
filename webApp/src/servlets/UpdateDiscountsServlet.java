package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.DiscountDto;
import dto.OfferDto;
import dto.OrderDiscountsDto;
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
 * input:
 *      storeName: string
 *      discountName: string
 *      thenYouGetId: integer (in 'all-or-nothing' pass thenYouGetId = 0)
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
@WebServlet(name = "UpdateDiscountsServlet", urlPatterns = "/updateDiscounts")
public class UpdateDiscountsServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        PrintWriter out = response.getWriter();
        OrderDiscountsDto orderDiscountsDto = new OrderDiscountsDto();
        Set<DiscountDto> discountDtos = new HashSet<>();

        String rawRequestData = request.getReader().lines().collect(Collectors.joining());
        JsonObject requestData = new JsonParser().parse(rawRequestData).getAsJsonObject();
        String storeNameParameter = requestData.get(STORE_NAME).getAsString();
        String discountNameParameter = requestData.get(DISCOUNT_NAME).getAsString();
        Integer thenYouGetIdParameter = requestData.get(THEN_YOU_GET_ID).getAsInt();
        Customer customer = (Customer) engineManager.getUserByName(SessionUtils.getUsername(request));
        Zone zone = engineManager.newOrder_getZone(customer);

        Store storeParameter = engineManager.getStoreByStoreNameAndZoneName(storeNameParameter, zone.getName());
        Discount discount = storeParameter.getDiscountByName(discountNameParameter);
        Offer offer = null;
        if(discount.getOperator() == DiscountOperator.ONE_OF){
            offer = storeParameter.getOfferByDiscountNameAndThenYouGetId(discountNameParameter, thenYouGetIdParameter);
        }


        synchronized (getServletContext()) {
            try {
                Map<Store, ArrayList<Discount>> discounts = engineManager.newOrder_UpdateDiscountsList(storeParameter,
                        discountNameParameter, offer, customer);
                for (Store store : discounts.keySet()) {
                    for (Discount tempDiscount : discounts.get(store)) {
                        DiscountDto discountDto = new DiscountDto();
                        discountDto.setName(tempDiscount.getName());
                        discountDto.setStoreName(store.getName());
                        discountDto.setStoreId(store.getID());
                        discountDto.setIfYouBuyId(tempDiscount.getItemId());
                        discountDto.setIfYouBuyName(engineManager.newOrder_getZone(customer).getItemById(tempDiscount.getItemId()).getName());
                        discountDto.setIfYouBuyQuantity(Utils.twoNumbersAfterDot(tempDiscount.getItemQuantity()));
                        discountDto.setOperator(tempDiscount.getOperator().getDiscountOperatorStr());
                        discountDto.setOffers(addDiscountOffers(tempDiscount, engineManager, customer));
                        discountDtos.add(discountDto);
                    }
                }
                orderDiscountsDto.setDiscounts(discountDtos);
                orderDiscountsDto.setSucceed(true);
                out.append(GsonUtils.toJson(orderDiscountsDto));
            } catch (IllegalArgumentException e) {
                orderDiscountsDto.setSucceed(false);
                orderDiscountsDto.setErrorMessage(e.getMessage());
                out.append(GsonUtils.toJson(orderDiscountsDto));
            } catch (Exception e) {
                orderDiscountsDto.setSucceed(false);
                orderDiscountsDto.setErrorMessage(e.getMessage());
                out.append(GsonUtils.toJson(orderDiscountsDto));
            }
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
