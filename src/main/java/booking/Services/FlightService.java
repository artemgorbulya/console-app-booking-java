package booking.Services;

import booking.ConstEnum.DataUtil;
import booking.DAO.CollectionFlightDAO;
import booking.DAO.FlightDAO;
import booking.Entities.Flight;
import booking.Utils.Logger;
import booking.Utils.FlightsPrinter;

import java.io.Serializable;
import java.util.List;

public class FlightService implements Serializable, DataUtil {

    private FlightDAO flightDao = new CollectionFlightDAO();
    private Logger logger = new Logger();

    public FlightDAO getFlightDao() {
        return flightDao;
    }

    public List<Flight> getAllFlights() {
        logger.addLogString("Получение списка всех полетов");
        return flightDao.getAll();
    }

    public void displayAllFlights() {
        logger.addLogString("Вывод всех полетов на экран");
        flightDao.getAll().forEach(System.out::println);
    }

    public void saveFlight(Flight flight) {
        logger.addLogString("Сохранение полета в коллекцию");
        flightDao.save(flight);
    }

    public void deleteFlightByIndex(int index) {
        logger.addLogString("Удаление полета из коллекции по индексу");
        flightDao.remove(index);
    }

    public void deleteFlightByObject(Flight flight) {
        logger.addLogString("Удаление полета из коллекции по полету");
        flightDao.remove(flight);
    }

    public Flight getFlightById(int index) {
        logger.addLogString("Поиск полета по индексу");
        if (index >= 0 && index < flightDao.getAll().size()) {
            return flightDao.getAll().get(index);
        } else {
            return null;
        }
    }

    public Flight getFlightByFlightNumber(String str) {
        logger.addLogString("Поиск полета по номеру");
        try {
            return flightDao.getAll().stream().filter(x->x.getFlightNumber().equals(str)).findFirst().get();
        } catch (Exception e){
            System.out.println(e.getMessage());

        }
        return null;
    }

    public void printOnlineBoard(){
        logger.addLogString("Просмотр онлайн табло");
        System.out.println("|-----------------------+=Онлайн табло полетов=+---------------------|");
        FlightsPrinter.printFlights(flightDao.getAll(), false);
    }

    public int getFlightCount() {
        logger.addLogString("Получение величины коллекции");
        return flightDao.getAll().size();
    }

    public void saveData(String filePath) {
        logger.addLogString("Сохранение данных полетов в файл");
        System.out.println("Сохранение в файл...");
        flightDao.saveData(filePath);
    }

    public void readData(String filePath) {
        logger.addLogString("Чтение данных полетов из файла");
        System.out.println("Чтение из файла...");
        flightDao.readData(filePath);
    }

    public void loadData(List<Flight> list){
        logger.addLogString("Загрузка данных в коллекцию");
        System.out.println("Загрузка данных в коллекцию...");
        flightDao.loadData(list);
    }

}
