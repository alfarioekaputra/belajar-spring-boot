# Contact Api Spec

## Create Contact

Endpoint: POST /api/contacts/{idContact}/addresses

Request Header :

- Authorization: Bearer {Token} (mandatory)

Request Body :

```json
{
    "street": "staf",
    "city": "operational",
    "province": "staf@mail.com",
    "country": "0812345678",
    "postalCode": "12345"
}
```

Response Body (Success) :

```json
{
  "data" : {
        "id": "uuid",
        "street": "staf",
        "city": "operational",
        "province": "staf@mail.com",
        "country": "0812345678",
        "postalCode": "12345"
    }
}
```

Response Body (Failed) :

```json
{
  "errors": "Contact not found"
}
```

## Update Contact

Endpoint : PUT /api/contacts/{idContact}/addresses/{idAddresses}

Request Header :

- Authorization: Bearer {Token} (mandatory)

Request Body :

```json
{
    "street": "staf",
    "city": "operational",
    "province": "staf@mail.com",
    "country": "0812345678",
    "postalCode": "12345"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id": "uuid",
    "street": "staf",
    "city": "operational",
    "province": "staf@mail.com",
    "country": "0812345678",
    "postalCode": "12345"
  }
}
```

Response Body (Failed) :

```json
{
  "errors": "Address not found"
}
```

## Get Address

Endpoint : GET /api/contacts/{idContact}/addresses/{idAddress

Request Header :

- Authorization: Bearer {Token} (mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "id": "uuid",
    "street": "staf",
    "city": "operational",
    "province": "staf@mail.com",
    "country": "0812345678",
    "postalCode": "12345"
  }
}
```

Response Body (Failed) :

```json
{
  "errors": "Contact Not Found"
}
```

## Remove Contact

Endpoint : DELETE /api/contacts/{idContact}/addresses/{idAddress}

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
  "errors": "Address not found"
}
```

## List Address

Endpoint: GET /api/contacts/{idContact/addresses

Request Header :

- Authorization: Bearer {Token} (mandatory)

Response Body (Success):

```json
{
  "data": [
    {
      "id": "uuid",
      "street": "staf",
      "city": "operational",
      "province": "staf@mail.com",
      "country": "0812345678",
      "postalCode": "12345"
    }
  ]
}
```

Response Body (Failed) :

```json
{
  "errors": "Address not found"
}
```