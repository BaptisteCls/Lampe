const panierTitleHtml = `
<div class="title">Panier<i class="fa-solid fa-xmark" onclick="togglePanier()"></i></div>
<h2>Votre panier est vide...</h2>
`;

const panierButtonsHtml = `
<div class="buttons">
    <button onclick="togglePanier()">Continuer mon shopping</button>
    <button>Finaliser mon panier</button>
</div>
`;

const panierFogHtml = `
<div id="fog"></div>
`;

const panierHeaderHtml = `
<i class="fa-solid fa-bag-shopping" onclick="togglePanier()"></i>
`;

const panier = document.getElementById("panier");

function clearPanier(panier){
    for (let index = 0; index < panier.childNodes.length; index++) {
        const child = panier.childNodes[index];
        if(child.classList!=null && child.classList.contain("article")) panier.removeChild(child); 
    }
}

function panierItemToHtml(item){
    return `
            <div class="article">
                <img src="${item.image}" alt="">
                <div class="infos">
                    <p class="title">${item.name}</p>
                    <p>Couleur : ${item.color}</p>
                    <p>Prix unitaire : ${item.price} €</p>
                    <div class="number">
                        <i class="fa-solid fa-minus"></i>
                        <p>${item.number}</p>
                        <i class="fa-solid fa-plus"></i>
                    </div>
                </div>
            </div>
        `;
}


function getPanierToHtml(){
    return fetch('/lampe/getPanier', {
      method: 'GET',
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
    })
        .then(response => response.json())
        .then(data => data);
}

function updatePanier(panier){
    clearPanier(panier);
    panier.innerHTML = panierTitleHtml;
    console.log(getPanierToHtml());
    panier.innerHTML += panierButtonsHtml;
}

if(panier != null){
    updatePanier(panier);

    const body = document.querySelector("body");

    body.innerHTML += panierFogHtml;
    document.querySelector("header").innerHTML += panierHeaderHtml;  

    /*if(document.querySelectorAll("#panier .article").length === 0){
        document.querySelector("#panier .buttons>button:last-child").style.display = "none";
        document.querySelector("#panier h2").style.display = "block";
    }*/

    function getTranslateX(e) {
        var style = window.getComputedStyle(e);
        var matrix = new WebKitCSSMatrix(style.transform);
        return matrix.m41;
    }

    function togglePanier(){
        const panier = document.querySelector("#panier");
        const fog = document.querySelector("#fog");
        if(getTranslateX(panier) === 1000){
            panier.style.transform = "translateX(0)";
            fog.style.visibility = "visible";
            fog.style.opacity = "1";
            body.style.overflow = "hidden";
        }
        else{
            panier.style.transform = "translateX(1000px)";
            fog.style.visibility = "hidden";
            fog.style.opacity = "0";
            body.style.overflow = "auto";
        }
    }

    document.querySelector("#fog").addEventListener("click", event => {if(getTranslateX(document.querySelector("#panier"))===0) togglePanier()})
    
}
else{
    document.querySelector("header").innerHTML += '<i></i>';
}