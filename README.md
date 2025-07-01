🏋️‍♀️ FitZone App - Tu Centro de Entrenamiento en el Bolsillo
¡Bienvenido al repositorio de FitZone! Esta aplicación móvil es un MVP (Producto Mínimo Viable) diseñado para digitalizar la experiencia de los usuarios de la cadena de centros deportivos "FitZone", permitiéndoles explorar, reservar y gestionar sus clases de entrenamiento de manera eficiente y moderna.

Este proyecto ha sido desarrollado con un enfoque riguroso en la calidad del código, la escalabilidad, la experiencia de usuario y la integración con servicios de backend, utilizando las mejores prácticas de la industria para Android.

🎯 Objetivo del Proyecto
El objetivo principal de este proyecto es construir una aplicación móvil nativa en Android (Kotlin) que integre un backend robusto (Firebase) para gestionar usuarios, clases, suscripciones y notificaciones. Se busca una aplicación funcional y bien estructurada, lista para evolucionar y escalar.

🚀 Funcionalidades Implementadas (MVP)
El MVP de FitZone ofrece las siguientes características clave para una experiencia de usuario fluida:

Autenticación de Usuario (Login & Registro):

Sistema de registro de nuevos usuarios con email y contraseña.

Inicio de sesión seguro para usuarios existentes.

Manejo de sesiones: el usuario es redirigido automáticamente al Home si ya está logueado al abrir la app.

Gestión de errores clara para credenciales inválidas o problemas de registro.

Tecnología: Firebase Authentication.

Exploración de Clases:

Visualización de un listado dinámico de tipos de clases (Crossfit, Yoga, Spinning, etc.).

Funcionalidad de búsqueda en tiempo real para filtrar clases por nombre.

Tecnología: Firebase Cloud Firestore para datos de clases.

Detalle y Filtrado de Clases por Tipo:

Al seleccionar un tipo de clase, la aplicación muestra una vista detallada con su descripción e imagen.

Permite filtrar las clases disponibles por fecha y/o ciudad.

Las ciudades se cargan dinámicamente desde Firestore.

Tecnología: Firebase Cloud Firestore para datos de clases y ubicaciones, consultas avanzadas con Timestamp y whereIn.

Agendamiento de Clases:

El usuario puede agendar una clase específica.

Validación de Suscripción: El sistema verifica si el usuario tiene una suscripción activa (Mensual, Trimestral, Anual) consultando su perfil en Firestore.

Si la suscripción está vigente, se muestra el resumen de la misma.

Si no está vigente o ha expirado, se presentan las opciones de suscripción disponibles (Gold, Elite Gold, Platino, Pago por Clase).

Confirmación de Agendamiento: Un modal de confirmación solicita al usuario verificar la acción antes de agendar.

Transacciones Atómicas: El agendamiento se realiza mediante una transacción atómica en Firestore, asegurando que:

El campo booked de la clase se incrementa en 1.

El ID de la clase se añade a la colección de clases agendadas del usuario.

Una notificación de agendamiento exitoso se crea en la subcolección de notificaciones del usuario.

Todo esto ocurre de forma segura y consistente, o ninguna parte de la operación se completa.

Tecnología: Firebase Cloud Firestore (transacciones), Firebase Authentication (para el userId).

Notificaciones de Usuario:

Visualización de un listado de notificaciones del usuario.

Las notificaciones se almacenan en una subcolección notifications dentro del documento del usuario en Firestore.

Notificaciones Push (implementación futura/recomendada): La arquitectura está preparada para integrar Firebase Cloud Messaging (FCM) mediante una Firebase Cloud Function que escucharía la creación de estas notificaciones en Firestore y las enviaría como notificaciones push al dispositivo del usuario. (Ver sección de Evolución).

Tecnología: Firebase Cloud Firestore.

Visualización de Clases Agendadas:

Listado de todas las clases que el usuario ha agendado.

Segmentación Inteligente: Las clases se dividen automáticamente en "Próximas Clases" y "Clases Pasadas", facilitando la gestión del usuario.

Tecnología: Firebase Cloud Firestore, lógica de filtrado y ordenamiento por fecha.

📐 Diseño y Arquitectura
El proyecto FitZone sigue una arquitectura Clean Architecture combinada con el patrón MVVM (Model-View-ViewModel), y utiliza Hilt para la inyección de dependencias. Esta elección garantiza:

Clara Separación de Responsabilidades: El código está dividido en capas distintas, cada una con un propósito específico, lo que mejora la mantenibilidad, escalabilidad y testabilidad.

Presentation Layer:

View (Composables): Responsable de la UI. Observa el ViewModel y reacciona a los cambios de estado. No contiene lógica de negocio.

ViewModel: Mantiene el estado de la UI y orquesta los casos de uso. No contiene lógica de negocio directa, solo prepara los datos para la UI y maneja las interacciones del usuario.

UI State: data class que representa el estado completo de la UI, facilitando el manejo de carga, errores, datos vacíos, etc.

Domain Layer:

Domain Models: Entidades de negocio puras, independientes de cualquier implementación de UI o base de datos.

