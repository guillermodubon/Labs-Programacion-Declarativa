Proyecto del Laboratorio 5 con Java Swing y tuProlog.

JAVA contiene la interfaz y la conexión. PROLOG/calculo.pl contiene las reglas de las cuatro operaciones.

Para abrirlo en Windows, hacé doble clic en ejecutar.bat. Requiere JDK 17 o superior e internet en la primera ejecución para descargar las dependencias.

En IntelliJ, abrí JAVA/pom.xml como proyecto Maven y ejecutá org.example.Main. También podés ejecutar `mvn -f JAVA/pom.xml compile exec:java`.

Escribí la expresión completa usando x en minúscula, * para multiplicar y ^ para las potencias. Luego elegí la operación. Enter deriva y Copiar copia el resultado.

| Botón | Expresión | Resultado |
| --- | --- | --- |
| Sumar | x^2 + 5*x^2 | 6*x^2 |
| Restar | 7*x^3 - 3*x^3 | 4*x^3 |
| Sumar | 4*x + 2*x | 6*x |
| Restar | 8*x^2 - 5*x^2 | 3*x^2 |

Sumar y Restar requieren una expresión algebraica con x. Las potencias distintas se conservan y los términos semejantes se agrupan. Integrar muestra una primitiva con su constante C.
