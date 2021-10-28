package booking.ConstEnum;

import java.util.Scanner;

public interface RegUtil {

    final Scanner SCANNER = new Scanner(System.in);
    final String REG_NUM = "([0-9])+";
    final String REG_STR = "\\D+";
    final String REG_FLIGHTS = "[a-zA-z]{2}[0-9]{4}";
    final String REG_MENU = "[1-6]";
}
