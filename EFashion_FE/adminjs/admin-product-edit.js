_ = document.querySelector.bind(document)
const urlParamsProduct = new URLSearchParams(window.location.search);
const productId_edit = urlParamsProduct.get("id");

async function originData(){
    const option = {
        method: "GET",
    }
    const resProduct = await fetch(`http://localhost:8080/api/products/${productId_edit}`, option);
    dataProduct = await resProduct.json()
    // Render du lieu
    console.log(dataProduct)

    if (dataProduct !== null){
        $('#nameProduct').val(dataProduct.name)
        $('#priceProduct').val(dataProduct.price)
        $('#descriptionProduct').val(dataProduct.description)
        $('#quantityProduct').val(dataProduct.quantity)
        $('#statusProduct').val(dataProduct.status)
        $('#categoryProduct').val(dataProduct.categoryId).change()
        $('#imageProduct').val(dataProduct.image)
    }
}


var dataCate = null
async function getCategories() {
    const option = {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
        
    }
    const resCate = await fetch("http://localhost:8080/api/category/getAll", option);
    dataCate = await resCate.json()
    // Render du lieu
    if (dataCate.data.length > 0){
        var x = document.getElementById("categoryProduct");
        for(var i = 0 ; i< dataCate.data.length; i++) {
            var option_cate = document.createElement("option");
            option_cate.text = dataCate.data[i].name;
            option_cate.value = dataCate.data[i].id;
            x.add(option_cate);
        }
    }
    console.log(dataCate)
}

async function editProduct() {
    var idPro = productId_edit;
    var namePro = $('#nameProduct').val();
    var catePro = $('#categoryProduct').val();
    var pricePro = $('#priceProduct').val();
    var desPro = $('#descriptionProduct').val();
    var quanPro = $('#quantityProduct').val();
    var statusPro = $('#statusProduct').val();
    var valueImage = $('#imageProduct')[0].files[0]
    if (valueImage === undefined){
        alert('Vui long insert hinh anh!')
    } else{
        var formData = new FormData()
        formData.append('image', valueImage)
        formData.append('pro_id', idPro)
        formData.append('pro_name', namePro)
        formData.append('pro_price', pricePro)
        formData.append('pro_des', desPro)
        formData.append('pro_quant', quanPro)
        formData.append('pro_status', statusPro)
        formData.append('pro_cate', catePro)
        console.log(valueImage);
        $.ajax({
            url: `http://localhost:8080/api/products/edit`,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                alert('Cập nhật thành công !');
                window.location.href="admin-product-edit.html"
            },
            error: function() {
                alert("Đã xảy ra lỗi khi cập nhật thông tin user");
            }
        })
    }
    
    
    
}

$(document).ready(function() {
    getCategories();

    originData()

    $('#btn-add-update').click(function (e) {
        e.preventDefault()
        editProduct()
    })
})