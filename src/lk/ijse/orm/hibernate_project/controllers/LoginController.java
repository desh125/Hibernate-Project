package lk.ijse.orm.hibernate_project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.orm.hibernate_project.validations.ControllerValidations;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label resetPasswordLabel;
    @FXML
    private AnchorPane root;


    @FXML
    private void resetPasswordOnAction(MouseEvent event) {


    }

    public void btnClickOnAction(ActionEvent actionEvent) throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isMatched = ControllerValidations.checkLoginDetails(username, password);
        System.out.println(username + " " + password + " " + isMatched);

        if (isMatched) {
            Stage stage = (Stage) root.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/DashboardView.fxml"));
            stage.setScene(new Scene(parent));
            stage.setTitle("Dashboard");
            stage.centerOnScreen();
            stage.show();

            // Navigate to main view
            System.out.println("Login successful!");
        } else {
            new Alert(Alert.AlertType.ERROR, "username or password is incorrect!").show();
            System.out.println("Invalid username or password!");
        }
    }
}
