package lk.ijse.orm.hibernate_project.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/DashboardView.fxml"));
        stage.setScene(new Scene(parent));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();


        stage.show();
    }
}
