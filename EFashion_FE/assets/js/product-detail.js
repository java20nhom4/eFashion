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
                style="background-image: url(${product.image})"><img
                    src="${product.image}" alt="product-thumb">
            </div>
        </div>            
    </div>
    <div class="contents">
        <div class="product-status">
            <span class="product-catagory">${product.categoryId}</span>
            <div class="rating-stars-group">
                <div class="rating-star"><i class="fas fa-star"></i></div>
                <div class="rating-star"><i class="fas fa-star"></i></div>
                <div class="rating-star"><i class="fas fa-star-half-alt"></i></div>
                <span>10 Reviews</span>
            </div>
        </div>
        <h2 class="product-title">${product.name} <span class="stock">In Stock</span></h2>
        <span class="product-price"> ${product.price}</span>
        <p>
        ${product.description}
        </p>
        <div class="product-bottom-action">
            <div class="cart-edit">
                <div class="quantity-edit action-item">
                    <button class="button"><i class="fal fa-minus minus"></i></button>
                    <input type="text" class="input" value="01" />
                    <button class="button plus">+<i class="fal fa-plus plus"></i></button>
                </div>
            </div>
            <a href="cart.html" class="addto-cart-btn action-item"><i class="rt-basket-shopping"></i> Add To
                Cart</a>
            <a href="wishlist.html" class="wishlist-btn action-item"><i class="rt-heart"></i></a>
        </div>
        <div class="product-uniques">
            <span class="sku product-unipue"><span>SKU: </span> BO1D0MX8SJ</span>
            <span class="catagorys product-unipue"><span>Categories: </span> T-Shirts, Tops, Mens</span>
            <span class="tags product-unipue"><span>Tags: </span> fashion, t-shirts, Men</span>
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
  })
  .catch((error) => {
    console.log("Error at Product Details" + error);
  });
