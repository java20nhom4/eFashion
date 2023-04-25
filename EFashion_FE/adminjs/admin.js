$(document).ready(function (){
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/admin/admin`,
    }).done(function (data){
        console.log(data.data)
        console.log(data.data["name"])

        if (data.data != null) {
            $('#user-name').html(data.data["name"])
            $('#user-avatar').attr('src',"plugins/images/users/"+data.data["avatar"])
            $('#total-user').html(data.data["totalUsers"])
            $('#total-product').html(data.data["totalProducts"])
            $('#total-order').html(data.data["totalOrder"])
        }
        // if(data.data != null){
        //     for (const i in data.data){

        //         let stt = Number(Number(i)+1)
        //         const html = `<li class="mega-dropdown-li">
        //                                         <ul class="mega-dropdown-ul">
        //                                             <li class="dropdown-li"><a class="dropdown-link2"
        //                                                     href="products.html">${data.data[i]["name"]}</a>
        //                                             </li>                                         
        //                                         </ul>
        //                         </li>`
        //         $('#show-category').append(html)

        //         const html1 = `
        //                             <div class="category-item-inner">
        //                                 <div class="category-title-area">
        //                                     <span class="point"></span>
        //                                     <span class="category-title">${data.data[i]["name"]}</span>
        //                                 </div>
        //                             </div>
        //                         `
        //         $('#show-category1').append(html1)

        //     }
        // }
    })
})
