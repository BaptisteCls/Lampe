function hideAllPart(){
    document.querySelectorAll(".part").forEach(element => {
        element.style.display = "none";
    })
}

function togglePartDisplay(number){
    const isFlex = document.querySelectorAll(".part")[number].style.display === "flex";
    hideAllPart();
    document.querySelectorAll(".part")[number].style.display = isFlex ? "none" : "flex";
}

const cats = document.querySelectorAll(".cat");
for(let index = 0; index < cats.length; index++){
    cats[index].addEventListener("click", event => {
        togglePartDisplay(index);
    })
}

document.querySelectorAll("label").forEach(element => {
    element.addEventListener("click", event =>{
        const input = document.querySelector(`input[name="${element.getAttribute("for")}"]`);
        input.checked = "true";
        input.focus();
    })
})