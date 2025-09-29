# Prueba técnica: Posts API
## Instrucciones de compilación y ejecución
Primero deberá clonarse el repositorio y acceder a su contenido
```bash
    git clone https://github.com/pruebas-tecnicas-desarrollo/2025-bancoHipotecario.git
    cd 2025-bancoHipotecario
```

Luego, utilizando la consola, deberá iniciar el proyecto

- Utilizando docker:
```bash
    docker compose up -d
    docker logs -f --tail 20 banco-hipotecario-app
```

- Utilizando la manera tradicional:
```bash
    mvn clean install
    mvn spring-boot:run
```

Una vez iniciado el proyecto se disponibilizará la url:
```https
    http://127.0.0.1:8080/
```

Con esta url podrá consumir los endpoints requeridos en la prueba técnica:
```https
    [GET] http://127.0.0.1:8080/posts/
    [GET] http://127.0.0.1:8080/posts/<id>
    [DELETE] http://127.0.0.1:8080/posts/<id>
```
También tendrá disponible:
- Documentación:
```https
    http://127.0.0.1:8080/swagger-ui/index.html
    http://127.0.0.1:8080/v3/api-docs
```
- Monitoreo:
```https
    http://127.0.0.1:8080/actuator/
```
**Nota:** al enviar una solicitud verá que del lado derecho al timestamp hay un número entre corchetes. Esto es el requestId, es el timestamp pero con sus 6 microsegundos  correspondientes para simular un id único entre peticiones con la finalidad de trazabilidad. En este proyecto piloto no hace nada más que logearse y devolverse en la respuesta (en caso de que el código de respuesta permita que haya una), pero podría utilizarse para métricas, logs centralizados, etcétera.

## Descripción de la arquitectura
Este proyecto sigue el patrón de arquitectura hexagonal (ports & adapters) con separación en tres capas principales:

- **domain:** contiene los modelos de negocio, las excepciones e interfaces (ports) que definen la lógica de la aplicación.

- **application:** contiene configuraciones, implementaciones de los casos de uso de la aplicación que coordinan la lógica de negocio y utiles. Acá residen los servicios que consumen los puertos.

- **infrastructure:** contiene los adaptadores de entrada/salida y el interceptor

    - **adapter.in.rest:** controlador API RESTful que expone los endpoints solicitados en la prueba ténica.
    - **adapter.out:** conexión con la API externa definida en la prueba técnica para obtener los datos correspondientes.

## Ejemplos de uso de los endpoints
- **Código de respuesta 200**
    
    1) Traer todos los posts con todos sus respectivos comentarios y la información de los autores de cada uno de esos posts:

    Request:

    ```https
    [GET] http://127.0.0.1:8080/posts/
    ```

    Response:

    ```json
    {
        "requestId": "20250929052617803212",
        "data": [
            {
                "id": 1,
                "userId": 1,
                "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
                "author": {
                    "id": 1,
                    "name": "Leanne Graham",
                    "email": "Sincere@april.biz",
                    "address": {
                        "street": "Kulas Light",
                        "suite": "Apt. 556",
                        "city": "Gwenborough",
                        "zipcode": "92998-3874",
                        "geo": {
                            "lat": "-37.3159",
                            "lng": "81.1496"
                        }
                    },
                    "phone": "1-770-736-8031 x56442",
                    "website": "hildegard.org",
                    "company": {
                        "name": "Romaguera-Crona",
                        "catchPhrase": "Multi-layered client-server neural-net",
                        "bs": "harness real-time e-markets"
                    }
                },
                "comments": [
                    {
                        "id": 1,
                        "postId": 1,
                        "name": "id labore ex et quam laborum",
                        "email": "Eliseo@gardner.biz",
                        "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
                    },
                    {
                        "id": 2,
                        "postId": 1,
                        "name": "quo vero reiciendis velit similique earum",
                        "email": "Jayne_Kuhic@sydney.com",
                        "body": "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
                    },
                    {
                        "id": 3,
                        "postId": 1,
                        "name": "odio adipisci rerum aut animi",
                        "email": "Nikita@garfield.biz",
                        "body": "quia molestiae reprehenderit quasi aspernatur\naut expedita occaecati aliquam eveniet laudantium\nomnis quibusdam delectus saepe quia accusamus maiores nam est\ncum et ducimus et vero voluptates excepturi deleniti ratione"
                    },
                    {
                        "id": 4,
                        "postId": 1,
                        "name": "alias odio sit",
                        "email": "Lew@alysha.tv",
                        "body": "non et atque\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\nquia voluptas consequuntur itaque dolor\net qui rerum deleniti ut occaecati"
                    },
                    {
                        "id": 5,
                        "postId": 1,
                        "name": "vero eaque aliquid doloribus et culpa",
                        "email": "Hayden@althea.biz",
                        "body": "harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit inventore cupiditate\nvoluptates magni quo et"
                    }
                ]
            }
        ]
    }
    ```

    **Nota:** response recortado por fines prácticos

    2) Traer un post específico, con sus respectivos comentarios e información del autor
  
    Request:
    ```https
    [GET] http://127.0.0.1:8080/posts/5
    ```
    Response:
    ```json
    {
        "requestId": "20250929052812473986",
        "data": {
            "id": 5,
            "userId": 1,
            "title": "nesciunt quas odio",
            "body": "repudiandae veniam quaerat sunt sed\nalias aut fugiat sit autem sed est\nvoluptatem omnis possimus esse voluptatibus quis\nest aut tenetur dolor neque",
            "author": {
                "id": 1,
                "name": "Leanne Graham",
                "email": "Sincere@april.biz",
                "address": {
                    "street": "Kulas Light",
                    "suite": "Apt. 556",
                    "city": "Gwenborough",
                    "zipcode": "92998-3874",
                    "geo": {
                        "lat": "-37.3159",
                        "lng": "81.1496"
                    }
                },
                "phone": "1-770-736-8031 x56442",
                "website": "hildegard.org",
                "company": {
                    "name": "Romaguera-Crona",
                    "catchPhrase": "Multi-layered client-server neural-net",
                    "bs": "harness real-time e-markets"
                }
            },
            "comments": [
                {
                    "id": 21,
                    "postId": 5,
                    "name": "aliquid rerum mollitia qui a consectetur eum sed",
                    "email": "Noemie@marques.me",
                    "body": "deleniti aut sed molestias explicabo\ncommodi odio ratione nesciunt\nvoluptate doloremque est\nnam autem error delectus"
                },
                {
                    "id": 22,
                    "postId": 5,
                    "name": "porro repellendus aut tempore quis hic",
                    "email": "Khalil@emile.co.uk",
                    "body": "qui ipsa animi nostrum praesentium voluptatibus odit\nqui non impedit cum qui nostrum aliquid fuga explicabo\nvoluptatem fugit earum voluptas exercitationem temporibus dignissimos distinctio\nesse inventore reprehenderit quidem ut incidunt nihil necessitatibus rerum"
                },
                {
                    "id": 23,
                    "postId": 5,
                    "name": "quis tempora quidem nihil iste",
                    "email": "Sophia@arianna.co.uk",
                    "body": "voluptates provident repellendus iusto perspiciatis ex fugiat ut\nut dolor nam aliquid et expedita voluptate\nsunt vitae illo rerum in quos\nvel eligendi enim quae fugiat est"
                },
                {
                    "id": 24,
                    "postId": 5,
                    "name": "in tempore eos beatae est",
                    "email": "Jeffery@juwan.us",
                    "body": "repudiandae repellat quia\nsequi est dolore explicabo nihil et\net sit et\net praesentium iste atque asperiores tenetur"
                },
                {
                    "id": 25,
                    "postId": 5,
                    "name": "autem ab ea sit alias hic provident sit",
                    "email": "Isaias_Kuhic@jarrett.net",
                    "body": "sunt aut quae laboriosam sit ut impedit\nadipisci harum laborum totam deleniti voluptas odit rem ea\nnon iure distinctio ut velit doloribus\net non ex"
                }
            ]
        }
    }
    ```

