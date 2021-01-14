package servlets;

import dto.AccountDto;
import dto.TransactionDto;
import sdm.engine.CutomSDMClasses.Account;
import sdm.engine.CutomSDMClasses.EngineManager;
import sdm.engine.CutomSDMClasses.Transaction;
import sdm.engine.CutomSDMClasses.Utils;
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
import java.util.Map;
import java.util.Set;

/**
 * input:
 *
 * Output:
 *     AccountDto :
 *            isSucceed: true/false
 *            balance: Double
 *            Set<TransactionDto> transactions:
 *                   each TransactionDto:
 *                              type: string (load/ credit/ debit)
 *                              date: Date
 *                              amount: Double
 *                              balanceBefore: Double
 *                              balanceAfter: Double
 *            errorMessage: String
 */
@WebServlet(name = "getAccountServlet", urlPatterns = "/getAccount")
public class getAccountServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EngineManager engineManager = UtilsServlet.getEngineManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        PrintWriter out = response.getWriter();
        AccountDto accountDto = new AccountDto();
        Set<TransactionDto> transactionsDtos = new HashSet<>();

        try{
            synchronized (getServletContext()) {
                Account account = engineManager.getAccountByUserName(usernameFromSession);
                Set<Transaction> transactions = account.getTransactions();
                accountDto.setBalance(Utils.twoNumbersAfterDot(account.getBalance()));
                for (Transaction transaction : transactions) {
                    TransactionDto transactionDto = new TransactionDto();
                    transactionDto.setType(transaction.getType().getTransactionTypeStr());
                    transactionDto.setDate(transaction.getDate());
                    transactionDto.setAmount(Utils.twoNumbersAfterDot(transaction.getAmount()));
                    transactionDto.setBalanceAfter(Utils.twoNumbersAfterDot(transaction.getBalanceAfter()));
                    transactionDto.setBalanceBefore(Utils.twoNumbersAfterDot(transaction.getBalanceBefore()));
                    transactionsDtos.add(transactionDto);
                }
                accountDto.setTransactions(transactionsDtos);
                accountDto.setSucceed(true);
                out.append(GsonUtils.toJson(accountDto));
            }
        } catch (IllegalArgumentException e){
            accountDto.setSucceed(false);
            accountDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(accountDto));
        } catch (Exception e){
            accountDto.setSucceed(false);
            accountDto.setErrorMessage(e.getMessage());
            out.append(GsonUtils.toJson(accountDto));
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
