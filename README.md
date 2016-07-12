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

**El consumidor de una API deberá importar un rango que comienza con la versión base y termina con la siguiente versión Major. Por ejemplo: [4.2,5)**

### Imports dinámicos

```css
DynamicImport-Package: *;vendor=acme, *
```

* Esta etiqueta contiene valores separados por comas, donde se indican los **paquetes que pueden ser importados dinámicamente** cuando sean necesarios. 
* Generalmente esta directiva se usa para **evitar dependencias cíclicas** cuando cargamos clases a través de Class.forName().
* Los paquetes pueden ser nombrados **explícitamente o** mediante el uso de **expresiones** como org.foo.* o *. 

### Cómo y cuando “the package wiring” ocurre

Un aspecto fundamental del framework de OSGI es la gestión de las dependencias entre bundles. Estas dependencias se expresan en el **MANIFEST.MF** y se pueden clasificar como **requerimientos y capacidades**.

El framework resuelve las dependencias durante la **fase de Resolución**. El framework debe resolver ser capaz de localizar una capacidad para cada requerimiento para poder declarar un bundle como **Resolved**.

Los paquetes se reflejan en el osgi.wiring.package. Un **Import-Package** se mapea en el osgi.wiring.package como un requerimiento mientras que un **Export-Package** se mapea como una capacidad.
* **Import-Package:** com.everis.foo; version=1
* **Export-Package:** com.everis.foo; version=1

TODO: [IMAGEN]

### Entornos de ejecución

TODO: [IMAGEN]

- Apache Felix
- Apache Karaf
- Equinox
- Jboss
- Knopflerfish
- ...

### Entornos de desarrollo

Para el desarrollo de bundles OSGI se recomienda disponer de los siguientes frameworks:

- **IDE** (Eclipse, Netbeans, IntelliJ,...): Como entorno de desarrollo.
- **Maven 3.0** para la gestión de dependencias y construcción de artefactos.
- **Maven-bundle-plugin** para la generación de los MANIFEST.MF a partir del pom.xml
- **Apache Felix** como entorno OSGI de despliegue de bundles.

TODO: [IMAGEN]

Opcionalmente a estos componentes, nombrar la existencia del plugin de eclipse **BndTools** que nos facilitará numerosas **ventajas** para la generación de artefactos en el entorno de desarrollo:

- Análisis y resolución de dependencias.
- Repositorios locales de bundles.
- Versionado semántico de bundles.
- Construcción y despliegue instantánea.
- Testeo de bundles.

### Capacidades y requerimientos

Un aspecto esencial en el modelo de dependencias establecido en los framework basados en OSGI es el concepto de Namespace.

Un **Namespace define la relación existente entre una capacidad y un requerimiento**, un productor de un determinado servicio y un consumidor del mismo.

Por ejemplo:

```css
Proveedor: System Bundle
Provide-Capability: osgi.ee; osgi.ee="OSGi/Minimum"; version:List="1.0, 1.1, 1.2", osgi.ee; osgi.ee="JavaSE"; version:List="1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6"

Consumidor: Cualquier budle
Require-Capability: osgi.ee; filter:="(&(osgi.ee=JavaSE)(version=1.6))"
```

TODO: [IMAGEN]

### Antipatrones

Los **antipatrones** son soluciones negativas que suelen presentar más problemas que soluciones. Su estudio permitirá conocer los errores más comunes con la arquitectura OSGI:

* **Class.forName()**

```java
Class clazz = Class.forName("org.example.domain.Event");
```

- Permite la carga de clases **sin especificar el ClassLoader** empleado y como único argumento el nombre de la clase.
- Utiliza el ClassLoader en ejecución que contiene la clase en la que se hace uso de la misma.
- Complejo de utilizar el OSGI ya que el **ClassLoader es independiente por cada Bundle.**
- Es necesario tener cargada la clase en el ClassLoader del Bundle por lo que será necesario especificar dicha dependencia en el MANIFEST.MF **Import-Package**.
- Susceptible a generar excepciones en tiempo de ejecución de tipo **ClassNotFoundException**.

* **Thread Context ClassLoader**

```java
ClassLoader tccl = Thread.currentThread().getContextClassLoader();
Class clazz = Class.forName("org.example.domain.Event", true, tccl);
```

- Es una de las soluciones **peor documentadas** de todas las especificaciones del J2EE.
- OSGI **no puede garantizar** que el hilo en ejecución es el que se encuentra en ejecución para un determinado código fuente.
- Los ClassLoaders en los hilos en OSGI no están definidos en las especificaciones de OSGI y su invocación **puede retornar “null”**.

