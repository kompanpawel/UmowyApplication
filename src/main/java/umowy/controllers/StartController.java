package umowy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import umowy.dbUtils.DbConnection;
import umowy.model.UmowyModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StartController {

    DbConnection db;
    ObservableList<UmowyModel> umowyList;

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

    public void initialize() throws SQLException {
        loadUmowy();
    }

    private String umowySql = "select u.id, u.data_zawarcia, u.sposob_rozliczenia, u.warunek_rozwiazania, p.pesel, " +
            "CONCAT(kf.imie,' ', kf.nazwisko) as imie_nazwisko_kf, CONCAT(ki.imie,' ' ,ki.nazwisko) as imie_nazwisko_ki, " +
            "r.rodzaj " +
            "from BD3.dbo.umowa u, BD3.dbo.pracownik p, BD3.dbo.kontrahent_indywidualny ki, BD3.dbo.kontakt_w_firmie kf, " +
            "BD3.dbo.rodzaj_umowy r where u.rodzaj_umowy_id_rodzaju = r.id_rodzaju " +
            "order by 1";

    @FXML
    public void loadUmowy() throws SQLException{
        try {
            Connection con = DbConnection.getConnection();
            this.umowyList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(umowySql);
            while(rs.next()) {
                this.umowyList.add(new UmowyModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            System.err.println("Error"+e);
        }
        this.umowy_id.setCellValueFactory(new PropertyValueFactory<UmowyModel, String>("id"));
        this.umowy_data.setCellValueFactory(new PropertyValueFactory<UmowyModel, String>("data"));
        this.umowy_rozliczanie.setCellValueFactory(new PropertyValueFactory<UmowyModel, String>("sposob"));
        this.umowy_rozwiazanie.setCellValueFactory(new PropertyValueFactory<UmowyModel, String>("warunek"));
        this.umowy_pesel.setCellValueFactory(new PropertyValueFactory<UmowyModel, String>("pesel"));
        this.umowy_kontakt.setCellValueFactory(new PropertyValueFactory<UmowyModel, String>("kontakt"));
        this.umowy_kontrahent.setCellValueFactory(new PropertyValueFactory<UmowyModel, String>("kontrahent"));
        this.umowy_rodzaj.setCellValueFactory(new PropertyValueFactory<UmowyModel, String>("rodzaj"));


        this.umowy.setItems(null);
        this.umowy.setItems(this.umowyList);
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
