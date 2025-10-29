# 🏪 BodeApp - Control de Ventas e Inventario

**Una aplicación móvil completa para la gestión de inventario y ventas en pequeñas empresas y bodegas.**

> 📱 Desarrollada con **Kotlin + Jetpack Compose** | 🎯 **Curso:** Aplicaciones Móviles con Android

---

## 📋 Descripción del Proyecto

BodeApp es una solución integral para pequeñas empresas que necesitan llevar un control automatizado de:
- **Inventario de productos**
- **Ventas diarias** 
- **Compras y abastecimiento**
- **Reportes financieros**
- **Cierre de caja**

La aplicación resuelve el problema común de las bodegas que no tienen un sistema automatizado para gestionar sus operaciones diarias.

---

## ✨ Funcionalidades Principales

### 🏠 **Dashboard (Home)**
- Resumen de ventas del día
- Métricas de ganancias en tiempo real
- Alertas de productos con stock bajo
- Indicadores de rendimiento

### 📦 **Gestión de Productos**
- ➕ Crear productos (stock inicial = 0)
- ✏️ Editar información y precios
- 🔍 Búsqueda de productos
- 🗑️ Eliminación con confirmación

### 🛒 **Sistema de Ventas**
- 🛍️ Carrito de compras intuitivo
- ✅ Validación de stock disponible
- 💰 Cálculo automático de subtotales
- 📉 Actualización automática de inventario

### 📋 **Control de Compras**
- 📥 Registro de compras a proveedores
- 📈 Aumento automático de stock
- 💵 Control de costos y precios sugeridos
- 🔄 Creación automática de productos nuevos

### 📊 **Reportes y Cierre de Caja**
- 📅 Reportes por fecha seleccionable
- 💹 Análisis de ingresos vs gastos
- 📈 Margen de ganancia
- 🗂️ Historial completo de transacciones
- 🗑️ Función de limpieza de datos

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Propósito |
|-----------|-----------|
| **Kotlin** | Lenguaje principal |
| **Jetpack Compose** | UI moderna y declarativa |
| **Room Database** | Persistencia local |
| **MVVM Architecture** | Arquitectura escalable |
| **Navigation Compose** | Navegación entre pantallas |
| **Material 3** | Diseño y componentes UI |
| **Coroutines** | Programación asíncrona |
| **StateFlow** | Gestión reactiva de estados |

---

## 📱 Capturas de Pantalla

### 🏠 Pantalla Principal
<img width="378" height="805" alt="image" src="https://github.com/user-attachments/assets/99a2ef7f-9fcb-4038-84ba-098549eda299" />

### 📦 Gestión de Productos
<img width="387" height="813" alt="image" src="https://github.com/user-attachments/assets/6e0b3401-dfc3-4ce8-adfa-693166effbda" />

### 🛒 Sistema de Ventas
<img width="384" height="811" alt="image" src="https://github.com/user-attachments/assets/e79913f9-3bc8-475a-8887-2e1820511893" />

### 📋 Control de Compras
<img width="380" height="815" alt="image" src="https://github.com/user-attachments/assets/f69f504d-144c-401c-8a64-481f200d6b73" />


### 📊 Reportes y Análisis
<img width="389" height="816" alt="image" src="https://github.com/user-attachments/assets/785b5665-a084-492f-b860-e383118e4bcd" />


---

## 🎨 Diseño UI/UX

