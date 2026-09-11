# Proyecto Bingo - Java / Visual Studio Code

Proyecto de Bingo desarrollado en Java usando Swing y pensado para ejecutarse desde Visual Studio Code.

## Archivos
- `src/Bingo.java` - interfaz gráfica y control del juego.
- `src/Tabla.java` - generación y validación de las tablas.
- `src/Cantor.java` - generación y control de las balotas.

## Ejecutar
1. Abre la carpeta del proyecto en Visual Studio Code.
2. Verifica que tengas instalado un JDK de Java.
3. Abre `src/Bingo.java`.
4. Ejecuta el método `main`.

También puedes compilar desde una terminal ubicada en la carpeta del proyecto:

```bash
javac -d out src/*.java
java -cp out Bingo
```

No requiere librerías externas; utiliza Java Swing.