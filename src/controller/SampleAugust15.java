package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.DBData;
import model.StaticData;
import model.UserInterest;
import model.UserType;
import util.DBConnector;

public class SampleAugust15 {
	
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
		
	//there are two question for that debug is not working
		//questions.add("How many major cities are in Texas");
		//questions.add("what the elevation of Cheaha Mountain");
   //////////questions.add("what are the lakes in states bordering Texas");
      //questions.add("what is the elevation of DeathValley");
///////questions.add("what state has the highest elevation");
		//questions.add("How many major cities are in states bordering Utah");
		///questions.add("How many major cities are in Texas");
		//questions.add("what is the largest state border with Arizona");
        //questions.add("which states have a capital"); //select StateCapital from state;//i am getting the ans but not getting expected answer
		//questions.add("what is the highest point of Alabama");
		//questions.add("what is the lowest point of Alabama");	

	 
		  //small letter of road is not detected as proper noun
			
			
		
	
		//questions.add("where is Massachusetts");
		questions.add("where is Mesa");
	    //questions.add("what is the largest road");
        //questions.add("what is the shortest road");
		//questions.add("name the rivers in Arkansas");
		//questions.add("what is the largest river that passes through the USA");
		//questions.add("what is the shortest river that passes through the USA");
		//questions.add("what are the neighbouring states for Michigan");//answer generation//
		//questions.add("Through which states the Mississippi river runs");
		//questions.add("which city has the highest population");//answer generation//
		//questions.add("which city has the lowest population");
		//questions.add("what is the length of Road 95 road");
		//questions.add("what is the highest point in Maine");
		//questions.add("what is the lowest point in Maine");
		//questions.add("what is the shortest river in Texas");
		//questions.add("Which states does Road 20 pass through");//solved//Road 65 is not detected as a proper noun//problem in answer generation
		//questions.add("what is the length of Road 10 road");//RoadLength prepareSameTableAttributeMappings//problem with landelevation question////
		//questions.add("what states border with Missouri");
		//questions.add("which states does Road 15 pass through");
		//questions.add("what states border states that the Mississippi runs through");
	
		//questions.add("where is Kansas");
		//questions.add("what states border with Missouri");
        //questions.add("what is the shortest city in California"); //need to implement user interest //done //shortest should be implemented
        //questions.add("what is the shortest river"); //done //have to implement shortest and as well as user interest//
		//questions.add("How many citizens are there in New York");
		//questions.add("How many citizens are in Arkansas");
		//questions.add("How many citizens in Boulder"); // problem of table
		//questions.add("which states does Road 95 pass through");
		//questions.add("Which largest state are border with Arizona");
		//questions.add("Which smallest state are border with Arizona");
        //questions.add("through which states does Road 95 pass through");
        //questions.add("Name the rivers in Arkansas");
        //questions.add("How big is Kansas"); //big comes twice//table name causing the problem //second table name should be here for this type of question
		//questions.add("How big is Texas");
		//questions.add("How big is Kansas City");
		//questions.add("what is the length of Mississippi river");
		//questions.add("how long is the Delaware river");   //answer needs to be fixed
        //questions.add("which rivers traverse through California");
		//questions.add("which river traverse through California");
        //questions.add("what is the length of Colorado river");
		//questions.add("which roads pass through Tennessee");
        //questions.add("which road passes through Tennessee");
        //questions.add("which rivers are passing through Arkansas");//done //(should remove third bracket )
        //questions.add("Which roads are passing through to California");
        //questions.add("which river passes through Arkansas");
        //questions.add("which rivers pass through Arkansas"); 
		//questions.add("Give me the states that border with Utah");
		//questions.add("Give me the state that border with Utah");
        //questions.add("What is the largest city in Kansas");//user preference//
        //questions.add("What is the biggest state in America");
		//questions.add("what is the shortest river in Texas");//longest//
		//questions.add("what is the largest river that passes through the America");//longest//
        //questions.add("which city has the highest population");//answer needs to be fixed//
        //questions.add("which state has the highest population");
		//questions.add("how many population of Kansas");
	    //questions.add("Tell me the cities of California");		
		//questions.add("what is the largest state");
		//questions.add("tell me the rivers in Texas");
		//questions.add("what is the largest river in Texas");//longest//
		//questions.add("What is the area of Kansas City");
		//questions.add("How long is the Colorado river");//answer needs to be fixed  
        //questions.add("which states does Missouri river pass through");
		//questions.add("through which states does the Mississippi river flow");
        //questions.add("which state does Missouri river pass through");
        //questions.add("which states have cities named Austin");
	    //questions.add("what is the largest mountain");
		//questions.add("how large is the largest city in California"); // implement biggest too which is missing
		//questions.add("what is the richest city in California");//gdp// 
        //questions.add("which state has city named Mobile"); 
		//questions.add("which state does Missouri river pass through");
		//questions.add("Which largest state are border with Arizona");//have to fix the answer generation
		//questions.add("Through which states does Shuvro river pass through");//there is not such of question
        //questions.add("what are the neighbour states for Michigan");
        //questions.add("which state has the highest population");
		// word for all words
		
