package mx.unam.fi.poo.g1.proyecto3;
import mx.unam.fi.poo.g1.proyecto3.GeneraWAV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase Proyecto3 
 * @author Axel Cote 
 * @version Noviembre-2024
**/

public class Proyecto3 {
  /**
   * Metodo main
   * Se ejecuta todo el funcionamiento de la aplicacion
   * @param args -> Arreglo por defecto del metodo main
  **/
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("No se agrego archivo prueba.txt al ejecutar el rpograma");
      return;
    }

    String archivoParametros = args[0];

    try (BufferedReader br = new BufferedReader(new FileReader(archivoParametros))) {
      String nombre = br.readLine();
      int frecuenciaMuestreo = Integer.parseInt(br.readLine());
      int canal = Integer.parseInt(br.readLine()); 
      int armonico = Integer.parseInt(br.readLine());
      int tiempo = Integer.parseInt(br.readLine());

      if (canal != 1) {
          throw new IllegalArgumentException("Solo se permite audio monoaural");
      }

      GeneraWAV g = new GeneraWAV();
      g.Escribe(nombre, tiempo, frecuenciaMuestreo, armonico);
      System.out.println("Archivo WAV creado: " + nombre);

    } catch (IOException e) {
      System.err.println("Error al leer el archivo: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Error en los parametros: " + e.getMessage());
    }
  }
}