Repositories (Interfaces): Contratos que definen las operaciones de datos que la capa de dominio necesita.

Use Cases (Interactors): Encapsulan una única pieza de lógica de negocio. Son el punto de entrada para ejecutar operaciones de negocio desde el ViewModel. Su uso es esencial para mantener el ViewModel ligero, la lógica de negocio aislada y fácilmente testeable, y para orquestar múltiples repositorios si fuera necesario.

Data Layer:

Data Transfer Objects (DTOs): Modelos que representan la estructura de los datos tal como vienen de las fuentes de datos (ej. Firestore).

Data Sources (Remote/Local): Implementaciones concretas para interactuar con APIs externas (Firestore) o bases de datos locales.

Repository Implementations: Implementan los contratos de repositorio del dominio y son responsables de mapear los DTOs a los modelos de dominio.

Gestión Eficiente de Estados:

Uso de StateFlow en los ViewModels para exponer el estado de la UI de forma reactiva y segura.

Manejo explícito de estados de isLoading, errorMessage y empty en la UI para proporcionar feedback al usuario.

Prevención de Memory Leaks y Eficiencia de Peticiones:

Firebase Firestore con Flow y addSnapshotListener: Las peticiones de lectura de datos se realizan utilizando Flows que se conectan a los listeners de snapshots de Firestore. Esto asegura:

Actualizaciones en tiempo real: La UI se actualiza automáticamente cuando los datos cambian en la base de datos.

Eficiencia: Solo se reciben datos cuando hay un cambio, evitando el polling constante y el consumo excesivo de recursos.

Prevención de Memory Leaks: El callbackFlow con awaitClose { subscription.remove() } garantiza que los listeners de Firestore se desregistran automáticamente cuando ya no son observados (ej. cuando el ViewModel se destruye), evitando fugas de memoria.

Consultas Optimizadas: Uso de whereEqualTo, whereGreaterThanOrEqualTo, whereLessThan y FieldPath.documentId() para filtrar y ordenar los datos directamente en Firestore, minimizando la cantidad de datos transferidos y procesados en el cliente.

Transacciones Atómicas: Las operaciones críticas (como agendar una clase) se realizan dentro de transacciones de Firestore para garantizar la consistencia de los datos, evitando estados corruptos.

Inyección de Dependencias (Hilt):

Simplifica la gestión de dependencias, haciendo que los componentes sean más fáciles de probar y reutilizar.

Asegura que las instancias correctas se proporcionen a los ViewModels y repositorios.

Test Unitarios Básicos:

La arquitectura limpia facilita enormemente la escritura de tests unitarios. Cada capa y caso de uso puede ser probado de forma aislada, mockeando sus dependencias. Aunque no se incluyen tests en este proyecto, la estructura está diseñada para su fácil implementación.

📱 UI Responsiva y Adaptada
La interfaz de usuario está construida con Jetpack Compose, lo que garantiza una UI moderna, declarativa y, lo más importante, totalmente responsiva.

Diseño Adaptativo: Los Composables utilizan Modifier.fillMaxWidth(), weight, padding, y otras propiedades flexibles para adaptarse a diferentes tamaños de pantalla y orientaciones (teléfonos, tablets).

Componentes Reutilizables: Se han creado componentes Composables reutilizables (ej. ClassTypeCardItem, SubscriptionTypeCard, ClassCardItem) para mantener la consistencia visual y la eficiencia en el desarrollo.

Manejo de Estados Visuales: Los estados de carga (CircularProgressIndicator), error (Text en rojo) y vacío (Text indicando no hay datos) se manejan explícitamente en la UI para proporcionar feedback claro al usuario.

📦 Versionamiento y Buenas Prácticas
El proyecto sigue un enfoque de desarrollo robusto:

Repositorio Git: Organizado con un flujo de trabajo basado en ramas:

main: Rama estable para versiones de producción.

develop: Rama principal de integración para nuevas características.

feature/<nombre-feature>: Ramas individuales para el desarrollo de nuevas funcionalidades.

Commits Semánticos: Se utilizan mensajes de commit claros y concisos, siguiendo convenciones semánticas (ej. feat:, fix:, refactor:) para facilitar el seguimiento del historial y la generación de changelogs.

Pull Requests (PR) y Revisión de Código: Se fomenta el uso de Pull Requests para integrar cambios de las ramas de feature a develop o main, permitiendo la revisión de código por pares para asegurar la calidad y detectar posibles problemas.

Documentación Mínima en README: Este mismo documento sirve como una guía esencial para entender la estructura del proyecto y cómo interactuar con él.

⚙️ Cómo Ejecutar y Desplegar el Proyecto
Esta sección detalla los pasos para configurar y ejecutar la aplicación en tu entorno de desarrollo, así como las consideraciones para su despliegue.

Requisitos Previos:
Android Studio instalado y configurado.

Conexión a internet.

Una cuenta de Firebase (gratuita para empezar).

Pasos para Configurar y Ejecutar:
Clonar el Repositorio:

git clone https://github.com/tu-usuario/FitZone.git
cd FitZone/app

