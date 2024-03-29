package pidev.javafx.tools.transport;

import pidev.javafx.model.Transport.Station;

public class DataHolder {
    private static Station station;

    public static Station getStation() {
        return station;
    }

    public static void setStation(Station station) {
        DataHolder.station = station;
    }
}
