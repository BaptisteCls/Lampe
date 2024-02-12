function hideAllPart(){
    document.querySelectorAll(".part").forEach(element => {
        element.style.display = "none";
    })
}

function togglePartDisplay(number){
    if(!beforeIsSubmit(number)){
        togglePartDisplay(number-1);
    }
    else{
        const isFlex = document.querySelectorAll(".part")[number].style.display === "flex";
        hideAllPart();
        document.querySelectorAll(".part")[number].style.display = isFlex ? "none" : "flex";
        removeCheckAfter(number);
    }
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

function isSubmit(number){
    return cats[number].querySelector("i").classList.contains("fa-check");
}

function beforeIsSubmit(number){
    if(number==0) return true;
    return isSubmit(number-1);
}

function removeCheckAfter(number){
    for (let index = number; index < cats.length; index++) {
        cats[index].querySelector("i").classList.remove("fa-check");
    }
}

const forms = document.querySelectorAll(".part form");
for (let index = 0; index < forms.length; index++) {
    const form = forms[index];
    form.addEventListener("submit", event => {
        event.preventDefault();
        cats[index].querySelector("i").classList.add("fa-check");
        togglePartDisplay(index+1);
    })
}

togglePartDisplay(0);