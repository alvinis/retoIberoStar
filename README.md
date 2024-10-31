# Aplicacion de Spring boot - Iberostar

Este proyecto contiene una aplicación de Spring Boot que se ejecuta en Docker.

## Requisitos

- [Docker](https://www.docker.com/products/docker-desktop)
## Pasos para ejecutar la aplicación en Docker 

### 1. Clonar el repositorio

Clona este repositorio en tu máquina local:

 ```
 git clone https://github.com/alvinis/retoIberoStar.git
 cd retoIberoStar
 ```

### 2. Construir proyecto maven

Ejecutar el siguiente comando en la terminal para construir el proyecto.

 ```
 mvn clean package
 ```

### 3. Construir la imagen Docker

Ejecuta el siguiente comando en la terminal para construir la imagen Docker. Asegúrate de estar en el directorio raíz del proyecto, donde se encuentra el archivo `Dockerfile`:

```
docker build -t iberostar .
```
### 3. Ejecutar el contenedor

Inicia un contenedor desde la imagen creada y mapea el puerto `8080` del contenedor al puerto `8080` de tu máquina local:

```
docker run -p 8080:8080 iberostar
```

## Notas adicionales

- [Swagger](http://localhost:8080/swagger-ui/index.html)
- [API Docs](http://localhost:8080/v3/api-docs)