(Asegúrate de reemplazar https://github.com/tu-usuario/FitZone.git con la URL real de tu repositorio).

Configurar Firebase:

Crea un proyecto en la Consola de Firebase.

Registra tu aplicación Android en Firebase y descarga el archivo google-services.json.

Coloca google-services.json en el directorio app/.

En tu proyecto Firebase, habilita los siguientes servicios:

Authentication: Activa el método de inicio de sesión Email/Password.

Firestore Database: Crea una base de datos en modo de producción.

Configura las Reglas de Seguridad de Firestore:

Para empezar, puedes usar reglas permisivas para desarrollo (¡cuidado en producción!):

rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null; // Solo usuarios autenticados
    }
  }
}

Luego, refina estas reglas para un control más granular (ej. solo el usuario puede escribir en su propio documento).

Crea las colecciones iniciales en Firestore:

class_types (con documentos para ClassTypeDto)

locations (con documentos para LocationDto)

classes (con documentos para ClassDto, incluyendo typeClassId, locationId, dateTime como Timestamp, y booked como Number).

users (con documentos para UserDto, incluyendo fcmToken, subscriptionType, startSubscription, classesBooked).

subscription_types (con documentos para SubscriptionTypeDto).

Importante: Para las consultas de fecha y whereIn, asegúrate de crear los índices compuestos necesarios en Firestore. La consola de Firebase te sugerirá los índices si intentas una consulta que los requiere.

Abrir en Android Studio:

Abre el proyecto FitZone en Android Studio.

Permite que Gradle sincronice las dependencias.

Ejecutar:

Conecta un dispositivo Android o inicia un emulador.

Haz clic en el botón "Run" (▶️) en Android Studio para instalar y ejecutar la aplicación.

Guía de Uso Rápido:
Inicio de la App: La app detectará si hay una sesión activa. Si no, te llevará a la pantalla de Login.

Registro: Haz clic en "¿No tienes una cuenta? Regístrate aquí." para crear una nueva cuenta con email y contraseña.

Login: Ingresa tus credenciales para acceder.

Pantalla Home:

Verás un listado de tipos de clases.

Usa la barra de búsqueda superior para filtrar por nombre de clase.

Detalle de Clase:

Haz clic en un tipo de clase para ver sus detalles.

Usa los campos de "Fecha" y "Ciudad" para filtrar las clases disponibles por fecha y/o ubicación.

Haz clic en "Limpiar filtros" para restablecer la búsqueda.

Agendamiento:

En la pantalla de detalle de clase, haz clic en el botón "Agendar Clase".

Si no tienes suscripción activa, se te mostrarán las opciones de suscripción.

Confirma el agendamiento en el modal.

Recibirás una confirmación visual (Snackbar) al agendar exitosamente.

Mis Clases Agendadas:

(Asumiendo que hay un punto de navegación a esta pantalla, ej. en el ModalNavigationDrawer).

Verás tus clases agendadas segmentadas en "Próximas Clases" y "Clases Pasadas".

Notificaciones:

(Asumiendo un punto de navegación).

Consulta el listado de notificaciones, donde verás las confirmaciones de agendamiento.

📈 Propuesta de Evolución para Siguientes Versiones
Este MVP sienta una base sólida para futuras expansiones. Aquí hay algunas ideas para la evolución del proyecto:

Gestión Completa de Membresías y Pagos:

Integración con pasarelas de pago reales (ej. Stripe, PayPal) para la compra de suscripciones o clases individuales.

Pantalla de gestión de membresías, mostrando historial de pagos y renovación.

Lógica de negocio para asignar "créditos" de clases según la suscripción y descontarlos al agendar.

Sistema de Notificaciones Push Avanzado:

Implementar completamente Firebase Cloud Messaging (FCM) con Firebase Cloud Functions para enviar notificaciones push al dispositivo del usuario (ej. recordatorios de clases, promociones personalizadas, cambios de horario).

Perfil de Usuario y Personalización:

Pantalla de perfil de usuario con edición de datos (nombre, foto, preferencias).

Historial de actividad y estadísticas de entrenamiento.

Personalización de la experiencia (ej. clases recomendadas basadas en el historial).

Funcionalidades Sociales y Comunidad:

Chat entre Usuarios/Instructores: Implementar un sistema de chat en tiempo real (utilizando Firebase Realtime Database o Firestore) para que los usuarios puedan comunicarse entre sí o con los instructores.

Compartir logros en redes sociales.

Tablas de clasificación (gamificación).

Gamificación:

Sistema de puntos o insignias por asistir a clases, alcanzar objetivos, etc.

Desafíos y retos de entrenamiento.

Integración con Calendario:

Permitir a los usuarios añadir las clases agendadas directamente a su calendario personal (Google Calendar, Apple Calendar).

Mapas y Navegación:

Integración de Google Maps para mostrar la ubicación de los centros de entrenamiento y ofrecer navegación.

Reseñas y Calificaciones:

Permitir a los usuarios calificar clases e instructores.

Soporte Multi-idioma:

Internacionalización de la aplicación para soportar múltiples idiomas.
