package application.obj;

public class CartItem {
    private String name, id;
    private int quantity;
    private double subtotal, price;

    public CartItem(String id, String name, int quantity, double price) {
    	setId(id);
        setName(name);
        setQuantity(quantity);
        setPrice(price);
        setSubtotal(price * quantity);
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
		this.name = name;
	}
    
    public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    public int getQuantity() {
        return quantity;
    }

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
}
