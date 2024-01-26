const panierHtml = `
<div id="panier">
    <div class="title">Panier<i class="fa-solid fa-xmark" onclick="togglePanier()"></i></div>
    <div class="article">
        <img src="./img/logo.jpg" alt="">
        <div class="infos">
            <p class="title">Titre de l'article</p>
            <p>Couleur : Bleu</p>
            <p>Prix unitaire : 65,00 €</p>
            <div class="number">
                <i class="fa-solid fa-minus"></i>
                <p>1</p>
                <i class="fa-solid fa-plus"></i>
            </div>
        </div>
    </div>
    <div class="buttons">
        <button onclick="togglePanier()">Continuer mon shopping</button>
        <button>Finaliser mon panier</button>
    </div>
</div>
<div id="fog"></div>
`;

document.querySelector("body").innerHTML += panierHtml;


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
    }
    else{
        panier.style.transform = "translateX(1000px)";
        fog.style.visibility = "hidden";
        fog.style.opacity = "0";
    }
}

document.querySelector("#fog").addEventListener("click", event => {if(getTranslateX(document.querySelector("#panier"))===0) togglePanier()})
