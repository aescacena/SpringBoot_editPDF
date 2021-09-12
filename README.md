Requisitos del ejercicio:

- Desarrollar una aplicación que publique los siguientes servicios:

1) Servicio web en la url 
  ``` 
  http://localhost:9080/img2pdf
  ``` 
  que reciba una petición POST que contenga una imagen en el cuerpo de la petición. El servicio deberá generar en disco un fichero en formato PDF que tenga la imagen enviada en la primera página del documento ocupando el mayor espacio posible de la misma sin deformarse.
  El servicio contestará a dicha llamada con un identificador único en una respuesta en formato JSON:
  ```
  {
	  "idDocument":"[[id]]"
  }
  ```
  
  El servicio deberá devolver mensajes claros en caso de error y logar en disco la excepción producida.
  
  ![CAPTURA POST img2pdf](/images/DOWNLOAD.JPG)

2) Servicio web en la url 
```
http://localhost:9080/pdf/{{idDocument}}  
```
que en respuesta a una llamada GET indicando un identificador generado anteriormente, permita descargar el documento asociado al mismo.
  El servicio deberá devolver mensajes claros en caso de error y logar en disco la excepción producida.

- Herramientas requeridas:
	- Java 8+
	- Maven
	- SpringBoot
	- Jackson
	- PDFBox

- Elementos evaluables
	- Funcionamiento correcto
	- Validación de la entrada de datos de los servicios
	- Manejo de errores
	- Calidad del código fuente y la estructura de clases

- El ejercicio deberá quedar publicado en github en un repositorio público.
