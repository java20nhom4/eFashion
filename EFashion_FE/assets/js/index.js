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
                                                            href="cart.html">${data.data[i]["name"]}</a>
                                                    </li>                                         
                                                </ul>
                                </li>`
                $('#show-index').append(html)

            }
        }
    })
})

async function getEmailFromToken() {
    const token = localStorage.getItem('token')
    const option = {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${ token }`,
        },
    }
    const resToken = await fetch("http://localhost:8080/checkout?token=" + token, option);

    const dataToken = await resToken.json();

    const obj = JSON.parse(dataToken.data)

    const resUser = await fetch("http://localhost:8080/api/user/getUserByEmail?email=" + obj.principal, option);

    const dataUser = await resUser.json()
    localStorage.setItem('RoleId', dataUser.data.roleId)
    return dataUser.data
}
getEmailFromToken()