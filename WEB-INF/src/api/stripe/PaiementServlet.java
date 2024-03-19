import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/paiement/effectuerPaiement")
public class PaiementServlet extends HttpServlet {

    private static final String STRIPE_SECRET_KEY = "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";

    static {
        Stripe.apiKey = STRIPE_SECRET_KEY;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        String json = req.getReader().readLine();

        Paiement paiement = new Gson().fromJson(json, Paiement.class);

        String stripeToken = paiement.stripeToken;
        int montant = paiement.montant; // Le montant est envoyé en centimes

        try {
            // Création des paramètres de paiement avec le token de carte Stripe
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount(Long.valueOf(montant))
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
}