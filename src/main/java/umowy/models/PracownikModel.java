package umowy.models;

public class PracownikModel {

    private String imie;
    private String nazwisko;
    private String imie_nazwisko;

    public PracownikModel(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.imie_nazwisko = imie + " " + nazwisko;
    }

    public String getImie_nazwisko() {
        return imie_nazwisko;
    }

    public void setImie_nazwisko(String imie_nazwisko) {
        this.imie_nazwisko = imie_nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Override
    public String toString() {
        return this.imie_nazwisko;
    }
}
