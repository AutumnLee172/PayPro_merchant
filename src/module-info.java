module PayPro {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.desktop;
	requires org.json;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.Controllers to javafx.fxml;
	opens application.obj to javafx.base;
}
