package api.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Database;
import utils.SessionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/create-payment-session")
public class CreatePaymentSessionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialisez Stripe avec votre clé secrète
        Stripe.apiKey = "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";

        try {
            // Montant personnalisé en centimes
            long amount = getAmount(new SessionManager(request, response).getUserId());

            List<SessionCreateParams.PaymentMethodType> payment_method_types = new ArrayList<>();
            payment_method_types.add(SessionCreateParams.PaymentMethodType.CARD);

            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName("Paiement Lychna")
                .build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("eur")
                .setUnitAmount(amount)
                .setProductData(productData)
                .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(priceData)
                .setQuantity(1L)
                .build();

            // Créez une session de paiement avec les paramètres spécifiés
            SessionCreateParams createParams = new SessionCreateParams.Builder()
                        .addAllPaymentMethodType(payment_method_types)
                        .setCurrency("eur")
                        .addLineItem(lineItem)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("https://checkout.stripe.com/pay/success")
                        .setCancelUrl("https://checkout.stripe.com/pay/cancel")
                        .build();
                            

            // Exécutez la création de la session de paiement
            Session session = Session.create(createParams);


            // Récupérez le clientSecret de la session créée
            String clientSecret = session.getClientSecret();

            // Envoyez le clientSecret en réponse
            //response.setContentType("text/plain");
            //response.getWriter().write(clientSecret);
            response.getWriter().print(session.getCustomer());
        } catch (StripeException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error occurred: " + e.getMessage());
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
