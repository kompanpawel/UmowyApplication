package umowy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import umowy.dbUtils.DbConnection;
import umowy.models.PracownikModel;
import umowy.models.UmowyModel;

import java.sql.*;

public class StartController {

    private ObservableList<UmowyModel> umowyList;
    private ObservableList<UmowyModel> filteredUmowyList;

    private ObservableList<String> dzialyList;
    private ObservableList<PracownikModel> pracownicyList;

    @FXML
    private TableView<UmowyModel> umowy;

    @FXML
    private TableColumn<UmowyModel, String> umowy_id;
    @FXML
    private TableColumn<UmowyModel, String> umowy_data;
    @FXML
    private TableColumn<UmowyModel, String> umowy_rozliczanie;
    @FXML
    private TableColumn<UmowyModel, String> umowy_rozwiazanie;
    @FXML
    private TableColumn<UmowyModel, String> umowy_pesel;
    @FXML
    private TableColumn<UmowyModel, String> umowy_kontakt;
    @FXML
    private TableColumn<UmowyModel, String> umowy_kontrahent;
    @FXML
    private TableColumn<UmowyModel, String> umowy_rodzaj;


    @FXML
    private DatePicker filter_from;
    @FXML
    private DatePicker filter_to;
    @FXML
    private ComboBox<String> filter_dzial;
    @FXML
    private ComboBox<PracownikModel> filter_pracownik;

    public void initialize() throws SQLException {
        fillDzialComboBox();
        fillPracownikComboBox();
        loadUmowy();
    }

    @FXML
    public void loadPracownicy() throws SQLException {
        updatePracownikComboBox();
    }

    @FXML
    public void update() throws SQLException{
        loadUmowy();
    }

