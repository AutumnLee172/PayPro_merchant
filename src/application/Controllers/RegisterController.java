package application.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import API.UserController;

public class RegisterController implements Initializable {

	private UserController UserController = new UserController();

	@FXML
	private Hyperlink HypLogin;

	@FXML
	private Button btnLogin;

	@FXML
	private Pane pnlRegister;

	@FXML
	private PasswordField txtCPassword;

	@FXML
	private TextField txtContact;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtName;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Label lblValidate;

	private Main mainApp;

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	void checkPassword() {
		if(txtPassword.getText().trim().equals(txtCPassword.getText().trim())) {
			lblValidate.setVisible(true);
			lblValidate.setText("Passwords matched.");
		}else {
			lblValidate.setVisible(true);
			lblValidate.setText("Passwords do not match.");
		}
	}

	@FXML
	void submit() {
		
		if(validate()) {
		String response = UserController.register(txtName.getText(), txtEmail.getText(), txtContact.getText(),
				txtPassword.getText());

		// Create an Alert based on the response
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Registration Status");

		if (response.equals("Success")) {
			alert.setHeaderText("Registration Successful");
			alert.setContentText("Congratulations! You have successfully registered.");
		} else {
			alert.setHeaderText("Registration Failed");
			alert.setContentText(response);
		}

		// Display the alert
		alert.showAndWait();
		lblValidate.setVisible(false);
		}
		else {
			lblValidate.setVisible(true);
			lblValidate.setText("Please check your inputs");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialization code goes here
		HypLogin.setOnMouseClicked(event -> mainApp.showLoginForm());
	}

	private boolean validate() {
		boolean isValid = false;
		if (txtPassword.getText().trim().equals(txtCPassword.getText().trim()) && txtEmail.getText().trim() != ""
				&& txtName.getText().trim() != "" && txtContact.getText().trim() != ""
				&& txtPassword.getText().trim() != "") {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}
}
