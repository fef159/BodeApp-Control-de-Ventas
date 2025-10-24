
# 📦 BodeApp – Día 1: Planificación y Diseño

**Curso:** Aplicaciones Móviles con Android (Kotlin + Jetpack Compose)
**Duración total:** 6 días
**Docente:** Juan León

---

## 🎯 Objetivo del Día 1

Definir el alcance del proyecto, público objetivo y crear el prototipo visual en Figma.

---

## 👥 Roles del equipo

* **Líder técnico:** Ninahuaman Yuto Anderson
* **Diseñador UI:** Santos Sausnzabar Paulo

---

## ⚙️ Funciones clave

* Registro de productos.
* Registro de ventas diarias.
* Registro de compras o insumos.
* Cierre de caja diario.
* Reportes básicos.

---

## 🧑‍💻 Historias de usuario

1. Como usuario, quiero registrar un producto para poder llevar inventario.
2. Como usuario, quiero registrar una venta para controlar ingresos.
3. Como usuario, quiero registrar una compra o insumo para controlar costos.
4. Como usuario, quiero calcular el cierre diario para saber el balance.
5. Como usuario, quiero ver historial de ventas y compras para análisis.

---

## 🎨 Prototipo (Figma)

Puedes revisarlo aquí: [Prototipo en Figma](https://www.figma.com/make/57Di6JZnEyaCey2vKzrtml/Inventory-Management-App?node-id=0-4&t=1xdXjYJ87or7CxxS-1)

Debe incluir **mínimo 5 pantallas**:

* Home
* Registro de Producto
* Ventas
* Compras
* Cierre de Caja / Reportes

---

## 📁 Entregables del Día 1

* Prototipo en Figma (link arriba).
* Repositorio en GitHub con este README inicial y el enlace al Figma.

---
Perfecto 🙌 Aquí tienes tu **README actualizado para el Día 2**, manteniendo el mismo formato que usaste para el Día 1.
Incluye la configuración del proyecto, la estructura base y las ramas en GitHub.

---

# 📦 BodeApp – Día 2: Configuración del Proyecto y Estructura Base

**Curso:** Aplicaciones Móviles con Android (Kotlin + Jetpack Compose)
**Duración total:** 6 días
**Docente:** Juan León

---

## 🎯 Objetivo del Día 2

Configurar el proyecto base en Android Studio, crear la estructura de paquetes y establecer la navegación inicial entre pantallas.

---

## ⚙️ Actividades realizadas

* Creación del proyecto **BodeApp** con **Kotlin + Jetpack Compose**.
* Configuración del **tema visual (Material 3)**.
* Implementación de la **navegación inicial** con `Navigation Compose`.
* Creación de pantallas vacías conectadas:

  * Home
  * Productos
  * Ventas
  * Compras
  * Cierre de Caja
* Estructura de paquetes organizada:

  ```
  com.bodeapp/
  ├── data/
  ├── model/
  ├── navigation/
  │   ├── AppNavigation.kt
  │   └── HomeScreen.kt
  ├── ui/
  ├── util/
  └── MainActivity.kt
  ```
* Integración de los **botones en HomeScreen** para navegar a cada pantalla.
* Verificación de ejecución correcta de la navegación en emulador.

---

## 🌿 Ramas en GitHub

Cada integrante del equipo trabaja en su propia rama para mantener un flujo limpio de desarrollo:

| Rama                   | Descripción                                               |
| ---------------------- | --------------------------------------------------------- |
| `main`                 | Rama principal con el proyecto base y navegación inicial. |
| `feature/home-ui`      | Desarrollo de la pantalla Home y flujo de botones.        |
| `feature/productos-ui` | Desarrollo del formulario de productos.                   |
| `feature/ventas-ui`    | Desarrollo de la pantalla de ventas.                      |
| `feature/db-room`      | Configuración de base de datos local (Room).              |

📘 Las ramas se crean desde GitHub seleccionando **“main” → “Create branch from main”**
y nombrándolas según la funcionalidad o el integrante responsable.

---

## 🧱 Dependencias agregadas

```kotlin
// Jetpack Compose
implementation("androidx.activity:activity-compose:1.9.2")
implementation(platform("androidx.compose:compose-bom:2024.09.01"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.ui:ui-tooling-preview")
debugImplementation("androidx.compose.ui:ui-tooling")

// Navigation Compose
implementation("androidx.navigation:navigation-compose:2.8.3")
```


---

## 📁 Entregables del Día 2

✅ Proyecto base funcional en Android Studio.
✅ Navegación inicial entre pantallas implementada.
✅ Ramas creadas en GitHub para desarrollo colaborativo.
✅ README actualizado con estructura y dependencias.

---

¿Deseas que te lo prepare ya con formato Markdown (`README.md`) listo para copiar y pegar en GitHub?
