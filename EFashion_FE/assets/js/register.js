$(document).ready(function (){
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/category/getAll`,
    }).done(function (data){
        console.log(data)
        if(data.data != null){
            for (const i in data.data){

                let stt = Number(Number(i)+1)
                const html = `<li class="mega-dropdown-li">
                                                <ul class="mega-dropdown-ul">
                                                    <li class="dropdown-li"><a class="dropdown-link2"
                                                            href="cart.html">${data.data[i]["name"]}</a>
                                                    </li>                                         
                                                </ul>
                                </li>`
                $('#show-register').append(html)

            }
        }
    })
})