function addToProduits(html){
    const produits = document.querySelector('.produits');
    produits.innerHTML += html;
}

function produitToHtml(produit){
    return `
        <div class="produit">
            <div class="container" style="background-image: url(${produit.image});">
                <div class="btn"><a href="${produit.id}">Ajouter au panier</a></div>
            </div>
            <div class="text">
                <p class="title">${produit.name}</p>
                <p class="prix">${produit.price}€</p>
            </div>
        </div>
    `
}

async function initProduits(){
    await fetch(`/Lampe/getItems`)
        .then(resp => resp.json())
        .then(data => {
            data.forEach(produit => {
                addToProduits(produitToHtml(produit));
            });
        })
    addToProduits(`
        <div class="voir">
            <a href="">Voir tout les produit</a>
        </div>
    `)
    document.querySelectorAll('.produits .produit .container .btn a')
        .forEach(element => {
            element.addEventListener("click", event => {
                event.preventDefault();
                async function execute(){
                    const id = element.getAttribute('href');
                    const color = "Defaut";
                    await fetch(`/Lampe/addToPanier?item=${id}&color=${color}`).then(resp => resp.text()).then(data => console.log(data));
                    togglePanier();
                }
                execute();
            })
        })
}

initProduits();