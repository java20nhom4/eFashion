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
                                                            href="products.html?cateId=${data.data[i]["id"]}">${data.data[i]["name"]}</a>
                                                    </li>                                         
                                                </ul>
                                </li>`
                $('#show-cart').append(html)
            }
        }
    })

    $(document).ready(function() {
        $('#send-otp').click(function(e) {
            e.preventDefault()
            const email = $('#email').val()

            $.ajax({
                method: 'POST',
                url: `http://localhost:8080/api/user/findPasswordByEmail`,
                data: {
                    'inputEmail': email
                }
            }).done(function(data) {
                if (data.statusCode === 200) {
                    window.location.href = "forget-password-otp.html"
                } else {
                    alert(data.desc)
                }
            })
        })
    })

    $(document).ready(function() {
        $('#send-otp').click(function(e) {
            e.preventDefault()
            const otp = $('#confirm-otp').val()
            const password = $('#password')

            $.ajax({
                method: 'POST',
                url: `http://localhost:8080/api/user/signup`,
                data: {
                    'inputOTP': otp
                }
            }).done(function(data) {
                if (data.statusCode === 400) {
                    alert(data.desc)
                } else {
                    alert(data.desc)
                }
            })
        })
    })
})