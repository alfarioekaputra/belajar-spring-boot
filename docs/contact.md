# Contact Api Spec

## Create Contact

Endpoint : POST /api/contacts

Request Header :

- Authorization: Bearer {Token} (mandatory)

Request Body :

```json
{
    "firstName": "staf",
    "lastName": "operational",
    "email": "staf@mail.com",
    "phone": "0812345678"
}
```

Response Body (Success) :

```json
{
  "data" : {
        "id": "uuid",
        "firstName": "staf",
        "lastName": "operational",
        "email": "staf@mail.com",
        "phone": "0812345678"
    }
}
```

Response Body (Failed) :

```json
{
  "errors": "Email format invalid, phone format invalid ..."
}
```

## Update Contact

Endpoint : PUT /api/contacts/{id}

Request Header :

- Authorization: Bearer {Token} (mandatory)

Request Body :

```json
{
    "firstName": "staf",
    "lastName": "operational",
    "email": "staf@mail.com",
    "phone": "0812345678"
}
```

Response Body (Success) :

```json
{
  "data" : {
        "id": "uuid",
        "firstName": "staf",
        "lastName": "operational",
        "email": "staf@mail.com",
        "phone": "0812345678"
    }
}
```

Response Body (Failed) :

```json
{
  "errors": "Email format invalid, phone format invalid ..."
}
```

## Get Contact

Endpoint : GET /api/contacts/{id}

Request Header :

- Authorization: Bearer {Token} (mandatory)

Response Body (Success) :

```json
{
  "data" : {
        "id": "uuid",
        "firstName": "staf",
        "lastName": "operational",
        "email": "staf@mail.com",
        "phone": "0812345678"
    }
}
```

Response Body (Failed) :

```json
{
  "errors": "Contact Not Found"
}
```

## Search Contact

Endpoint : GET /api/contacts

Query Param:

- name : String, contact first name or last name, using like query, optional
- phone : String, contact phone, using like query, optional
- email : String, contact email, using like query, optional
- page : Integer, start from 0, default 0
- size : Integer, default 10

Request Header :

- Authorization: Bearer {Token} (mandatory)

Response Body (Success) :

```json
{
  "data" : [
        {
        "id": "uuid",
        "firstName": "staf",
        "lastName": "operational",
        "email": "staf@mail.com",
        "phone": "0812345678"
      }
    ],
  "paging" : {
    "currentPage": 0,
    "totalPage": 10,
    "size": 10
  }
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```

## Remove Contact

Endpoint : DELETE /api/contacts/{id}

Request Header :

- Authorization: Bearer {Token} (mandatory)


Response Body (Success) :

```json
{
  "data": "OK"
}
```

Response Body (Failed) :

```json
{
  "errors": "Contact not found"
}
```