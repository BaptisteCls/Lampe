package api.stripe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Database;
import utils.SessionManager;

@WebServlet("/clientSecret")
public class CreatePaiementSecrete extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Stripe.apiKey = "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";

            PrintWriter out = resp.getWriter();

            long amount = getAmount(new SessionManager(req, resp).getUserId());

            PaymentIntentCreateParams params = PaymentIntentCreateParams
                    .builder()
                    .setAmount(51L)
                    .setCurrency("eur")
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            
            resp.setContentType("text/plain");
            out.println(paymentIntent.getClientSecret());

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error occurred: " + e.getMessage());
        }
    }

    private long getAmount(long userId){
        try ( Connection con = Database.getConnection("website")) {
            Statement stmt = con.createStatement();
            String query = "select sum(price) from panier p inner join items i on p.ino = i.ino where uno = "+userId;
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return rs.getLong(1);
            }
        } catch (Exception e) {}
        return 0;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
