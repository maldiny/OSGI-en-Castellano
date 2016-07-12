# OSGI-en-Castellano

## Índice
  1. introducción
  2. modularidad
  3. ciclo de vida
  4. principios de los servicios
  5. uso de componentes básicos

## Introducción

**OSGI (Open Services Gateway Initiative)**

Framework creado en Marzo de 1999 **orientado a Servicios** e implementado en Java, que define la forma de crear módulos y la forma en que estos interactúan entre sí en tiempo de ejecución. 

La **OSGi Alliance** es un consorcio de empresas tecnológicas a nivel mundial que trata de asegurar la interoperabilidad de las aplicaciones y servicios. Algunos ejemplos de miembros: Motorola, Nokia, Mitsubishi Electric Corporation, Vodafone Group Services, LinkedIn, LG Electronics...

Este framework provee al desarrollador de un entorno Java gestionado y seguro que permite el despliegue de aplicaciones denominadas **bundles**. El framework de OSGI permitirá la **descarga, instalación y borrado de bundles en tiempo de ejecución.**

OSGi intenta solventar los problemas del tradicional "classloader" de la máquina virtual y de los servidores de aplicaciones Java. Para ello, en OSGI **cada bundle tiene su propio classpath separado del resto de classpath de los demás módulos.**

La arquitectura OSGI se divide en capas, tal y como se muestra en la siguiente figura, las cuales se detallan a continuación:

TODO: [Imagen]

* **Bundles**: Componentes OSGI creados por los desarrolladores.
* **Servicios**: Capa encargada de conectar distintos bundles de manera dinámica.
* **Ciclo de vida**: API que permite instalar, iniciar, parar, actualizar y desinstalar bundles sin necesidad de reiniciar el framework.
* **Módulos**: Capa que define cómo importar/exportar código fuente de un bundle.
* **Seguridad**: Capa que administra los aspectos de seguridad del framework.
* **Entorno de ejecución**: Especifica qué métodos están disponibles en la plataforma.

**[Ir al índice](#Índice)**

## Modularidad

### Modelos de dependencias

* El sistema debe resolver las dependencias del bundle, posiblemente en múltiples alternativas válidas de las cuales el sistema automáticamente **resolverá las dependencias con la mejor de las opciones posibles.**
* El modelo de resolución de dependencias dinámico de OSGI siempre encuentra el bundle que mejor encaja con cada invocación **en tiempo de ejecución.**
* OSGI recomienda la **dependencia a través de APIs.**
* Las APIs se definen **a nivel de paquetes.**
* Lee casi todos los JSR (**J**ava **S**pecification **R**equests). OSGI define los paquetes que JSR específica. Ej:
  * JSR 112 → javax.resource.spi, cci
  * JSR 173 → javax.xml.stream
  * JSR 315 → javax.servlet (3.0)

### Versionado semántico

TODO [IMAGEN]

* **Major**: Actualizaciones incompatibles para ambos, el consumidor y el publicador de la API.
* **Minor**: Actualizaciones compatibles para el consumidor pero no para el proveedor de la API.
* **Micro**: Actualizaciones que no afectan al API, por ejemplo, un fix.
* **Qualifier**: Un identificador como puede ser un timestamp.

> **El consumidor de una API deberá importar un rango que comienza con la versión base y termina con la siguiente versión Major. Por ejemplo: [4.2,5)**

**[Ir al índice](#Índice)**

## Ciclo de vida

TODO:

**[Ir al índice](#Índice)**

## Principios de los servicios

TODO:

**[Ir al índice](#Índice)**

## Uso de componentes básicos

TODO:

**[Ir al índice](#Índice)**

## Referencias

TODO:

**[Ir al índice](#Índice)**