### Antipatrones - Soluciones

* **Instanciación de objetos a través de una Factoría**

```java
public interface DomainObjectFactory {
    Object createInstance(String tableName);
}
```

  1. Evita la carga de clases dinámica por el classloader.
  2. Crea objetos bajo petición desde el bundle que contiene las clases necesarias para crearlo.
  3. No es válido para todos los casos de uso.

* **Registro de Clases**

```java
session.registerClassForTable("EVENTS", Event.class);
List events = session.createQuery("from Event").list();
session.registerClass("org.example.domain.Person", Person.class);
```

  1. El cliente antes de realizar la petición realiza el registro de las clases en el la sesión.
  2. Permite el registro de clases directamente utilizando únicamente su nombre.
  3. Una vez registradas en la sesión puede recuperarse con Class.forName.

* **Envío del ClassLoader**

```java
SessionFactory.createSession(MyClass.class.getClassLoader());
session.setDomainClassLoader(MyClass.class.getClassLoader());
```

  1. Es la opción con menor impacto en el código existente.
  2. Habrá que evaluar si esta opción se ajusta a las necesidades presentadas en cada caso.
  3. Se puede enviar “null” para indicar que el framework debe encontrar el ClassLoader adecuado.

**[Ir al índice](#Índice)**

## Ciclo de vida

### Bundle Manifest

- **Bundle-Activator:** Especifica el nombre de la clase que será invocada durante el arranque y la parada del bundle a los métodos respectivamente. Esta clase deberá implementar los métodos de BundleActivator start() / stop(). p.e.j: com.acme.fw.Activator
- **Bundle-Category:** Listado de categorías separados por coma al que pertenece el bundle. p.e.j.: osgi, test, nursery
- **Bundle-ClassPath:** Listado de jar separados por coma que serán cargados en el classpath del bundle durante su arranque. p.e.j: /jar/http.jar,.
- **Bundle-ManifestVersion:** Define el listado de reglas de especificación del bundle. p.e.j.: 2
- **Bundle-Name:** Define el nombre del bundle. p.e.j: Firewall
- **Bundle-SymbolicName:** Especifica el nombre identificativo único para el contenedor de bundles. p.e.j: com.acme.daffy
- **Bundle-Version:** Número de la versión del bundle. p.e.j: 1.1
- **Export-Package:** Listado de paquetes exportados por el bundle especificando la versión de forma opcional pero recomendable. p.e.j: org.osgi.util.tracker;version=1.3
- **Import-Package:** Listado de paquetes importados por el bundle necesarios para la ejecución del mismo. p.e.j: org.osgi.util.tracker,org.osgi.service.io;version=1.4

### Estados de un bundle

TODO: IMAGEN

- **Installed:** El bundle se ha instalado satisfactoriamente.
- **Resolved:** Todas las clases de java necesarias están disponibles. Este estado indica que el bundle está listo para ser iniciado o detenido.
- **Starting:** El bundle está iniciándose. El método BundleActivator.start está siendo invocado y aún no terminado su ejecución. 
- **Stopping:** El bundle está deteniéndose. El método BundleActivator.stop está siendo invocado y aún no terminado su ejecución.
- **Active:** El bundle ha sido activado satisfactoriamente y está en ejecución. El método BundleActivator.start ha sido invocado y ha terminado su ejecución.
- **Uninstalled:** El bundle se ha desinstalado. No podrá cambiar de estado nuevamente.

### Bundle/BundleContext/BundleActivator

* **Bundle Simbolic Name (BSN) y Bundle Version**
El nombre y la versión especificada por el desarrollador. La combinación de ambos es el identificador único de un bundle en el framework.

	* **getSymbolicName**: Obtiene el bundle name de un bundle.
	* **getVersion**: Obtiene la versión de un bundle.

Es posible instalar el mismo bundle varias veces siempre y cuando esta pareja de valores no se encuentre ya desplegada en el framework.

* **Bundle location**

Nombre asignado por el framework en el momento de la instalación. Especifica la URL para localizar el JAR.

	* **getLocation:** Obtiene la localización de un bundle (inputstream, OBR, URL,...)

* **Bundle ID**

Es un ID de tipo long asignado por el framework único para el bundle durante todo su ciclo de vida. Su propósito es identificar de forma única un bundle dentro del framework.

	* **getBundleID:** Obtiene el identificador de un bundle.

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
