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