	    //now i have to write which question needs which candidate attributes
	   //system should detect same name as a different table elements.
		
	   //system is not able to detect the parameter from which table the parameter belong. 
	   //Almost all cases it detects as a state table if the name is same.	
	   //questions.add("what is the largest state");//that problem is solved // 
	   //questions.add("what are the largest state");// problem solved//
	   //now its giving same as is singular verb question//we have to give different answer for this rather than giving Kansas has maximam area.	
       
       //questions.add("what is the largest mountain in Alaska");//solved//only work for none category//small and capital letter problem//mountain is detected as proper noun which is not and adjective is detected as improper noun
       //questions.add("what is the largest lake in Michigan");
       //questions.add("How big is Kansas"); //select  Area, TotalPopulation from state where StateId= 1028 and (Area = (select max(Area)from state where  StateId= 1028 group by StateId) or TotalPopulation = (select max(TotalPopulation)  from state where StateId = 1028 group by StateId));
       
	    // 1st problem, selecting different table name
		
		//questions.add("which road passes through Tennessee");
		//questions.add("through which states does Road 65 pass through");
		//questions.add("which rivers flows through Montana");//solved //else {answer += ( results.get(1) );//river and rivers problem//problem....should work like this road question
	    //questions.add("which river pass through Montana");//soved
        //questions.add("which rivers pass through Arkansas");

		
		
	   //for that we have to make new sql queries

	// 2nd problem, name ambiguity 
		
		//questions.add("what is the area of Mississippi");
        //questions.add("give me the cities of Alabama");
		//questions.add("what is the length of Mississippi river"); //solved//same case as white river 
				
	// 3rd problem
		
		
		//questions.add("what is the largest mountain in Alaska");//solved//problem of border and mountain

		//questions.add("Give me the states that border with Utah");
        //questions.add("which river traverse through California");//solved//if there is river instead of rivers then the system is taking another query for that.
        //questions.add("Give me the river that traverse with California");//solved//river and rivers problem
	    //questions.add("Tell me the rivers flow through Montana");//solved//select RiverName from river where StateId = 1064;   

		//questions.add("what is the richest state");//solved//gdp////we have not given entry in database in city column.
        //questions.add("which is the largest lake");//area////category 5 question
        //questions.add("what is the largest lake in Minnesota");// i want sql query something like give me question//LakeLength//-> // haven't catered the "longest"
        //questions.add("which road passes through Tennessee");//solved//problem in answer generation//     
				     