**🎨 Prototipo en Figma:** [https://www.figma.com/make/57Di6JZnEyaCey2vKzrtml/Sistema-de-Gesti%C3%B3n-de-Bodega?node-id=0-4&t=2Aok48fy0bN668Fi-0]

### Características del Diseño:
- ✅ **Material 3** con colores modernos
- ✅ **Navegación intuitiva** con tabs superiores
- ✅ **Indicadores visuales** para estados de stock
- ✅ **Confirmaciones de seguridad** para acciones críticas
- ✅ **Responsive design** para diferentes tamaños

---

## 🚀 Instalación y Uso

### Prerrequisitos
```bash
- Android Studio Hedgehog | 2023.1.1+
- Kotlin 1.9.0+
- Android SDK 26+ (API Level 26)
- JVM Target 11
```

### Instalación
```bash
1. git clone https://github.com/TU_USUARIO/BodeApp-Control-de-Ventas.git
2. cd BodeApp-Control-de-Ventas
3. ./gradlew build
4. Ejecutar en emulador o dispositivo físico
```

### Flujo de Uso Básico
```
1. 📦 Crear productos en "Productos" (stock = 0)
2. 📋 Comprar inventario en "Compras" 
3. 🛒 Realizar ventas en "Ventas"
4. 📊 Revisar reportes en "Reportes"
```

---

## 📁 Estructura del Proyecto

```
app/src/main/java/com/bodeapp/
├── 📱 presentation/
│   ├── screens/          # Pantallas de la app
│   ├── navigation/       # Navegación
│   └── MainActivity.kt   # Actividad principal
├── 🗃️ data/
│   ├── model/           # Modelos de datos
│   ├── dao/             # Acceso a datos
│   └── db/              # Base de datos Room
├── 📚 repository/       # Repositorios
├── 🧠 viewmodel/        # ViewModels MVVM
├── 🎨 ui/theme/         # Temas y colores
└── ⚙️ util/            # Utilidades
```

---

## 👥 Equipo de Desarrollo

### Roles y Responsabilidades

| Integrante | Rol | Responsabilidades |
|-----------|-----|------------------|
| **[Ninahuaman Yuto Anderson]** | 🔧 **Líder Técnico** | Arquitectura, Backend, Base de datos |
| **[Paulo Santos Zuasnabar]** | 🎨 **Diseñador UI/UX** | Interfaz, Experiencia de usuario, Figma, Navegacion |

---

## 🎯 Cumplimiento de Requerimientos

### ✅ Día 1 - Planificación y Diseño
- [x] Definición de alcance y público objetivo
- [x] 5+ pantallas en Figma
- [x] Historias de usuario completadas
- [x] Repositorio GitHub configurado

### ✅ Día 2 - Configuración del Proyecto
- [x] Proyecto Kotlin + Jetpack Compose
- [x] Estructura de paquetes organizada
- [x] Navegación inicial implementada
- [x] Ramas de trabajo configuradas

### ✅ Día 3 - Desarrollo de Interfaz
- [x] 5 pantallas principales completadas
- [x] Material 3 implementado
- [x] Navegación funcional
- [x] Formularios y validaciones

### ✅ Día 4 - Lógica y Manejo de Datos
- [x] Modelos Room definidos
- [x] Base de datos SQLite funcional
- [x] CRUD completo implementado
- [x] Actualización automática de stock

### ✅ Día 5 - Funcionalidades y Reportes
- [x] Cierre de caja implementado
- [x] Reportes por fecha
- [x] Validaciones de stock
- [x] Pruebas en dispositivos

### ✅ Día 6 - Entrega Final
- [x] README completo
- [x] Release v1.0 preparado
- [x] Presentación estructurada

---

## 🚀 Release v1.0

### Características de la Versión
- ✅ **Funcionalidad completa** para gestión de bodegas
- ✅ **Base de datos local** con Room
- ✅ **Interfaz moderna** con Material 3
- ✅ **Validaciones robustas** en todas las operaciones
- ✅ **Reportes detallados** con análisis financiero

### 📥 Descarga
**[📱 APK Release v1.0](https://drive.google.com/file/d/1vouG-oQsP3roBod_9TM8sVJ0tlrZcsWI/view?usp=sharing)**

---

### Flujo Demostrado:
1. 🏠 Dashboard con métricas
2. 📦 Creación de productos
3. 📋 Registro de compras
4. 🛒 Proceso de venta
5. 📊 Generación de reportes

---

## 🔮 Futuras Mejoras

- [ ] 🌐 Sincronización en la nube
- [ ] 📧 Reportes por email
- [ ] 📊 Gráficos avanzados
- [ ] 👥 Múltiples usuarios
- [ ] 🔔 Notificaciones push
- [ ] 📱 Modo offline mejorado

---

## 📄 Licencia

Este proyecto fue desarrollado como parte del curso **"Aplicaciones Móviles con Android"** con fines educativos.

---

## 📞 Contacto

**🐙 GitHub:** [https://github.com/fef159/BodeApp-Control-de-Ventas]  
**🎨 Figma:** [https://www.figma.com/make/57Di6JZnEyaCey2vKzrtml/Sistema-de-Gesti%C3%B3n-de-Bodega?node-id=0-4&t=2Aok48fy0bN668Fi-0]

---

<div align="center">

**Desarrollado con ❤️ por [SalchiTeam]**

</div>
