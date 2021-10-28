package booking.ConstEnum;

public enum CityDeparture {


    KYIV("Kyiv");

    private final String name;

    CityDeparture(String destination) {
        name = destination;
    }

    public String getName() {
        return name;
    }

}

