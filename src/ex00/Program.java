package ex00;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Program {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    File f = null;
    FileInputStream fis = null;
    FileOutputStream fos = null;
    String sig = null;
    while (true) {
      String input = sc.nextLine();
      if (input.equals("42"))
        break;
      try {
        f = new File(input);
        if (f.exists())
          fis = new FileInputStream(f);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (fis != null) {
        byte[] b = new byte[10];
        for (int i = 0; i < 10; i++)
          b[i] = (byte)fis.read();
        fis.close();
        sig = findFileType(byteToHex(b));
        if (sig != null) {
          fos = new FileOutputStream("signatures.txt", true);
          fos.write(sig.getBytes());
          System.out.println("PROCESSED");
          fos.close();
        }
      }
    }
    sc.close();
  }

  static String byteToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02X ", b));
    }
    System.out.println(sb.toString());
    return sb.toString();
  }

  static String findFileType(String s) {
    String res = null;
    if (s.substring(0, 23).equals("89 50 4E 47 0D 0A 1A 0A"))
      res = "PNG\n";
    else if (s.substring(0, 11).equals("50 4B 03 04") ||
             s.substring(0, 11).equals("50 4B 05 06") ||
             s.substring(0, 11).equals("50 4B 07 08"))
      res = "ZIP\n";
    else if (s.substring(0, 20).equals("52 61 72 21 1A 07 00") ||
             s.substring(0, 23).equals("52 61 72 21 1A 07 01 00"))
      res = "RAR\n";
    else if (s.substring(0, 14).equals("25 50 44 46 2D"))
      res = "PDF\n";
    else if (s.substring(0, 11).equals("38 42 50 53"))
      res = "PSD\n";
    else if (s.substring(0, 17).equals("7B 5C 72 74 66 31"))
      res = "RTF\n";
    else if (s.substring(0, 14).equals("43 44 30 30 31"))
      res = "ISO\n";
    else if (s.substring(0, 17).equals("47 49 46 38 37 61"))
      res = "GIF\n";
    else if (s.substring(0, 11).equals("FF D8 FF DB") ||
             s.substring(0, 11).equals("FF D8 FF E0") ||
             s.substring(0, 11).equals("FF D8 FF E1"))
      res = "JPEG\n";
    else if (s.substring(0, 20).equals("78 01 73 0D 62 62 60"))
      res = "GIF\n";
    else
      System.out.println("UNDEFINED");
    return res;
  }
}
