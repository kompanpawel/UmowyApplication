package umowy.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KontaktModel {

    private final StringProperty id;
    private final StringProperty imie;
    private final StringProperty nazwisko;
    private final StringProperty email;
    private final StringProperty telefon;
    private final StringProperty firma;

    public KontaktModel(String id, String imie, String nazwisko, String email, String telefon, String firma) {
        this.id = new SimpleStringProperty(id);
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.email = new SimpleStringProperty(email);
        this.telefon = new SimpleStringProperty(telefon);
        this.firma = new SimpleStringProperty(firma);
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

    public String getImie() {
        return imie.get();
    }

    public StringProperty imieProperty() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTelefon() {
        return telefon.get();
    }

    public StringProperty telefonProperty() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon.set(telefon);
    }

    public String getFirma() {
        return firma.get();
    }

    public StringProperty firmaProperty() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma.set(firma);
    }
}
