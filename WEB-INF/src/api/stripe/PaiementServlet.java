package api.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Database;
import utils.SessionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/paiement/effectuerPaiement")
public class PaiementServlet extends HttpServlet {

    private static final String STRIPE_SECRET_KEY = "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";

    static {
        Stripe.apiKey = STRIPE_SECRET_KEY;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        String stripeToken = req.getReader().readLine();
        long montant = getAmount(new SessionManager(req, resp).getUserId());

        try {
            // Création des paramètres de paiement avec le token de carte Stripe
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount(montant)
                    .setCurrency("eur")
                    .setSource(stripeToken)
                    .setDescription("Paiement Lychna")
                    .build();

            // Création de la charge (paiement)
            Charge charge = Charge.create(params);

            // Paiement réussi
            out.println("Paiement effectué avec succès : " + charge.getId());
        } catch (StripeException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("Erreur lors du paiement : " + e.getMessage());
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
}