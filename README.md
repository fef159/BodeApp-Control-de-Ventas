
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


# 📦 BodeApp – Día 3: Desarrollo de Interfaz (UI/UX)

**Curso:** Aplicaciones Móviles con Android (Kotlin + Jetpack Compose)
**Duración total:** 6 días
**Docente:** Juan León

---

## 🎯 Objetivo del Día 3

Desarrollar las **interfaces principales** de la aplicación BodeApp utilizando **Material Design 3**, aplicando componentes visuales modernos y funcionales.
Los formularios permiten **escritura momentánea** con estado local mediante `remember`, que será reemplazado por persistencia real en los próximos días.

---

## ⚙️ Actividades realizadas

* Implementación de las pantallas base con Jetpack Compose y Material 3:

  * **Home:** menú principal con accesos a Productos, Ventas, Compras y Cierre.
  * **Registro de Producto:** formulario con nombre, precio y stock inicial.
  * **Ventas:** campos para producto, cantidad y subtotal.
  * **Compras/Insumos:** formulario con producto, costo y cantidad.
  * **Cierre de Caja:** muestra resumen de ventas, compras y utilidad.
* Integración de la navegación entre pantallas con `NavHostController`.
* Aplicación de **Material Design 3**: `Scaffold`, `TopAppBar`, `OutlinedTextField`, `Button`, `Surface`, `MaterialTheme`.
* Formularios con **estado temporal editable** (`remember { mutableStateOf("") }`) para permitir escritura.
* Proyecto estructurado con el patrón **MVVM** para las próximas etapas de desarrollo.

---

## 🧱 Estructura del proyecto

```
com.bodeapp/
├── data/                   → Base de datos y entidades (próximo día)
│   ├── dao/
│   ├── db/
│   └── model/
│
├── presentation/           → Capa de presentación (UI y navegación)
│   ├── navigation/
│   │   └── AppNavigation.kt
│   └── screens/
│       ├── HomeScreen.kt
│       ├── ProductoScreen.kt
│       ├── VentaScreen.kt
│       ├── CompraScreen.kt
│       └── CierreScreen.kt
│
├── repository/             → Conexión entre datos y UI (próximo día)
├── ui/theme/               → Colores, tipografía y estilos de Material 3
├── viewmodel/              → Lógica de presentación (a implementar)
└── MainActivity.kt         → Punto de entrada de la aplicación
```

---

## 🖥️ Pantallas implementadas

| Pantalla              | Descripción                                             | Estado     |
| --------------------- | ------------------------------------------------------- | ---------- |
| 🏠 **Home**           | Menú con botones hacia cada módulo.                     | ✅ Completa |
| 📦 **Productos**      | Formulario editable con nombre, precio y stock inicial. | ✅ Completa |
| 💰 **Ventas**         | Campos para registrar producto, cantidad y subtotal.    | ✅ Completa |
| 🧾 **Compras**        | Formulario para registrar producto, costo y cantidad.   | ✅ Completa |
| 💼 **Cierre de Caja** | Resumen con ventas, compras y utilidad.                 | ✅ Completa |

---

## ⚙️ Dependencias utilizadas

Basadas en el archivo `build.gradle.kts` actual del proyecto 👇

```kotlin
// Jetpack Compose + Material 3
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.lifecycle.runtime.ktx)
implementation(libs.androidx.activity.compose)
implementation(platform(libs.androidx.compose.bom))
implementation(libs.androidx.ui)
implementation(libs.androidx.ui.graphics)
implementation(libs.androidx.ui.tooling.preview)
implementation(libs.androidx.material3)

// Navigation Compose
implementation("androidx.navigation:navigation-compose:2.8.3")

// Room (base de datos local - se usará en el Día 4)
val roomVersion = "2.6.1"
implementation("androidx.room:room-runtime:$roomVersion")
kapt("androidx.room:room-compiler:$roomVersion")
implementation("androidx.room:room-ktx:$roomVersion")

// ViewModel + ciclo de vida
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")

// Corrutinas
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

// Depuración y pruebas
debugImplementation(libs.androidx.ui.tooling)
debugImplementation(libs.androidx.ui.test.manifest)
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.androidx.espresso.core)
androidTestImplementation(platform(libs.androidx.compose.bom))
androidTestImplementation(libs.androidx.ui.test.junit4)
```

---

## 🌿 Ramas en GitHub

