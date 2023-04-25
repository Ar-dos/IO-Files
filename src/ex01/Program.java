package ex01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Program {
  public static void main(String[] args) throws IOException {
    if (args.length == 2) {
      ArrayList<String> la = new ArrayList<>();
      ArrayList<String> lb = new ArrayList<>();
      ArrayList<String> dic = new ArrayList<>();
      BufferedReader fa = new BufferedReader(new FileReader(args[0]));
      String input = null;
      while ((input = fa.readLine()) != null) {
        String[] split = input.split("[^a-zA-Z0-9]");
        for (int i = 0; i < split.length; i++) {
          la.add(split[i]);
          if (!dic.contains(split[i]) && !split[i].isEmpty())
            dic.add(split[i]);
        }
      }
      fa.close();
      fa = new BufferedReader(new FileReader(args[1]));
      while ((input = fa.readLine()) != null) {
        String[] split = input.split("\\W+");
        for (int i = 0; i < split.length; i++) {
          lb.add(split[i]);
          if (!dic.contains(split[i]) && !split[i].isEmpty())
            dic.add(split[i]);
        }
      }
      fa.close();
      int size = dic.size();
      BufferedWriter fdic =
          new BufferedWriter(new FileWriter("dictionary.txt"));
      int[] va = new int[size];
      int[] vb = new int[size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < la.size(); j++) {
          if (dic.get(i).equals(la.get(j)))
            va[i]++;
        }
        for (int j = 0; j < lb.size(); j++) {
          if (dic.get(i).equals(lb.get(j)))
            vb[i]++;
        }
        fdic.write(dic.get(i) + "\n");
      }
      fdic.close();
      Double num = 0.0;
      for (int j = 0; j < size; j++)
        num = num + (va[j] * vb[j]);
      Double dena = 0.0;
      Double denb = 0.0;
      for (int j = 0; j < size; j++) {
        dena = dena + (va[j] * va[j]);
        denb = denb + (vb[j] * vb[j]);
      }

      Double res = num / (Math.sqrt(dena) * Math.sqrt(denb));
      System.out.printf("%.4s", res.toString());
    }
  }
}