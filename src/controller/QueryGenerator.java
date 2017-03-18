package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import model.StaticData;
import model.UserType;
import util.DBConnector;
import util.Util;

public class QueryGenerator {

	public static String getAnswerForQuestion(String question, UserType userType) throws Exception {
 		String answerSentence = "";
		if(question.contains("next to")) {
			question = question.replace("next to", "border with");
		}
		if(question.contains("neighbouring")) {
			question = question.replace("neighbouring", "neighbour");
		}
		
		if (question.contains("USA")){
			question=question.replace("USA", "America");
		}
		// tokenize - get all the words
		// process all the words and get their POS type
		// adjectives and nouns are of interest for us
		// in the above question, adjective: longest (which are the richest state)
		// nouns:
		// NN or NNS: lake
		// NNP or NNPS: Michigan
		StringTokenizer st =  new StringTokenizer(question);
		
	
		MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");

		List<String> improperNouns = new ArrayList<>(); // fill this set after
														// parsing using
		// Tokenizer -> NN or NNS
		String properNoun = ""; // will be used in the where clause
		String JJS = ""; // adjective
		String JJR = ""; // adjective
		String RB = ""; // adverb
		String Verb =""; // Verb, gerund or present participle
		String Where="";
		List<String> improperNounsPlural = new ArrayList<>();
		String properNounPlural = "";
		String preposition = "";
	    String thirdPersonSin = "";
		List<String> adjectives = new ArrayList<>(); // fill this hashset after
														// parsing using
		String prevWordTag = "";
		
		String prevWord = "";// tokenizer
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			String tag = tagger.tagString(word);
			Util.print(word + " - " + tag);

			if(tag.contains("_CD") && prevWordTag.contains("_NNP")) {
				properNoun += (" " + word);
			}
			
			if(prevWordTag.contains("_JJS") && tag.contains("_NN")) {
			//	word = adjectives + improperNouns;
				improperNouns.add( prevWord +" "+ word); 
			}
			
			if (tag.contains("_NNP")) {
				if(properNoun.isEmpty()) {
					properNoun = word;
				}
				else if(prevWordTag.contains("_NNP")){
					properNoun += (" " + word);
				}
			} else if (tag.contains("_NNPS")) {
				properNounPlural = word;
			} else if (tag.contains("_NN")|| (tag.contains("_RB"))) {
				improperNouns.add(word);
			} else if (tag.contains("_NNS")) {
				improperNounsPlural.add(word);
			} else if (tag.contains("_JJS") || (tag.contains("_JJR") ||(tag.contains("_JJ")))) {
				JJS = word;
				adjectives.add(word);
			} else if (tag.contains("_VBG")){
				Verb = word;
			} 
			else if (tag.contains("_VBZ")){
				thirdPersonSin = word;
			}
			else if (tag.contains("_IN")) {
				preposition = word;
			}
			else if (tag.contains("_WRB")){
			Where = word;
		}
		prevWordTag = tag;
		prevWord = word;
		}
	
	
		Util.print("proper noun - " + properNoun);
		Util.print("proper noun plural -" + properNounPlural);
		Util.print("Verb - " + Verb);
		System.out.println("improper nouns -"+ improperNouns); 
		//Util.print("improper noun plural -"+ improperNounsPlural);  //cities, states, rivers
		System.out.println("adjective -" + adjectives);
		Util.print("preposition -" + preposition);
		
		
//		if ((improperNouns==null || improperNouns.isEmpty())){
//			
//			
//			improperNouns==candidateAttributes;
//			
//			queryTable==whereType;
//		}
		
		// iterate through all the NNP or NNPS and see if they are state, lake
		// or what?

		// ----------------------------------------------------------------------//
		// WHENEVER A NEW TYPE OF QUESTION COMES, CHANGES WILL START FROM HERE
		// ----------------------------------------------------------------------//

		String whereType = Util.determineTypeOfProperNoun(properNoun, improperNouns, adjectives);//table name
		// now i'd know that Michigan is a State. This will help us in
		// formulating sql query (where clause) -> where State='Michigan'

		Util.print("Where type - " + whereType);

		improperNouns.addAll(improperNounsPlural);
		if(improperNouns.size() == 0) {
			improperNouns = adjectives;
		}
		boolean isPrepositionOf = Util.isPrepositionOf(preposition); 
		boolean isMinimumQuestion = Util.isMinQuestion(adjectives);
		boolean isMaximumQuestion = Util.isMaxQuestion(adjectives);
		boolean isCountQuestion = Util.isCountQuestion(question);
		boolean isWhereQuestion = Util.isWhere(Where);

		boolean isSeen = false;
		boolean isHas = false;
	
		
		st = new StringTokenizer(question);
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			
			

			if(word.equals("is")) {
				isSeen = true;
				
			}
			
