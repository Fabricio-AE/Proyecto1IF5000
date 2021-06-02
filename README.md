# Proyecto1IF5000
Este proyecto trata de una conexión TFTP usando sockets en Java 8.

El proyecto se desarrollo con el IDE NetBeans y la base de datos con MSSQL Server.

Pasos para la correcta ejecución del proyecto:

*******************Preparación del entorno para la ejecución*******************

1.  Como primer paso es necesario crear la base de datos en la instancia local.
    Para ello es necesario ejecutar el Script que se encuentra en la carpta "SQL Server DB".

2.  Seguidamente para ejecutar el proyecto es necesario abrir NetBeans y buscar
    el directorio del cliente y el servidor.

3.  Una vez abierto, se debe comprobar que las bibliotecas estén incluidas, si no
    lo están, entonces se deben agregar. Para el caso del cliente, solo es necesario
    jdom.jar que se encuentra en la carpeta lib del proyecto. Por otra parte, el
    servidor ocupa tanto jdom.jar como mssql-jdbc-9.2.1.jre8.jar.
    
*******************Ejecución del proyecto y uso dentro de él*******************

4.  Luego de completar con exito los pasos anteriores, se ejecuta el servidor y luego
    el o los clientes.

5.  Dentro del cliente, es necesario loguearse o registrarse como usuario. Si ya tiene
    un login entonces sale al paso 8, sino, entonces debe pulsar el botón "Registrarse"
    que se encuentra en la sección de "Sesión".

7.  En la ventan que se desplega, es necesario un nombre de usuario junto con una
    contraseña, luego de completar los campos requeridos, se presiona el botón
    "Registrar".

8.  Teniendo el usuario y contraseña, se procede a llenar la IP del servidor, esta se
    desplega al ejecutar el proyecto del servidor, luego el usuario y contraseña, y por
    último el puerto, este también se muestra en la ventana del servidor, depués de los
    dos puntos (:) de la IP. Luego de rellenar todos los campos, se presiona el botón
    "Conectar".

9.  Para buscar una imagen en el cliente se presiona el botón "Buscar imagen", esta se
    desplegará desordenada en el recuadro en la sección "Cliente". También es posible
    mover las piezas de la imagen a gusto, esto pulsando sobre la deseada y luego al
    espacio que se quiera cambiar.

10. Para enviar la imagen al servidor entonces se presiona el botón "Enviar" que
    se encuentra abajo de la imagen.

11. Para ver las imágenes que posee en el servidor, es necesario darle al botón
    "Lista imagenes" que se encuentra en la sección de "Servidor", se desplegarán
    los nombres de las imágenes en el combobox inferior.

12. Si se gusta ver una imagen, basta con seleccionarla y darle al botón "Ver", esta
    debe cargar en el recuadro que se encuentra en el servidor.
    
13. En caso de querer desordenar la imagen, esto se debe hacer ya sea con el botón
    "Combinar" o manualmente seleccionando una pieza y luego su destino.
    
14. Para enviar una imagen al cliente desde el servidor, se presiona el botón "Obtener"
    
15. Para ver las imágenes recibidas en el cliente, se presiona el botón "Ver recibidas"
    y al seleccionar alguna, se pintará en el recuadro de la sección "Cliente"
