package lk.ijse.orm.hibernate_project.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.orm.hibernate_project.bo.BoFactory;
import lk.ijse.orm.hibernate_project.bo.custom.UserBo;

import java.io.IOException;

public class LoginController {
    UserBo userBO = (UserBo) BoFactory.getBoFactory().getBo(BoFactory.BoType.USER);
    @FXML
    private ImageView imgPasswordView;
    @FXML
    private Label shownPassword;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtUserName;

    @FXML
    void forgotClickOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION, "Please contact Developer !\n0743525973").show();
    }

    public void initialize() {
        shownPassword.setVisible(false);
    }

    @FXML
    private void passwordFieldKeyTyped(KeyEvent keyEvent) {
        shownPassword.textProperty().bind(Bindings.concat(txtPassword.getText()));
    }

    public void toggleButton(ActionEvent actionEvent) {
        if (toggleButton.isSelected()) {
            shownPassword.setVisible(true);
            shownPassword.textProperty().bind(Bindings.concat(txtPassword.getText()));
            toggleButton.setText("Hide");
            imgPasswordView.setImage(new Image("resources/img/eye-close.png"));

        } else {
            shownPassword.setVisible(false);
            txtPassword.setVisible(true);
            toggleButton.setText("Show");
            imgPasswordView.setImage(new Image("resources/img/eye-open.png"));
        }
    }


    @FXML
    void loginClickOnAction(ActionEvent event) throws IOException {

        if ((isCorrectUserName()) & (isCorrectPassword())) {

            txtUserName.setStyle("");
            txtPassword.setStyle("");


            System.out.println("Login successful!");
            Stage stage = (Stage) pane.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/DashboardView.fxml"));
            stage.setScene(new Scene(parent));
            stage.setTitle("Dashboard");
            stage.centerOnScreen();
            stage.show();

        } else {

            txtUserName.setStyle("-fx-border-color: red;");
            txtPassword.setStyle("-fx-border-color: red;");

            System.out.println("Login failed. Please try again.");
            new Alert(Alert.AlertType.ERROR, "username or password is incorrect!").show();
        }
    }

    private boolean isCorrectUserName() {
        String user = userBO.getUser("1");
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, " Database Error !").show();
            return false;
        }
        return txtUserName.getText().equals(user);
    }

    private boolean isCorrectPassword() {
        String password = userBO.getPassword("1");
        if (password == null) {
            new Alert(Alert.AlertType.ERROR, " Database Error !").show();
            return false;
        }
        return txtPassword.getText().equals(password);
    }


    public void resetPasswordOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/PasswordResetView.fxml"));
        stage.setScene(new Scene(parent));
        stage.setTitle("ResetPassword");
        stage.centerOnScreen();
        stage.show();

    }
}