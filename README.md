![AP7PD_projekt](./dokumentace/AP7PD_projekt.png)

> Aplikace vyžaduje Java JRE & JDK 17

## Nastavení databaze
1) Instalace Mongo DB https://www.mongodb.com/docs/manual/installation/
```
mongosh
```

2) Zvolení databaze
```
use libraryapp
```

3) Vytvoření uživatele pro aplikaci
```
db.createUser(
  {
    user: "LibraryUser",
    pwd:  "WmdOpdSs9YHiGShT3f00",  
    roles: [ { role: "readWrite", db: "libraryapp" } ]
  }
)
```

> Java Spring + MongoDB + Thymeleaf

## ERD diabram
<img src="./dokumentace/erd_diagram/erd_diagram.png">

## View hierarchy diagram
[Wireframe navrhy ke vsem view](./dokumentace/view)

<img src="./dokumentace/view/view_diagram.png">

## Views
> Login

<img src="./dokumentace/view/login/login.png" width="80%">

> Register

<img src="./dokumentace/view/register/register.png" width="80%">

> Navigation bar

<img src="./dokumentace/view/navigation_bar/navigation_bar.png" width="80%">

> Book catalog (home)

<img src="./dokumentace/view/book_catalog/book_catalog.png" width="80%">

> Profile

<img src="./dokumentace/view/profile/profile.png" width="80%">

> Chane password

<img src="./dokumentace/view/change_password/change_password.png" width="80%">

> Edit user/proofile

<img src="./dokumentace/view/user_edit/user_edit.png" width="80%">

> Admin

<img src="./dokumentace/view/admin/admin.png" width="80%">

> Create/Edit book

<img src="./dokumentace/view/book_edit/book_edit.png" width="80%">
