Requisitos:

- Aplicación que publique los siguientes servicios:

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
  
  ![CAPTURA POST img2pdf](/images/POST_IMAGEJPG.JPG)

2) Servicio web en la url 
```
http://localhost:9080/pdf/{{idDocument}}  
```
que en respuesta a una llamada GET indicando un identificador generado anteriormente, permita descargar el documento asociado al mismo.
  El servicio deberá devolver mensajes claros en caso de error y logar en disco la excepción producida.
  
  ![CAPTURA GET PDF POSTMAN](/images/GET_PDF.JPG)
  ![CAPTURA GET PDF](/images/DOWNLOAD.JPG)
  
  
3) EXTRA: Se añade petición GET para listar ficheros en directorio
```
http://localhost:9080/files  
```
  ![CAPTURA GET FILES](/images/GET_FILES.JPG)


4) EXTRA: Se añade petición GET para eliminar todos los ficheros del directorio
```
http://localhost:9080/delete  
```
  ![CAPTURA GET DELETE](/images/GET_DELETE.JPG)
  
- Herramientas Usadas:
	- Java 8+
	- Maven
	- SpringBoot
	- Jackson
	- PDFBox
