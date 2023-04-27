class Pagination {
    constructor(items, itemsPerPage) {
        this.items = items;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = 1;
    }

    get totalPages() {
        return Math.ceil(this.items.length / this.itemsPerPage);
    }

    get visibleItems() {
        const startIndex = (this.currentPage - 1) * this.itemsPerPage;
        const endIndex = startIndex + this.itemsPerPage;
        return this.items.slice(startIndex, endIndex);
    }

    goToPage(pageNumber) {
        this.currentPage = pageNumber;
    }
}

const productList = document.querySelector("#product-list");
const urlParams = new URLSearchParams(window.location.search);
const cateId = urlParams.get("cateId");
// Lấy danh sách sản phẩm từ API GET
fetch(`http://localhost:8080/api/products/getProductByCateId?cateId=${cateId}`)
    .then((response) => response.json())
    .then((products) => {
        const itemsPerPage = 9;
        const pagination = new Pagination(products, itemsPerPage);

        // Render initial page
        renderPage(pagination.visibleItems);

        // Render pagination controls
        renderPaginationControls(pagination);

        function renderPage(items) {
            // Xóa danh sách sản phẩm hiện tại
            productList.innerHTML = "";

            // Tạo phần tử HTML cho mỗi sản phẩm
            items.forEach((product) => {
                const productItem = document.createElement("div");
                productItem.classList.add("col-xl-4", "col-md-4", "col-sm-6");
                productItem.innerHTML = `
          <div class="product-item product-item2 element-item3 sidebar-left" data-id="${product.id} >
              <a href="#" class="product-image">
                <img style="width:338px; height:450px;" src="..\\eFashion_BE\\${product.image}" alt="product-image">
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
                // Bắt sự kiện click vào sản phẩm
                productItem.addEventListener("click", () => {
                    // Chuyển sang trang product details
                    window.location.href = `product-details.html?id=${product.id}`;
                });
                productList.appendChild(productItem);
            });
        }

        function renderPaginationControls(pagination) {
            const paginationContainer = document.querySelector("#pagination");
            const totalPages = pagination.totalPages;

            for (let i = 1; i <= totalPages; i++) {
                const pageButton = document.createElement("button");
                pageButton.textContent = i;
                pageButton.addEventListener("click", () => {
                    pagination.goToPage(i);
                    renderPage(pagination.visibleItems);
                });
                paginationContainer.appendChild(pageButton);
            }
        }
    });