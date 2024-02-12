const commande = {};

function fillCommande(){
    forms.forEach(form => {
        form.querySelectorAll("input:not(input[type=submit]), textarea, select").forEach(input =>{
            commande[input.getAttribute("name")] = input.value ;
        })
    })
}

fillCommande();
console.log(commande);

