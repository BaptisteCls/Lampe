// Configuration de Stripe.js avec la clé publique
const stripe = Stripe('pk_live_51OieqRJ3WYZv8x07PfQncGPaXElwtouGwmREa88cLKAQDiiuf7p6Fa1juFZb3sNuRqJqwI3lZBblXCECL88rvYly00XdJdtFFf');
const clientSecret = "pi_3OwCEmJ3WYZv8x070CsJwQnk_secret_jSjs81brWHNACnpHILzwZ2AA0";

/*
fetch('http://localhost:8080/Stripe/paiement/clientSecret')
    .then(response => {response.text})
    .then(clientSecret => {
        const appearance = {
            theme: 'stripe'
          };
        
        
        // Création d'un élément de carte
        const elements = stripe.elements({clientSecret,appearance});
        const cardElement = elements.create('payment');

        // Montrez les erreurs de validation dans le div #card-errors
        cardElement.mount('#card-element');

        // Gestion de la soumission du formulaire
        const form = document.getElementById('payment-form');
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            
            // Création d'un token avec les informations de carte saisies
            stripe.createToken(cardElement, {
                name: document.getElementById('card-holder-name').value
            }).then(function(result) {
                if (result.error) {
                    // Affichage des erreurs à l'utilisateur
                    console.error(result.error.message);
                } else {
                    console.log(result.token.id)
                    // Envoi du token à votre backend pour le traitement du paiement
                    fetch('/Stripe/paiement/effectuerPaiement', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'text/plain',
                        },
                        body: result.token.id,
                    })
                    .then(function(response) {
                        return response.json();
                    })
                    .then(function(data) {
                        console.log(data); // Traitez la réponse du serveur
                    })
                    .catch(function(error) {
                        console.error('Erreur lors de l\'envoi du token Stripe au serveur:', error);
                    });
                }
            });
        });
    })
*/


const appearance = {
    theme: 'stripe'
  };


// Création d'un élément de carte
const elements = stripe.elements({clientSecret,appearance});
const cardElement = elements.create('payment',{
    paymentMethodOrder: ['apple_pay', 'google_pay', 'card', 'paypal']
});

// Montrez les erreurs de validation dans le div #card-errors
cardElement.mount('#card-element');

// Gestion de la soumission du formulaire
const form = document.getElementById('payment-form');
form.addEventListener('submit', async function(event) {
    event.preventDefault();

    const { error } = await stripe.confirmPayment({
        elements,
        confirmParams: {
          // Make sure to change this to your payment completion page
          return_url: "http://localhost:8080/checkout.html",
        },
      });
    
      // This point will only be reached if there is an immediate error when
      // confirming the payment. Otherwise, your customer will be redirected to
      // your `return_url`. For some payment methods like iDEAL, your customer will
      // be redirected to an intermediate site first to authorize the payment, then
      // redirected to the `return_url`.
    if (error.type === "card_error" || error.type === "validation_error") {
        console.log(error.message);
    } else {
        console.log("An unexpected error occurred.");
    }
    
    /*
    // Création d'un token avec les informations de carte saisies
    stripe.createToken(cardElement, {
        name: document.getElementById('card-holder-name').value
    }).then(function(result) {
        if (result.error) {
            // Affichage des erreurs à l'utilisateur
            console.error(result.error.message);
        } else {
            console.log(result.token.id)
            // Envoi du token à votre backend pour le traitement du paiement
            fetch('/Stripe/paiement/effectuerPaiement', {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain',
                },
                body: result.token.id,
            })
            .then(function(response) {
                return response.json();
            })
            .then(function(data) {
                console.log(data); // Traitez la réponse du serveur
            })
            .catch(function(error) {
                console.error('Erreur lors de l\'envoi du token Stripe au serveur:', error);
            });
        }
    });
    */

});


