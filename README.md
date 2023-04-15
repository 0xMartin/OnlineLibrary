![OnlineLibrary](https://socialify.git.ci/0xMartin/OnlineLibrary/image?description=1&forks=1&issues=1&language=1&name=1&owner=1&stargazers=1&theme=Light)

Online library web application (Java Spring + MongoDB + Thymeleaf). Require Java JRE & JDK 17. 

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

## ERD diabram
<img src="./dokumentace/erd_diagram/erd_diagram.png">

## View hierarchy diagram
[Wireframe navrhy ke vsem view](./dokumentace/view)

<img src="./dokumentace/view/view_diagram.png">

## Základní nahled do aplikace
> Katalog knih
<img src="./dokumentace/catalog.png">

> Info o knize
<img src="./dokumentace/info.png">

> Profil uživatel
<img src="./dokumentace/profile.png">

> Admin - books
<img src="./dokumentace/admin.png">

## Authors:
Martin Krčma, Pavel Ševčík, Eliška Obadalová
