// const productId = window.location.pathname.split('/').pop();
const urlParams = new URLSearchParams(window.location.search);
const productId = urlParams.get("id");

fetch(`http://localhost:8080/api/products/${productId}`)
    .then((response) => response.json())
    .then((product) => {
        const productDetails = document.getElementById("product-details");
        productDetails.innerHTML = `
    <div class="details-product-area mb--70">
    <div class="product-thumb-area">
        <div class="cursor"></div>
        <div class="thumb-wrapper one filterd-items figure">
            <div class="product-thumb zoom" onmousemove="zoom(event)"
                style="background-image: url(..\\eFashion_BE\\${product.image})">
                <img style=" width:440px; height:500px"src="..\\eFashion_BE\\${product.image}" alt="product-thumb">
            </div>
        </div>            
    </div>
    <div class="contents">
        <div class="product-status">
            <div class="rating-stars-group">
                <div class="rating-star"><i class="fas fa-star"></i></div>
                <div class="rating-star"><i class="fas fa-star"></i></div>
                <div class="rating-star"><i class="fas fa-star-half-alt"></i></div>
                <span>10 Reviews</span>
            </div>
        </div>
        <h2 class="product-title">${product.name} <span class="stock">In Stock</span></h2>
        <span class="product-price">$ ${product.price}</span>
        <p>
        ${product.description}
        </p>
        <div class="product-bottom-action">
            <div style="cursor:pointer;" class="addto-cart-btn action-item"><i class="rt-basket-shopping"></i> Add To
                Cart</div>
          
        </div>
        <div class="product-uniques">
            <span class="catagorys product-unipue"><span>Categories: </span> ${product.categoryId}</span>
        </div>
        <div class="share-social">
            <span>Share:</span>
            <a class="platform" href="http://facebook.com" target="_blank"><i
                    class="fab fa-facebook-f"></i></a>
            <a class="platform" href="http://twitter.com" target="_blank"><i class="fab fa-twitter"></i></a>
            <a class="platform" href="http://behance.com" target="_blank"><i class="fab fa-behance"></i></a>
            <a class="platform" href="http://youtube.com" target="_blank"><i class="fab fa-youtube"></i></a>
        </div>
    </div>
</div>
  `;
        if (localStorage.getItem('token') != null) {
            $(".addto-cart-btn").on('click', function() {
                const token = localStorage.getItem('token')
                const userId = localStorage.getItem('userId')
                console.log(userId)
                const option = {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${ token }`,
                    },
                }
                fetch(`http://localhost:8080/cart?proId=${productId}&userId=` + userId + ``, option).then(location.href = "cart.html");


            });

        } else {
            $(".addto-cart-btn").on('click', function() {
                $(".product-details-popup-wrapper").addClass("popup")
                $(".anywere").addClass("bgshow")
            });
            $(".product-bottom-action .view-btn").on('click', function() {
                $(".product-details-popup-wrapper").addClass("popup")
                $(".anywere").addClass("bgshow")
            });
            $(".product-details-popup-wrapper .cart-edit").on('click', function() {
                $(".product-details-popup-wrapper").addClass("popup")
                $(".anywere-home").addClass("bgshow")
            });
            $(".anywere").on('click', function() {
                $(".product-details-popup-wrapper").removeClass("popup")
                $(".anywere").removeClass("bgshow")
            });
        }

    })
    .catch((error) => {
        console.log("Error at Product Details" + error);
    });



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
                $('#show-detail').append(html)

            }
        }
    })
})