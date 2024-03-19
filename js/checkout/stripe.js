// Configuration de Stripe.js avec la clé publique
const stripe = Stripe('pk_live_51OieqRJ3WYZv8x07PfQncGPaXElwtouGwmREa88cLKAQDiiuf7p6Fa1juFZb3sNuRqJqwI3lZBblXCECL88rvYly00XdJdtFFf');

// Création d'un élément de carte
const elements = stripe.elements();
const cardElement = elements.create('card');

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