			else if(word.equals("has")){
				isHas=true;
			}
			//System.out.println("tokenizer example: " +word);
		}
		
		

		boolean isSameTableQuestion = Util.isSameTableQuestion(isPrepositionOf, improperNouns, whereType); // category
																											// 1
																											// //
																											// eg.
																											// give
																											// the
																											// capital
																											// of
																											// kansas

		
		boolean isSameTableMinMaxQuestion = Util.isSameTableMinMaxQuestion(thirdPersonSin,preposition, properNoun, isMinimumQuestion || isMaximumQuestion); // category 5 // eg.
															// give the largest
															// state. or which are the richest state
															// this is the
															// category for
															// which we have to
															// get alternative
															// answers

		// Change the userType to none i.e. default if this question does not
		// belong to category 5.
		// Answers for such questions will always be same for all types of
		// users.
		// For example: What is the capital of Kansas?
		// It's answer will be same, no matter what the user type is
		String userTypeForIs = "";
		if (userType == UserType.TOURIST){
			userTypeForIs = "TOURIST";
		}else if (userType == UserType.BUSINESSMAN){
			userTypeForIs = "BUSINESSMAN";
		}
		
		userType = isSameTableMinMaxQuestion ? userType : userType.NONE;
		
		

		Set<String> candidateAttributes = new HashSet<>();
		for (String adj : adjectives) {
			
			candidateAttributes.addAll(Util.getPossibleCandidateAttributesForWord(adj, userType, improperNouns, isSeen)); // for
			// longest,
			// area
			// and
			// populationBUSINESSMAN
			// will
			// be
			// returned.
			// System.out.println();
			// this will help in formulating sql query -> max(area) or
			// max(population)
			// especially the where clause
		}

		// candidateAttributes now contains all the possible attributes that
		// need to be considered.
		// in the given question, candidateAttiributes will contain: area,
		// population
		// sample query:
		// SELECT StateName
		// FROM [State]
		// where Area = (select max(Area) from [State])
		// or TotalPopulation = (select max(TotalPopulation)
		// from [State]);

		ArrayList<String> queryTables = Util.getPossibleCandidateQueryTableForWord(improperNouns, whereType,
				isSameTableQuestion && !isSameTableMinMaxQuestion); //table name// now
		// we
		// know
		// the
		// query
		// table
		// as
		// well
		// sql:
		// from
		// <table
		// name>
		//queryTable = "landelevation";
		// String mainAttribute = Util.getMainAttributeForTable(queryTable);

		ArrayList<String> mainAttributes = Util.getDesiredAttributeForTable(isSameTableQuestion || isSameTableMinMaxQuestion,
				queryTables, improperNouns); //column name
		String whereAttributeName = Util.getWhereAttributeForWhereType(whereType);// sql where name
		String whereAttributeValue = Util.getIdForName(properNoun, whereType);//sql where name value
		System.out.println("\n\n\n");
		System.out.println("question: " + question);		
		Util.print("improper noun: " + improperNouns.toString());
		Util.print("proper noun: " + properNoun);
		Util.print("where type: " + whereType);
		Util.print("where type attribute name: " + whereAttributeName);
		Util.print("where type value: " + whereAttributeValue);
		Util.print("candidate attributes: " + candidateAttributes.toString());
		Util.print("main attribute: " + mainAttributes);  // gives the column name
		Util.print("table: " + queryTables);
		Util.print("adjectives: " + adjectives.toString());
		Util.print("preposition: " + preposition);
		// Class.forName("com.mysql.jdbc.Driver");
		// Connection conn =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/mtsaar2016?user=shuvro&password=password");
		// Util.print("test");
		// Statement sta = conn.createStatement();
		// String Sql = "select * from lake where "+type+"="+pNoun;
		// ResultSet rs = sta.executeQuery(Sql);
		// while (rs.next()) {
		// Util.print(rs.getString("LakeName"));
		// }
		// now that we know the main attribute, table name, and conditions,
		// formulate sql query
		// sqlQuery = ...

		// just made a new boolean for readability

		boolean isSingularVerbQuestion = false;
		boolean isUserInterestQuestion = isSameTableMinMaxQuestion; // String preposition, String properNoun, boolean isMinMaxQuestion
		st = new StringTokenizer(question);
		isSeen = false;
		
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			if(word.equals("is")) {
				isSeen = true;
			}
			else if (word.equals("has")){
				isHas=true;
			}
			
			
			for(String candidateAttribute : candidateAttributes) {
				if((isSeen || isHas) && StaticData.attributeMappings.get(candidateAttribute).contains(word)) {
					isSingularVerbQuestion = true;
					break;
				}
			}
		}	
		
		
		
		
		
	/* boolean isUserInterestQuestion = false;
	  boolean areSeen =false;
		while(st.hasMoreElements()){
			String word = st.nextToken();
			if(word.equals("are")){
				areSeen=true;
			}
			for(String candidateAttribute : candidateAttributes) {
				if(StaticData.attributeMappings.get(candidateAttribute).contains(word)) {
					isUserInterestQuestion = true;
				}
			}
			
		}*/
		if(isSingularVerbQuestion) {
			List<String> kh = improperNouns;
			List<String> kh1 = adjectives;
		
			if ((!(kh1.contains("largest") && (!kh1.contains("lowest")) && (kh.contains("city") ||  kh.contains("state"))) ||!(kh1.contains("richest") && (kh.contains("city") ||  kh.contains("state"))) || kh1.contains("highest")) && kh.contains("lake") || kh.contains("mountain") || kh.contains("river") || kh.contains("population") || kh.contains("road")){
				
		//		if((!(kh1.contains("smallest") && kh.contains("road")))){
				userType= UserType.NONE;
				
				System.out.println("shuvro:" + userType);
			} else if ((kh1.contains("largest")||(kh1.contains("richest")))  && (kh.contains("city") ||  kh.contains("state"))){
				if(userTypeForIs.equals("TOURIST")){
					userType = UserType.TOURIST;
				}else if(userTypeForIs.equals("BUSINESSMAN")){
					userType = UserType.BUSINESSMAN;
				}
				
			}
			
			if (userTypeForIs.equals("TOURIST") && kh1.contains("richest")){
				userType= UserType.TOURIST;
			}
			
			switch(userType) {
				case TOURIST:
					if(kh.contains("city")) {
						userType = UserType.TOURIST;
					}
					break;
				case BUSINESSMAN:
					if(kh.contains("city")) {
						userType = UserType.BUSINESSMAN;
					}
					break;
				default:
					userType = UserType.NONE;
					break;
			}

            
            
           System.out.println("usertype is none:" + userType);
			Set<String> candidateAttributes1 = new HashSet<>();
			for (String adj : adjectives) {
				candidateAttributes1.addAll(Util.getPossibleCandidateAttributesForWord(adj, userType, improperNouns, isSeen)); // for
				// longest,
				// area
				// and
				// population
				// will
				// be
				// returned.
				// System.out.println();
				// this will help in formulating sql query -> max(area) or
				// max(population)
				// especially the where clause
			}
			
			String desiredAttribute = mainAttributes.get(0);
			String queryTable = queryTables.get(0);
			Map<String, String> sqlQueryWithAttributeNormalName = getSqlQueriesForSingularVerbQuestion(desiredAttribute,
					queryTable, candidateAttributes1, isMinimumQuestion, isMaximumQuestion, whereAttributeName, whereAttributeValue, properNoun, isSameTableMinMaxQuestion);
			
			Map<String, List<String>> attributeNormalNameQueryResults = new HashMap<>();
			List<String> results = null;
			for (Entry<String, String> entry : sqlQueryWithAttributeNormalName.entrySet()) {
				String attributeNormalName = entry.getValue();
				String sqlQuery = entry.getKey();
				results = getResultForSqlQuery(sqlQuery);
				attributeNormalNameQueryResults.put(attributeNormalName, results);
				System.out.println("\n\nsql(isSingularVerbQuestion): " + sqlQuery);
				answerSentence = IntensionalAnswerGenerator.getAnswerSentenceForSingularVerbQuestion(improperNouns, attributeNormalNameQueryResults, isMinimumQuestion, adjectives);
				
			}
			//answerSentence = IntensionalAnswerGenerator.getAnswerSentence(adjectives, improperNouns, properNoun,results, isSameTableQuestion);
			
		}
		
		else if (isUserInterestQuestion) {
			String desiredAttribute = mainAttributes.get(0);
			String queryTable = queryTables.get(0);
			Map<String, String> sqlQueryWithAttributeNormalName = getSqlQueriesForUserInterest(desiredAttribute,
					queryTable, candidateAttributes, isMinimumQuestion, isMaximumQuestion);
			
			Map<String, List<String>> attributeNormalNameQueryResults = new HashMap<>();
			for (Entry<String, String> entry : sqlQueryWithAttributeNormalName.entrySet()) {
				String attributeNormalName = entry.getKey();
				String sqlQuery = entry.getValue();
				System.out.println("Query - "+sqlQuery);
				List<String> results = getResultForSqlQuery(sqlQuery);
				
				System.out.println(results);
				Iterator<String> iterator = results.iterator();
				for(int i=0; i<results.size(); i=i+2) {
					
					if (attributeNormalName.contains("gdp")){
			    		
			    		System.out.println(attributeNormalName + " " + "of " + results.get(i) + " is " + results.get(i+1));
			    		answerSentence += (attributeNormalName + " " + "of " + results.get(i) + " is " + results.get(i+1) + " million usd " +"\n");
			    	}
			    	else{
			    		System.out.println(results.get(i) + " has " + results.get(i+1) + " " + attributeNormalName);
			    		answerSentence += (results.get(i) + " has " + results.get(i+1) + " " + attributeNormalName + "\n");

			    	}
					 
					
				}
				//attributeNormalNameQueryResults.put(attributeNormalName, results);
				//System.out.println("\n\n\n sql(user interest question query): " + sqlQuery);
				answerSentence += "\n";
			}
			
			
			
			//answerSentence = IntensionalAnswerGenerator.getAnswerSentenceForUserInterest(improperNouns, attributeNormalNameQueryResults, candidateAttributes, isMinimumQuestion);
			//Util.print("Sql Queries: " + sqlQueries.toString());

			
		}
		
		
		
		
		else if (isCountQuestion) {
			String queryTable = queryTables.get(0);
			String sqlQuery = getSqlQueryForCountQuestions(improperNouns, mainAttributes, queryTable, whereAttributeName, whereAttributeValue);
			System.out.println("\n\nsql(is count question): " + sqlQuery); //aksdhashdahsdjhasjdhjashdjashdhadhajshdjashdjashdhadha
			List<String> results = getResultForSqlQuery(sqlQuery);
			answerSentence = IntensionalAnswerGenerator.getAnswerSentenceForCountQuestions(results, improperNouns, properNoun);
		}
		
		
		
		
		
		
		
		
		
		else if(mainAttributes.size() == 1 || queryTables.size() == 1) {
			String desiredAttribute = mainAttributes.get(0);
			String queryTable = queryTables.get(0);
			String sqlQuery = getSqlQuery(desiredAttribute, queryTable, Verb, candidateAttributes, whereAttributeName,whereAttributeValue,
					isMinimumQuestion, isMaximumQuestion, isSameTableMinMaxQuestion, queryTables, improperNouns, properNoun);
			System.out.println("\n\nsql(capital): " + sqlQuery); //Capital type of question
			List<String> results = getResultForSqlQuery(sqlQuery);
			answerSentence = IntensionalAnswerGenerator.getAnswerSentence(desiredAttribute, adjectives, Verb, improperNouns, properNoun,
					results, isSameTableQuestion);
		}
		else if(isWhereQuestion) {
			
			String sqlQuery = getWhereQuestions(whereAttributeName, whereAttributeValue, whereType, Where);
			System.out.println("\n\nsql isWhere: " + sqlQuery); //Capital type of question
			List<String> results = getResultForSqlQuery(sqlQuery);
			answerSentence = IntensionalAnswerGenerator.getAnswerSentenceForWhereQuestions(results, improperNouns, properNoun, whereType);
		}
		
		
		else {
			String sqlQuery = getSqlQueryForMultipleImproperNouns(mainAttributes, queryTables, whereAttributeName, isMinimumQuestion, whereAttributeValue,JJS,properNoun, improperNouns, candidateAttributes);
			System.out.println("multiple query:"+sqlQuery);

			List<String> result = getResultForSqlQuery(sqlQuery);
			answerSentence = IntensionalAnswerGenerator.getAnswerSentenceForMultipleProperNouns(queryTables, properNoun,preposition, result, improperNouns, JJS);
		}
		
		System.out.println("\nAnswer(userInterestAnswer): " + answerSentence + "\n\n");//User Interest Answer
		return answerSentence;
	}

