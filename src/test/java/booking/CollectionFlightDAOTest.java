package booking;

import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;
import booking.DAO.CollectionFlightDAO;
import booking.Entities.Flight;
import org.junit.Test;
import static org.junit.Assert.*;

public class CollectionFlightDAOTest {


    @Test
    public void addFlightPositive(){

        CollectionFlightDAO collection = new CollectionFlightDAO();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        Flight flight3 = new Flight( "AA4456", 1616525935, 5000, CityDeparture.KYIV, CityArrival.BOSTON, 20);

        int startSize = collection.getAll().size();

        collection.save(flight1);
        collection.save(flight2);
        collection.save(flight3);

        int finishSize = collection.getAll().size();

        assertEquals(startSize+3, finishSize);
    }

    @Test
    public void addFlightPositiveDouble(){

        CollectionFlightDAO collection = new CollectionFlightDAO();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);

        int startSize = collection.getAll().size();

        collection.save(flight1);
        collection.save(flight1);
        collection.save(flight1);

        int finishSize = collection.getAll().size();

        assertEquals(startSize+1, finishSize);
    }

    @Test
    public void getFlightByIndexPositive(){

        CollectionFlightDAO collection = new CollectionFlightDAO();

        int startSize = collection.getAll().size();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        collection.save(flight1);

        Flight flight2 = collection.get(startSize);

        assertEquals(flight1, flight2);

    }

    @Test
    public void getFlightByIndexNegative(){

        CollectionFlightDAO collection = new CollectionFlightDAO();
        int startSize = collection.getAll().size();
        Flight flight2 = collection.get(startSize+10);
        assertNull(flight2);

    }

    @Test
    public void getAllFlightPositive(){

        CollectionFlightDAO collection = new CollectionFlightDAO();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        collection.save(flight1);
        assertNotNull(collection.getAll());

    }

    @Test
    public void removeFlightByFlightPositive(){
        CollectionFlightDAO collection = new CollectionFlightDAO();
        int startSize = collection.getAll().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        collection.save(flight1);
        int sizeAdd = collection.getAll().size();
        collection.remove(flight1);
        int sizeFinish = collection.getAll().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd-1,sizeFinish);
    }

    @Test
    public void removeFlightByFlightNegative(){
        CollectionFlightDAO collection = new CollectionFlightDAO();
        int startSize = collection.getAll().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        collection.save(flight1);
        int sizeAdd = collection.getAll().size();
        collection.remove(flight2);
        int sizeFinish = collection.getAll().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd,sizeFinish);
    }

    @Test
    public void removeFlightByIndexPositive(){
        CollectionFlightDAO collection = new CollectionFlightDAO();
        int startSize = collection.getAll().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        collection.save(flight1);
        int sizeAdd = collection.getAll().size();
        collection.remove(startSize);
        int sizeFinish = collection.getAll().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd-1,sizeFinish);
    }

    @Test
    public void removeFlightByIndexNegative(){
        CollectionFlightDAO collection = new CollectionFlightDAO();
        int startSize = collection.getAll().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        collection.save(flight1);
        int sizeAdd = collection.getAll().size();
        collection.remove(startSize+10);
        int sizeFinish = collection.getAll().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd,sizeFinish);
    }

    @Test
    public void saveAndReadDataFromFilePositive(){

        String FILE_PATH="./src/file/test/flights.dat";
        CollectionFlightDAO collection1 = new CollectionFlightDAO();
        Flight flight1 = collection1.get(1);
        collection1.saveData(FILE_PATH);

        CollectionFlightDAO collection2 = new CollectionFlightDAO();
        collection2.readData(FILE_PATH);
        Flight flight2 = collection2.get(1);

        assertEquals(flight1,flight2);

    }




}
