// _ = document.querySelector.bind(document)
var valueRate = 5;
const token = localStorage.getItem('token')
const userId = localStorage.getItem('userId')
const urlParams_rate = new URLSearchParams(window.location.search);
const productId_rate = urlParams_rate.get("id");

async function getRatingByProduct(pro_id) {
    const option = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        
    }

    const resRating = await fetch("http://localhost:8080/rating/product?pro_id=" + pro_id, option);

    const dataRating = await resRating.json()
    
    // Render du lieu
    if (dataRating.data.length > 0){
        var strHtml = "";
        for(var i = 0 ; i< dataRating.data.length; i++) {
            var pathImage = dataRating.data[i].image
            var splitPath = pathImage.split("\\")
            var divImage = ""
            if (splitPath[splitPath.length -1] !== 'null'){
                divImage = `<img src="..\\eFashion_BE\\${dataRating.data[i].image}" style="width: 150px !important; height: 150px !important;" alt="product-thumb" id="imageRating__${dataRating.data[i].id}">`
            }
            strHtml += `<div class="col-lg-12 col-md-12 mr-10">
            <div class="row"  style="margin-bottom: 50px;">
                    <div class="col-lg-3 col-sm-12">
                        <div class="row">
                            <div class="col-sm-3">
                                <img src="assets/images/slider/image2.jpg" alt="Avatar" class="avatar"> 
                            </div>  
                            <div class="col-sm-9" style="padding-left: 0px;">
                                <div style="font-weight: normal; color: #777777;"> ${dataRating.data[i].user_name}</div>
                                <div class="rating-icon" style="margin-top: 5px;" id="divRating__${dataRating.data[i].id}">
                                    ${RenderHTMLStar(dataRating.data[i].star)}
                                </div>
                            </div>  
                        </div>
                    </div>
                    <div class="col-lg-9 col-sm-12">  
                        <div style="font-weight: normal; color: #777777;"> 
                        ${dataRating.data[i].comment}
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-12">
            
                    </div>
                    <div class="col-lg-9 col-sm-12">` + 
                    divImage +
                    `</div>
                </div>
            </div>`
            
        }
        //<div class="product-thumb zoom" onmousemove="zoom(event)" style="background-image: url(${dataRating.data[i].image.replaceAll("\\", '/')}); width: 175px !important; height: 175px !important; margin-top: 5px;">
        //</div>
        $('#showReview').html(strHtml);
        $('#noReviewYet').css('display', 'none');
    }
    else{
        $('#noReviewYet').css('display', 'block');
    }
    
    console.log(dataRating)
}

function RenderHTMLStar(numberRating) {
    var content = ``
    var class_temp = ``
    for(var i=1; i<= 5; i++) {
        switch(i){
            case 1:
                class_temp = 'one'
                break;
            case 2:
                class_temp = 'two'
                break;
            case 3:
                class_temp = 'three'
                break;
            case 4:
                class_temp = 'four'
                break;
            default:
                class_temp = 'five'
                break;
        }

        if(i <= numberRating) {
            content+= `<span class="${class_temp}"><a href="#"> <i class="fas fa-star"></i></a></span>`
        }
        else {
            content+= `<span class="${class_temp}"><a href="#"> <i class="fal fa-star"></i></a></span>`
        }
    }
    return content
    
}

function readURL(input) {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
      var bResult = false;
      reader.onload = function (e) {
        if (input.files[0].type == "image/png" || input.files[0].type == "image/gif" || input.files[0].type == "image/jpeg"){
            if (input.files[0].size <= 10097152){
                bResult = true;
                $('#imageCreateRating').attr('src', e.target.result).width(175).height(175);
            }
        }
        if(!bResult) {
            input.value = "";
            $('#imageCreateRating').attr('src','').width(0).height(0);
        }
      };
      reader.readAsDataURL(input.files[0]);
    }
}

function ChooseStarRating() {
    $('#divCreateRating span').off('click').on('click', function(){
        var numberRating = $(this).attr('numberRating');
        valueRate = numberRating;
        $('#divCreateRating span').each(function(index, item) {
            $(this).children('i').removeClass('fal').removeClass('fa-star').removeClass('fas');
            if($(this).attr('numberRating') <= numberRating) {
                $(this).children('i').addClass('fas').addClass('fa-star');
            }
            else {
                $(this).children('i').addClass('fal').addClass('fa-star');
            }
        })
    });
}

async function addRating() {
    var valueText = $('#textCommentRating').val();
    var valueRating = valueRate;
    var valueImage = "";
    if($('#fileCreateRating').val() != "") {
        valueImage = $('#fileCreateRating')[0].files[0];
    }
    console.log(token);
    // var object_ = {file: valueImage, user_id: 1, product_id:2, star:3, comment: "sddd"};
    if (valueImage === ""){
        if (!userId){
            alert("Bạn chưa đăng nhập! Vui lòng đăng nhập")
            window.location.href = window.location
        }else{
            $.ajax({
                url: 'http://localhost:8080/rating/addNoImage',
                type: 'POST',
                // beforeSend: function (xhr) {
                //     xhr.setRequestHeader('Authorization', token);
                // },
                headers: {
                    Authorization: `Bearer ${ token }`,
                },
                data: {
                    'user_id': userId,
                    'product_id': productId_rate,
                    'star':valueRating,
                    'comment':valueText
                },
                success: function(data) {
                    console.log('thanh cong', data)
                },
                error: function (data) {
                    console.log('that bai')
                },
            }).done(function (data){
                if(data.statusCode === 400){
                    alert(data.desc)
                }else {
                    alert(data.desc)
                }
                window.location.href = window.location;
            })
        }
    }else{
        var formData = new FormData()
        formData.append('file', valueImage)
        formData.append('user_id', userId)
        formData.append('product_id', productId_rate)
        formData.append('star', valueRating)
        formData.append('comment', valueText)
        console.log(valueImage)
        if (!userId){
            alert("Bạn chưa đăng nhập! Vui lòng đăng nhập")
            window.location.href = window.location
        }else{
            $.ajax({
                url: 'http://localhost:8080/rating/add',
                type: 'POST',
                // beforeSend: function (xhr) {
                //     xhr.setRequestHeader('Authorization', token);
                // },
                headers: {
                    Authorization: `Bearer ${ token }`,
                },
                data: formData,
                contentType: false,
                processData: false,
                success: function(data) {
                    console.log('thanh cong', data)
                },
                error: function (data) {
                    console.log('that bai')
                },
            }).done(function (data){
                if(data.statusCode === 400){
                    alert(data.desc)
                }else {
                    alert(data.desc)
                }
                window.location.href = window.location;
            })
        }
    }
}

$(document).ready(function() {
    getRatingByProduct(productId_rate);
    ChooseStarRating();
})