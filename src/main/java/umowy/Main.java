package umowy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import umowy.controllers.StartController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(getClass().getResource("/start.fxml"));
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/start.fxml").openStream());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Aplikacja do tworzenia umów i raportów");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
