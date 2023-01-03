# databaze pro aplikaci
use libraryapp

# vytvoreni uzivatele ktery bude v databazi provadet zmeny
db.createUser(
  {
    user: "LibraryUser",
    pwd:  "WmdOpdSs9YHiGShT3f00",  
    roles: [ { role: "readWrite", db: "libraryapp" } ]
  }
)