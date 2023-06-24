package application.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.UUID;

import application.obj.CartItem;
import application.obj.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ProductsController implements Initializable {

	@FXML
	private Button btnCreate;

	@FXML
	private Button btnNewProduct;

	@FXML
	private Button btnUpload;

	@FXML
	private ImageView imvImage;

	@FXML
	private Label lblimgpath;

	@FXML
	private Pane pnlNewProduct;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtPrice;

	@FXML
	private ScrollPane pnlProducts;

	@FXML
	private Button btnUpload_edit;

	@FXML
	private ImageView imvImage_edit;

	@FXML
	private Label lblimgpath_edit;

	@FXML
	private Pane pnlEditProduct;

	@FXML
	private TextField txtName_edit;

	@FXML
	private TextField txtPrice_edit;

	@FXML
	private Label lblProductid;

	@FXML
	private VBox productItem;

	@FXML
	private Button btnSave;

	@FXML
	private TextField txtTotal;

	@FXML
	private TableView<CartItem> tblItems;

	@FXML
	private Pane pnlCart;

	@FXML
	private Button btnPayment;

	private ObservableList<CartItem> cartItems;

	@FXML
	private TableColumn<CartItem, String> col_name;

	@FXML
	private TableColumn<CartItem, Integer> col_quantity;

	@FXML
	private TableColumn<CartItem, String> col_id;

	@FXML
	private TableColumn<CartItem, Integer> col_price;

	@FXML
	private TableColumn<CartItem, Integer> col_subtotal;

	@FXML
	void showNewProductPanel() {
		pnlNewProduct.setVisible(true);
	}

	@FXML
	void hideAllPanels() {
		pnlNewProduct.setVisible(false);
		pnlEditProduct.setVisible(false);
		pnlCart.setVisible(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		reloadProducts();
		cartItems = FXCollections.observableArrayList();
		// Define the cell value factories for each column
		col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		col_subtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
		// Set the items to the table
		tblItems.setItems(cartItems);

		tblItems.setRowFactory(tv -> {
			TableRow<CartItem> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					CartItem itemToRemove = row.getItem();
					removeCartItem(itemToRemove);
				}
			});
			return row;
		});
	}

	@FXML
	void createProduct() {
		String name = txtName.getText().trim();
		String price = txtPrice.getText().trim();
		String imagePath = lblimgpath.getText().trim();

		// Validate if required fields are not empty
		if (name.isEmpty() || price.isEmpty() || imagePath.isEmpty()) {
			// Show an error message or perform appropriate validation
			return;
		}

		// Create a Product object
		Product product = new Product(generateID(), name, price, imagePath);

		// Save the product record to the text file
		saveProduct(product);

		// Clear the input fields and reset the image view
		txtName.clear();
		txtPrice.clear();
		imvImage.setImage(null);
		lblimgpath.setText("");

		// Hide the new product panel if needed
		hideAllPanels();
		reloadProducts();
	}

	@FXML
	void uploadImage() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image");
		File selectedFile = fileChooser.showOpenDialog(imvImage.getScene().getWindow());

		if (selectedFile != null) {
			try {
				// Copy the selected image file to a desired location (e.g., image directory)
				String imagePath = System.getProperty("user.dir") + "/src/img/products/" + selectedFile.getName();
				Files.copy(selectedFile.toPath(), new File(imagePath).toPath(), StandardCopyOption.REPLACE_EXISTING);

				// Display the selected image in the ImageView
				imvImage.setImage(new Image("file:" + imagePath));
				lblimgpath.setText(imagePath); // Update the label with the image path
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveProduct(Product product) {
		String Id = product.getId();
		String name = product.getName();
		String price = product.getPrice();
		String imagePath = product.getImgpath();

		// Create the record string to be written to the text file
		String record = Id + "," + name + "," + price + "," + imagePath + "\n";

		// Append the record to the text file
		try (FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/src/application/obj/produts.txt",
				true)) {
			writer.write(record);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String generateID() {
		return UUID.randomUUID().toString();
	}

	private List<Product> loadProductsFromFile() {
		List<Product> productList = new ArrayList<>();

		try (Scanner scanner = new Scanner(
				new File(System.getProperty("user.dir") + "/src/application/obj/produts.txt"))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");

				if (parts.length == 4) {
					String id = parts[0];
					String name = parts[1];
					String price = parts[2];
					String imagePath = parts[3];
					Product product = new Product(id, name, price, imagePath);
					productList.add(product);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return productList;
	}

	private void addProductToContainer(Product product, int numberPerRow) {
		// Create UI nodes for the product
		ImageView imageView = new ImageView(new Image("file:" + product.getImgpath()));
		imageView.setFitWidth(177);
		imageView.setFitHeight(131);
		Label nameLabel = new Label(product.getName());
		Label priceLabel = new Label("$ " + product.getPrice());
		Button addButton = new Button("Add to Order");
		Spinner<Integer> spinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
		spinner.setValueFactory(valueFactory);
		addButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
		addButton.setOnMouseClicked(event -> {
			addtoCart(product, spinner.getValue());
		});

		// edit button
		Button editButton = new Button();
		Image editIcon = new Image(System.getProperty("user.dir") + "/src/img/icons8-edit-64.png");
		ImageView editImageView = new ImageView(editIcon);
		editImageView.setFitWidth(10);
		editImageView.setFitHeight(10);
		editButton.setGraphic(editImageView);
		editButton.setStyle("-fx-background-color: #E27429;");

		editButton.setOnMouseClicked(event -> {
			showEditPanel(product);
		});

		// Create a VBox container for the product
		VBox productContainer = new VBox(editButton, imageView, nameLabel, priceLabel, spinner, addButton);
		productContainer.setSpacing(10); // Set spacing between nodes
		productContainer.setAlignment(Pos.CENTER);
		editButton.setTranslateX(75);

		// Check if a new row needs to be created
		if (numberPerRow == 3 || numberPerRow == 0) {
			HBox row = new HBox(productContainer);
			row.setSpacing(10);
			productItem.getChildren().add(row);
		} else {
			HBox row = (HBox) productItem.getChildren().get(productItem.getChildren().size() - 1);
			row.getChildren().add(productContainer);
		}
	}

	private void reloadProducts() {
		productItem.getChildren().clear();
		List<Product> productList = loadProductsFromFile();
		int numberPerRow = 0;
		for (Product product : productList) {
			addProductToContainer(product, numberPerRow);
			numberPerRow++;
			if (numberPerRow == 3) {
				numberPerRow = 0;
			}
		}
	}

	private void showEditPanel(Product product) {
		txtName_edit.setText(product.getName());
		txtPrice_edit.setText(product.getPrice());
		lblProductid.setText(product.getId());
		lblimgpath_edit.setText(product.getImgpath());
		Image editIcon = new Image(product.getImgpath());
		imvImage_edit.setImage(editIcon);
		pnlEditProduct.setVisible(true);
	}

	@FXML
	void saveProduct() {
		String id = lblProductid.getText().trim();
		String name = txtName_edit.getText().trim();
		String price = txtPrice_edit.getText().trim();
		String imagePath = lblimgpath_edit.getText().trim();

		// Validate if required fields are not empty
		if (id.isEmpty() || name.isEmpty() || price.isEmpty() || imagePath.isEmpty()) {
			// Show an error message or perform appropriate validation
			return;
		}

		// Create a modified Product object
		Product updatedProduct = new Product(id, name, price, imagePath);

		// Load the existing products from the file
		List<Product> productList = loadProductsFromFile();

		// Find the index of the product to be updated
		int index = -1;
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).getId().equals(id)) {
				index = i;
				break;
			}
		}

		// Update the product in the list if found
		if (index != -1) {
			productList.set(index, updatedProduct);
		}

		// Save the updated product records to the text file
		saveProductsToFile(productList);

		// Clear the input fields and reset the image view
		txtName_edit.clear();
		txtPrice_edit.clear();
		lblProductid.setText("");
		lblimgpath_edit.setText("");
		imvImage_edit.setImage(null);

		// Hide the edit product panel if needed
		hideAllPanels();
		reloadProducts();
	}

	private void saveProductsToFile(List<Product> productList) {
		// Specify the path to the text file
		String filePath = System.getProperty("user.dir") + "/src/application/obj/produts.txt";

		try (FileWriter writer = new FileWriter(filePath, false)) {
			// Write each product record to the file
			for (Product product : productList) {
				String record = product.getId() + "," + product.getName() + "," + product.getPrice() + ","
						+ product.getImgpath() + "\n";
				writer.write(record);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void showCart() {
		pnlCart.setVisible(true);
	}

	private void addtoCart(Product product, int quantity) {
		CartItem cartItem = new CartItem(product.getId(), product.getName(), quantity,
				Double.parseDouble(product.getPrice()));
		cartItems.add(cartItem);
		tblItems.setItems(cartItems);

		double total = 0.0;
		for (CartItem item : cartItems) {
			total += item.getSubtotal();
		}
		txtTotal.setText(String.valueOf(total));
	}

	private void removeCartItem(CartItem itemToRemove) {
		cartItems.remove(itemToRemove);

		double total = 0.0;
		for (CartItem item : cartItems) {
			total += item.getSubtotal();
		}
		txtTotal.setText(String.valueOf(total));
	}

	private void saveCartToFile(List<CartItem> cartItems, double total) {
		String filePath = System.getProperty("user.dir") + "/src/application/obj/cart.txt";
		String record = "";
		try {
			// Delete the existing file
			Files.deleteIfExists(Paths.get(filePath));

			// Create a new file
			Files.createFile(Paths.get(filePath));

			// Write the cart items to the file
			FileWriter writer = new FileWriter(filePath);
			for (CartItem item : cartItems) {
				record += item.getQuantity() + " x " + item.getName() + ",";

			}
			record = record.substring(0, record.length() - 1);
			record += "; " + total;
			writer.write(record);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void gotoPayment() {
		saveCartToFile(cartItems, Double.parseDouble(txtTotal.getText()));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Order Confirmed");
		alert.setHeaderText("Please go to payment tab to complete the transaction.");
		alert.showAndWait();
	}
}