		//questions.add("Give me the states name that border with Utah"); 
		//questions.add("what is the population of the largest city");//debug is not working
		//questions.add("what is the population of the largest state");//for that debug is not working//
		//questions.add("what is the population of the largest state");
		// questions.add("what is the lowland of America");
		// questions.add("what is the hihgland of America");
		// questions.add("what is the top point of America");
		// questions.add("what is the bottom point of America");
        //questions.add("what is the peak length of Alabama");//mainAttribute:HighLength; table:landelevation//problem with river/road/lake/landelevation/prepareSameTableAttributeMappings
        //questions.add("what is the low length of Alabama");//mainAttribute:LowLength; table:landelevation//
		//questions.add("how large is the largest city in Alabama");
        //questions.add("Through which state does Missouri river pass");//solved//problem in answer generation
		//questions.add("what are the cities of Kansas");
        //questions.add("what are the top points surroundings Mississippi");//done//
        //questions.add("what is the bottom point of Louisiana");//lowest point //done//
	    //questions.add("What is the length of Superior lake");//lakelength in prepareSameTableAttributeMappings//attribute id was not there in preparewhereattributeforwheretype //we dont have where type attribute name here			
        //questions.add("what is the length of Missouri river");
        //questions.add("what is the lowland of Alabama"); //done// havent catered lowland//
		//questions.add("through which state does Road 95 pass through");
		//questions.add("what is the bottom point of Alabama");
	    //questions.add("give me the biggest city in California");
		//questions.add("what is the highland of Alabama");
	    //questions.add("what is the low length of Alabama");//need to make lots of change in prepare sameTableAttributeMapping to set table name 
		//questions.add("give me the length of the Road 90 road"); //problem of lake length, road length and landelevation length
		//questions.add("which road passes through Tennessee");
		//questions.add("Which road run through to Tennessee"); //Select Roadname from road where StateId=1057;		
        //questions.add("Which rivers are passing through to Arkansas");		
		//questions.add("How big is Kansas City ");//solved//we can get kansas city area by other question //
		//questions.add("Give me the state that border with Utah");
   		//questions.add("what is the area of Arkansas ");
		//questions.add("what is the gdp in California");    //questions.add("what is the area of Dhaka");
		//questions.add("what is the area of Oakland");
	    //questions.add("what is the area of Kansas City "); // its not giving answer
		//questions.add("How big is Detroit"); //SELECT Area from city where CityId =1176;
		//questions.add("what is the population live in Arkansas");//problem with count phrase question
		//questions.add("Which river run through Arizona");//select BorderName from border where StateId= 1011;//i need border here instead of states
        //questions.add("Which largest state are border with Arizona");
        //questions.add("Which smallest state are border with Arizona");
        //questions.add("which states border Missouri");//if i take border as improper noun then all is fine//select BorderName from border where StateId = 1023;//just need to border count column in state table
        //questions.add("Give me the state that border with Utah");//Select BorderName from border where StateId=1059;//if i omit states system works fine
		//questions.add("how tall is the top point of Alabama");
		//questions.add("Which states are next to Arizona");//if there is present next can we direct it as a border  //select B
		//questions.add("what is the largest point of Alabama");
		//questions.add("what is the bottom point of Alabama");
		//questions.add("what is the area of Wichita");
		//questions.add("what is the area of Topeka");
		//questions.add("what is the population live in Arkansas");	
        //questions.add("what is the length of Colorado river");
		//questions.add("can you tell me the capital of California");
        //questions.add("what is the length of Colorado river");////Colorado is detected as a state rather than river name
		//questions.add("what is the population live in Arkansas");
		//questions.add("what is the highland of Alabama");
	    //questions.add("what is the height of mount Mckinley");//In this question we dont have improper noun which give table name in our system //select Mountainheight from mountain where MountainId= 1500;
        //questions.add("how high is mount Mckinley"); 
        //questions.add("what is the height of Mckinley");////here where type value is not used 
		//questions.add("what is the region of America");
		//questions.add("what is the gmt of Alabama");
		//questions.add("what is the abbreviation of Kansas");
		//questions.add("what is the population of Arkansas"); //select TotalPopulation from state  where StateID = 1001 and TotalPopulation = (select max(TotalPopulation) from state where StateID = 1001 group by StateID);
		//questions.add("which are the largest city");//area //its not working for none category
		//questions.add("what are the largest state");//area, TotalPopulation, gdp, RiverCount, CityCount, RoadCount, LakeCount, MountainCount //
		//questions.add("Which lakes are in Michigan"); 
        //questions.add("What is the most populous city in California");//there is a difference between is and are.
		//questions.add("What is the largest city in Kansas");
		//questions.add("What is the shortest city in Kansas");// area //i need only 1 answer here//;//category 4 question//select CityName, Area, TotalPopulation from city where  StateID = 1028 and (Area = (select max(Area) from city where StateID = 1028 group by StateID) or TotalPopulation = (select max(TotalPopulation) from mtsaar2016.city where StateID = 1028 group by StateID));
		//questions.add("What is the largest city");
		//questions.add("what are the largest mountain in California");//MountainHeight //select  MountainName,Mountainheight from mountainwhere StateId= 1006 and (Mountainheight = (select max(Mountainheight) from mountain where  StateId= 1006 group by StateId));
		//questions.add("what is the population of Arkansas");
	    //questions.add("how many mountains are in America");
		//questions.add("What are the city of Kansas");// -// there is the difference of in and of.
	    //questions.add("What are the cities in Kansas");// -> //
		//questions.add("give me the cities which are in Kansas");
	    //questions.add("What is the largest lake in Michigan");//LakeLength//we have to make new sql for it// select LakeName, LakeLength from lake  where StateId = 1035 and (LakeLength = (select max(LakeLength) from lake where StateId = 1035 group by StateId));
        //questions.add("what is the lowest mountain in Alaska");
		//questions.add("How many cities in Kansas");// -> 
		//questions.add("What are the cities in Kansas");// -> //
		//questions.add("what cities are located in California");
		//questions.add("What are the states in USA");
		//questions.add("what is the largest lake in Michigan");
		//questions.add("what is the smallest lake in Michigan"); //space is not detected
	    //questions.add("Give me the lakes in Michigan");//category 2 question
		//questions.add("which lakes are in Michigan");
        //questions.add("which are the poorest states");
		//questions.add("what are the rivers in Arkansas");
		//questions.add("give me the capital of USA");//Category 1 question
		//questions.add("how many cities are in Kansas");
		//questions.add("how many rivers are in America");
		//questions.add("What are the lakes in Michigan");//select Lakename from lake where StateiD =1099 ;
		//questions.add("What is the area of Phoenix");
	    //questions.add("How many cities are in America");
		//questions.add("What is the population of Kansas");
		//questions.add("what are the cities in Kansas");//we have two table here.
		
