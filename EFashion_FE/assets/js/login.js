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

const token = localStorage.getItem('token')

async function getEmailFromToken(token) {
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



$(document).ready(function() {

    $('#sunmit-login').click(function() {
        const username = document.getElementById("email").value
        const password = document.getElementById("password").value
        getToken(username, password)
        getEmailFromToken(token)
    })
})