/*	private static String getSqlQueryForMultipleImproperNouns(ArrayList<String> mainAttributes, ArrayList<String> tableNames,
			String whereAttributeName, String whereAttributeValue) throws SQLException {
		String sqlQuery = "";
		for(int i = 0; i < tableNames.size(); i++) {
			String tableName = tableNames.get(i);
			String mainAttribute = mainAttributes.get(i);
			sqlQuery += "select " + mainAttribute + " from " + tableName;
			sqlQuery += " where " + whereAttributeName + " = " + whereAttributeValue;
			if(i < tableNames.size()-1) {
				sqlQuery += " UNION "; 
			}
		}
		sqlQuery += ";";
	//	System.out.println(sqlQuery);
		return sqlQuery;
	}
*/
	private static String getSqlQueryForMultipleImproperNouns(ArrayList<String> mainAttributes, ArrayList<String> tableNames,
			String whereAttributeName, boolean isMinQuestion, String whereAttributeValue, String adjectives, String properNoun, List<String> improperNouns, Set<String> candidateAttributes) throws SQLException {
		String sqlQuery = "";
		for(int i = 0; i < tableNames.size(); i++) {
			String tableName = tableNames.get(0);
			String tableName1=tableNames.get(1);
			
			String mainAttribute = mainAttributes.get(0);
			String mainAttribute1 = mainAttributes.get(1);
		
			if( improperNouns.contains("states") || (improperNouns.contains("state"))|| improperNouns.contains("STATES") || improperNouns.contains("STATE")){
				sqlQuery += "select " + mainAttribute + " from " + tableName1;
			}else if(improperNouns.contains("road") || (improperNouns.contains("roads"))|| improperNouns.contains("river") || improperNouns.contains("rivers") || improperNouns.contains("RIVER") || improperNouns.contains("RIVERS") || improperNouns.contains("pass")){
				sqlQuery += "select " + mainAttribute + " from " + tableName;
					
			}
			else if(improperNouns.contains("name") && ((improperNouns.contains("mountains"))|| improperNouns.contains("mountain"))){
				sqlQuery += "select " + mainAttribute + " from " + tableName + " where " +  whereAttributeName + " = " + whereAttributeValue ;
					return sqlQuery;
			}
			else if(improperNouns.contains("name") && ((improperNouns.contains("lakes"))|| improperNouns.contains("lake"))){
				sqlQuery += "select " + mainAttribute + " from " + tableName + " where " +  whereAttributeName + " = " + whereAttributeValue ;
					return sqlQuery;
			}
			else if(improperNouns.contains("name") && ((improperNouns.contains("roads"))|| improperNouns.contains("road"))){
				sqlQuery += "select " + mainAttribute + " from " + tableName + " where " +  whereAttributeName + " = " + whereAttributeValue ;
					return sqlQuery;
			}
  		 
		//	sqlQuery += "select " + mainAttribute + " from " + tableName;
			//sqlQuery += "select " + mainAttribute + " from " + tableName1;
			if((improperNouns.contains("state") && improperNouns.contains("border") || (improperNouns.contains("states")) && improperNouns.contains("border")) ||
			(improperNouns.contains("state") && !improperNouns.contains("pass") && !improperNouns.contains("city") && adjectives.contains("largest"))) {
				sqlQuery = " select " + mainAttribute1 + " from " + tableName1 + " where " +  whereAttributeName + " = " + whereAttributeValue ;
				if (adjectives.contains("largest")){
					for (String candidateAttribute : candidateAttributes) {
						
						sqlQuery += " and " + "( " + candidateAttribute + " = " + "(" + "select " + "max"+ "("+ candidateAttribute 
								+")"+ " from "+ tableName1 + " where " +  whereAttributeName + " = " + whereAttributeValue +")"+")";
					
						
						System.out.println(sqlQuery);
						
					}
					
					
	  //select BorderName from border where StateId = 1011and( area = (select max(area)fromborder where StateId = 1011;		
		//select BorderName from border where StateId = 1011 and (area = (select max(area) from border where StateId = 1011));	
				}
				else{
                      for (String candidateAttribute : candidateAttributes) {
						
						sqlQuery += " and " + "( " + candidateAttribute + " = " + "(" + "select " + "min"+ "("+ candidateAttribute 
								+")"+ " from "+ tableName1 + " where " +  whereAttributeName + " = " + whereAttributeValue +")"+")";
					
						
						System.out.println(sqlQuery);
						
					
				}
				
				
			}
			
			}
				
				
			else{
				sqlQuery += " where " + mainAttribute1 + " = " + "\'" + properNoun + "\'"; // adjectives should be replaced with properNoun and properNoun should be replaced with adjectives
			}
			
		///	if(i < tableNames.size()-1) {
			//	sqlQuery += " UNION "; 
			//}
//		}
		sqlQuery += ";";
	//	System.out.println(sqlQuery);
		return sqlQuery;}
		return sqlQuery;
	}
	

	
	
	
	
	
	
	/**
	 * Example: <br />
	 * <br />
	 * <b>Input:</b> <br />
	 * mainAttribute: "LakeName" <br />
	 * tableName: "lake" <br />
	 * candidateAttributes: ["length"] // not taken into consideration for now -
	 * TODO <br />
	 * whereAttributeName: StateId <br />
	 * whereAttributeValue: "1035" <br />
	 * <b>Output:<b> <br />
	 * sqlQuery: "select LakeName from lake where StateId = 1035;" <br />
	 * <br />
	 * 
	 * isMinQuestion -> if the question contains words like smallest, minimum,
	 * lowest isMaxQuestion -> if the question contains words like largest,
	 * maximum, highest
	 * 
	 * @param mainAttribute
	 * @param tableName
	 * @param candidateAttributes
	 * @param whereAttributeName
	 * @param whereAttributeValue
	 * @return
	 */
	
	public static String getSqlQuery(String mainAttribute, String tableName, String Verb,  Set<String> candidateAttributes,
			String whereAttributeName, String whereAttributeValue, boolean isMinQuestion, boolean isMaxQuestion,
			boolean isSameTableMinMaxQuestion, ArrayList<String> tableNames, List<String> improperNouns, String properNoun) throws SQLException {
	
		
		
	
		for(int i = tableNames.size()-1; i >=0; i--) {
			
		    tableName = tableNames.get(i);
		    String sqlQuery = "";
		    String tableName1= tableNames.get(0);
			System.out.println(Arrays.toString(improperNouns.toArray()));
			System.out.println(whereAttributeName);
			if (improperNouns.contains("big") && whereAttributeName.contains("StateId")){
				sqlQuery = "select " + mainAttribute + " from " + tableName + " where " + whereAttributeName + " = " + whereAttributeValue + "; " ;
				System.out.println(sqlQuery);
				return sqlQuery;
			}
			else if (improperNouns.contains("capital")){
				
				sqlQuery = "select " + " StateCapital" + " from " +  " state " ;
				return sqlQuery;
				
				//+ whereAttributeName + " = " + whereAttributeValue + "; " ;
			}
			
			else if (improperNouns.contains("big") && whereAttributeName.contains("CityId")){
				 sqlQuery = "select " + mainAttribute + " from " + tableName1 + " where " + whereAttributeName + " = " + whereAttributeValue + "; " ;
				 return sqlQuery; 
			}
			else if
			
				
		
		 (Verb.contains("passing")){
			//String sqlQuery = "";
			//select RiverName from river where StateName = 'Arkansas';
			sqlQuery += "select " + mainAttribute + " from " + tableName1 + " where " + "StateName " + " = " +  "\'" + properNoun + "\'" ;
			return sqlQuery;
		}
			else if (improperNouns.contains("name") && (improperNouns.contains("rivers") || (improperNouns.contains("river")))){
				sqlQuery += "select " + "RiverName" + " from " + tableName1 + " where " + whereAttributeName + " = " + whereAttributeValue + "; " ;
				return sqlQuery;
			}
			else if (improperNouns.contains("name") && (improperNouns.contains("lakes"))){
				sqlQuery += "select " + "LakeName" + " from " + tableName1 + " where " + whereAttributeName + " = " + whereAttributeValue + "; " ;
				return sqlQuery;
			}
			else if (improperNouns.contains("name") && (improperNouns.contains("rivers"))){
				sqlQuery += "select " + "RiverName" + " from " + tableName1 + " where " + whereAttributeName + " = " + whereAttributeValue + "; " ;
				return sqlQuery;
			}

                          			
		
		}
		
		
		
		String sqlQuery = "";
		
		sqlQuery = "select " + mainAttribute + " from " + tableName;
			
				
		boolean isWhereClauseAdded = false;
		
		if (!whereAttributeName.isEmpty() && !whereAttributeValue.isEmpty()) {
			sqlQuery  += " where " + whereAttributeName + " = " + whereAttributeValue;
			isWhereClauseAdded = true;
			System.out.println("shuvro:" +sqlQuery);
			
			return sqlQuery;
			
		}

		// have to apply group by and additional conditions in where clause!
		if (isMaxQuestion || isMinQuestion) {
			
		
			sqlQuery += isWhereClauseAdded ? " and (" : " where (";
			
			boolean isFirst = true;

			for (String candidateAttribute : candidateAttributes) {
				// 'or' will only be added if this is not the first condition
				if (!isFirst) {
					sqlQuery += " or ";
				} else {
					isFirst = false;
				}
			
				sqlQuery += (candidateAttribute + " = (");
				sqlQuery += isSameTableMinMaxQuestion ? getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion)
						: getGroupBySubQuery(candidateAttribute, tableName, whereAttributeName, whereAttributeValue,
								isMinQuestion);
				sqlQuery += ")";
			}

			sqlQuery += ")";
		}

		sqlQuery += ";";

		
		return sqlQuery;}
		//return sqlQuery;}
	
	
