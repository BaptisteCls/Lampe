const isDisplayFilter = element => element.style.display === "none";

function setAnnoncesBorder(){
    annonces = document.querySelectorAll(".annonce");
    if(annonces.length === 1) annonces[0].style.borderBottom = "none";
    else if(annonces.length > 1) for(let index = 0 ; index < annonces.length-1; index ++) annonces[index].style.borderBottom = "grey solid .5px"; 
}

let annonces = document.querySelectorAll(".annonce");
annonces.forEach( element => {
    const cross = `<i class="fa-solid fa-xmark"></i>`;
    const space = `<i></i>`
    element.innerHTML = space  + element.innerHTML  + cross;
    setAnnoncesBorder();
})

const annoncesCross = document.querySelectorAll(".annonce>i:last-child");
annoncesCross.forEach( element => {
    
    element.addEventListener("click", event => {
        element.parentNode.style.display = "none"; 
        setAnnoncesBorder();
    })
})

document.querySelector("head").innerHTML += `
<style>
.annonce{
    top: 0;
    padding: 7px 15px;

    width: calc(100% - 30px);

    background-color: #e9eef1;

    display: flex;
    justify-content: space-between;
    align-items: center;

    font-family: 'Ubuntu', sans-serif;
    font-weight: bolder;
}

.annonce i{
    font-size: 20px;
    cursor: pointer;
}
</style>
`;
