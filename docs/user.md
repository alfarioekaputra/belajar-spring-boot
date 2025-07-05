# User Api Spec

## Register User

Endpoint : POST /api/users

Request Body :

```json
{
    "username": "staf",
    "password": "rahasia",
    "name": "User Staf"
}
```

Response Body (Success) :

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "erros: "Username must not blank ?"
}
```

## Login User

Endpoint : POST /api/auth/login

Request Body :

```json
{
    "username": "staf",
    "password": "rahasia",
}
```

Response Body (Success) :

```json
{
  "data" : {
    "token": "TOKEN",
    "expiredAt": 123123123 // millisecond
  }
}
```

Response Body (Failed) :

```json
{
  "erros: "Username or password wrong"
}
```

## Get User

Endpoint : GET /api/user/current

Request Header :

- Authorization: Beare {Token} (mandatory)


Response Body (Success) :

```json
{
  "data" : {
    "username": "staf",
    "name": "User Staf"
  }
}
```

Response Body (Failed) :

```json
{
  "erros: "Unauthorized"
}
```

## Update User

Endpoint : PATCH /api/user/current

Request Header :

- Authorization: Beare {Token} (mandatory)

Request Body :

```json
{
    "name": "User Staf", //put if only want to update name
    "password": "rahasia", //put if only want to update password
}

Response Body (Success) :

```json
{
  "data" : {
    "username": "staf",
    "name": "User Staf"
  }
}
```

Response Body (Failed) :

```json
{
  "erros: "Unauthorized"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- Authorization: Beare {Token} (mandatory)

Response Body (Success) :

```json
{
  "data: "OK"
}
```