_ = document.querySelector.bind(document)
const token = 'Bearer ' + localStorage.getItem('token')

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

function readURL(input) {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
      var bResult = false;
      reader.onload = function (e) {
        if (input.files[0].type == "image/png" || input.files[0].type == "image/gif" || input.files[0].type == "image/jpeg"){
            if (input.files[0].size <= 10097152){
                bResult = true;
                $('#imageCreateProduct').attr('src', e.target.result).width(175).height(175);
            }
        }
        if(!bResult) {
            input.value = "";
            $('#imageCreateProduct').attr('src','').width(0).height(0);
        }
      };
      reader.readAsDataURL(input.files[0]);
    }
}

async function addProduct() {
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
        formData.append('pro_name', namePro)
        formData.append('pro_price', pricePro)
        formData.append('pro_des', desPro)
        formData.append('pro_quant', quanPro)
        formData.append('pro_status', statusPro)
        formData.append('pro_cate', catePro)
        console.log(valueImage);
        
        $.ajax({
            url: 'http://localhost:8080/api/products/add',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                alert('thanh cong')
                window.location.href="admin-product-add.html"
            },
            error: function (data) {
                alert('that bai')
            },
        })
    }
}

$(document).ready(function() {
    getCategories();

    $('#btn-add-update').click(function (e) {
        e.preventDefault()
        addProduct()
    })
})