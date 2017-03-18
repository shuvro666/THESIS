import java.util.Scanner;

public class Banki
{

		public static void main(String[] args) {
			while(true){
			Scanner scan = new Scanner(System.in);
			System.out.println("Please give your harvest details");
			int totalNumHarvest=scan.nextInt();
			System.out.println("Please tell us how much you want to deliver");
			int delivaryDetails=scan.nextInt();
			totalNumHarvest=totalNumHarvest-delivaryDetails;
			System.out.println("remaining harvest:"+ totalNumHarvest);
			
			int Truck=5;
			System.out.println("for your information number of truck we have is:" + Truck);
			int numberOfTruckNeeded = delivaryDetails/2;
			if(numberOfTruckNeeded>Truck){
				System.out.println("we dont have enough truck to carry.....please reduce your harvest amount");
		}
		else
		{
		System.out.println("total number of truck needed: "+ numberOfTruckNeeded);
		}
	    }
 }
}
/*
class Harvest{
	

	int remainingAmount =15;
	Harvest(){
		
		
		int totalHarvest=15;
		
		
	}
	int getTotalAmount(int totalAmount){
		
		return remainingAmount = remainingAmount-totalAmount;
		
		
	}
	
}
*/
