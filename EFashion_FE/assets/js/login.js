async function getToken(username, password) {
    const option = {
        method: "POST"
    }
    const res = await fetch("http://localhost:8080/api/login/signin?username=" + username + "&password=" + password, option);

    const data = await res.json();

    localStorage.setItem('token', data.data)
    console.log("localStorage: " + localStorage.getItem('token'))
    return data.data

}


$(document).ready(function() {

    $('#sunmit-login').click(function() {
        const username = document.getElementById("email").value
        const password = document.getElementById("password").value
        getToken(username, password)
    })
})