| Rama                   | Descripción                                              |
| ---------------------- | -------------------------------------------------------- |
| `main`                 | Interfaz completa con navegación funcional y Material 3. |
| `feature/home-ui`      | Pantalla Home con botones de navegación.                 |
| `feature/productos-ui` | Formulario editable de registro de producto.             |
| `feature/ventas-ui`    | Interfaz de registro de ventas.                          |
| `feature/compras-ui`   | Formulario de compras/insumos.                           |
| `feature/cierre-ui`    | Pantalla del resumen de cierre de caja.                  |

---

## 🧩 Entregables del Día 3

✅ Interfaz completa y navegable con Material 3.
✅ Formularios funcionales con escritura momentánea (`remember`).
✅ Navegación entre pantallas con `NavHostController`.
✅ Estructura organizada bajo el patrón **MVVM**.
✅ Proyecto preparado para implementar persistencia con **Room** y **ViewModel** en el Día 4.

---

# 📦 BodeApp – Día 4: Integración con Base de Datos Local (Room)

**Curso:** Aplicaciones Móviles con Android (Kotlin + Jetpack Compose)
**Duración total:** 6 días
**Docente:** Juan León

---

## 🎯 Objetivo del Día 4

Conectar la interfaz de usuario con modelos de datos reales usando **Room Database**.
Implementar operaciones **CRUD** (crear, leer, actualizar, disminuir stock y aumentar stock) para los productos registrados.

---

## ⚙️ Actividades realizadas

* Configuración de la **base de datos local Room** (`BodeAppDatabase`).
* Creación de las **entidades**:

  * `Producto`
  * `Venta`
  * `Compra`
* Implementación del **DAO** (`ProductosDao`) con métodos:

  * `insert()`, `update()`, `delete()`
  * `aumentarStock()` y `disminuirStock()`
* Implementación del **Repositorio (`ProductoRepository`)** para manejar la comunicación entre Room y el ViewModel.
* Creación del **InstructorViewModel**, utilizando `StateFlow` para actualizar la UI en tiempo real.
* Implementación del **InstructorViewModelFactory** para conectar correctamente la BD con el ViewModel.
* Conexión total entre **UI ↔ ViewModel ↔ Repository ↔ Room**.
* Adaptación de las pantallas con lógica real:

  * **Productos:** registrar, listar y mostrar stock actualizado.
  * **Ventas:** reducir stock al registrar una venta.
  * **Compras:** aumentar stock al registrar una compra.
  * **Cierre:** mostrar resumen del inventario y utilidad estimada.
* Verificación de persistencia real (datos se mantienen al cerrar la app).

---

## 🧱 Estructura del proyecto

```
com.bodeapp/
├── data/
│   ├── dao/
│   │   └── ProductosDao.kt
│   ├── db/
│   │   └── BodeAppDatabase.kt
│   └── model/
│       ├── Producto.kt
│       ├── Venta.kt
│       └── Compra.kt
│
├── repository/
│   └── ProductoRepository.kt
│
├── viewmodel/
│   ├── InstructorViewModel.kt
│   └── InstructorViewModelFactory.kt
│
├── presentation/
│   └── screens/
│       ├── ProductoScreen.kt
│       ├── VentasScreen.kt
│       ├── ComprasScreen.kt
│       └── CierreScreen.kt
│
├── navigation/
│   ├── AppNavigation.kt
│   └── HomeScreen.kt
│
└── MainActivity.kt
```

---

## 🧩 Dependencias principales

```kotlin
// Room Database
implementation("androidx.room:room-runtime:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")

// Jetpack Compose + Material 3
implementation("androidx.activity:activity-compose:1.9.2")
implementation(platform("androidx.compose:compose-bom:2024.09.01"))
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose:2.8.3")

// Lifecycle y ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
```

---

## 🧠 Pruebas realizadas

✅ **Inserción:** los productos se guardan correctamente en la base de datos.
✅ **Persistencia:** los datos permanecen tras cerrar la app.
✅ **Actualización:** las ventas y compras modifican el stock en tiempo real.
✅ **Reactividad:** los cambios en Room se reflejan automáticamente en Compose.
✅ **Integración completa:** UI ↔ ViewModel ↔ Repository ↔ Room funcionando sin errores.

---

## 📁 Entregables del Día 4

✅ Base de datos Room totalmente funcional.
✅ Pantallas conectadas a datos reales (Productos, Ventas, Compras, Cierre).
✅ Persistencia y actualización en tiempo real de stock.
✅ Pruebas de inserción y modificación exitosas.
✅ README actualizado con estructura y dependencias.

---


# 📦 BodeApp – Día 5: Persistencia Completa y Cálculo de Utilidad

