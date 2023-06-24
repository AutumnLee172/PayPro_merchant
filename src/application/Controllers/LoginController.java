package application.Controllers;


import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import API.UserController;
import application.Main;
import application.obj.CartItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class LoginController implements Initializable {

	private UserController UserController = new UserController();
	
	@FXML
    private Hyperlink HypSignUp;

    @FXML
    private Button btnLogin;

    @FXML
    private Pane pnlLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    void showRegisterPanel() {
    	
    }
    
    @FXML
    void login() {
    String response = UserController.login(txtEmail.getText(),txtPassword.getText());
        
   	 // Create an Alert based on the response
   	 Alert alert = new Alert(AlertType.INFORMATION);
   	 alert.setTitle("Login Status");
   	    
   	 if (response.equals("Authorized")) {
   	     mainApp.showDashboard();
   	     saveUserData(txtEmail.getText());
   	 } else {
   	     alert.setHeaderText(response);
   	     alert.showAndWait();
   	 }
   	    
   	 // Display the alert
   	 
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	   // Initialization code goes here
		HypSignUp.setOnMouseClicked(event -> mainApp.showRegisterForm());
	}


	public void saveUserData(String email) {
		String filePath = System.getProperty("user.dir") + "/src/application/obj/user.txt";
		String record = email;
		try {
			// Delete the existing file
			Files.deleteIfExists(Paths.get(filePath));

			// Create a new file
			Files.createFile(Paths.get(filePath));

			// Write the cart items to the file
			FileWriter writer = new FileWriter(filePath);
			writer.write(record);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
