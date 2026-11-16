# Agentemcp

Agente conversacional para gestionar pedidos de un supermercado a domicilio.
El agente utiliza Google ADK y se conecta a un servidor MCP externo para
consultar productos y administrar pedidos.

## Requisitos

- Java 21
- Un servidor MCP disponible en `http://localhost:8088/mcp`
- Credenciales configuradas para el modelo de Google utilizado por Google ADK
- Maven (opcional; el proyecto incluye Maven Wrapper)

## Funcionalidades

`PedidoAgent` puede utilizar las herramientas publicadas por el servidor MCP
para:

- Buscar productos.
- Crear pedidos.
- Agregar productos a un pedido.
- Consultar pedidos.
- Actualizar el estado de un pedido.
- Cancelar pedidos, solicitando confirmación explícita antes de hacerlo.

El agente responde en español, de forma breve y clara. Antes de agregar un
producto, busca su identificador mediante `buscarProductos`. Para cambiar un
pedido a `EN_CAMINO` o `ENTREGADO`, utiliza `actualizarEstadoPedido`.

## Configuración

La URL y el tiempo de espera del servidor MCP están definidos en
[`PedidoAgent.java`](src/main/java/com/udea/agentemcp/PedidoAgent.java):

```text
URL:     http://localhost:8088/mcp
Timeout: 30 segundos
```

Si el servidor MCP no está disponible, el agente no puede inicializarse y
mostrará un error indicando que no fue posible conectarse.

## Ejecución

### 1. Iniciar el servidor MCP

Inicia primero el servidor MCP que expone las herramientas de productos y
pedidos en:

```text
http://localhost:8088/mcp
```

### 2. Compilar el proyecto

En Windows:

```powershell
.\mvnw.cmd clean package
```

En Linux o macOS:

```bash
./mvnw clean package
```

### 3. Iniciar la interfaz web de Google ADK

Desde la raíz del proyecto:

```powershell
.\mvnw.cmd exec:java
```

El servidor ADK se configura para ejecutarse en:

```text
http://localhost:8080
```

El agente se carga desde los artefactos compilados en `target`.

### Ejecutar la aplicación Spring Boot

También se puede iniciar la aplicación Spring Boot con:

```powershell
.\mvnw.cmd spring-boot:run
```

## Pruebas

Para ejecutar las pruebas:

```powershell
.\mvnw.cmd test
```

## Tecnologías

- Java 21
- Spring Boot 3.5.16
- Google ADK Dev 1.0.0
- Apache HttpClient 5.4.2
- RxJava 3.1.8
- Maven

## Estructura principal

```text
src/
├── main/
│   ├── java/com/udea/agentemcp/
│   │   ├── AgentemcpApplication.java
│   │   └── PedidoAgent.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/udea/agentemcp/
```

## Licencia

Este proyecto no define actualmente una licencia en su `pom.xml`.
