package umowy.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UmowyModel {

    private final StringProperty id;
    private final StringProperty data;
    private final StringProperty sposob;
    private final StringProperty warunek;
    private final StringProperty pesel;
    private final StringProperty kontakt;
    private final StringProperty kontrahent;
    private final StringProperty rodzaj;

    public UmowyModel(String id, String data, String sposob, String warunek, String pesel, String kontakt,
                      String kontrahent, String rodzaj) {
        this.id = new SimpleStringProperty(id);
        this.data = new SimpleStringProperty(data);
        this.sposob = new SimpleStringProperty(sposob);
        this.warunek = new SimpleStringProperty(warunek);
        this.pesel = new SimpleStringProperty(pesel);
        this.kontakt = new SimpleStringProperty(kontakt);
        this.kontrahent = new SimpleStringProperty(kontrahent);
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

    public String getData() {
        return data.get();
    }

    public StringProperty dataProperty() {
        return data;
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public String getSposob() {
        return sposob.get();
    }

    public StringProperty sposobProperty() {
        return sposob;
    }

    public void setSposob(String sposob) {
        this.sposob.set(sposob);
    }

    public String getWarunek() {
        return warunek.get();
    }

    public StringProperty warunekProperty() {
        return warunek;
    }

    public void setWarunek(String warunek) {
        this.warunek.set(warunek);
    }

    public String getPesel() {
        return pesel.get();
    }

    public StringProperty peselProperty() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel.set(pesel);
    }

    public String getKontakt() {
        return kontakt.get();
    }

    public StringProperty kontaktProperty() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt.set(kontakt);
    }

    public String getKontrahent() {
        return kontrahent.get();
    }

    public StringProperty kontrahentProperty() {
        return kontrahent;
    }

    public void setKontrahent(String kontrahent) {
        this.kontrahent.set(kontrahent);
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
