package mx.unam.fi.poo.g1.proyecto3;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase GeneraWAV 
 * @author Axel Cote 
 * @version Noviembre-2024
**/

public class GeneraWAV {
  public void Escribe(String nombre, int iTiempo, int iFrecuenciaMuestreo, int iArmonico) {
    if (nombre == null || iTiempo <= 0 || iFrecuenciaMuestreo <= 0 || iArmonico <= 0) {
        throw new IllegalArgumentException("Parametros o parametro no valido, favor de verificar");
    }

    try (FileOutputStream fos = new FileOutputStream(nombre)) {
      int numSamples = iFrecuenciaMuestreo * iTiempo;
      int dataSize = numSamples * 2; 
      int fileSize = 36 + dataSize;

      fos.write("RIFF".getBytes());
      fos.write(intToBytesLittleEndian(fileSize));
      fos.write("WAVE".getBytes());
      fos.write("fmt ".getBytes());
      fos.write(intToBytesLittleEndian(16)); 
      fos.write(shortToBytesLittleEndian((short) 1)); 
      fos.write(shortToBytesLittleEndian((short) 1)); 
      fos.write(intToBytesLittleEndian(iFrecuenciaMuestreo));
      fos.write(intToBytesLittleEndian(iFrecuenciaMuestreo * 2)); 
      fos.write(shortToBytesLittleEndian((short) 2)); 
      fos.write(shortToBytesLittleEndian((short) 16)); 

      fos.write("data".getBytes());
      fos.write(intToBytesLittleEndian(dataSize));

      for (int i = 0; i < numSamples; i++) {
        double angle = 2.0 * Math.PI * iArmonico * i / iFrecuenciaMuestreo;
        short sample = (short) (Math.sin(angle) * 32767); 
        fos.write(shortToBytesLittleEndian(sample));
      }

    } catch (IOException e) {
      System.err.println("Error al crear el archivo: " + e.getMessage());
    }
  }

  private byte[] intToBytesLittleEndian(int value) {
    return new byte[]{
      (byte) (value & 0xFF),
      (byte) ((value >> 8) & 0xFF),
      (byte) ((value >> 16) & 0xFF),
      (byte) ((value >> 24) & 0xFF)
    };
  }

  private byte[] shortToBytesLittleEndian(short value) {
    return new byte[]{
      (byte) (value & 0xFF),
      (byte) ((value >> 8) & 0xFF)
    };
  }
}
