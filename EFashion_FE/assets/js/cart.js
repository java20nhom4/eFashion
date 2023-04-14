_ = document.querySelector.bind(document)

async function getApi(uri, token) {
    const option = {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${ token }`,
        },
    }
    const res = await fetch(uri, option);

    const data = await res.json();

    console.log(data.data)
    return data.data
}

async function renderData() {
    const token = localStorage.getItem('token')
    cartItem = _('.order-item-js')
    const data = await getApi('http://localhost:8080/cart', token);
    cartItem.innerHTML = data.map((c) => `<tr>
        <td>
            <div class="product-thumb"><img src="assets/images/products/inner/cart/1.jpg" alt="product-thumb"></div>
        </td>
        <td>
            <div class="product-title-area">
                <span class="pretitle">Nighty</span>
                <h4 class="product-title">${c.name}</h4>
            </div>
        </td>
        <td><span class="product-price">$${c.price}</span></td>
        <td>
            <div class="cart-edit">
                <div class="quantity-edit">
                    <button class="button btn_sub" id="${c.id}"><i class="fal fa-minus minus"></i></button>
                    <input type="text" class="input" value="${c.quantity}" />
                    <button class="button plus btn_plus" id="${c.id}" >+<i class="fal fa-plus" ></i></button>
                </div>
            </div>
        </td>
        <td class="last-td"><a class="remove-btn" id="${c.id}">Remove</a></button></td>
        <td class="last-td"><a href="checkout.html" class="btn-buy" id="${c.id}">Buy now</a></td>
    </tr>`).join("")
}

renderData()


$(document).ready(function() {
    const token = localStorage.getItem('token')
    $('.remove-btn').click(function() {
        const id = $(this).attr('id')
        const This = $(this)
        $.ajax({
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${ token }`,
            },
            url: `http://localhost:8080/cart/removeProduct?id=${id}`,
        }).done(function() {
            This.closest('tr').remove()
        })
    })
})

$(document).ready(function() {
    const token = localStorage.getItem('token')
    $('.btn_sub').click(function() {
        const id = $(this).attr('id')
        console.log(id)
        $.ajax({
            method: "PUT",
            headers: {
                Authorization: `Bearer ${ token }`,
            },
            url: `http://localhost:8080/cart/subtractQuantity?id=${id}`,
        })
    })
})

$(document).ready(function() {
    const token = localStorage.getItem('token')
    $('.btn_plus').click(function() {
        const id = $(this).attr('id')

        $.ajax({
            method: "PUT",
            headers: {
                Authorization: `Bearer ${ token }`,
            },
            url: `http://localhost:8080/cart/plusQuantity?id=${id}`,

        })
        console.log(id)
    })
})
$(document).ready(function() {
    $('.btn-buy').click(function() {
        const id = $(this).attr('id')
        console.log(id)
        localStorage.setItem('cartId', id)
    })
})