package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.DBData;
import model.StaticData;
import model.UserInterest;
import model.UserType;
import util.DBConnector;

public class SampleMainClass {
	
	static DecimalFormat df = new DecimalFormat("#.###");

	/**
	 * load all the prerequisites. this should be the first function that is
	 * called when your program starts.
	 * 
	 * @throws Exception
	 */
	private static void loadPrereq() throws Exception {
		DBData.loadAllDBData();
		StaticData.prepareAllStaticData();
		UserInterest.prepareAllUserInterests();
	}

	public static void main(String[] args) throws Exception {
		// load prerequisites
		loadPrereq();
		List<String> questions = new ArrayList<>();

		
		//questions.add("what city has largest population?");
		//questions.add("what is the longest river");
		//questions.add("where is Massachusetts?");
		//questions.add("where is Mesa");
	    //questions.add("what is the largest road");
        //questions.add("what is the shortest road");
		//questions.add("name the rivers in Arkansas");
		//questions.add("what is the largest river that passes through the USA");
		//questions.add("what is the shortest river that passes through the USA");
		//questions.add("what are the neighbouring states for Michigan");
		//questions.add("Through which states the Mississippi river flow");
		//questions.add("which city has the highest population");
		//questions.add("which city has the lowest population");
		//questions.add("what is the length of Road 95 road");
		//questions.add("how tall is the highest point in Montana");
		//questions.add("what is the lowest point in Maine");
		//questions.add("what is the shortest river in Nebraska");
		//questions.add("Which states does Road 20 pass through");
		//questions.add("what is the length of Road 10 road");
		//questions.add("what states border Missouri");
		//questions.add("which states does Road 15 pass through");
		//questions.add("what states border states that the Mississippi runs through");
		//questions.add("what is the elevation of Death Valley");
		//questions.add("where is Kansas");
		//questions.add("what states border with Missouri");
	    //questions.add("what is the population of the largest state");
	    //questions.add("what is the population of the largest city");
		//questions.add("what is the richest city in Alabama");//gdp// not working for tourist
		//questions.add("Which largest state are border with Arizona");//need to add area column in border table		/////////questions.add("what is the shortest city in California"); //need to implement user interest //done //shortest should be implemented
		//questions.add("What is the largest city in Kansas");//user preference//
		//questions.add("what is the shortest river"); 
		//questions.add("How many citizens are there in New York");
		//questions.add("How many people are in New York");
		//questions.add("How many citizens in Boulder"); 
		//questions.add("which states does Road 95 pass through");
		//questions.add("Which largest state are border with Arizona");
		//questions.add("Which smallest state are border with Arizona");
        //questions.add("through which states does Road 95 pass through");
        //questions.add("Name the rivers in Arkansas");
        //questions.add("How big is Texas");
		//questions.add("How big is Texas");
		//questions.add("How big is Kansas City");
		//questions.add("what is the length of Mississippi river");
		//questions.add("how long is the Delaware river");
        //questions.add("which rivers traverse through California");
		//questions.add("which river traverse through California");
        //questions.add("what is the length of Colorado river");
		//questions.add("which roads pass through Tennessee");
        //questions.add("which road passes through Tennessee");
        //questions.add("which rivers are passing through Arkansas");
        //questions.add("Which roads are passing through to California");
        //questions.add("which river passes through Arkansas");
        //questions.add("which rivers run through Arizona"); 
		//questions.add("Give me the states that border with Utah");
		//questions.add("Give me the state that border with Utah");
        //questions.add("What is the biggest river in Texas");
		//questions.add("what is the shortest river in Texas");
		//questions.add("what is the largest river that passes through the America");
        //questions.add("which city has the highest population");
        //questions.add("which state has the highest population");
		//questions.add("how many population of Kansas");
	    //questions.add("Tell me the cities of Kansas");		
		//questions.add("what is the largest state");
		//questions.add("what are the rivers in Texas");
		//questions.add("what is the longest river in Texas");
		//questions.add("What is the area of Kansas City");
		//questions.add("How long is the Colorado river");  
        //questions.add("which states does the Missouri river pass through");
		//questions.add("through which states does the Mississippi river flow");
        //questions.add("which state does Missouri river pass through");
        //questions.add("which states have cities named Mobile");
	    //questions.add("what is the largest mountain");
		//questions.add("how large is the biggest city in California");
        //questions.add("which states have city named Mobile"); 
		//questions.add("which state does Missouri river pass through");
        //questions.add("what are the neighbouring states for Michigan");
        //questions.add("which state has the highest population");
		//questions.add("how high is the highest point in America");
	    //questions.add("what is the largest state"); 
	    //questions.add("what are the largest state");
        //questions.add("what is the largest mountain in Alaska");
        //questions.add("what is the largest lake in Minnesota");
        //questions.add("How big is Kansas");
		//questions.add("which road passes through Tennessee");
		//questions.add("through which states does Road 65 pass through");
		//questions.add("which rivers flows through Montana");
	    //questions.add("which river pass through Montana");
        //questions.add("which rivers pass through Arkansas");
		//questions.add("what is the area of Mississippi");
        //questions.add("give me the cities in Virginia");
		//questions.add("what is the length of Arkansas river");
		//questions.add("what is the largest mountain in Alaska");
		//questions.add("Give me the states that border with Utah");
        //questions.add("which river traverse through California");
        //questions.add("Give me the river that traverse with California");
	    //questions.add("Tell me the rivers flow through Montana");  
		//questions.add("what is the richest state");
        //questions.add("which is the largest lake");
        //questions.add("what is the largest lake in Minnesota");
        //questions.add("which road passes through Tennessee");     
	    //questions.add("what is the highest point of Alabama");
	    //questions.add("what is the lowest point of Alabama");	 
		//questions.add("Give me the states name that border with Utah"); 
        //questions.add("what is the population of the largest city");
		//questions.add("what is the lowland of America");
		//questions.add("what is the highland of America");
		//questions.add("what is the top point of America");
		//questions.add("what is the bottom point of America");
        //questions.add("what is the peak length of Alabama");
        //questions.add("what is the low length of Alabama");
		//questions.add("how large is the largest city in Alabama");
        //questions.add("Through which state does Missouri river pass");
		//questions.add("what are the cities of Kansas");
        //questions.add("what is the high points surroundings Mississippi");
        //questions.add("what is the bottom point of Louisiana");
	    //questions.add("What is the length of Superior lake");			
        //questions.add("what is the length of Missouri river");
        //questions.add("what is the lowland of Alabama");
		//questions.add("through which state does Road 95 pass through");
	    //questions.add("what is the bottom point of Alabama");
	    ///questions.add("what is the biggest city in California");
        //questions.add("what is the peak length of Alabama"); 
        //questions.add("what is the low length of Alabama");
        //questions.add("give me the length of the Road 95 road"); 
		//questions.add("which road passes through Tennessee");
		//questions.add("Which road run through to Tennessee");		
        //questions.add("Which rivers are passing through to Arkansas");		
	    //questions.add("How big is Kansas City ");
		//questions.add("Give me the states that border with Utah");
   		//questions.add("what is the area of Arkansas ");
		//questions.add("what is the gdp in California");  
		//questions.add("what is the area of Dhaka");
		//questions.add("what is the area of Maine");
	    //questions.add("what is the area of Kansas City ");
	    //questions.add("How big is Detroit"); 
		//questions.add("what is the population live in Arkansas");
	    //questions.add("Which river run through Arizona");
        //questions.add("Which largest state are border with Arizona");
        //questions.add("Which smallest state are border with Arizona");
        //questions.add("which states border Missouri");
        //questions.add("Give me the state that border with Utah");
	    //questions.add("how tall is the top point of Alabama");
		//questions.add("Which states are next to Arizona");
		//questions.add("what is the largest point of Alabama");
	    //questions.add("what is the top point of Alabama");
	    //questions.add("what is the bottom point of Alabama");
		//questions.add("what is the area of Wichita");
		//questions.add("what is the area of Topeka");
		//questions.add("what is the population live in Arkansas");	
        //questions.add("what is the length of Snake river");
		//questions.add("can you tell me the capital of Texas");
        //questions.add("what is the length of Colorado river");
		//questions.add("what is the population live in Arkansas");
	    //questions.add("what is the height of mount Mckinley");
        //questions.add("how high is mount Mckinley"); 
        //questions.add("what is the height of Mckinley");
		//questions.add("what is the region of America");
		//questions.add("what is the gmt of Alabama");
		//questions.add("what is the abbreviation of Kansas");
		//questions.add("what is the population of Arkansas"); 
		//questions.add("which are the largest city");
		//questions.add("what are the largest state");
		//questions.add("Which lakes are in Michigan"); 
        //questions.add("What is the most populous city in California");
		//questions.add("What is the biggest city in Arizona");
		//questions.add("What is the shortest city in Kansas");
		//questions.add("What is the largest city");
		//questions.add("what is the largest mountain in California");
		//questions.add("what is the population of largest state");
	    //questions.add("how many mountains are in America");
		//questions.add("What are the city of Kansas");
	    //questions.add("What are the cities in Kansas");
		//questions.add("give me the cities which are in Kansas");
	    //questions.add("What is the largest lake in Michigan");
        //questions.add("what is the lowest mountain in Alaska");
		//questions.add("tell me the city in Kansas");
		//questions.add("tell me the cities in Kansas");
		//questions.add("what cities are located in California");
		//questions.add("What are the states in USA");
		//questions.add("what is the largest lake in Michigan");
		//questions.add("what is the smallest lake in Michigan");
	    //questions.add("Give me the lakes in Michigan");
		//questions.add("which lakes are in Michigan");
        //questions.add("which are the poorest states");
		//questions.add("what are the rivers in Arkansas");
		//questions.add("give me the capital of USA");
		//questions.add("how many cities are in Kansas");
		//questions.add("how many rivers are in Arkansas");
		//questions.add("What are the lakes in Michigan");
		//questions.add("What is the area of Kansas");
	    //questions.add("How many cities are in America");
		//questions.add("What is the population of Kansas");
		//questions.add("what are the cities in Kansas");
		//questions.add("how many rivers are in America");
		//questions.add("how many mountains are in California");
		//questions.add("how many cities are in Kansas");
		//questions.add("how many states are in America");
		//questions.add("how many roads are in Tennessee");
	    //questions.add("what are the richest states");
        //questions.add("what are the poorest states");
		//questions.add("what are the richest cities");
		//questions.add("what are the largest states");
		//questions.add("what are the smallest states");
		//questions.add("what are the largest cities");
		//questions.add("what is the richest state");
		//questions.add("what is the richest city");
		//questions.add("what is the largest state");
		//questions.add("what is the largest city");
		//questions.add("what are the largest states");
	    //questions.add("what are the richest cities");
		//questions.add("what are the richest states");
		//questions.add("what are the poorest states");
		//questions.add("what is the largest state in America");
		//questions.add("what is the smallest state in America");
		//questions.add("what is the richest city");
		//questions.add("what is the poorest city");
		//questions.add("what is the richest state");
		//questions.add("what is the poorest state");
		//questions.add("what is the largest city");
		//questions.add("what is the smallest city");
		//questions.add("what is the largest state");
		//questions.add("what is the smallest state");
        //questions.add("what is the lowest mountain");
		//questions.add("what is the largest mountain in Alaska");
        //questions.add("what is the lowest mountain in Alaska");
		//questions.add("what is the largest lake");
		//questions.add("what is the shortest lake");
		//questions.add("what is the smallest lake");
		//questions.add("what is the largest state in America");
		//questions.add("what is the smallest state in America");
		//questions.add("which road passes through Tennessee");
    	//questions.add("which states have cities named Joliet");
		//questions.add("which rivers pass through California");
		//questions.add("give me the states that border with Utah");	
		//questions.add("how many city in Kansas");
		//questions.add("tell me the city in Kansas");
		//questions.add("name the rivers of Arkansas");
		//questions.add("what is the length of Missouri river");
		//questions.add("tell me the roads of California");
		//questions.add("tell me the mountains of Missouri");
		//questions.add("which rivers are passing through Arkansas");
		//questions.add("Road 95 pass through which states");
        //questions.add("which is the length of Colorado river");                                      
		//questions.add("what are the largest states");
        //questions.add("what is the richest city in California");
        //questions.add("What is the largest city in Kansas");
        //questions.add("What is the length of Superior lake");
        //questions.add("what are the largest state");
		//questions.add("which is the largest city in Kansas ");
		//questions.add("what are the richest states");
		//questions.add("what is the largest mountain");
		//questions.add("what is the largest road in Alabama");
		//questions.add("which road goes through Alabama");
        //questions.add("what is the largest capital state in America");
		//questions.add("what is the largest river");
	    //questions.add("what is the capital of America");
        //questions.add("what is the largest state in America");
        //questions.add("what is the largest lake in Michigan");
		//questions.add("which are the largest cities");
		//questions.add("what are the richest city in Kansas");
	    //questions.add("what are the richest states");
		//questions.add("what are the largest states");
        //questions.add("what is the largest city in California");
		//questions.add("what are the largest states");
        //questions.add("What is the largest lake");
        //questions.add("what is the largest mountain in Alaska");
        //questions.add("What is the largest state in the America");
		//questions.add("what is the richest state");
	    //questions.add("what is the richest city");
        //questions.add("what is the largest state");
		//questions.add("what is the largest state in USA");
        //questions.add("what is the largest city");
		//questions.add("what are the richest states");	
		//questions.add("what are the richest cities");
		//questions.add("what are the largest states");
		//questions.add("what are the largest cities");
		
				
		
		for (String question : questions) {
			try {
				long before = System.nanoTime();
				String sqlQuery = QueryGenerator.getAnswerForQuestion(question, UserType.BUSINESSMAN); //TOURIST// BUSINESSMAN// NONE			                                                                                 //Default is NONE 
				long after = System.nanoTime();
				System.out.println("Time taken to answer the question: " + df.format((after-before)/10e8) + " seconds");
			}
			catch(Exception e) {
				System.out.println("THIS QUESTION IS NOT IN OUR DATABASE!!!");
			}

		}
	}
	
	
}
