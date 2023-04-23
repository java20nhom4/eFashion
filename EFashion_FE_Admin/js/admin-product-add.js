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
    var valueImage = "";
    if($('#fileCreateProduct').val() != "") {
        valueImage = $('#fileCreateProduct')[0].files[0];
    }
    var formData = new FormData()
    formData.append('file', valueImage)
    formData.append('pro_name', namePro)
    formData.append('pro_price', pricePro)
    formData.append('pro_des', desPro)
    formData.append('pro_quant', quanPro)
    formData.append('pro_status', statusPro)
    formData.append('pro_cate', catePro)
    console.log(token);
    
    $.ajax({
        url: 'http://localhost:8080/api/products/add',
        type: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', token);
        },
        data: formData,
        contentType: false,
        processData: false,
        mimeType: 'multipart/form-data',
        success: function(data) {
            console.log('thanh cong', data)
        },
        error: function (data) {
            console.log('that bai')
        },
    })
}


// function readURL(input) {
//     if (input.files && input.files[0]) {
//       var reader = new FileReader();
//       var bResult = false;
//       reader.onload = function (e) {
//         if (input.files[0].type == "image/png" || input.files[0].type == "image/gif" || input.files[0].type == "image/jpeg"){
//             if (input.files[0].size <= 10097152){
//                 bResult = true;
//                 $('#imageCreateRating').attr('src', e.target.result).width(175).height(175);
//             }
//         }
//         if(!bResult) {
//             input.value = "";
//             $('#imageCreateRating').attr('src','').width(0).height(0);
//         }
//       };
//       reader.readAsDataURL(input.files[0]);
//     }
// }

// async function addRating() {
//     var valueText = $('#textCommentRating').val();
//     var valueRating = valueRate;
//     var valueImage = "";
//     if($('#fileCreateRating').val() != "") {
//         valueImage = $('#fileCreateRating')[0].files[0];
//     }
//     console.log(token);
//     // var object_ = {file: valueImage, user_id: 1, product_id:2, star:3, comment: "sddd"};
//     var formData = new FormData()
//     formData.append('file', valueImage)
//     formData.append('user_id', 1)
//     formData.append('product_id', 2)
//     formData.append('star', valueRating)
//     formData.append('comment', 'fhgÁDFASDFS')
//     console.log(valueImage);
//     $.ajax({
//         url: 'http://localhost:8080/rating/add',
//         type: 'POST',
//         // headers: {
//         //     'Content-Type':'application/json'
//         // },
//         data: formData,
//         contentType: false,
//         processData: false,
//         success: function(data) {
//             console.log('thanh cong', data)
//         },
//         error: function (data) {
//             console.log('that bai')
//         },
//     })
// }

$(document).ready(function() {
    getCategories();
})