    @FXML
    private void loadUmowy() throws SQLException {
        try {
            Connection con = DbConnection.getConnection();
            this.umowyList = FXCollections.observableArrayList();

            String umowyView = "select * from BD3.dbo.main_umowy";
            ResultSet rs = con.createStatement().executeQuery(umowyView);
            while(rs.next()) {
                this.umowyList.add(new UmowyModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            System.err.println("Error"+e);
        }
        setCellValues();
        this.umowy.setItems(this.umowyList);
    }

    private void fillDzialComboBox() throws SQLException {
        String sqlDzialy = "select nazwa from BD3.dbo.dzial";
        try {
            Connection con = DbConnection.getConnection();
            this.dzialyList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(sqlDzialy);
            this.dzialyList.add("Wszystkie działy");
            while(rs.next()) {
                this.dzialyList.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println("Error"+e);
        }
        this.filter_dzial.setItems(null);
        this.filter_dzial.setItems(dzialyList);
        this.filter_dzial.setValue("Wszystkie działy");
    }

private void fillPracownikComboBox() throws SQLException {
    String sqlPracownicy = "select imie, nazwisko from BD3.dbo.pracownik";
    try {
        Connection con = DbConnection.getConnection();
        this.pracownicyList = FXCollections.observableArrayList();

        ResultSet rs = con.createStatement().executeQuery(sqlPracownicy);
        this.pracownicyList.add(new PracownikModel("Wszyscy", "pracownicy"));
        while (rs.next()) {
            this.pracownicyList.add(new PracownikModel(rs.getString(1), rs.getString(2)));
        }
    } catch (SQLException e) {
        System.err.println("Error" + e);
    }
    this.filter_pracownik.setItems(null);
    this.filter_pracownik.setItems(pracownicyList);
    this.filter_pracownik.setValue(new PracownikModel("Wszyscy", "pracownicy"));
}

    public void updatePracownikComboBox() throws SQLException {
        ObservableList<PracownikModel> filteredPracownicy = null;
        if (!this.filter_dzial.getSelectionModel().getSelectedItem().equals("Wszystkie działy")) {
            String getDzialKey = "select id_dzialu from BD3.dbo.dzial where nazwa = ?";
            String sqlFilterDzial = "select imie, nazwisko from BD3.dbo.pracownik inner join BD3.dbo.pelnione_stanowisko " +
                    "on pracownik.pesel = pelnione_stanowisko.pracownik_pesel where dzial_id_dzialu = ?";
            try {
                Connection con = DbConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(getDzialKey);
                String dzial = filter_dzial.getSelectionModel().getSelectedItem();
                pst.setString(1, dzial);
                ResultSet rs = pst.executeQuery();
                rs.next();
                int key = rs.getInt(1);
                PreparedStatement pst2 = con.prepareStatement(sqlFilterDzial);
                pst2.setInt(1, key);
                ResultSet rs2 = pst2.executeQuery();
                while (rs2.next()) {
                    filteredPracownicy.add(new PracownikModel(rs2.getString(1), rs2.getString(2)));
                }

            } catch (SQLException e) {
                System.err.println("Error" + e);
            }
            this.filter_pracownik.setItems(null);
            this.filter_pracownik.setItems(filteredPracownicy);
        } else {
            fillPracownikComboBox();
        }
    }

    @FXML
    public void loadFilteredUmowy() throws SQLException {
        String dateFrom = null;
        String dateTo = null;
        String dzial = null;
        String pracownik = null;
        int licznik = 0;

        if(this.filter_from.getValue() != null) {
            dateFrom = "data_zawarcia > \'" + this.filter_from.getValue().toString()+"\'";
            licznik++;
        }
        if(this.filter_to.getValue() != null) {
            dateTo = "data_zawarcia < \'" + this.filter_to.getValue().toString()+"\'";
            licznik++;
        }
        if(!this.filter_dzial.getSelectionModel().getSelectedItem().equals("Wszystkie działy")) {
            dzial = "nazwa = \'" + this.filter_dzial.getSelectionModel().getSelectedItem()+"\'";
            licznik++;
        }
        if(!this.filter_pracownik.getSelectionModel().getSelectedItem().getImie_nazwisko().equals("Wszyscy pracownicy")) {
            pracownik = "imie = \'" + this.filter_pracownik.getSelectionModel().getSelectedItem().getImie() + "\' and nazwisko = \'" + this.filter_pracownik.getSelectionModel().getSelectedItem().getNazwisko()+ "\'";
            licznik++;
        }
        String sqlFilterDzial = "select * from BD3.dbo.main_umowy inner join BD3.dbo.pracownik " +
                "on pracownik.pesel = main_umowy.pracownik_pesel inner join BD3.dbo.pelnione_stanowisko " +
                "on main_umowy.pracownik_pesel = pelnione_stanowisko.pracownik_pesel " +
                "inner join BD3.dbo.dzial on pelnione_stanowisko.dzial_id_dzialu = dzial.id_dzialu";
        if (dateFrom != null || dateTo != null || dzial != null || pracownik != null) {
            sqlFilterDzial += " where ";
        }
        if(dateFrom != null) {
            licznik--;
            if(licznik == 0)
                sqlFilterDzial += dateFrom;
            else {
                sqlFilterDzial += dateFrom + " and ";
            }
        }
        if(dateTo != null) {
            licznik--;
            if(licznik == 0)
                sqlFilterDzial += dateTo;
            else {
                sqlFilterDzial += dateTo + " and ";
            }
        }
        if(dzial != null) {
            licznik--;
            if(licznik == 0)
                sqlFilterDzial += dzial;
            else {
                sqlFilterDzial += dzial + " and ";
            }
        }
        if(pracownik != null) {
            sqlFilterDzial += pracownik;
        }

        try {
            Connection con = DbConnection.getConnection();
            this.filteredUmowyList = FXCollections.observableArrayList();
            ResultSet rs = con.prepareStatement(sqlFilterDzial).executeQuery();
            while (rs.next()) {
                this.filteredUmowyList.add(new UmowyModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            System.err.println("Error"+e);
        }
        setCellValues();
        this.umowy.setItems(this.filteredUmowyList);
    }

    private void setCellValues() {
        this.umowy_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.umowy_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        this.umowy_rozliczanie.setCellValueFactory(new PropertyValueFactory<>("sposob"));
        this.umowy_rozwiazanie.setCellValueFactory(new PropertyValueFactory<>("warunek"));
        this.umowy_pesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        this.umowy_kontakt.setCellValueFactory(new PropertyValueFactory<>("kontakt"));
        this.umowy_kontrahent.setCellValueFactory(new PropertyValueFactory<>("kontrahent"));
        this.umowy_rodzaj.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));

        this.umowy.setItems(null);
    }

    @FXML
    public void stworzNowaUmowe() throws SQLException {
        try {
            Stage insert = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/nowaUmowa.fxml").openStream());

            NowaUmowaController nowaUmowaController = (NowaUmowaController) loader.getController();

            Scene scene = new Scene(root);
            insert.setScene(scene);
            insert.setTitle("Utwórz nową umowę");
            insert.setResizable(false);
            insert.show();
        }  catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
