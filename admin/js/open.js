const navElements = document.querySelectorAll("nav>p");
const contentElements = document.querySelectorAll("#content>div");

navElements.forEach( element => {
    element.addEventListener("click", event => {
        contentElements.forEach( content => {
            if(element.textContent.toLowerCase() === content.id) content.style.display = "block";
            else content.style.display = "none";
        })
    })
})