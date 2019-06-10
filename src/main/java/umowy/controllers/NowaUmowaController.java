package umowy.controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import umowy.dbUtils.DbConnection;
import umowy.models.KontaktModel;
import umowy.models.KontrahentModel;
import umowy.models.RodzajModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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


    @FXML
    private DatePicker new_data;
    @FXML
    private TextField new_sposob;
    @FXML
    private TextField new_warunek;
    @FXML
    private TextField pesel;
    @FXML
    private TextField new_kontakt;
    @FXML
    private TextField new_kontrahent;
    @FXML
    private TextField new_rodzaj;
    @FXML
    private Button confirm;


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

    public void dodajNowaUmowe() throws SQLException {
        String sqlKey = "select max(u.id) from BD3.dbo.umowa u";
        String sqlCheckPracownik = "select * from BD3.dbo.pracownik where pesel = ?";
        String sqlCheckKontakt = "select * from BD3.dbo.kontakt_w_firmie where id_kontaktu = ?";
        String sqlCheckKontrahent = "select * from BD3.dbo.kontrahent_indywidualny where id_klienta = ?";
        String sqlCheckRodzaj = "select * from BD3.dbo.rodzaj_umowy where id_rodzaju = ?";
        LocalDate date = this.new_data.getValue();

        try {
            Connection con = DbConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery(sqlKey);
            rs.next();
            int key = rs.getInt(1);
            key++;
            String sqlInsert = "Insert into BD3.dbo.umowa (id, data_zawarcia, sposob_rozliczenia, warunek_rozwiazania, pracownik_pesel, kontakt_w_firmie_id_kontaktu, kontrahent_indywidualny, rodzaj_umowy_id_rodzaju)" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setString(1, String.valueOf(key));
            if(date != null)
                stmt.setString(2, date.toString());
            stmt.setString(3, this.new_sposob.getText());
            stmt.setString(4, this.new_warunek.getText());
            stmt.setString(5, this.pesel.getText());
            stmt.setString(6, this.new_kontakt.getText());
            stmt.setString(7, this.new_kontrahent.getText());
            stmt.setString(8, this.new_rodzaj.getText());
            boolean pracownikChecked = checkData(sqlCheckPracownik, this.pesel.getText());
            boolean kontaktChecked = checkData(sqlCheckKontakt, this.new_kontakt.getText());
            boolean kontrahentChecked = checkData(sqlCheckKontrahent, this.new_kontrahent.getText());
            boolean rodzajChecked = checkData(sqlCheckRodzaj, this.new_rodzaj.getText());
            if(pracownikChecked && kontaktChecked && kontrahentChecked && rodzajChecked && this.new_data.getValue() != null) {
                stmt.execute();
                Stage stage = (Stage) this.confirm.getScene().getWindow();
                stage.close();
            }
            else {
                String alertText = "Błędne: \n";
                if(this.new_data.getValue() == null) alertText += " brak daty \n";
                if(!pracownikChecked) alertText += " pesel \n";
                if(!kontaktChecked) alertText += " kontakt w firmie \n";
                if(!kontrahentChecked) alertText += " kontrahent indywidualny \n";
                if(!rodzajChecked) alertText += " rodzaj umowy \n";
                alertText += "Popraw dane.";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(alertText);
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkData(String query, String data) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection con = DbConnection.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, data);

            rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            ps.close();
            rs.close();
        }
    }
}
