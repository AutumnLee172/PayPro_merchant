package application.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

import API.BiometricController;
import application.obj.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class PaymentsController implements Initializable {

	@FXML
	private Button btnCreate;

	@FXML
	private Button btnPay;

	@FXML
	private Pane pnlPayment;

	@FXML
	private Pane pnlRegistration;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtEmail_pay;

	@FXML
	private TextField txtTotal;

	@FXML
	private TextArea txtItems;

	@FXML
	void hideAllPanels() {
		pnlRegistration.setVisible(false);
		pnlPayment.setVisible(false);
	}

	@FXML
	void showpnlRegistration() {
		pnlRegistration.setVisible(true);
	}

	@FXML
	void registerBiometric() {
		String response;
		try {
			response = BiometricController.register(txtEmail.getText());

			// Create an Alert based on the response
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Registration Status");

			if (response.equals("Success")) {
				alert.setHeaderText("Successful");
				alert.setContentText("Biometric registration successful, waiting for user to confirm.");
			} else {
				alert.setHeaderText("Registration Failed");
				alert.setContentText(response);
			}

			// Display the alert
			alert.showAndWait();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	void showpnlPayment() {
		pnlPayment.setVisible(true);
		try (Scanner scanner = new Scanner(
				new File(System.getProperty("user.dir") + "/src/application/obj/cart.txt"))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(";");

				if (parts.length == 2) {
					String content = parts[0];
					String total = parts[1];
					txtItems.setText(content);
					txtTotal.setText(total);
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void Pay() throws IOException {
		try (Scanner scanner = new Scanner(
				new File(System.getProperty("user.dir") + "/src/application/obj/user.txt"))) {
			while (scanner.hasNextLine()) {
				String payerEmail = txtEmail_pay.getText();
				String merchantEmail = scanner.nextLine();
				String total = txtTotal.getText().trim();
				
				String response = BiometricController.pay(payerEmail, merchantEmail, total);

				// Create an Alert based on the response
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Payment Status");

				if (response.equals("Success")) {
					alert.setHeaderText("Payment Successful");
					String filePath = System.getProperty("user.dir") + "/src/application/obj/cart.txt";
					Files.deleteIfExists(Paths.get(filePath));
				} else {
					alert.setHeaderText("Payment Failed");
					alert.setContentText(response);
				}

				// Display the alert
				alert.showAndWait();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
