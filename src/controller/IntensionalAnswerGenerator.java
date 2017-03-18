package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import model.StaticData;

public class IntensionalAnswerGenerator {
	public static String getAnswerSentence(String mainAttribute,  List<String> adjectives, String Verb, List<String> improperNouns, String properNoun, List<String> results, boolean isSameTableQuestion) {
		String answer = "";

		if (results == null || results.isEmpty()) {
			answer = "There are no " + improperNouns.get(0) + (!properNoun.isEmpty() ? (" in " + properNoun) : "") + ".";
		} 
		
		else if (Verb.contains("passing")){
			
			String result = "";
			
			for (int i=0; i<results.size(); i++) {
				if (i == results.size()-1){
					result += results.get(i) + " ";
				}else{
					result += results.get(i) + ", ";
				}
			}
			
			answer = result + "are " + " passing " +  " through "+ properNoun;
		}
		
		else if(improperNouns.contains("capital")){
			answer = "every states has capital." ;
		}
		
		else {
			int resultSize = results.size();
			int maxAnswersToShow = 5;
			// a maximum of 5 answers should be displayed!
			int numberOfIterations = resultSize > maxAnswersToShow ? maxAnswersToShow : resultSize;
			
			if (resultSize > maxAnswersToShow) {
				answer = "Some of the";
			}
			
			answer += (adjectives != null && !adjectives.isEmpty()) ? (answer.length() > 0 ? " " : "") + adjectives.get(0) : "";
			int adjectivesCount = adjectives.size();
			for (int i = 1; i < adjectivesCount; i++) {
				// the last adjective
				if (i == adjectivesCount - 1)
					answer += " and " + adjectives.get(i);
				else
					answer += ", " + adjectives.get(i);
			}
			
			answer += (answer.length() > 0 ? " " : "") + improperNouns.get(0) + (!properNoun.isEmpty() ? ((isSameTableQuestion ? " of " : " in ") + properNoun) : "") + (results.size() > 1 ? " are " : " is ");
			answer += results.get(0);
			
			for (int i = 1; i < numberOfIterations; i++) {
				// the last element
				if (i == numberOfIterations - 1)
					answer += " and " + results.get(i);
				else
					answer += ", " + results.get(i);
			}
            if (improperNouns.contains("length") || mainAttribute.contains("RiverLength"))
               {
            	answer += " km" + ".";
            	}
            else if (improperNouns.contains("height")){
            	answer += " m" + ".";
            }
            	else if (mainAttribute.contains("Area"))
            	{
            		answer += " km²" + ".";
            	}
            	else	
            {
			answer += ".";
		}}

		return answer;
	}
	
	public static String getAnswerSentenceForUserInterest(List<String> improperNouns, Map<String, List<String>> attributeNameWithResults, Set<String> candidateAttributes, boolean isMinQuestion) {
		String answer = "";
		
		if (attributeNameWithResults == null || attributeNameWithResults.isEmpty()) {
			answer = "There are no " + improperNouns.get(0) + ".";
		} else {
			Iterator<String> iterator = candidateAttributes.iterator(); 
			int index = 0;
			Set<String> entryset = attributeNameWithResults.keySet(); 
			String key = entryset.iterator().next();
			ArrayList<List<String>> subLists = new ArrayList<List<String>>();
			int numRecords = attributeNameWithResults.get(key).size()/(candidateAttributes.size()+1);
	    	for(int i = 0; i < numRecords; i++) { 
		    	List<String> subList = attributeNameWithResults.get(key).subList(index, index+candidateAttributes.size()+1);
		    	subLists.add(subList);
		    	index += (candidateAttributes.size()+1);
	    	}
	    	int numResultsToShow = 5;
			int attributeID = 1;
			for(int i = 0; i < candidateAttributes.size(); i++) {
		    	String candidateAttribute = iterator.next();
		    	for(int j = 0; j < numResultsToShow; j++) {
		    		List<String> subList = subLists.get(new Random().nextInt(subLists.size()));
		    		//List<String> subList = subLists.max.nextInt(subLists.size()));
		    		
		    		String stateName = subList.get(0);
			    	int count = Integer.parseInt(subList.get(attributeID));
			    	String normalName = StaticData.attributeNameNormalNameMappings.get(candidateAttribute);
			    	
			    	if (candidateAttribute.contains("gdp")){
			    		
			    		System.out.println(normalName + " " + "of " + stateName + " is " + count);
			    		answer += (normalName + " " + "of " + stateName + " is " + count + "\n");
			    	}
			    	else{
			    		System.out.println(stateName + " has " + count + " " + normalName);
			    		answer += (stateName + " has " + count + " " + normalName + "\n");

			    	}
			    			  
		    	}
		    	attributeID++;
		    	answer += ("\n");
		    	System.out.println();

			}
		    
		
		}
		
		return answer;
	}
	
