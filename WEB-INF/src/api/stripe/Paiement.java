public class Paiement {
    String stripeToken;
    int montant;

    public Paiement(String token, int montant) {
        this.stripeToken = token;
        this.montant = montant;
    }

    public Paiement(){};    
}
