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

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getRodzaj() {
        return rodzaj.get();
    }

    public StringProperty rodzajProperty() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj.set(rodzaj);
    }
}