/*
	public static Map<String, String> getSqlQueriesForUserInterest(String mainAttribute, String tableName, Set<String> candidateAttributes,
			boolean isMinQuestion, boolean isMaxQuestion) {
		Map<String, String> sqlQueries = new HashMap<>();
		String startOfQuery = "select " + mainAttribute + " from " + tableName + " where (";
		
		for (String candidateAttribute : candidateAttributes) {
			String sqlQuery = startOfQuery + candidateAttribute + " = (" + getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion) + "));";
			String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
			sqlQueries.put(sqlQuery, attributeNameForAnswer);
		}
		
		return sqlQueries;
	}  
	*/



	
	
	

	  public static Map<String, String> getSqlQueriesForUserInterest(String mainAttribute, String tableName, Set<String> candidateAttributes,
			boolean isMinQuestion, boolean isMaxQuestion) {
		Map<String, String> sqlQueries = new HashMap<>();
		String startOfQuery = " select " + mainAttribute + ",";
		
		
//			Iterator<String> iterator = candidateAttributes.iterator();
//			String s = "";
//		    while(iterator.hasNext()) {
//		    	String candidateAttribute = iterator.next();
//		    	s += candidateAttribute;
//		    	if(iterator.hasNext()) {
//		    		s += ", ";
//		    	}
//		    }
//		   
//
//			String sqlQuery = startOfQuery + s; 
//			
//			
//			sqlQuery+= " from " + tableName + ";";
//			sqlQuery = sqlQuery.trim();
//			System.out.println("final"+sqlQuery);
		
			//SELECT statename, mountaincount FROM ( SELECT @row := @row +1 AS rownum, StateName AS statename, MountainCount AS mountaincount FROM (SELECT @row :=0) r, state order by MountainCount ASC ) AS statetable WHERE rownum % 10 = 1 LIMIT 5;
		
			String sqlQuery = "";
			
			for (String candidateAttribute : candidateAttributes) {
				String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
				
				if (isMaxQuestion)
					//sqlQuery = startOfQuery + candidateAttribute + " from "+ tableName + " order by "+ candidateAttribute + " DESC LIMIT 5;";
					sqlQuery = "SELECT statename, mountaincount" + " from "+ "( SELECT @row := @row +1 AS rownum," + mainAttribute + " AS statename, " + candidateAttribute + " AS mountaincount FROM (SELECT @row :=0) r, "+ tableName + " order by "+ candidateAttribute + " DESC ) AS statetable WHERE rownum % 10 = 1 LIMIT 5;";
				else{
					//sqlQuery = startOfQuery + candidateAttribute + " from "+ tableName + " order by "+ candidateAttribute + " ASC LIMIT 5;";
					sqlQuery = "SELECT statename, mountaincount" + " from "+ "( SELECT @row := @row +1 AS rownum," + mainAttribute + " AS statename, " + candidateAttribute + " AS mountaincount FROM (SELECT @row :=0) r, "+ tableName + " order by "+ candidateAttribute + " ASC ) AS statetable WHERE rownum % 10 = 1 LIMIT 5;";
				}
				
				sqlQueries.put(attributeNameForAnswer, sqlQuery);
			}
			
			
			

			return sqlQueries;
		
}
		

		
	
	
	public static String getSqlQueryForCountQuestions(List<String> improperNouns,List<String> mainAttributes,String tableName, String whereAttributeName, String whereAttributeValue) {
		String sqlQuery = "";
		String mainAttribute= mainAttributes.get(0);
		String improperNoun= improperNouns.get(0);
		
		if (improperNouns.contains("population") ||improperNouns.contains("people") || improperNoun.contains("citizens") && (!whereAttributeName.contains("CityId"))){
		
		sqlQuery = "select " +  mainAttribute + " from "+ tableName + " where " + whereAttributeName + " = " + whereAttributeValue + ";";
		}
		
		else if (whereAttributeName.contains("CityId"))
		{
			sqlQuery = "select " +  mainAttribute + " from "+ "city" + " where " + whereAttributeName + " = " + whereAttributeValue + ";";
		
		}
		
		else{
			
		sqlQuery = "select count(*) from " + tableName + " where " + whereAttributeName + " = " + whereAttributeValue + ";";
		
		}
			
		return sqlQuery;
	}
	
	//sql(capital): select MountainName from mountain where StateId = 1006 
	//and (area = (select max(area) from mountain where StateId = 1006 
	//group by StateId) or TotalPopulation = (select max(TotalPopulation)
	//from mountain where StateId = 1006 group by StateId));
	
	public static String getWhereQuestions(String whereAttributeName,String whereAttributeValue, String whereType,String Where){
		String sqlQuery = "";
		
		if (whereAttributeName.contains("StateId")){
			//SELECT Area, TotalPopulation from state where stateId = 1028;
			sqlQuery = "select" + " Area" + ", " + "TotalPopulation" + " from" + " "+ whereType + " where" + " " +whereAttributeName + " = "+ whereAttributeValue + ";" ;
		      return sqlQuery;
		      
		}
		if (whereAttributeName.contains("CityId")){
			//SELECT Area, TotalPopulation from state where stateId = 1028;
			sqlQuery = "select" + " Area" + ", " + "TotalPopulation" + " from" + " "+ whereType + " where" + " " +whereAttributeName + " = "+ whereAttributeValue + ";" ;
		      return sqlQuery;
		      
		}
				
		return sqlQuery;
		
	}
	
	

	public static Map<String, String> getSqlQueriesForSingularVerbQuestion(String mainAttribute, String tableName, Set<String> candidateAttributes,
			boolean isMinQuestion, boolean isMaxQuestion,String whereAttributeName, String whereAttributeValue, String properNoun, boolean isSameTableMinMaxQuestion) {
		
		Map<String, String> sqlQueries = new HashMap<>();
		if (properNoun.isEmpty()){
			String[] candidateAttributeArray = null;
			int count=0;
			String startOfQuery = null;
			for (String candidateAttribute : candidateAttributes) {
				System.out.println("candidateAttribute: " + candidateAttribute);
				
		/*		if (improperNouns.contains("population") && (improperNouns.contains("state"))){
					startOfQuery = "select " + " StateName" + ","  + candidateAttribute + 
							" from " + tableName + " where (";
				}
				else if (improperNouns.contains("population") && (improperNouns.contains("city"))){
					startOfQuery = "select " + " CityName" + ","  + candidateAttribute + 
							" from " + tableName + " where (";
				}
				else
				{
				*/
				startOfQuery = "select " + mainAttribute + ","  + candidateAttribute + 
						" from " + tableName + " where (";
				
				String sqlQuery = startOfQuery + candidateAttribute + " = (" + getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion) + "));";
				String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
				sqlQueries.put(sqlQuery, attributeNameForAnswer);
		    }
			
			
//			for (String candidateAttribute : candidateAttributes) {
//				String sqlQuery = startOfQuery + candidateAttribute + " = (" + getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion) + "));";
//				String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
//				sqlQueries.put(sqlQuery, attributeNameForAnswer);
//			
//			}
		}
		
		else
			
		{
			Map<String, String> sqlQueries1 = new HashMap<>();
		
			String[] candidateAttributeArray = null;
			int count=0;
			String startOfQuery = null;
			for (String candidateAttribute : candidateAttributes) {
				System.out.println("candidateAttribute: " + candidateAttribute);
				startOfQuery = "select " + mainAttribute + ","  + candidateAttribute + 
						" from " + tableName ;
				
				System.out.println(startOfQuery);
				
				boolean isWhereClauseAdded = false;
				if (!whereAttributeName.isEmpty() && !whereAttributeValue.isEmpty()) {
					startOfQuery += " where " + whereAttributeName + " = " + whereAttributeValue + " and ";
					System.out.println(startOfQuery);
					isWhereClauseAdded = true;
				}
				
				String sqlQuery = startOfQuery + candidateAttribute + " = (" + getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion) ;
				String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
				sqlQuery += " where " + whereAttributeName + " = " + whereAttributeValue + " ); " ;
				System.out.println("final: " +sqlQuery);
				sqlQueries.put(sqlQuery, attributeNameForAnswer);
		    }
		}
		return sqlQueries;}
			
			
			
			
			
			
			
			
	
