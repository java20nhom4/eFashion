$(document).ready(function (){
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/admin/getUserByEmail`,
    }).done(function (data){
        console.log(data)
        console.log(data[0].body.data["name"])
        console.log(data[1].data["fullName"])
        
        $('#nav-name').html(data[0].body.data["name"])
        $('#nav-avatar').attr('src',"../eFashion_BE/src/test/resources/eFashionFileStorage/Users/"+data[0].body.data["avatar"])

    //     $('#fullName').val(data[1].data["fullName"])
    //     $('#email-user').val(data[1].data["email"])
    //     $('#phone-user').val(data[1].data["phone"])
    //     $('#password').val(data[1].data["password"])
    //     $('#address-user').val(data[1].data["address"])
    //     $('#role-id').val(data[1].data["roleId"])
    //     $('#image-user').val(data[1].data["avatar"])
        
    })

    $('#btn-add-update').click(function (e) {
        e.preventDefault()
        
        const fullName = $('#fullName').val()
        const email = $('#email-user').val()
        const phone = $('#phone-user').val()
        const password = $('#password').val()
        const address = $('#address-user').val()
        const roleId = $('#role-id').val()
        var file = $('#image-user')[0].files[0]

        var formData = new FormData();
        formData.append('email', email);
        formData.append('phone', phone);
        formData.append('fullName', fullName);
        formData.append('address', address);
        formData.append('password', password);
        formData.append('roleId', roleId);
        formData.append('file', file);

        $.ajax({
            url: `http://localhost:8080/api/admin/add`,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                alert('Thêm thành công !');
            },
            error: function() {
                alert("Đã xảy ra lỗi khi cập nhật thông tin user");
            }
        })
    })
})