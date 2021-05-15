## Crear una API Web Básica

# Tecnologías 
* PHP7
* NGINX
* COMPOSER
* PDO
* MYSQL

## Identificar el VERBO
if ($_SERVER['REQUEST_METHOD'] === 'POST'){
}

## Serializar un objeto
json_encode();

## Codificación de binarios 
base64_encode()
base64_decode()

## el standard psr-4

## Arquitectura Simple

```json

request-->api-->controler.method-->"orm"-->database
response<--(json)---<--api--(list)--<--"orm"--database

```
## VERBOS HTTP que se usaran   
* GET
* POST
> Ejemplo de mensaje:
```json

[
    {
        "intID": "2",
        "strTitle": "Help!",
        "strDescription": "Album de los Beatles, lanzado en 1965",
        "intIdImage": "0",
        "intIdGenre": "1",
        "imgArray": "data:image\/png;base64,XDXD"
    }
]
```
> Ejemplo de un registro nuevo
POST: leo.api/Geo/paises

```json
{
    "intID":0,
    "strTitle":"Help!",
    "strDescription":"Album de los Beatles, lanzado en 1965",
    "intIdImage":0,
    "intIdGenre":1,
    "imgArray":"data:image/png;base64,XDXD"
}
```

# urls
```plain
index.php?c=[CONTROLLER_NAME]e=[ENDPOINT_NAME]&i=[IDENTIFIER]
leo.api/[CONTROLLER_NAME]/[ENDPOINT_NAME]/[IDENTIFIER]

http://www.leonardosantosgrc.com/leo.api/Album/Albums
http://www.leonardosantosgrc.com/leo.api/Album/Albums/2

//si modificas las reglas
http://TuDominio.com/NombreApp.api/NombreControlador/NombreEndPoint

```
   
## mod rewrite settings(APACHE)
```plain
RewriteEngine on
RewriteRule ^leo.api/(.*)/(.*)/(.*)$ leo/index.php?c=$1&e=$2&i=$3 [QSA]
RewriteRule ^leo.api/(.*)/(.*)$ leo/index.php?c=$1&e=$2 [QSA]

//notas
RewriteEngine on
RewriteRule ^NombreApp.api/(.*)/(.*)/(.*)$ CarpetaServidor/index.php?c=$1&e=$2&i=$3 [QSA]
RewriteRule ^NombreApp.api/(.*)/(.*)$ CarpetaServidor/index.php?c=$1&e=$2 [QSA]

```

# Database scripts (mysql) 
```mysql
CREATE TABLE Albums(
    intID INTEGER NOT NULL AUTO_INCREMENT,
    strTitle VARCHAR(256) NOT NULL,
    strDescription VARCHAR(256) NOT NULL,
    intIdImage INTEGER,
    intIdGenre INTEGER,
    imgArray longblob,
     PRIMARY KEY (intID)
)
```