/*		{
			String[] candidateAttributeArray = null;
			int count=0;
			String startOfQuery = null;
			for (String candidateAttribute : candidateAttributes) {
				System.out.println("candidateAttribute:" +candidateAttribute);
				startOfQuery = "select " + mainAttribute + ","  + candidateAttribute + 
						" from " + tableName + " where ";
				
				boolean isWhereClauseAdded = false;
				if (!whereAttributeName.isEmpty() && !whereAttributeValue.isEmpty()) {
					startOfQuery +=  whereAttributeName + " = " + whereAttributeValue;
					isWhereClauseAdded = true;
				}
				if (isMaxQuestion || isMinQuestion) {
					startOfQuery += isWhereClauseAdded ? " and " : " where (";
					boolean isFirst = true;
				
					for (String candidateAttribute1 : candidateAttributes) {
						// 'or' will only be added if this is not the first condition
						if (!isFirst) {
							startOfQuery += " or ";
						} else {
							isFirst = false;
						}
						startOfQuery += (candidateAttribute1 + " = (");
						
				 System.out.println("adding tablename: " +startOfQuery);
				
				 startOfQuery += isSameTableMinMaxQuestion ? getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion)
							: getGroupBySubQuery(candidateAttribute, tableName, whereAttributeName, whereAttributeValue,
									isMinQuestion);
				 startOfQuery += ")";
					String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
					sqlQueries.put(startOfQuery, attributeNameForAnswer);
				}
				System.out.println("final query:" +sqlQueries);}
		return sqlQueries;
			}
		return sqlQueries;}
		return sqlQueries;}
		
		*/
		
		
