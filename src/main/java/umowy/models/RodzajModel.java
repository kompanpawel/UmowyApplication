package umowy.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RodzajModel {

    private final StringProperty id;
    private final StringProperty rodzaj;

    public RodzajModel(String id, String rodzaj) {
        this.id = new SimpleStringProperty(id);
        this.rodzaj = new SimpleStringProperty(rodzaj);
    }
}
