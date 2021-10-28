package booking;

import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;
import booking.Entities.Flight;
import booking.Services.FlightService;
import org.junit.Test;
import static org.junit.Assert.*;

public class FlightServiceTest {

    @Test
    public void getAllFlightsPositive(){

        FlightService service = new FlightService();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        Flight flight3 = new Flight( "AA4456", 1616525935, 5000, CityDeparture.KYIV, CityArrival.BOSTON, 20);

        service.saveFlight(flight1);
        service.saveFlight(flight2);
        service.saveFlight(flight3);

        assertNotNull(service.getAllFlights());
    }

    @Test
    public void saveFlightPositive(){

        FlightService service = new FlightService();

        int startSize = service.getAllFlights().size();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        Flight flight3 = new Flight( "AA4456", 1616525935, 5000, CityDeparture.KYIV, CityArrival.BOSTON, 20);

        service.saveFlight(flight1);
        service.saveFlight(flight2);
        service.saveFlight(flight3);

        int finishSize = service.getAllFlights().size();

        assertEquals(startSize+3, finishSize);
    }

    @Test
    public void saveFlightPositiveDouble(){

        FlightService service = new FlightService();

        int startSize = service.getAllFlights().size();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);

        service.saveFlight(flight1);
        service.saveFlight(flight1);
        service.saveFlight(flight1);

        int finishSize = service.getAllFlights().size();

        assertEquals(startSize+1, finishSize);
    }

    @Test
    public void deleteFlightByIndexPositive(){
        FlightService service = new FlightService();
        int startSize = service.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        service.saveFlight(flight1);
        int sizeAdd = service.getAllFlights().size();
        service.deleteFlightByIndex(startSize);
        int sizeFinish = service.getAllFlights().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd-1,sizeFinish);
    }

    @Test
    public void deleteFlightByIndexNegative(){
        FlightService service = new FlightService();
        int startSize = service.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        service.saveFlight(flight1);
        int sizeAdd = service.getAllFlights().size();
        service.deleteFlightByIndex(startSize+10);
        int sizeFinish = service.getAllFlights().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd,sizeFinish);
    }

    @Test
    public void deleteFlightByObjectPositive(){
        FlightService service = new FlightService();
        int startSize = service.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        service.saveFlight(flight1);
        int sizeAdd = service.getAllFlights().size();
        service.deleteFlightByObject(flight1);
        int sizeFinish = service.getAllFlights().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd-1,sizeFinish);
    }

    @Test
    public void deleteFlightByObjectNegative(){
        FlightService service = new FlightService();
        int startSize = service.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        service.saveFlight(flight1);
        int sizeAdd = service.getAllFlights().size();
        service.deleteFlightByObject(flight2);
        int sizeFinish = service.getAllFlights().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd,sizeFinish);
    }

    @Test
    public void getFlightByIdPositive(){
        FlightService service = new FlightService();
        int startSize = service.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        service.saveFlight(flight1);
        Flight flight2 = service.getFlightById(startSize);
        assertEquals(flight1, flight2);
    }

    @Test
    public void getFlightByIdNegative(){
        FlightService service = new FlightService();
        int startSize = service.getAllFlights().size();
        Flight flight2 = service.getFlightById(startSize+10);
        assertNull(flight2);
    }

    @Test
    public void getFlightByFlightNumberPositive(){
        FlightService service = new FlightService();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        service.saveFlight(flight1);
        Flight flight2 = service.getFlightByFlightNumber("AA1234");
        assertEquals(flight1, flight2);
    }

    @Test
    public void getFlightByFlightNumberNegative(){
        FlightService service = new FlightService();
        Flight flight2 = service.getFlightByFlightNumber("!@#$%^");
        assertNull(flight2);
    }

    @Test
    public void getFlightCountPositive(){
        FlightService service = new FlightService();

        int startSize = service.getFlightCount();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        Flight flight3 = new Flight( "AA4456", 1616525935, 5000, CityDeparture.KYIV, CityArrival.BOSTON, 20);

        service.saveFlight(flight1);
        service.saveFlight(flight2);
        service.saveFlight(flight3);

        int finishSize = service.getFlightCount();

        assertEquals(startSize+3, finishSize);


    }

}
