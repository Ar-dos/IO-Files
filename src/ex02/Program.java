package ex02;

import static java.nio.file.StandardCopyOption.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {

  static File folder;

  static Path getNewPath(String input) {
    String[] cmd = input.split("/");
    Path newpath = folder.toPath();
    for (int i = 0; i < cmd.length; i++) {
      if (cmd[i].equals("..")) {
        if (newpath.getParent() != null)
          newpath = newpath.getParent();
      } else {
        newpath = Paths.get(newpath.toString() + "/" + cmd[i]);
      }
    }
    return newpath;
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 1) {
      if (args[0].substring(0, 17).equals("--current-folder=")) {
        String path = args[0].substring(17);
        System.out.println(path);
        folder = new File(path);

        Scanner sc = new Scanner(System.in);
        String input = null;
        while (true) {
          input = sc.nextLine();
          if (!input.isEmpty()) {
            if (input.equals("exit")) {
              break;
            }
            if (input.equals("ls")) {
              File[] listOfFiles = folder.listFiles();
              for (int i = 0; i < listOfFiles.length; i++) {
                long fsize = listOfFiles[i].length() / 1024;
                if (fsize == 0) {
                  fsize = listOfFiles[i].length();
                  System.out.printf("%s %s byte\n", listOfFiles[i].getName(),
                                    Long.toString(fsize));
                } else {
                  System.out.printf("%s %s KB\n", listOfFiles[i].getName(),
                                    Long.toString(fsize));
                }
              }
            }
            if (input.length() > 2) {
              if (input.substring(0, 3).equals("cd ")) {
                String[] split = input.split("\\s+");
                if (split.length == 2) {
                  File temp = new File(getNewPath(split[1]).toString());
                  if (temp.isDirectory()) {
                    folder = temp;
                    System.out.println(folder.getAbsolutePath());
                  } else {
                    System.out.println("Dyrectory dosent exist.");
                  }
                }
              } else if (input.substring(0, 3).equals("mv ")) {
                String[] split = input.split("\\s+");
                if (split.length == 3) {
                  File from = new File(getNewPath(split[1]).toString());
                  File to = new File(getNewPath(split[2]).toString());
                  if (from.isFile()) {
                    if (!to.isFile()) {
                      if (to.isDirectory())
                        to = new File(to.toPath().toString() + "/" +
                                      (from.getName()));
                      from.renameTo(to);
                    } else if (to.isDirectory()) {
                      Files.move(from.toPath(), to.toPath(), REPLACE_EXISTING);
                    } else {
                      System.out.println("Wrong Input.");
                    }
                  }
                }
              } else {
                System.out.println("Wrong Input.");
              }
            }
          }
        }
        sc.close();
      }
    }
  }
}