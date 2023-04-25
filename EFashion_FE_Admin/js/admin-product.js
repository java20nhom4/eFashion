$(document).ready(function (){
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/products/getAll`,
    }).done(function (data){
        console.log(data[1])

        if (data != null) {
            for (const i in data) {
                let stt = Number(Number(i)+1)
                const html = 
                    `<tr>
                    <td>${stt}</td>
                    <td>${data[i]["id"]}</td>
                    <td>${data[i]["name"]}</td>
                    <td>${data[i]["price"]}</td>
                    <td>${data[i]["categoryDTO"]["name"]}</td>
                    <td>${data[i]["status"]}</td>

                    <td>
                        <a href="#" class="btn btn-sm btn-primary btn-edit" id="${data[i]["id"]}">Sửa</a>
                        <a href="#" class="btn btn-sm btn-danger btn-delete" id="${data[i]["id"]}">Xóa</a>
                    </td>
                </tr>`
                $('#product-list').append(html)
            }

        }

        $('.btn-edit').click(function (e) {
            e.preventDefault()
            
            const id = $(this).attr('id')
            $.ajax({
                method: 'POST',
                url: `http://localhost:8080/api/products/postIdProductDetail`,
                data: {
                    'id': id
                }
            }).done(function (data) {
                console.log(data[1]) 
                $.ajax({
                    method:'POST',
                    url:'http://localhost:8080/api/products/update',
                    data: {
                        'name':id
                    }
                })
                // window.location.href=""
            })
        })

        $('.btn-delete').click(function (e) {
            e.preventDefault()
            const id = $(this).attr('id')
            const This = $(this)

            $.ajax({
                method: 'POST',
                url: `http://localhost:8080/api/products/delete/` + id,
                data: {
                    'id': id
                }
            }).done(function (data) {
                alert(data)
                const This = $(this)
            })
        })

        
    })

    
})