package booking.ConstEnum;

public enum CityArrival {

    AMSTERDAM("Amsterdam"),
    BOSTON("Boston"),
    DENVER("Denver"),
    LAS_VEGAS("Las_Vegas"),
    MIAMI("Miami"),
    PORTLAND("Portland"),
    SAN_FRANCISCO("San_Francisco");

    private final String name;

    CityArrival(String destination) {
        name = destination;
    }

    public String getName() {
        return name;
    }

}
