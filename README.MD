# Hotel System - Backend

## Instrucciones Básicas de Git

### 1. Hacer un Commit

Para guardar tus cambios en el repositorio:

```bash
# Ver el estado de tus cambios
git status

# Agregar los cambios (todos o específicos)
git add .                  # Agregar todos los cambios
git add nombre_archivo    # Agregar un archivo específico

# Hacer el commit con un mensaje descriptivo
git commit -m "Tu mensaje descriptivo aquí"

# Subir los cambios al repositorio remoto
git push origin nombre-rama
```

---

### 2. Cambiar a Ramas de Feature

Para trabajar en las diferentes microservicios:

#### Rama de Huéspedes

```bash
git checkout feature/msv-huespedes
```

#### Rama de Usuarios

```bash
git checkout feature/msv-usuarios
```

---

### 3. Guardar Cambios en Stash

Si necesitas cambiar de rama sin perder tus cambios:

```bash
# Guardar tus cambios sin hacer commit
git stash

# Ver lista de cambios guardados
git stash list

# Recuperar los cambios guardados más recientes
git stash pop
```

---

### 4. Traer Cambios de la Rama dev

Para sincronizar tu rama con los cambios más recientes de `dev`:

```bash
# Actualizar la rama dev
git checkout dev
git pull

# Volver a tu rama de trabajo
git checkout nombre-rama

# Rebase para traer los cambios de dev
git rebase dev
```

---

### 5. Flujo Completo: Stash Pop desde dev

Este es el flujo recomendado para actualizar tu rama con los cambios de `dev`:

```bash
# Paso 1: Guardar tus cambios actuales
git stash

# Paso 2: Ir a la rama dev y actualizar
git checkout dev
git pull

# Paso 3: Volver a tu rama de trabajo
git checkout feature/msv-huespedes
# o
git checkout feature/msv-usuarios

# Paso 4: Traer los cambios de dev
git rebase dev

# Paso 5: Recuperar tus cambios guardados
git stash pop
```

---

### 6. Crear un Pull Request (PR) a dev

Una vez que hayas terminado tus cambios y los hayas sincronizado con `dev`, es hora de crear un Pull Request:

#### Por línea de comandos (usando GitHub CLI)

```bash
# Asegúrate de que tus cambios están en tu rama
git checkout tu-rama

# Subir tu rama al repositorio remoto
git push origin tu-rama

# Crear un Pull Request desde la línea de comandos
gh pr create --base dev --head tu-rama --title "Descripción de tu PR" --body "Detalles opcionales"
```

#### Por la interfaz web (GitHub)

1. Ve a tu repositorio en GitHub
2. Verás un botón **"Compare & pull request"** (aparecerá automáticamente después de hacer `git push`)
3. Asegúrate de que:
   - **Base**: `dev` (rama donde quieres mergear)
   - **Compare**: `tu-rama` (tu rama de feature)
4. Agrega un título descriptivo y detalles en la descripción
5. Haz clic en **"Create pull request"**

#### Ejemplo para nuestros microservicios

```bash
# Para huéspedes
git push origin feature/msv-huespedes
# Luego crear PR desde GitHub dirigida a dev

# Para usuarios
git push origin feature/msv-usuarios
# Luego crear PR desde GitHub dirigida a dev
```

#### Antes de crear el PR

Verifica que:
- Tu rama esté actualizada con `dev` (ejecuta `git rebase dev`)
- No haya conflictos
- Tus cambios estén correctamente pusheados
- El título y descripción sean claros

---

### Recomendaciones

- **Antes de empezar a trabajar**: Siempre asegúrate de estar en la rama correcta con `git branch`
- **Commits frecuentes**: Haz commits regularmente con mensajes claros
- **Sincronizar regularmente**: Actualiza tu rama frecuentemente desde `dev` para evitar conflictos
- **Resolver conflictos**: Si hay conflictos durante el `git rebase`, resuélvelos
- **Mantén limpio tu PR**: Sincroniza con `dev` antes de crear el PR para evitar conflictos
- **Descripción clara**: Incluye en la descripción del PR qué cambios hiciste y por qué

---