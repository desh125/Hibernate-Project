package lk.ijse.orm.hibernate_project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lk.ijse.orm.hibernate_project.configuration.SessionFactoryConfiguration;
import lk.ijse.orm.hibernate_project.entities.User;
import org.hibernate.Session;

public class LoginController {

    private final SessionFactoryConfiguration factoryConfig = SessionFactoryConfiguration.getInstance();
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label resetPasswordLabel;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validateInputs(username, password)) {
                if (authenticateUser(username, password)) {

                } else {

                }
            } else {
                // Validation failed
                // Show an error message
            }
        });

        resetPasswordLabel.setOnMouseClicked(this::resetPasswordOnAction);
    }

    private boolean validateInputs(String username, String password) {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }

    private boolean authenticateUser(String username, String password) {
        try (Session session = factoryConfig.getSession()) {

            User user = session.createQuery("FROM User WHERE username = :username AND password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void resetPasswordOnAction(MouseEvent event) {

    }

    public void btnClickOnAction(ActionEvent actionEvent) {
    }
}
