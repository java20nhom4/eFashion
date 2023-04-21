_ = document.querySelector.bind(document)
if (localStorage.getItem('RoleId') == 1) {
    navItem = _('.nav__menu')
    navItem.innerHTML = `<li><a class="menu-item" href="contact.html">About E-Fashion</a></li>`
} else {
    location.href('page403.html')
}