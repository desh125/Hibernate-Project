package lk.ijse.orm.hibernate_project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.orm.hibernate_project.bo.custom.UserBo;
import lk.ijse.orm.hibernate_project.bo.custom.impl.UserBoImpl;

public class PasswordResetController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private TextField newUserNameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane root;

    @FXML
    private void resetPassword() {
        String username = usernameField.getText();
        String currentPassword = currentPasswordField.getText();
        String newUsername = newUserNameField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || currentPassword.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }

        if (newPassword.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "New password must contain 8 characters");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "New password and confirm password do not match.");
            return;
        }


        UserBo userBo = new UserBoImpl();
        String storedUsername = userBo.getUser("1");
        String storedPassword = userBo.getPassword("1");

        if (!username.equals(storedUsername) || !currentPassword.equals(storedPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid current username or password.");
            return;
        }


        boolean updated = userBo.updateUser_Pw(newUsername, newPassword);

        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully.");
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update the password.");
        }
    }

    @FXML
    private void backButtonClicked() {
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
            stage.setScene(new Scene(parent));
            stage.setTitle("Dashboard");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            System.err.println("An error occurred");

        }

    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        usernameField.clear();
        currentPasswordField.clear();
        newUserNameField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }
}
