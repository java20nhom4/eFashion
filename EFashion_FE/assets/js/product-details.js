if (localStorage.getItem('token') != null) {
    $(".addto-cart-btn").on('click', function() {
        location.href = 'cart.html';
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
                                                            href="products.html?id=${data.data[i]["id"]}">${data.data[i]["name"]}</a>
                                                    </li>                                         
                                                </ul>
                                </li>`
                $('#show-detail').append(html)

            }
        }
    })
})