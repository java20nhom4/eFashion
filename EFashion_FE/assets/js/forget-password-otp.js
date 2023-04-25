$(document).ready(function (){
    $('#send-otp').click(function (e) {
        e.preventDefault()
        const otp = $('#confirm-otp').val()
        const password = $('#password').val()
        
        $.ajax({
            method: 'POST',
            url: `http://localhost:8080/api/user/checkOTPOfForgettingPassword`,
            data: {
                'inputOTP': otp,
                'newPassword': password
            }
        }).done(function (data){
            console.log(data)
            if(data.statusCode === 400){
                alert(data.desc)
            }else {
                alert(data.desc)
            }
        })
    })
})