<h1 align="center">EDA6 2022 - QUORIDOR</h1>
<h2 align="center">JAVA BOT  by <a href="https://www.linkedin.com/in/martin-fiordelisi-b42184226/" target="_blank">Martín Fiordelisi</a></h2>
<p align="left"> Bienvenido al que lea! Les presento el resultado de mi primer desafio real fuera de un ambiente de aprendizaje. Fue toda una experiencia con varios contratiempos, e incluso un reinicio completo del proyecto desde cero una semana antes de la entrega! Pero mas alla de las complicaciones me quedo con esa sensación de exito cada vez que pude resolver los problemas que iba encontrando, hasta conseguir armar una inteligencia artificial de la cual estoy muy orgulloso!</p>

<h3 align="center">IA - Descripción de la estrategia aplicada</h3>
<p align="left"> 
1- Al recibir información del webSocket, mi aplicación reinterpreta el tablero para procesarlo como un Objeto "Board", posicionando mis fichas siempre como el lado sur, y al oponente en el lado norte. Unicamente se controla, e invierte el tablero de ser requerido, en este punto inicial del proceso. Y al final, cuando ya se tiene definida una respuesta, se reinterpretan las coordenadas para transformar la posición dentor de mi"Board", al formato de los tableros procesados por el servidor. Todas las tareas de comunicación y "traducción" estan definidas en la clase "Connection" y "Util".
</p>
<p align="left"> 
2- El siguiente gran paso es leer, reprocesar e interpretar el tablero para definir objetos "Pawn" individuales, los cuales a su vez van a detectar cada uno el camino mas corto para llegar al lado opuesto, y guardar las coordenadas de origen y de destino aplicables. Este paso es el mas complejo a nivel de codigo, y se encuentra incluido principalmente en la clase "Reader".
</p>
<p align="left"> 
3- El tercer paso, y último proceso lógico propiamente dicho, consiste en recibir el objeto "Board" con todos sus parametros internos ya definidos, y en base a un proceso principal en formato cascada, definir cual sera la respuesta mas apropiada a ese tablero especifico. Aqui es donde se incorpora, de corresponder, la interptetación de los peones del oponente, y de ser aplicable, la generación de un Objeto "Wall" que bloquee su avance. Este paso esta definido principalmente en la clase "Finder".
</p>
<p align="left"> 
4- Finalmente, una vez que la IA ya definió cual va a ser la accion de respuesta, incorpora los datos del objeto "Pawn" o "Wall" segun corresponda, dentro de un String que será enviado al WebSocket del servidor para su ejecución en la partida. 
</p>
<p align="left"> 
5- A nivel de testeos, esta fue mi primera experiencia en tener que aplicar pruebas en general (y particularmente en este caso, de tipo unitaras) dentro de mi aplicación, logrando por suerte una covertura del 74% sobre el total de  metodos testeables dentro de mi aplicación (excluye metodos sin parametros, funciones void, y funciones propias de las dependencias autogeneradas de Spring y del WebSocket). 
</p>
<br/>
<p align="left"> 
Como adelanté, el presente bot fue mi segundo intento, resultado de una revisión completa desde cero sobre la primer versión. Unicamente a los findes de dejar documentado todo el proceso aquí, dejo link al repositorio del primer intento a continuación: <a href="https://github.com/MartinFiorde/EDA6_MAF_Bot_alpha" target="_blank">EDA6_MAF_Bot_alpha</a>
</p>
<br/>
<p align="left"> 
Desde ya agracezco enormemente a EvenBrite por la posibilidad de practicar y mejorar mis habilidades de codeo con esta experiencia, a mi hermano Agustín que a pesar de estar full con su trabajo me pudo dar una mano para resolver la inicialización del WebSocket y Junit, y a todos los compañeros que lograron otros robots igual de increibles para esta competencia!
</p>
<br/>
<h3 align="center">Muchas gracias por visitar el proyecto! <img src="https://raw.githubusercontent.com/verma-anushka/verma-anushka/master/gifs/wave.gif" width="30px" style="max-width:100%;"></h3>
