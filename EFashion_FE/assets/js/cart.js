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
    const userId = localStorage.getItem('userId')
    const token = localStorage.getItem('token')
    cartItem = _('.order-item-js')
    const data = await getApi(`http://localhost:8080/cart?userId=` + userId + ``, token);
    cartItem.innerHTML = data.map((c) => `<tr>
        <td>
            <div class="product-thumb"><img style="width:130px;" src="..\\eFashion_BE\\${c.image}" alt="product-thumb"></div>
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
                    <button class="button btn_sub" id="${c.productId}"><i class="fal fa-minus minus"></i></button>
                    <input type="text" class="input" value="${c.quantity}" />
                    <button class="button btn_plus" id="${c.productId}" >+<i class="fal fa-plus" ></i></button>
                </div>
            </div>
        </td>
        <td class="last-td"><a class="remove-btn" id="${c.productId}">Remove</a></button></td>
        <td class="last-td"><a href="checkout.html?id=${c.id}" class="btn-buy" id="">Buy now</a></td>
    </tr>`).join("")
}

renderData()


const userId = localStorage.getItem('userId')

$(document).ready(function() {
    const token = localStorage.getItem('token')
    $('.remove-btn').on('click', function() {
        const productId = $(this).attr('id')
        const This = $(this)
        $.ajax({
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${ token }`,
            },
            url: `http://localhost:8080/cart/removeProduct?proId=${productId}&userId=` + userId + ``,
        }).done(function() {
            This.closest('tr').remove()
        })
    })
})

$(document).ready(function() {
    const token = localStorage.getItem('token')
    $('.btn_sub').on('click', function() {
        const productId = $(this).attr('id')
        $.ajax({
            method: "PUT",
            headers: {
                Authorization: `Bearer ${ token }`,
            },
            url: `http://localhost:8080/cart/subtractQuantity?proId=${productId}&userId=` + userId + ``,
        })
    })
})

$(document).ready(function() {
    const token = localStorage.getItem('token')
    $('.btn_plus').on('click', function() {
        const productId = $(this).attr('id')

        $.ajax({
            method: "PUT",
            headers: {
                Authorization: `Bearer ${ token }`,
            },
            url: `http://localhost:8080/cart/plusQuantity?proId=${productId}&userId=` + userId + ``,

        })
    })
})

$(document).ready(function() {
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/category/getAll`,
    }).done(function(data) {
        console.log(data)
        if (data.data != null) {
            for (const i in data.data) {

                let stt = Number(Number(i) + 1)
                const html = `<li class="mega-dropdown-li">
                                                <ul class="mega-dropdown-ul">
                                                    <li class="dropdown-li"><a class="dropdown-link2"
                                                            href="products.html?cateId=${data.data[i]["id"]}">${data.data[i]["name"]}</a>
                                                    </li>                                         
                                                </ul>
                                </li>`
                $('#show-cart').append(html)

            }
        }
    })
})