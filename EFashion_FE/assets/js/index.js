$(document).ready(function() {
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/category/getAll`,
    }).done(function(data) {
        console.log(data)
        if (data.data != null) {
            for (const i in data.data) {

                let stt = Number(Number(i) + 1)
                const html = `<li class="mega-dropdown-li">
                                                <ul class="mega-dropdown-ul">
                                                    <li class="dropdown-li"><a class="dropdown-link2"
                                                            href="products.html?id=${data.data[i]["id"]}">${data.data[i]["name"]}</a>
                                                    </li>                                         
                                                </ul>
                                </li>`
                $('#show-index').append(html)

            }
        }
    })
})
_ = document.querySelector.bind(document)
if (localStorage.getItem('RoleId') == 1) {
    navItem = _('.nav__menu')
    navItem.innerHTML = `
    <li class="has-dropdown"><a class="menu-item" href="#">Clothes <i class="rt-plus"></i></a>
    <ul class="dropdown-ul mega-dropdown" id="show-index">

    </ul>
    </li>

    <li><a class="menu-item" href="contact.html">About E-Fashion</a></li>
    <li><a class="menu-item" href="contact.html">Blog</a></li>
    <li><a class="menu-item" href="admin-product.html">Manage product</a></li>
    <li><a class="menu-item" href="admin-user-table.html">Manage user</a></li>`
}