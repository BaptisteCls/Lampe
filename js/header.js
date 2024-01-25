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