const productList = document.querySelector('#product-list');

// Lấy danh sách sản phẩm từ API GET
fetch('http://localhost:8080/api/products')
  .then(response => response.json())
  .then(products => {
    // Tạo phần tử HTML cho mỗi sản phẩm
    products.forEach(product => {
      const productItem = document.createElement('div');
      productItem.classList.add('col-xl-4', 'col-md-4', 'col-sm-6');
      productItem.innerHTML = `
        <div class="product-item product-item2 element-item3 sidebar-left">
            <a href="#" class="product-image">
                <img src="${product.image}" alt="product-image">
            </a>
            <div class="bottom-content">
                <a href="#" class="product-name">${product.name}</a>
                <div class="action-wrap">
                    <span class="product-price">$${product.price}</span>
                    <a href="#" class="addto-cart"><i class="fal fa-shopping-cart"></i> Add To Cart</a>
                </div>
            </div>
            <div class="product-actions">
                <a href="#" class="product-action"><i class="fal fa-heart"></i></a>
                <button class="product-action product-details-popup-btn"><i class="fal fa-eye"></i></button>
            </div>
        </div>
      `;
      productList.appendChild(productItem);
    });
  });
