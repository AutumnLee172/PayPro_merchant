package application.obj;

import java.util.UUID;

public class Product {
	private String name, price, imgpath, id;
	
	public Product(String ID, String name, String price, String imgpath){
        this.setId(ID);
        this.setName(name);
        this.setPrice(price);
        this.setImgpath(imgpath);
    }
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getImgpath() {
		return imgpath;
	}
	
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	
	
}
