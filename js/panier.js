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
const body = document.querySelector("body");


function clearPanier(panier){
    document.querySelectorAll("#panier .article").forEach(item =>{
        item.parentNode.removeChild(item);
    })
}


function panierItemToHtml(item){
    return `
            <div class="article" name="item=${item.id}&color=${item.color}">
                <img src="${item.image}" alt="">
                <div class="infos">
                    <p class="title">${item.name}</p>
                    <p>Couleur : ${item.color}</p>
                    <p>Prix unitaire : ${item.price} €</p>
                    <div class="number">
                        <i class="fa-solid fa-minus" onclick="decrement(this)"></i>
                        <p>${item.number}</p>
                        <i class="fa-solid fa-plus" onclick="increment(this)"></i>
                    </div>
                </div>
            </div>
        `;
}

async function reloadPanier(){
    const panier = document.getElementById("panier");
    panier.innerHTML = panierTitleHtml;
    await fetch("/Lampe/getPanier").then(resp => resp.json()).then(data => data.forEach(element => {
        panier.innerHTML += panierItemToHtml(element);
    }));
    panier.innerHTML += panierButtonsHtml;

    reloadPanierIsVide();
}

function reloadPanierIsVide(){
    if(document.querySelectorAll("#panier .article").length === 0){
        document.querySelector("#panier .buttons>button:last-child").style.display = "none";
        document.querySelector("#panier h2").style.display = "block";
    }
}

async function decrement(i){
    item = i.parentNode.parentNode.parentNode;
    await fetch(`/Lampe/removeFromPanier?${item.getAttribute("name")}`);
    numberElement = i.parentNode.childNodes[3];
    number = numberElement.innerHTML;
    if(number > 1) numberElement.innerHTML = number - 1 ;
    else{ 
        item.parentNode.removeChild(item);
        reloadPanierIsVide();
    }
}

async function increment(i){
    item = i.parentNode.parentNode.parentNode;
    await fetch(`/Lampe/addToPanier?${item.getAttribute("name")}`);
    i.parentNode.childNodes[3].innerHTML = parseInt(i.parentNode.childNodes[3].innerHTML) +1;
}

function getTranslateX(e) {
    var style = window.getComputedStyle(e);
    var matrix = new WebKitCSSMatrix(style.transform);
    return matrix.m41;
}

async function togglePanier(){
    const panier = document.querySelector("#panier");
    const fog = document.querySelector("#fog");
    if(getTranslateX(panier) === 1000){
        reloadPanier();
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

async function init(){
    await reloadPanier();

    body.innerHTML += panierFogHtml;
    document.querySelector("header").innerHTML += panierHeaderHtml; 

    document.querySelector("#fog").addEventListener("click", event => {if(getTranslateX(document.querySelector("#panier"))===0) togglePanier()})
}

if(panier != null){
    init();
}
else{
    document.querySelector("header").innerHTML += '<i></i>';
} 
