# Falta el Driver de MySQL

## Problema
No se encuentra el driver JDBC de MySQL (`mysql-connector-java-*.jar`) en la carpeta `lib/`.

## Solución

### Opción 1: Descargar manualmente
1. Ve a: https://dev.mysql.com/downloads/connector/j/
2. Selecciona "Platform Independent"
3. Descarga el archivo ZIP
4. Descomprime y busca el archivo `mysql-connector-j-8.x.x.jar`
5. Copia ese archivo a la carpeta `lib/` de tu proyecto

### Opción 2: Buscar en tu instalación de MySQL
Si instalaste MySQL Workbench, es posible que ya tengas el driver:
- Busca en: `C:\Program Files\MySQL\Connector J 8.0\`
- O busca archivos con nombre `mysql-connector-*.jar` en tu sistema
- Copia el archivo `.jar` a la carpeta `lib/` del proyecto

### Después de agregar el driver
Una vez que tengas el archivo en `lib/`, ejecuta de nuevo:
```powershell
cd 'C:\Users\sergio.gomezg\Desktop\UOC DAW\POO con BBDD\Producto 3\Proyecto producto 3'
java -cp "bin;lib/*" test.PruebasBasicas
```

## Verificar
La carpeta `lib/` debería contener:
- `junit-platform-console-standalone-1.9.3.jar` (ya lo tienes ✓)
- `mysql-connector-j-8.x.x.jar` (FALTA - necesitas agregarlo)
