package application.Controllers;

import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class DashboardController implements Initializable {

	@FXML
	private AnchorPane ap;

	@FXML
	private BorderPane bp;
	
	@FXML
	private Button btnDashboard;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnPayment;

	@FXML
	private Button btnProduct;

	@FXML
	private Button btnProfile;

	@FXML
	void showDashboard() {
		loadPage("Home");
	}
	
	@FXML
	void showProducts() {
		loadPage("Products");
	}
	
	@FXML
	void showPayments() {
		loadPage("Payments");
	}
	
	@FXML
	private void home(MouseEvent event) {
		bp.setCenter(ap);
	}

	private Main mainApp;

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialization code goes here
	}

	private void loadPage(String page) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/" + page + ".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bp.setCenter(root);
	}
	
	@FXML
	private void logout() {
		mainApp.showLoginForm();
		String filePath = System.getProperty("user.dir") + "/src/application/obj/user.txt";
		try {
			// Delete the existing file
			Files.deleteIfExists(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
