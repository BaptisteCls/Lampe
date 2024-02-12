function itemToHtml(item){
    return `
    <div class="item line">
        <pre>
            <p>x ${item.number}</p>
            <p>${item.name} : ${item.color}</p>
        </pre>
        <p>${item.price} €</p>
    </div>
    `;
}

function total(items){
    let res = 0.0;
    items.forEach(item => {
        res += item.number * item.price;
    })
    return res;
}

async function init(){
    const estimate = document.querySelector(".estimate");

    await fetch("/Lampe/getPanier").then(resp => resp.json()).then(data => {
        data.forEach(item => {
            estimate.innerHTML = itemToHtml(item) + estimate.innerHTML;
        });
        document.querySelector(".estimate .total").childNodes[3].innerHTML = total(data) + ' €';
    });

    estimate.innerHTML = `<i class="fa-solid fa-box"></i>` + estimate.innerHTML;
}

init();