		 		                                         // CANDIDATE ATTRIBUTE 
		 // how many category 1
		
		//questions.add("how many rivers are in America");//system is not detecting USA as a country.
		//questions.add("how many mountains are in America");
		//questions.add("how many cities are in Kansas");
		//questions.add("how many states are in America");
		//questions.add("how many roads are in Tennessee");
		
		//user interest question category 2
		
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
		
		//is singular verb question category 3
		
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
        //questions.add("what is the lowest mountain");// works only when user type is none
		//questions.add("what is the largest mountain in Alaska");
        //questions.add("what is the lowest mountain in Alaska");
		//questions.add("what is the largest lake");
		//questions.add("what is the shortest lake");
		//questions.add("what is the smallest lake");
		//questions.add("what is the largest state in America");
		//questions.add("what is the smallest state in America");
		
		// multiple improper noun question category 4
		
		//questions.add("which road passes through Tennessee");
    	//questions.add("which states have cities named Birmingham");
		//questions.add("which rivers pass through California");
		//questions.add("give me the states that border with Utah");
		 
		//normal question category 5 
		
		//questions.add("tell me the city in Kansas");
		//questions.add("name the rivers of Arkansas");
		//questions.add("what is the length of Missouri");
		//questions.add("tell me the lakes of Kansas");
		//questions.add("tell me the mountains of Missouri");
		//questions.add("which rivers are passing through Arkansas");
		//questions.add("Road 95 pass through which states");
        //questions.add("which is the length of Colorado river");                                      
		//questions.add("what are the largest states");//solved//GDP//if we consider two candidate attribute we will receive two seperate answers for that
        //questions.add("what is the richest city in California");//if we take of then it goes to normal sql query// we should ask with in... problem////gdp//
        //questions.add("What is the largest city in Kansas");//area//in and of problem//select CityName,Area,TotalPopulation from city where StateID = 1001 and (Area = (select max(Area) from city where StateID = 1001 group by StateID) or TotalPopulation = (select max(TotalPopulation)  from city where StateID = 1001 group by StateID));
        //questions.add("What is the length of Superior lake");
        //questions.add("what are the largest state");//area
		//questions.add("which is the largest city in Kansas ");//area//
		//questions.add("what are the richest states");//area//
		//questions.add("what is the largest mountain");//Mountainheight//
		//questions.add("what is the largest road in Alabama");//LakeLength//
		//questions.add("which road goes through Alabama");
        //questions.add("what is the largest capital state in America");//area//answer generation problem //there is a problem of " of and in "//
		//questions.add("what is the largest river");//RiverLength
		//questions.add("what is the capital of America");//area//there is a problem of " of and in "//
	 	//questions.add("give me the biggest city in California");//area//
        //questions.add("Give me the largest state in America"); //area//
        //questions.add("what is the largest lake in Michigan");//LakeLength
		//questions.add("which are the largest cities");//we havenot given the entry is DB//area, TotalPopulation, gdp, RiverCount, CityCount, RoadCount, LakeCount, MountainCount //
		//questions.add("what are the richest city in Kansas");//area, gdp, totalpopulation//we have to add gdp column in city table
	    //questions.add("what are the richest states");
		//questions.add("what are the largest states");//area, TotalPopulation, gdp, RiverCount, CityCount, RoadCount, LakeCount, MountainCount //
        //questions.add("what is the largest city in California");//area, gdp, totalpopulation //we have to add gdp column in city table
		//questions.add("what are the largest states");//area, population//
        //questions.add("What is the largest lake");//Mountainheight //only works for NONE category
        //questions.add("what is the largest mountain in Alaska");//Mountainheight//i want to show its height//select MountainName from mountain where StateId = 1006 and (Mountainheight = (select min(Mountainheight) from mountain where StateId = 1006 group by StateId));
        //questions.add("What is the largest state in the America");//area //select StateCapital, Area, TotalPopulation from state where CountryId= 104 and (Area = (select max(Area) from state where  CountryId= 104 group by CountryId) or TotalPopulation = (select max(TotalPopulation) from state where Cou ntryId = 104 group by CountryId));
		/*
		UserType u;
		if(string.equals("businessman")) {
			u = UserType.BUSINESSMAN;
		}
			*/	
		