	/*
		public static String getAnswerSentenceForUserInterest(List<String> improperNouns, Map<String, List<String>> attributeNameWithResults, boolean isMinQuestion) {
		String answer = "";

		
	 //	String results;
		if (attributeNameWithResults == null || attributeNameWithResults.isEmpty()) {
			answer = "There are no " + improperNouns.get(0) + ".";
		} else {
			for (Entry<String, List<String>> entry : attributeNameWithResults.entrySet()) {
				String attributeNormalName = entry.getKey();
				List<String> results = entry.getValue();
				
				int resultSize = results.size();
				
				
				for(int i = 0; i < results.size(); i++) {
					if(i < results.size()-1) {
						answer += ( results.get(i)+ ",");
					}
					
					
					else {
						answer += ( results.get(0));
					}
					
				
				answer += results.size() > 1 ? " have ":" has ";
				answer += isMinQuestion ? "minimum " : "maximum ";
				answer += results+ attributeNormalName;
				
			
				}
			
		return answer;}}
		return answer;
	
		}
	
	*/
	
	
	
	/*
	public static String getAnswerSentenceForMultipleProperNouns(ArrayList<String> tableNames, String properNoun, String preposition, List<String> results) {
		String answer = "";
		String tableName = tableNames.get(1);
		
		
	   answer += (properNoun + " has " + tableName + " with ");
		
		
		
		for(int i = 0; i < results.size(); i++) {
			if(i < results.size()-1) {
				answer += (results.get(i) + ", ");
			}
			
			
			else {
				answer += (results.get(i));
			}
		}
		return answer;	
		
	}*/
	// this is for the question through which states Road 65 passes through
	// this is for the question which states have cities named Austin

	
	public static String getAnswerSentenceForMultipleProperNouns(ArrayList<String> tableNames, String properNoun, String preposition, List<String> results, List<String> improperNouns, String adjectives) {
		String answer = "";
		String tableName = tableNames.get(1);
		
		 //Which states does Road 65 pass through
  //  answer = properNoun + " has " + tableName + " with ";
		
		
		int rz = results.size();
		for(int i = 0; i < results.size(); i++){
		
		if (i < results.size()-1)
		
		{
			answer+=results.get(i)+ "," ;
		
		}
		
		else if (rz==1)
		{
			answer+=results.get(0);
			
		}
		
		else
			
		{
			answer += ( results.get(i) );
			
		}
		
		}
		/*for(int i = 0; i < results.size(); i++) {
			if(i < results.size()-1) 
			{
				answer += ( results.get(i)+ "," ); 
			}
			
			
		else if (i > results.size()-1)
				
//				 (improperNouns.contains("rivers") && !(improperNouns.contains("traverse")) && !improperNouns.contains("pass") 
//						 || (improperNouns.contains("river")) && !(improperNouns.contains("traverse"))&& !improperNouns.contains("passes") && !improperNouns.contains("pass")
//						 || (improperNouns.contains("states") && !(improperNouns.contains("river")) && !(improperNouns.contains("pass")))
//						 || improperNouns.contains("border")
//						 || (improperNouns.contains("states")|| improperNouns.contains("state") && improperNouns.contains("pass") && improperNouns.contains("river")
//						 || improperNouns.contains("flow") 
//						 || improperNouns.contains("flows")
//						 || (improperNouns.contains("city") 
//						 || (improperNouns.contains("cities")
//						 || (improperNouns.contains("states") && !improperNouns.contains("pass") && !improperNouns.contains("river")
//						 ||((improperNouns.contains("state")) && improperNouns.contains("pass") && !improperNouns.contains("river"))
//						 
//							))))){
					{answer+=results.get(0);
				}
				
				
				else {
				answer += ( results.get(1) );//if results.get(0) then it works for river question
	                                         //which river flows through Montana			
			}
			
		}
*/
		
	
		if (improperNouns.contains("roads")&&improperNouns.contains("pass")){
			
			answer = properNoun + " pass " + preposition + " "+ answer;
		}
		
		
      if(improperNouns.contains("passes") || (improperNouns.contains("rivers")||improperNouns.contains("river")) && !improperNouns.contains("traverse") && !improperNouns.contains("flows") && !improperNouns.contains("flow")){
		
		
		answer += " pass " + preposition + " " + properNoun ;}
		
		if(tableName.contains("road")){
			
			answer = properNoun + " passes " + preposition + " "+ answer;
		}
		
		
		//Which road passes through Tennessee
      
      
      if(improperNouns.contains("border") && !adjectives.contains("largest") && !adjectives.contains("smallest")){
  		
  		
    	  answer = properNoun + " has " + "border " + preposition + " " + answer;
        }
      
    
      else if (improperNouns.contains("flow") || improperNouns.contains("flows")){	
    	  
    	  answer += " flows " + preposition + " " + properNoun ;    //Tell me the rivers flows through Montana.
    	  
      }
      
      else if (improperNouns.contains("traverse")){	
    	  
    	  answer += " traverse " + preposition + " " + properNoun ;    //Tell me the rivers flows through Montana.
    	  
      }
			
	//	answer += " traverse " + preposition + " " + properNoun ; //Give me the rivers that traverse through Montana.

		
      else if(improperNouns.contains("cities") || (improperNouns.contains("city"))){
    	  
    	  answer += " has " + tableName + " named " + " " +  properNoun;} //which states have cities named Birmingham

      else if(adjectives.contains("largest")){
    	  
    	  answer += "is " + "the " + adjectives + " state" + " that " + tableName + " " + preposition + " " + properNoun;
    	  
      }
      else if (adjectives.contains("smallest")){
    	  
    	  answer += "is " + "the " + adjectives + " state" + " that " + tableName + " " + preposition + " " + properNoun;
    	  
      }
      
      else {
    	  
    	  
      
      }
      return answer;	
		
	}
	

