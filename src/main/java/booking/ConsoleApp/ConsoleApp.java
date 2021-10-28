package booking.ConsoleApp;

import booking.ConstEnum.FileUtil;
import booking.ConstEnum.RegUtil;
import booking.Controllers.BookingController;
import booking.Controllers.FlightController;
import booking.Controllers.UserController;
import booking.Utils.Logger;


public class ConsoleApp implements FileUtil, RegUtil {

    public void consoleRun(){
        FlightController fc = new FlightController();
        BookingController bc = new BookingController(fc);
        UserController uc = new UserController();
        Logger logger = new Logger();

        System.out.println("--==Start Booking CONSOLE APP==--");

        int command;
        do {
            //COMANDS SHOW
            printCommand();
            System.out.print("Пожалуйста введите номер комманды 1-6: ");
            switch(command = Integer.parseInt(validEnter(REG_MENU, "\nВедите номер комманды(цифра от 1 до 6): "))) {

                case 1:
                    //24 HOURS
                    System.out.println("Вы выбрали пункт меню №1");
                    logger.addLogString("Выбор пункта 1 меню");
                    printOnlineBoard(fc);
                    break;

                case 2:
                    //SEARCH BY FLIGHT NUMBER
                    System.out.println("Вы выбрали пункт меню №2");
                    logger.addLogString("Выбор пункта 2 меню");
                    flightRoutInformation(fc);
                    break;

                case 3:
                    //BOOK AND SEARCH
                    System.out.println("Вы выбрали пункт меню №3");
                    logger.addLogString("Выбор пункта 3 меню");
                    startBooking(bc, uc);
                    break;
                case 4:
                    //CANCEL BY TICKET ID
                    System.out.println("Вы выбрали пункт меню №4");
                    cancelBooking(bc);
                    logger.addLogString("Выбор пункта 4 меню");
                    break;
                case 5:
                    //SHOW TICKET BY NAME AND SURNAME
                    System.out.println("Вы выбрали пункт меню №5");
                    logger.addLogString("Выбор пункта 5 меню");
                    searchBooking(bc, uc);
                    break;
                case 6:
                    //SAVE TO FILE AND EXIT
                    System.out.println("Вы выбрали пункт меню №6");
                    logger.addLogString("Выбор пункта 6 меню");
                    bc.updateData();
                    break;
            }
        } while (command != 6);
        System.out.println("************** END CONSOLE APP **************");

    }
    public void printCommand(){
        System.out.println(
                "\nЛИСТ КОММАНД: \n" +
                        "- 1. ПОКАЗАТЬ ОНЛАЙН ТАБЛО ПОЛЕТОВ\n" +
                        "- 2. ПОКАЗАТЬ ИНОФРМАЦИЮ О ПОЛЕТЕ ПО НОМЕРУ РЕЙСА\n" +
                        "- 3. НАЙТИ И ЗАБРОНИРОВАТЬ РЕЙС: \n" +
                        "- 4. ОТМЕНИТЬ БРОНИРОВАНИЕ: \n" +
                        "- 5. ПОКАЗАТЬ МОИ БРОНИРОВАНИЯ: \n" +
                        "- 6. ВЫЙТИ ИЗ ПРОГРАММЫ БРОНИРОВАНИЯ \n");
    }

    private String validEnter(String regex, String prntInfo){
        String string = SCANNER.nextLine();
        while(!string.matches(regex)){
            System.out.print("\nВведены неверные данные -> " + string);
            System.out.print(prntInfo);
            string = SCANNER.nextLine();
        }
        return string.toUpperCase();
    }

    public void printOnlineBoard(FlightController flcont) {
        System.out.println();
        System.out.print("- 1. ПОКАЗАТЬ ОНЛАЙН ТАБЛО ПОЛЕТОВ\n\t");
        flcont.printOnlineBoard();
        System.out.println();
    }

    private void flightRoutInformation(FlightController flcont) {
        System.out.println();
        System.out.print("- 2. ПОКАЗАТЬ ИНФРМАЦИЮ О ПОЛЕТЕ ПО НОМЕРУ РЕЙСА\n\t- ВВедите номер рейса (например - KA1260): ");
        String flightNumber = validEnter(REG_FLIGHTS,"\n\t- ВВедите номер рейса (например - KA1260): ");
        System.out.println(flcont.getFlightByFlightNumber(flightNumber).prettyFormat());
        System.out.println();
    }

    private void startBooking(BookingController bc, UserController uc) {
        bc.runBookingRequest(uc);
    }

    private void searchBooking(BookingController bc, UserController uc) {
        bc.searchBookingsByUser(uc);
    }

    private void cancelBooking(BookingController bc) {
        System.out.print("Введите ID бронирования для отмены: ");
        String id = SCANNER.next();
        bc.cancelBooking(id);
    }
}