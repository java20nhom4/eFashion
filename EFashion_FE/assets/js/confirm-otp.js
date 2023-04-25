$(document).ready(function (){
    $('#send-otp').click(function (e) {
        e.preventDefault()
        const otp = $('#confirm-otp').val()
        
        $.ajax({
            method: 'POST',
            url: `http://localhost:8080/api/user/signup`,
            data: {
                'inputOTP': otp
            }
        }).done(function (data){
            if(data.statusCode === 400){
                alert(data.desc)
            }else {
                alert(data.desc)
            }
        })
    })
})