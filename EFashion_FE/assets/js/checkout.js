_ = document.querySelector.bind(document)

const token = localStorage.getItem('token')
const urlParams = new URLSearchParams(window.location.search);
const cartId = urlParams.get("id");
async function getEmailFromToken(token) {
    const option = {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${ token }`,
        },
    }
    const resToken = await fetch("http://localhost:8080/checkout?token=" + token, option);

    const dataToken = await resToken.json();

    const obj = JSON.parse(dataToken.data)

    const resUser = await fetch("http://localhost:8080/checkout/getUser?email=" + obj.principal, option);

    const dataUser = await resUser.json()

    console.log(dataUser.data)

    return dataUser.data
}
getEmailFromToken(token)

async function getId() {
    const data = await getEmailFromToken(token)
    const id = data.map((c) => c.id)
    localStorage.setItem("userId", id)
    console.log(id)
}
getId()

async function renderData() {
    userInfor = _('.userdata')
    console.log(userInfor)
    const data = await getEmailFromToken(token);
    userInfor.innerHTML = data.map((c) => `
            <form class="checkout-form">
                <div class="row" style="height:146px;">
                    <div class="col-xl-6  col-md-6">
                        <div class="input-div">
                        <label style="margin-bottom:10px;">Email:</label>
                        <input style="pointer-events:none;" type="text" value="${c.email}">
                        </div>
                    </div>
                    <div class="col-xl-6  col-md-6">
                        <div class="input-div">
                        <label style="margin-bottom:10px;">Fullname:</label>
                        <input id="fullname" type="text" value="${c.fullName}">
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-xl-6 col-md-6">
                        <div class="input-div">
                        <label style="margin-bottom:10px;">Phone Number:</label>
                        <input id="phone" type="text" value="${c.phone}">
                        </div>
                    </div>
                    <div class="col-xl-6 col-md-6">
                        <div class="input-div">
                        <label style="margin-bottom:10px;">Adress:</label>
                        <input id="address" type="text" value="${c.address}">
                        </div>
                    </div>
                </div>   
            </form>
`).join("")
}

renderData()

async function getOrderItemById() {
    const option = {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${ token }`,
        },
    }
    const res = await fetch("http://localhost:8080/checkout/getOderItems?id=" + cartId, option);

    const data = await res.json();

    console.log(data.data)
    return data.data
}


async function renderCartData() {
    cartInfor = _('.cartdata')
    const data = await getOrderItemById();
    cartInfor.innerHTML = data.map((c) => ` 
            <div class="action-item">
                <div class="action-top">
                    <span class="action-title">Product</span>
                    <span class="subtotal">Subtotal</span>
                </div>
                <div class="category-item">
                    <div class="category-item-inner">
                        <div class="category-title-area">
                            <span class="category-title">${c.name} × ${c.quantity}</span>
                        </div>
                        <div class="price">$${c.price}</div>
                    </div>
                </div>
                <div class="action-middle">
                    <span class="subtotal">Subtotal</span>
                    <span class="total-price">$${c.price}</span>
                </div>
                <div class="shipping-options checkout-options">
                    <span class="shipping">Shipping</span>
                    <form>
                        <div class="form-group">
                            <input type="checkbox" id="fltrt2">
                            <label class="check-title" for="fltrt2">Flat rate</label>
                        </div>
                        <div class="form-group">
                            <input type="checkbox" id="frsh2">
                            <label class="check-title" for="frsh2">Free shipping</label>
                        </div>
                    </form>
                </div>
                <div class="action-bottom">
                    <span class="total">Total</span>
                    <span class="total-price">$${c.price * c.quantity}</span>
                </div>
            </div>
            <div href="thank-you.html" class="place-order-btn" class="">Place Order</div>
        </div>
</div>`).join("")
    $(document).ready(function() {
        $('.place-order-btn').click(function() {
            console.log('hello')
            $(".product-details-popup-wrapper").addClass("popup")
            $(".anywere").addClass("bgshow")

            const fullname = document.getElementById("fullname").value
            const phone = document.getElementById("phone").value
            const address = document.getElementById("address").value

            const option = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${ token }`,
                },
            }
            const res = fetch("http://localhost:8080/checkout?userId=" + localStorage.getItem('userId') + "&orderItemId=" + cartId, option);
            const resUser = fetch("http://localhost:8080/checkout/updateUser?id=" + localStorage.getItem('userId') + "&phone=" + phone + "&fullname=" + fullname + "&address=" + address, option);
        })
    })
}
renderCartData()



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
                $('#show-checkout').append(html)

            }
        }
    })
})