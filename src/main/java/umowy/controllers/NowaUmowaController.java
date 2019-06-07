package umowy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import umowy.dbUtils.DbConnection;
import umowy.models.KontaktModel;
import umowy.models.KontrahentModel;
import umowy.models.RodzajModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NowaUmowaController {

    DbConnection db;
    ObservableList<KontaktModel> kontaktList;
    ObservableList<KontrahentModel> kontrahentList;
    ObservableList<RodzajModel> rodzajList;

    @FXML
    private TableView<KontaktModel> kontakt;
    @FXML
    private TableView<KontrahentModel> kontrahent;
    @FXML
    private TableView<RodzajModel> rodzaj;

    @FXML
    private TableColumn<KontaktModel, String> kontakt_id;
    @FXML
    private TableColumn<KontaktModel, String> kontakt_imie;
    @FXML
    private TableColumn<KontaktModel, String> kontakt_nazwisko;
    @FXML
    private TableColumn<KontaktModel, String> kontakt_email;
    @FXML
    private TableColumn<KontaktModel, String> kontakt_telefon;
    @FXML
    private TableColumn<KontaktModel, String> kontakt_firma;

    @FXML
    private TableColumn<KontrahentModel, String> kontrahent_id;
    @FXML
    private TableColumn<KontrahentModel, String> kontrahent_imie;
    @FXML
    private TableColumn<KontrahentModel, String> kontrahent_nazwisko;
    @FXML
    private TableColumn<KontrahentModel, String> kontrahent_adres;
    @FXML
    private TableColumn<KontrahentModel, String> kontrahent_telefon;
    @FXML
    private TableColumn<KontrahentModel, String> kontrahent_email;

    @FXML
    private TableColumn<RodzajModel, String> rodzaj_id;
    @FXML
    private TableColumn<RodzajModel, String> rodzaj_rodzaj;




    public void initialize() throws SQLException {
        loadKontakty();
        loadKontrahenci();
        loadRodzaje();
    }

    private String kontaktySql = "select kwf.id_kontaktu, kwf.imie, kwf.nazwisko, kwf.email, kwf.telefon, kf.nazwa\n" +
            "from BD3.dbo.kontakt_w_firmie kwf, BD3.dbo.kontrahent_firmowy kf\n" +
            "where kwf.kontrahent_firmowy_nip = kf.nip\n" +
            "order by 1;";
    private String kontahenciSql = "select * from BD3.dbo.kontrahent_indywidualny";
    private String rodzajeSql = "select * from BD3.dbo.rodzaj_umowy";

    @FXML
    public void loadKontakty() throws SQLException {
        try {
            Connection con = DbConnection.getConnection();
            this.kontaktList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(kontaktySql);
            while(rs.next()) {
                this.kontaktList.add(new KontaktModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println("Error"+e);
        }

        this.kontakt_id.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("id"));
        this.kontakt_imie.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("imie"));
        this.kontakt_nazwisko.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("nazwisko"));
        this.kontakt_email.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("email"));
        this.kontakt_telefon.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("telefon"));
        this.kontakt_firma.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("firma"));

        this.kontakt.setItems(null);
        this.kontakt.setItems(kontaktList);

    }

    public void loadKontrahenci() throws SQLException {
        try {
            Connection con = DbConnection.getConnection();
            this.kontrahentList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(kontahenciSql);
            while(rs.next()) {
                this.kontrahentList.add(new KontrahentModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6)));
            }
        } catch (SQLException e) {
            System.err.println("Error"+e);
        }

        this.kontrahent_id.setCellValueFactory(new PropertyValueFactory<KontrahentModel, String>("id"));
        this.kontrahent_imie.setCellValueFactory(new PropertyValueFactory<KontrahentModel, String>("imie"));
        this.kontrahent_nazwisko.setCellValueFactory(new PropertyValueFactory<KontrahentModel, String>("nazwisko"));
        this.kontrahent_adres.setCellValueFactory(new PropertyValueFactory<KontrahentModel, String>("adres"));
        this.kontrahent_telefon.setCellValueFactory(new PropertyValueFactory<KontrahentModel, String>("telefon"));
        this.kontrahent_email.setCellValueFactory(new PropertyValueFactory<KontrahentModel, String>("email"));

        this.kontrahent.setItems(null);
        this.kontrahent.setItems(kontrahentList);
    }

    public void loadRodzaje() throws SQLException {
        try {
            Connection con = DbConnection.getConnection();
            this.rodzajList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(rodzajeSql);
            while(rs.next()) {
                this.rodzajList.add(new RodzajModel(rs.getString(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            System.err.println("Error"+e);
        }

        this.rodzaj_id.setCellValueFactory(new PropertyValueFactory<RodzajModel, String>("id"));
        this.rodzaj_rodzaj.setCellValueFactory(new PropertyValueFactory<RodzajModel, String>("rodzaj"));

        this.rodzaj.setItems(null);
        this.rodzaj.setItems(rodzajList);
    }
}