- **Código de respuesta 204**
    
    3) Eliminar un post específico
       
    Request:
    ```https
    [DELETE] http://127.0.0.1:8080/posts/5
    ```

- **Código de respuesta 400**

    4) Intentar traer un post específico mediante un id inválido
       
    Request:
    ```https
    [GET] http://127.0.0.1:8080/posts/-1
    ```

    Response:

    ```json
    {
        "message": "invalid post id: -1",
        "requestId": "20250929053954431802"
    }
    ```

- **Código de respuesta 404**
    
    5) Intentar traer un post específico mediante un id inexistente
       
    Request:
    ```https
    [GET] http://127.0.0.1:8080/posts/1999
    ```

    Response:

    ```json
    {
        "message": "the required post was not found",
        "requestId": "20250929053858728387"
    }
    ```
## Decisiones técnicas tomadas
- Definí como scaffold la arquitectura hexagonal por principios de código limpio
- Utilicé Spring boot por practicidad, ya que conocía como funcionaba. Es por esto que preferí ir a lo seguro en esta prueba pero me gustaría probar Quarkus
- Le sume lombok para reducir boilerplate en modelos (@Data, @Builder, @Value) y actuator para el monitoreo en caso de que se necesite
- Implementé GlobalExceptionHandler con @RestControllerAdvice para unificar el manejo de errores.
- Agregué RequestIdInterceptor y apliqué el uso del requestId generado por él en todos los logs y respuestas de los endpoints, facilitando trazabilidad y mejorando logging.
- Estandaricé las respuestas con SuccessResponse y ErrorResponse para mantener consistencia entre ellas.
- Para el cache decidí cachear todas las respuestas de cada endpoint en sus propios caches debido a que son respuestas estáticas que nunca van a variar, sé que no es una práctica real pero me adapté a lo que ofrece la página también por ser relativamente poco contenido porque sino debería haber buscado otro enfoque para el manejo de esto. En las simulaciones de eliminar algún post determiné que solamente borraria del cache el post id especificado y el que contiene todos los posts para simular que no quede inconsistente. Por otro lado, por razones de presentación (referido al código limpio y también en parte a la estética) y al ser un proyecto de prueba, lo dejé únicamente @Cacheable y @CacheEvict, pero sé que podría haber usado CacheManager para que, por ejemplo, al obtener todos los ids no se cree un nuevo cache sino que agregue los ids faltantes al cache por id o que al consumir el endpoint para traer todos pise el cache con todos los ids individuales solicitados y que el endpoint de obtener un post id especifico siempre valide contra ese si existe o no el post id que enviaron. Actualmente no tiene expiración el cache, tuve unos inconvenientes implementandolo. Si hubiera implementado el CacheManager, me hubiera gustado investigar como implementar crons acá en Spring Boot, pero la idea era que ejecute las consulta de traer todos los posts cada x tiempo en background cosa de que ya haya una version actualizada que valide si esta el id que me pidieron y en caso de que no esté recién ahí haría la peticion a la api externa. Esto ya lo hago hoy por hoy en mi trabajo y en proyectos académicos pero no tuve el tiempo para investigarlo para hacer acá con Spring Boot
