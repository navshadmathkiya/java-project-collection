

import java.util.*;

 abstract class Vehicle
 {
 
     String number;
	 String type;
	 int duration;
	 
	 
     Vehicle (String number,String type,int duration)
	 {
	   this.number = number;
	   this.type = type;
	   this.duration = duration;
	   
	 }
	 
	 public abstract double Calculatefee();
	 
	 public void displayDetails()
	 {
	   System.out.println("vehicle number : " + number);
	   System.out.println("vehicle type: " + type);
	   System.out.println("Parking hour : " + duration);
	   System.out.println("Parking fee :" + Calculatefee());
	   System.out.println("-----------------------------");
	   
	   
	 }
   
}

 class Car extends Vehicle {
 
    Car (String number ,int duration){
	
	 super (number, "Car",duration);
	 
	}
	
	public double Calculatefee()
	{
	
	 return 20 * duration;
	 
	  
	}
}

 class Bike extends Vehicle {
 
    Bike (String number,int duration)
	{
	    super(number, "Bike", duration);
	}
	
	public double Calculatefee()
	  {
	    return 10 * duration;
	  }
	
 }
 
 class Truck extends Vehicle {
 
     Truck (String number,int duration)
	 {
	   super(number,"Truck",duration);
	   
	 }
	 public double Calculatefee()
	 {
	 
	  return 30 * duration;
	  
	 }
 }
 
 public class SmartParkingManagementSystem{
        public static void main (String args[])
		{
		
		Scanner sc = new Scanner (System.in);
		List <Vehicle> ParkingList = new ArrayList <>();
		
		System.out.println("-----SmartParkingManagementSystem------");
		System.out.println("Enter the deatils of vehicles to park:");
		int n = sc.nextInt();
		
		
		for(int i=0;i<n;i++)
		{
		System.out.println("\n enter deatils for vehicles" + (i+1)+ ":");
		
         System.out.println("Enter the vehicle number :");
         String number = sc.next();

         System.out.println("Enter the vehicle type(car/bike/truck) :");
         String type = sc.next().toLowerCase();
 
         System.out.println("Enter the duration(int hour):");
         int duration = sc.nextInt(); 
		
		Vehicle v = null;
			
	     switch (type) {
                case "car":
                    v = new Car(number, duration);
                    break;
					
		        case "bike" :
				    v = new Bike(number,duration);
					break;
				case "truck" : 
				     v = new Truck(number,duration);
					 break;
				default :
                    System.out.println("invalid vehicle type:");
                    continue;
					
		}
		
		 ParkingList.add(v);
		 
		}
          System.out.println("\n---Parking details----");
		  for(Vehicle v : ParkingList)
		  {
			   v.displayDetails();
		  }
		  sc.close();
		  
		
		
		
  }
 
 }


















