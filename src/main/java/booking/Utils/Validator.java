package booking.Utils;

import java.util.Scanner;

public class Validator {
  public static boolean isString(String s) {
    return !isInteger(s);
  }

  public static boolean isInteger(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException err) {
      return false;
    }
  }

  public static String validName() {
    String name = new Scanner(System.in).next();
    while (!isString(name)) {
      System.out.print("Некорректный ввод, повторите попытку: ");
      name =  new Scanner(System.in).next();
    }
    return name;
  }
}
