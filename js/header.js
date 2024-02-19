function toggleHeaderColor(){
    const header = document.querySelector("header");
    if(document.documentElement.scrollTop != 0) {
        header.style.backgroundColor = "white";
        header.style.color = "black";
    }
    else{
        header.style.backgroundColor = "transparent";
        header.style.color = "white";
    }
}

window.addEventListener("scroll", event => {
    toggleHeaderColor();
});

toggleHeaderColor();

function toggleMenu(){
    const menu = document.querySelector("body nav");
    const fog = document.querySelector("#fog");
    if(getTranslateX(menu) === -1000){
        menu.style.transform = "translateX(0)";
        fog.style.visibility = "visible";
        fog.style.opacity = "1";
        body.style.overflow = "hidden";
    }
    else{
        menu.style.transform = "translateX(-1000px)";
        fog.style.visibility = "hidden";
        fog.style.opacity = "0";
        body.style.overflow = "auto";
    }
}