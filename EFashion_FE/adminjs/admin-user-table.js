$(document).ready(function (){
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/admin/admin`,
    }).done(function (data){
        console.log(data.data)
        console.log(data.data["name"])
        console.log(data.data["id"])

        $('#nav-name').html(data.data["name"])
        $('#avatar-user').attr('src', "../eFashion_BE/src/test/resources/eFashionFileStorage/Users/" + data.data["avatar"]) // Khi nao run thi thay link url hinh vao

        if (data.data != null) {
            for (const i in data.data["list"]) {
                let stt = Number(Number(i)+1)
                const html = 
                    `<tr>
                    <td>${stt}</td>
                    <td>${data.data["list"][i]["fullName"]}</td>
                    <td>${data.data["list"][i]["email"]}</td>
                    <td>${data.data["list"][i]["roleDTO"]["name"]}</td>
                    <td>
                        <a href="#" class="btn btn-sm btn-primary btn-edit" id="${data.data["list"][i]["email"]}">Sửa</a>
                        <a href="#" class="btn btn-sm btn-danger btn-delete" id="${data.data["list"][i]["id"]}">Xóa</a>
                    </td>
                </tr>`
                $('#user-list').append(html)
            }

            $('.btn-edit').click(function (e) {
                e.preventDefault()
                const emailUser = $(this).attr('id')

                $.ajax({
                    method: 'POST',
                    url: `http://localhost:8080/api/admin/postIdUser`,
                    data: {
                        'emailUser': emailUser
                    }
                }).done(function (data){
                    window.location.href="admin-user-edit.html"
                })
            })

            $('.btn-delete').click(function (e) {
                e.preventDefault()
                const id = $(this).attr('id')
                const This = $(this)

                $.ajax({
                  type: 'DELETE',
                  crossDomain: true,
                  url: 'http://localhost:8080/api/user/delete/' + id,
                  success: function(data) {
                    console.log(data.data)
                    if (data.data) {
                        alert('Xoá thành công')
                        This.closest('tr').remove()
                    } else {
                        alert('Xoá thất bại')
                    }
                  }
                })
            })
        }
    })

    $('#user-add').click(function (e) {
        e.preventDefault()

        $.ajax({
            method: 'POST',
            url: `http://localhost:8080/api/admin/postIdAdmin`,
            data: {
                'id': localStorage.getItem('userId')
            }
        }).done(function (data){
            window.location.href="admin-user-add.html"
        })
    })
    
})