	 public static String getAnswerSentenceForSingularVerbQuestion(List<String> improperNouns, Map<String, List<String>> attributeNameWithResults, boolean isMinQuestion,List<String> adjectives ) {
		String answer = "";
		
		if (attributeNameWithResults == null || attributeNameWithResults.isEmpty()) {
			answer = "There are no " + improperNouns.get(0) + ".";
		} else {
			for (Entry<String, List<String>> entry : attributeNameWithResults.entrySet()) {
				String attributeNormalName = entry.getKey();
				List<String> results = entry.getValue();
				
				

				if (results.get(0) != null){
				
				{ if(attributeNormalName.contains("gdp")) 				
					answer += adjectives.get(0) + " " +improperNouns.get(0) + " is " +  results.get(0) + " and " + attributeNormalName  + " is " + results.get(1) + " million usd"+ ". " ; 
				else if(attributeNormalName.contains("area")){
					answer += adjectives.get(0) + " " +improperNouns.get(0) + " is " +  results.get(0) + " and " + attributeNormalName  + " is " + results.get(1) + " km²"+ ". " ; 
				}
				else if (attributeNormalName.contains("RiverLength")){
					answer +=  adjectives.get(0) + " " +improperNouns.get(0) + " is " +  results.get(0) + " and" +" " +attributeNormalName  + " is " + results.get(1) +" km" + "." + "\n\n" ; 
					
				}
				else if (attributeNormalName.contains("Mountainheight")){
					answer +=  adjectives.get(0) + " " +improperNouns.get(0) + " is " +  results.get(0) + " and" +" " +attributeNormalName  + " is " + results.get(1) +" m" + "." + "\n\n" ; 
					
				}
				else if (attributeNormalName.contains("LakeLength")){
					answer +=  adjectives.get(0) + " " +improperNouns.get(0) + " is " +  results.get(0) + " and" +" " +attributeNormalName  + " is " + results.get(1) +" km²" + "." + "\n\n" ; 
					
				}
				else if (attributeNormalName.contains("RoadLength")){
					answer +=  adjectives.get(0) + " " +improperNouns.get(0) + " is " +  results.get(0) + " and" +" " +attributeNormalName  + " is " + results.get(1) +" km" + "." + "\n\n" ; 
					
				}
				else if (improperNouns.contains("population") && improperNouns.contains("state")){
					
					answer = adjectives.get(0) + " state" +  " is " + results.get(0) + " and" + " " +attributeNormalName  + " is " + results.get(1) + "." + "\n\n" ;;
				}
				
                else if (improperNouns.contains("population") && improperNouns.contains("city")){
					
					answer = adjectives.get(0) + " city" +  " is " + results.get(0) + " and" + " " +attributeNormalName  + " is " + results.get(1) + "." + "\n\n" ;;
				}
				else {
					
					answer +=  adjectives.get(0) + " " +improperNouns.get(0) + " is " +  results.get(0) + " and" +" " +attributeNormalName  + " is " + results.get(1) + "." + "\n\n" ; 
				}
				}
				
				
				
			}
		
			}}
		return answer;
	}
		
	public static String getAnswerSentenceForCountQuestions(List<String> results, List<String> improperNouns, String properNoun) {
		String answer = "";
		
		answer = "There are " + results.get(0) + " " + improperNouns.get(0) + " in " + properNoun + ".";
		
		return answer;
	}
	public static String getAnswerSentenceForWhereQuestions(List<String> results, List<String> improperNouns, String properNoun, String WhereType) {
		String answer = "";


		if (WhereType.contains("state")){
		answer = properNoun +" is a" + " " +WhereType + " of" + " USA" + "." + " its"+ " area"+ " is"+ " " +results.get(0) + " km²"+ " and" +" Population"+ " is" + " " +results.get(1) +"." ;
		//+ results.get(0) + " " + improperNouns.get(0) + " in " + properNoun + ".";
		}
		
		else{
			answer = properNoun +" is a" + " " +WhereType + " of" + " USA" + "." + " its"+ " area"+ " is"+ " " +results.get(0) + " km²"+ " and" +" Population"+ " is" + " " +results.get(1) +"." ;
		}
			
		return answer;
	}
	}