/*	
	
		{ 	
			
			
			String sqlQuery = "select " + mainAttribute + " , ";
			
			Iterator<String> iterator = candidateAttributes.iterator();
			String s = "";
		    while(iterator.hasNext()) {
		    	String candidateAttribute = iterator.next();
		    	s += candidateAttribute;
		    	if(iterator.hasNext()) {
		    		s += ", ";
		    		
		    	}
		    }
					
					
			sqlQuery+=s;		
					
					
		    sqlQuery	+= " from " + tableName;
		    System.out.println("adding tablename: " +sqlQuery);
		    
			boolean isWhereClauseAdded = false;
			if (!whereAttributeName.isEmpty() && !whereAttributeValue.isEmpty()) {
				sqlQuery += " where " + whereAttributeName + " = " + whereAttributeValue;
				isWhereClauseAdded = true;
			}
	
			// have to apply group by and additional conditions in where clause!
			if (isMaxQuestion || isMinQuestion) {
				sqlQuery += isWhereClauseAdded ? " and " : " where (";
				boolean isFirst = true;
				for (String candidateAttribute : candidateAttributes) {
					// 'or' will only be added if this is not the first condition
					if (!isFirst) {
						sqlQuery += " or ";
					} else {
						isFirst = false;
					}
					
					
					sqlQuery += (candidateAttribute + " = (");
					
					System.out.println("after adding candidate attribute :"+sqlQuery);
					sqlQuery += isSameTableMinMaxQuestion ? getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion)
							: getGroupBySubQuery(candidateAttribute, tableName, whereAttributeName, whereAttributeValue,
									isMinQuestion);
					sqlQuery += ")";
					String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
					sqlQueries.put(sqlQuery, attributeNameForAnswer);
				}
				sqlQuery += ";";
				System.out.println("final query:" +sqlQuery);
			}
		}
		
		return sqlQueries;
		
	}
		
/*	
	//		Map<String, String> sqlQueries = new HashMap<>();
		{	String startOfQuery = " select " + mainAttribute + ",";
			
			
				Iterator<String> iterator = candidateAttributes.iterator();
				String s = "";
			    while(iterator.hasNext()) {
			    	String candidateAttribute = iterator.next();
			    	s += candidateAttribute;
			    	if(iterator.hasNext()) {
			    		s += ", ";
			    	}
			    }
			   

				String sqlQuery = startOfQuery + s; 
				
				sqlQuery += isSameTableMinMaxQuestion ? getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion)
							: getGroupBySubQuery(candidateAttribute, tableName, whereAttributeName, whereAttributeValue,
									isMinQuestion);
					sqlQuery += ")";
					String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
					sqlQueries.put(sqlQuery, attributeNameForAnswer);
				}
				sqlQuery += ";";
				
				sqlQuery+= " from " + tableName + ";";
				
				
				
				
				sqlQuery = sqlQuery.trim();
				System.out.println(sqlQuery);
				
				for (String candidateAttribute : candidateAttributes) {
					String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;
					sqlQueries.put(attributeNameForAnswer, sqlQuery);
				}
				
				sqlQuery += " where " + whereAttributeName + " = " + whereAttributeValue;
				
		}
				return sqlQueries;
			
	
	}
		*/
	
	

	/**
	 * isMinimum: if true, then min() else max() <br />
	 * 
	 * returns queries like: select max(Area) from city where StateID = 1028
	 * group by StateID
	 * 
	 * @param candidateAttribute
	 * @param tableName
	 * @param whereAttributeName
	 * @param whereAttributeValue
	 * @param isMinQuestion
	 * @return
	 */
	public static String getGroupBySubQuery(String candidateAttribute, String tableName, String whereAttributeName,
			String whereAttributeValue, boolean isMinQuestion) {
		// select max(Area) from city
		// where
		// StateID = 1028
		// group by
		// StateID
		String sqlQuery = "select " + (isMinQuestion ? "min(" : "max(") + candidateAttribute + ") from " + tableName
				+ " where " + whereAttributeName + " = " + whereAttributeValue + " group by " + whereAttributeName;

		return sqlQuery;
	}

	/**
	 * isMinimum: if true, then min() else max() <br />
	 * 
	 * returns queries like: select max(Area) from States
	 * 
	 * @param candidateAttribute
	 * @param tableName
	 * @param isMinQuestion
	 * @return
	 */
	public static String getMinMaxSubQuery(String candidateAttribute, String tableName, boolean isMinQuestion) {
		// select max(Area) from States
		String sqlQuery = "select " + (isMinQuestion ? "min(" : "max(") + candidateAttribute + ") from " + tableName;

		return sqlQuery;
	}

	
	
	
	
	public static List<String> getResultForSqlQuery(String sql) throws SQLException {
		List<String> results = new ArrayList<>();

		Statement sta = DBConnector.conn.createStatement();
		ResultSet rs = sta.executeQuery(sql);

		while (rs.next()) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			for(int i = 1; i <= columns; i++) {
				results.add(rs.getString(i));
			}
		}

		return results;
	}
}
