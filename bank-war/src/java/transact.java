
import bankexamp.BankTransactLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ayuna Sridevi Samosi
 */
public class transact extends HttpServlet {
    BankTransactLocal bankTransact = lookupBankTransactLocal();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           String s =  request.getParameter("transaction");
           int amount = Integer.parseInt(request.getParameter("tl"));
           
           if (s.equals("deposit"))
           {
              bankTransact.deposit(amount);
              if (amount>2000) out.println(amount+500+" Deposit Berhasil!" );
              else  out.println(amount+" Deposit Berhasil!" );
              
           }
          if (s.equals("withdraw"))
        {
            out.println(amount+" Penarikan Berhasil!, Sisa Tabungan Anda "+bankTransact.withdraw(amount));
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

    private BankTransactLocal lookupBankTransactLocal() {
        try {
            Context c = new InitialContext();
            return (BankTransactLocal) c.lookup("java:global/bank/bank-ejb/BankTransact!bankexamp.BankTransactLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
