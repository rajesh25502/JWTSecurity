# JWTSecurity
springboot-token-based-authentication

Create a loacl database - and change database name in application.properties 

Endpoints to test

To signUp as (admin and user) -> http://localhost:8080/auth/signUp
body :
{
    "name":"admin",
    "email":"admin@gmail.com",
    "password":"password",
    "role":"ADMIN"
}


To sign in as (admin and user) -> http://localhost:8080/auth/signIn
body :
{
    "email":"admin@gmail.com",
    "password":"password"
}


To create new Prodct (only admin user can add) -> http://localhost:8080/admin/saveProduct
body :
{
    "name":"Oracle"
}

Add bearer token generated through admin login 


To check for user acess only ->  http://localhost:8080/user/alone (works with admin token)

To check for both (User and Admin) -> http://localhost:8080/adminUser/both