		//questions.add("what is the richest state");
	    //questions.add("what is the richest city");
		//questions.add("what is the largest state");
        //questions.add("what is the largest city");
		
		//questions.add("what are the richest states");	
		//questions.add("what are the richest cities");
		//questions.add("what are the largest states");
		//questions.add("what are the largest cities");
		
				
		
		for (String question : questions) {
			// String sqlQuery = getQueryForQuestion(question.toLowerCase());
			try {
				long before = System.nanoTime();
				String sqlQuery = QueryGenerator.getAnswerForQuestion(question, UserType.TOURIST); //TOURIST// BUSINESSMAN// NONE);  //TOURIST // BUSINESSMAN// NONE
			                                                                                 //Default is NONE 
				long after = System.nanoTime();
				System.out.println("Time taken to answer the question: " + df.format((after-before)/10e8) + " seconds");
			}
			catch(Exception e) {
				System.out.println("THIS QUESTION IS NOT IN OUR DATABASE!!!");
			}

		}
	}
	
	// SELECT COUNT(*) FROM city;
	// SELECT COUNT(*) FROM river ;
	// SELECT COUNT(*) FROM road ;
	// SELECT COUNT(*) FROM mountain ;
	// SELECT COUNT(*) FROM lake ;
	
	/*select CityName from city where StateID = 1028 and 
(Area = (select max(Area) from city where StateID = 1028 
group by StateID));
*/
	/*select CityName from city where StateID = 1028 and 
(TotalPopulation = (select max(TotalPopulation) from city where StateID = 1028 
group by StateID));
*/
/*select CityName,Area,TotalPopulation from mtsaar2016.city where StateID = 1028 and (Area = (select max(Area)
from mtsaar2016.city where StateID = 1028 group by StateID) or
TotalPopulation = (select max(TotalPopulation) from mtsaar2016.city where
StateID = 1028 group by StateID));*/	

	/*select MountainName from mountain 
where StateId = 1006 and 
(Mountainheight = (select min(Mountainheight) 
from mountain where StateId = 1006 group by StateId));*/
	
	/*select StateName,gdp, RoadCount, CityCount from state where (gdp = (select max(gdp) from state)) or 
(RoadCount = (select max(RoadCount) from state)) or (CityCount = (select max(CityCount) from state));*/

	/*
	  QUERY FOR MULTIPLE CANDIDATE ATTRIBUTES - WITH LARGEST: select CityName
	  from mtsaar2016.city where StateID = 1028 and (Area = (select max(Area)
	  from mtsaar2016.city where StateID = 1028 group by StateID) or
	  TotalPopulation = (select max(TotalPopulation) from mtsaar2016.city where
	  StateID = 1028 group by StateID));
	 */
}
