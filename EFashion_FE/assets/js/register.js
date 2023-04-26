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
                $('#show-register').append(html)

            }
        }
    })

    $('#sign-up-user').click(function(e) {
        const email = $('#username').val()
        const password = $('#password').val()
        const fullName = $('#fullname').val()
        const phone = $('#phone').val()
        const address = $('#address').val()
        var avatar = $('#avatar')[0].files[0]

        var formData = new FormData();
        formData.append('email', email);
        formData.append('password', password);
        formData.append('fullName', fullName);
        formData.append('phone', phone);
        formData.append('address', address);
        formData.append('avatar', avatar);
        e.preventDefault()
        $.ajax({
            url: `http://localhost:8080/api/user/sendEmailConfirmOTP`,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                window.location.href = "confirm-otp.html"
            },
            error: function() {
                alert("Đã xảy ra lỗi khi cập nhật thông tin user");
            }
        })
    })
})