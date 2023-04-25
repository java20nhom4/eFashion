$(document).ready(function () {

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

    ('.btn-xoa-user').click(function (e) {
        e.preventDefault()
        const id = $(this).attr('id')
        const This = $(this)
        $.ajax({
            method: 'POST',
            url: `http://localhost:8080/api/users/sendEmailConfirmOTP`,
            data: {
                'id': id
            }
        }).done(function (data) {
            if (data.data) {
                alert(data.message)
                This.closest('tr').remove()
            } else {
                alert(data.message)
            }
        })
    })

})