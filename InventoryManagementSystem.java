

 
 import java.util.*;
 
 class Product 
 {
	  int id;
	  String name;
	  String category;
	  double price;
	  int quantity;
	  
	  Product(int id,String name,String category,double price,int quantity)
	  {
		  this.id = id;
		  this.name = name;
		  this.category = category;
		  this.price = price;
		  this.quantity = quantity;
	  }
	  
	  public void displayProduct() {
    System.out.printf("%-5d %-15s %-12s %-10.2f %-5d%n", id, name, category, price, quantity);
    } 

 }
 
 class InventorySystem {
	 private ArrayList<Product> products = new ArrayList<>();
	 
	 // add new product 
	 
	 public void addProduct(Product p) {
		 products.add(p);
		 System.out.println("product added successfully!");
		 
	 }
	 // update product quantity
	 
	 public void updateStock(int id,int newQty) {
		 for(Product p : products) {
			 if(p.id == id)
			 {
				 p.quantity = newQty;
				 System.out.println("Stock updated for :" + p.name);
				 return;
				 
				 
			 }
		 }
		 System.out.println("product id not found!");
	 }
	 
	 // remove product 
	 public void removeProduct(int id) {
		 Iterator<Product> it = products.iterator();
		 while (it.hasNext ()) {
			 Product p = it.next();
			 if(p.id == id) {
				 it.remove();
				 System.out.println("product removed :" + p.name);
				 return;
			 }
		 }
		 
		 System.out.println("product id not found!");
	 }
	 // display all product 
	 public void displayInventory() {
		 System.out.println("\n === Inventory List ==== ");
		 System.out.printf("%-5s %-15s %-12s %-10s %-5s%n", "ID", "Name", "Category", "Price", "Qty");
           for (Product p : products) {
			   p.displayProduct();
		   }
		 
	 }
	 
  }
  
  public class InventoryManagementSystem{
	  public static void main (String args[])
	  {
		  Scanner sc = new Scanner(System.in);
		  InventorySystem system = new InventorySystem();
		  
		  system.addProduct(new Product (101,"laptop", "Electronics",55000,10));
		  system.addProduct(new Product (102,"T-Shirt", "Clothing",4300,60));
		  system.addProduct(new Product (103,"Pant", "Clothing",5600,33));
		  system.addProduct(new Product (104,"Rice-Bag", "Groceries",7000,56));
		  system.addProduct(new Product (105,"Machine", "Electronics",12000,70));
		  
		  int choice;
		  
		  do {
			  System.out.println("\n ==== Inventory menu ====");
			  System.out.println("1. Display Inventory");
			  System.out.println("2. Add Product");
			  System.out.println("3.update stock");
			  System.out.println("4. remove product");
			  System.out.println("5. Exit");
			  System.out.println("Enter your choice :");
			  choice = sc.nextInt();
			  
			  switch(choice)
			  {
				  case 1 :
				  system.displayInventory();
				  break;
				  
				  case 2 :
				   System.out.println("Enter Id, Name, Category,Price, Quantity :");
				   int id = sc.nextInt();
				   String name = sc.next();
				   String category= sc.next();
				   double price = sc.nextDouble();
				   int qty = sc.nextInt();
				   
				   system.addProduct(new Product(id,name,category,price,qty));
				   break;
				   
				   case 3 : 
				     System.out.println("Enter Product Id an new Quantity :");
					 int pid = sc.nextInt();
					 int newQty = sc.nextInt();
					 system.updateStock(pid,newQty);
					 break;
					 
				 case 4 :
				  System.out.println("Enter Product Is and remove :");
				  int rid = sc.nextInt();
				  system.removeProduct(rid);
				  break;
				  
				  case 5 :
				  System.out.println("Exiting .... Thank you! ");
				  break;
				  
				 default :
				  System.out.println("invalid choice! Try again!");
				
				}
				
		  }while(choice != 5 );
		  sc.close();
		  
	  }
  }
 