**Curso:** Aplicaciones Móviles con Android (Kotlin + Jetpack Compose)
**Duración total:** 6 días
**Docente:** Juan León

---

## 🎯 Objetivo del Día 5

Finalizar la integración entre la **interfaz gráfica** y la **base de datos local Room**,
añadiendo persistencia completa, cálculos de utilidad y reportes simples en tiempo real.

El sistema ahora permite **registrar, vender, comprar y visualizar resultados** directamente desde la app.

---

## ⚙️ Actividades realizadas

* Implementación completa del flujo **UI ↔ ViewModel ↔ Repository ↔ Room**.
* Persistencia de datos garantizada al cerrar y reabrir la aplicación.
* Actualización automática de la interfaz gracias a `Flow` y `StateFlow`.
* Cálculo de **utilidad estimada diaria** en base al valor actual del inventario.
* Mejoras visuales en todas las pantallas con **Material Design 3**.
* Validaciones básicas en formularios para evitar valores vacíos o erróneos.
* Pruebas de consistencia de stock:

  * **Ventas:** disminuyen correctamente el stock.
  * **Compras:** aumentan el stock.
  * **Cierre:** refleja totales y utilidad actualizada.

---

## 📱 Flujo de pantallas

| Pantalla              | Función                                                  | Estado actual        |
| --------------------- | -------------------------------------------------------- | -------------------- |
| 🏠 **Home**           | Menú principal con acceso a las demás secciones.         | ✅ Funcional          |
| 📦 **Productos**      | Permite registrar productos y mostrarlos en tiempo real. | ✅ Conectada a Room   |
| 💸 **Ventas**         | Reduce el stock de los productos seleccionados.          | ✅ Funcional con Room |
| 🛒 **Compras**        | Aumenta el stock de los productos seleccionados.         | ✅ Funcional con Room |
| 📊 **Cierre de Caja** | Calcula valor total del inventario y utilidad estimada.  | ✅ Dinámico           |

---

## 🧱 Estructura final del proyecto

```
com.bodeapp/
├── data/
│   ├── dao/
│   │   └── ProductosDao.kt
│   ├── db/
│   │   └── BodeAppDatabase.kt
│   └── model/
│       ├── Producto.kt
│       ├── Venta.kt
│       └── Compra.kt
│
├── repository/
│   └── ProductoRepository.kt
│
├── viewmodel/
│   ├── InstructorViewModel.kt
│   └── InstructorViewModelFactory.kt
│
├── presentation/
│   └── screens/
│       ├── HomeScreen.kt
│       ├── ProductoScreen.kt
│       ├── VentasScreen.kt
│       ├── ComprasScreen.kt
│       └── CierreScreen.kt
│
├── navigation/
│   └── AppNavigation.kt
│
└── MainActivity.kt
```

---

## 🧠 Lógica implementada

| Operación          | Descripción                                    | Resultado           |
| ------------------ | ---------------------------------------------- | ------------------- |
| `insertProducto()` | Registra nuevos productos en la BD.            | Producto agregado.  |
| `disminuirStock()` | Disminuye stock en cada venta.                 | Stock actualizado.  |
| `aumentarStock()`  | Incrementa stock en cada compra.               | Stock actualizado.  |
| `getProductos()`   | Devuelve flujo de productos en tiempo real.    | UI reactiva.        |
| `Cierre de Caja`   | Calcula valor total del inventario y utilidad. | Cálculo automático. |

---

## 🧩 Dependencias principales

```kotlin
// Room Database
implementation("androidx.room:room-runtime:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")

// Jetpack Compose + Material 3
implementation("androidx.activity:activity-compose:1.9.2")
implementation(platform("androidx.compose:compose-bom:2024.09.01"))
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose:2.8.3")

// Lifecycle y ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
```

---

## 🧪 Pruebas realizadas

| Prueba               | Resultado                                             |
| -------------------- | ----------------------------------------------------- |
| Registro de producto | Producto guardado y persistente tras cerrar app.      |
| Registro de venta    | Stock reducido correctamente en Room.                 |
| Registro de compra   | Stock incrementado correctamente.                     |
| Flujo de datos       | La UI se actualiza automáticamente con los cambios.   |
| Cierre de caja       | Muestra valor total y utilidad del inventario actual. |

---

## 📁 Entregables del Día 5

✅ Aplicación completa con persistencia local funcional.
✅ Todas las pantallas conectadas a la base de datos.
✅ Cálculo de utilidad y valor de inventario implementado.
✅ Validaciones y actualización en tiempo real.
✅ README documentado con estructura y pruebas.

---

