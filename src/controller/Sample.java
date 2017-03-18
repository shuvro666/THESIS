package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Map.Entry;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;




//import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Sample {

	// similar sets for cities, lakes, and other attributes present in db
	// change these sets with a Map<String, Integer> -> (StateName, StateId)
	// and work or query using these integer
	private static Map<String, Integer> states;
	private static Map<String, Integer> cities;
	private static Map<String, Integer> lakes;
	private static Map<String, Integer> rivers;
	private static Map<String, Integer> countries;
	private static Map<String, Integer> landElevations;
	private static Map<String, Integer> borders;
	private static Map<String, Integer> mountains;
	private static Map<String, Integer> roads;
	
	//private static Map<String, Integer> capitals;


	private static Connection conn;

	private static Map<String, Set<String>> attributeMappings;
	private static Map<String, Set<String>> queryTableMappings;
	private static Map<String, String> mainAttributeForTable;
	private static Map<String, String> whereAttributeForWhereType; // if
																	// condition
																	// is on
																	// state,
																	// "StateId"
																	// should be
																	// checked

	private static void connectToDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtsaar2016?user=shuvro&password=password");
		System.out.println("DB is connected");
	}

	/**
	 * load states present in DB to the set
	 * 
	 * @throws Exception
	 */
	
	/*  private static void loadCapitals() throws Exception {
    if (capitals == null || capitals.isEmpty()) {
	capitals = new HashMap<>();
	capitals.putAll(loadHashMapFromTable(capitals, "capital", "CapitalId" ,"mountainName"));
}}*/

	
	private static void loadStates() throws Exception {
		if (states == null || states.isEmpty()) {
			states = new HashMap<>();
			states.putAll(loadHashMapFromTable(states, "state", "StateId", "StateName"));
		}
	}

	private static void loadCities() throws Exception {
		if (cities == null || cities.isEmpty()) {
			cities = new HashMap<>();
			cities.putAll(loadHashMapFromTable(cities, "city", "CityId", "CityName"));
		}
	}
	
	 private static void loadLakes() throws Exception {
	    if (lakes == null || lakes.isEmpty()) {
		lakes = new HashMap<>();
		lakes.putAll(loadHashMapFromTable(lakes, "lake", "lakeId", "lakeName"));
	   }
     }
	
	 
	
	
	  private static void loadBorders() throws Exception {
		    if (borders == null || borders.isEmpty()) {
			borders = new HashMap<>();
			borders.putAll(loadHashMapFromTable(borders, "border", "borderId", "borderName"));
		}
	  }
		
		
	
	
	  private static void loadMountains() throws Exception {
		    if (mountains == null || mountains.isEmpty()) {
			mountains = new HashMap<>();
			mountains.putAll(loadHashMapFromTable(mountains, "mountain", "mountainId", "mountainName"));
		}
	  }
		
		
	
	
	  private static void landElevations() throws Exception {
		    if (landElevations == null || landElevations.isEmpty()) {
		    	landElevations = new HashMap<>();
		    	landElevations.putAll(loadHashMapFromTable(landElevations, "landElevation", "landElevationId", "landElevationName"));
		}
	  }
	
		
	
	
	  private static void loadRivers() throws Exception {
		    if (rivers == null || rivers.isEmpty()) {
		    	rivers = new HashMap<>();
		    	rivers.putAll(loadHashMapFromTable(rivers, "river", "riverId", "riverName"));
		}
	  }
		

	 
	  private static void loadRoads() throws Exception {
		    if (roads == null || roads.isEmpty()) {
		    	roads = new HashMap<>();
		    	roads.putAll(loadHashMapFromTable(roads, "road", "roadId", "roadName"));
		}
	  }
		
		
	 
	 
	  private static void loadCountries() throws Exception {
		    if (countries == null || countries.isEmpty()) {
		    	countries = new HashMap<>();
		    	countries.putAll(loadHashMapFromTable(countries, "country", "countryId", "countryName"));
		}
	  }
		
	


	private static Map<String, Integer> loadHashMapFromTable(Map<String, Integer> map, String tableName,
			String idAttributeName, String nameAttributeName) throws SQLException {
		if (map == null || map.isEmpty()) {
			map = new HashMap<>();

			Statement sta = conn.createStatement();
			String Sql = "select " + idAttributeName + ", " + nameAttributeName + " from " + tableName;
			ResultSet rs = sta.executeQuery(Sql);

			while (rs.next()) {
				map.put(rs.getString(nameAttributeName), rs.getInt(idAttributeName));
			}
		}

		return map;
	}

	private static Set<String> loadHashSetFromTable(Set<String> set, String tableName, String attributeName)
			throws SQLException {
		if (set == null || set.isEmpty()) {
			set = new HashSet<>();

			Statement sta = conn.createStatement();
			String Sql = "select " + attributeName + " from " + tableName;
			ResultSet rs = sta.executeQuery(Sql);

			while (rs.next()) {
				set.add(rs.getString(attributeName));
			}
		}

		return set;
	}

	/**
	 * function for preparing mappings that would help in question modelling
	 */
	private static void prepareAttributeMappings() {
		if (attributeMappings == null || attributeMappings.isEmpty()) {
			attributeMappings = new HashMap<>();

			Set<String> areaSet = new HashSet<>();
			areaSet.add("area");
			areaSet.add("largest");
			areaSet.add("smallest");
			areaSet.add("biggest");
			attributeMappings.put("area", areaSet);

			Set<String> populationSet = new HashSet<>();
			populationSet.add("population");
			populationSet.add("quantity");
			populationSet.add("people");
			populationSet.add("largest");
			populationSet.add("smallest");
			populationSet.add("highest");
			attributeMappings.put("population", populationSet);

			Set<String> lengthSet = new HashSet<>();
			lengthSet.add("lake");
			lengthSet.add("river");
			lengthSet.add("mountain");
			lengthSet.add("country");
			lengthSet.add("road");
			lengthSet.add("longest");
			lengthSet.add("smallest");
			lengthSet.add("deepest");

			attributeMappings.put("length", lengthSet);
		}
	}

	/**
	 * function for preparing mappings that would help in question modelling
	 */
	private static void prepareQueryTableMappings() {
		if (queryTableMappings == null || queryTableMappings.isEmpty()) {
			queryTableMappings = new HashMap<>();

			Set<String> lakeSet = new HashSet<>();
			lakeSet.add("lake");
			lakeSet.add("river");
			lakeSet.add("water");
			queryTableMappings.put("lake", lakeSet);

			Set<String> citySet = new HashSet<>();
			citySet.add("city");
			queryTableMappings.put("city", citySet);
			
			Set<String> riverSet = new HashSet<>();
			riverSet.add("river");
			queryTableMappings.put("river", riverSet);

			Set<String> countrySet = new HashSet<>();
			countrySet.add("country");
			countrySet.add("America");
			
			queryTableMappings.put("country", countrySet);
			
			Set<String> stateSet = new HashSet<>();
			stateSet.add("state");
			queryTableMappings.put("state", stateSet);
		/*	
			Set<String> capitalSet = new HashSet<>();
			capitalSet.add("state");
			queryTableMappings.put("capital", capitalSet); 
			*/
		}
	}

	private static void prepareMainAttributesForTables() {
		if (mainAttributeForTable == null || mainAttributeForTable.isEmpty()) {
			mainAttributeForTable = new HashMap<>();

			mainAttributeForTable.put("state", "StateName");
			mainAttributeForTable.put("city", "CityName");
			mainAttributeForTable.put("lake", "LakeName");
			mainAttributeForTable.put("river", "RiverName");
			mainAttributeForTable.put("country", "CountryName");
			mainAttributeForTable.put("landelevation", "HighLand");
			
			mainAttributeForTable.put("country", "CountryName");
			mainAttributeForTable.put("landelevation", "HighLand");
			mainAttributeForTable.put("river", "RiverName");
			
			mainAttributeForTable.put("road", "RoadName");
			mainAttributeForTable.put("border", "BorderName");
			mainAttributeForTable.put("mountain", "MountainName");
			
			//mainAttributeForTable.put("capital", "capitalName");
			
			// TODO: add remaining
		}
	}

	private static void prepareWhereAttributeForWhereType() {
		if (whereAttributeForWhereType == null || whereAttributeForWhereType.isEmpty()) {
			whereAttributeForWhereType = new HashMap<>();

			whereAttributeForWhereType.put("state", "StateId");
			whereAttributeForWhereType.put("city", "CityId");
			//whereAttributeForWhereType.put("lake", "LakeId");
			whereAttributeForWhereType.put("river", "RiverId");
			whereAttributeForWhereType.put("country", "CountryId");
			whereAttributeForWhereType.put("landelevation", "HighLand");
			
			whereAttributeForWhereType.put("country", "CountryId");
			whereAttributeForWhereType.put("landelevation", "LandElevationId");
			whereAttributeForWhereType.put("river", "RiverId");
			whereAttributeForWhereType.put("road", "RoadId");
			whereAttributeForWhereType.put("border", "BorderId");
		    whereAttributeForWhereType.put("capital", "StateId");
			whereAttributeForWhereType.put("capital", "CityId");
			
			// TODO: add remaining
		}
	}

	/**
	 * load all the prerequisites. this should be the first function that is
	 * called when your program starts.
	 * 
	 * @throws Exception
	 */
	private static void loadPrereq() throws Exception {
		connectToDB();
		loadStates();
		loadCities();
		loadLakes();
		loadRivers();
		loadCountries();
		landElevations();
		
	    loadCountries();
		
		loadRivers();
		loadRoads();
		loadBorders();
		loadMountains();
		//loadCapitals();
		
		
		prepareAttributeMappings();
		prepareQueryTableMappings();
		prepareMainAttributesForTables();
		prepareWhereAttributeForWhereType();

		// load other attributes here as well
	}

	// replace output string with Enum later
	/**
	 * determine if the word is a state, city, lake, or ... <br />
	 * Output will be a table name
	 * 
	 * @param inputNoun
	 * @return
	 */
	private static String determineTypeOfProperNoun(String inputNoun) {
		if (states.containsKey(inputNoun)) return "state";
		
		if  (lakes.containsKey(inputNoun)) return "lake";
		 if (states.containsKey(inputNoun)) return "state";
		 if (cities.containsKey(inputNoun)) return "city";
		 if (rivers.containsKey(inputNoun)) return "river";
		  if (countries.containsKey(inputNoun)) return "country";
		  
		 if (landElevations.containsKey(inputNoun)) return "landelevation";
		 //uncomment after other attributes have been loaded

		  if (borders.containsKey(inputNoun)) return "border";
		  
		  if (roads.containsKey(inputNoun)) return "road";

		  if (mountains.containsKey(inputNoun)) return "mountain";
		 // if (capitals.containsKey(inputNoun)) return "capital";
		
		 
		  
		 
		  

		return "";
	}

	/**
	 * get possible candidates for the input word. <br />
	 * Example: <br />
	 * Input: largest <br />
	 * Output: area, population
	 * 
	 * @param word
	 * @return
	 */
	private static Set<String> getPossibleCandidateAttributesForWord(String word) {
		Set<String> candidates = new HashSet<>();

		for (Entry<String, Set<String>> entry : attributeMappings.entrySet()) {
			Set<String> values = entry.getValue();
			if (values.contains(word))
				candidates.add(entry.getKey());
		}

		return candidates;
	}

	/**
	 * get possible candidate query tables for the input word. <br />
	 * Example: <br />
	 * Input: lake <br />
	 * Output: lake
	 * 
	 * TODO: this function should return a set of string (table names) and not just one string (table name)
	 * 
	 * @param word
	 * @return
	 */
	private static String getPossibleCandidateQueryTableForWord(String word) {
		String candidate = "";

		for (Entry<String, Set<String>> entry : queryTableMappings.entrySet()) {
			Set<String> values = entry.getValue();
			if (values.contains(word)) {
				candidate = entry.getKey();
				break;
			}
		}

		return candidate;
	}

	private static String getMainAttributeForTable(String tableName) {
		return mainAttributeForTable.containsKey(tableName) ? mainAttributeForTable.get(tableName) : "";
	}

	/**
	 * whereType is expected to be a table name
	 * 
	 * @param whereType
	 * @return
	 */
	private static String getWhereAttributeForWhereType(String whereType) {
		return whereAttributeForWhereType.containsKey(whereType) ? whereAttributeForWhereType.get(whereType) : "";
	}
	private static String getIdForName(String name, String tableName) {
		if (tableName.equals("country")) {
			return countries.containsKey(name) ? countries.get(name).toString() : "";
		}

		// TODO: add for city, lake, ...
		if (tableName.equals("state")) {
			return states.containsKey(name) ? states.get(name).toString() : "";
		}
		if (tableName.equals("city")) {
			return cities.containsKey(name) ? cities.get(name).toString() : "";
		}
		if (tableName.equals("lake")) {
			return lakes.containsKey(name) ? lakes.get(name).toString() : "";
		}
		
		if (tableName.equals("mountain")) {
			return mountains.containsKey(name) ? mountains.get(name).toString() : "";
		}
		if (tableName.equals("river")) {
			return rivers.containsKey(name) ? rivers.get(name).toString() : "";
		}
		if (tableName.equals("border")) {
			return borders.containsKey(name) ? borders.get(name).toString() : "";
		}
		if (tableName.equals("road")) {
			return roads.containsKey(name) ? roads.get(name).toString() : "";
		}
		if (tableName.equals("landElevation")) {
			return landElevations.containsKey(name) ? landElevations.get(name).toString() : "";
		}
		
		/*if (tableName.equals("capital")) {
			return capitals.containsKey(name) ? capitals.get(name).toString() : "";}
		
		*/
		return "";
	}

	


	public static void main(String[] args) throws Exception {
		// load prerequisites
		loadPrereq();

		List<String> questions = new ArrayList<>();
		//questions.add("Which lake are in Michigan");	// TODO: take the stem word for all words
		questions.add("What are the city in Kansas");
		//questions.add("What are largest city in Kansas");
		 //questions.add("Give me the cities in Virginia");
		//questions.add("name the river in Arkansas");
		//questions.add("Give me the city in Alabama");
		//questions.add("give me the capital of state ");
		//questions.add("give me the city of California");
		//questions.add("how many state are in America");
		
		
//		questions.add("Which is the longest lake in Michigan");
		// Which lakes are in Michigan? -> // haven't catered the "longest"
//		questions.add("What is the largest city of Kansas");
		// What is the city of Kansas? -> What are the cities in Kansas -> // haven't catered the "largest"

		for (String question : questions) {
			String sqlQuery = getQueryForQuestion(question);
		}
	}

	private static String getQueryForQuestion(String question) throws Exception {
		String sqlQuery = "";

		// tokenize - get all the words
		// process all the words and get their POS type
		// adjectives and nouns are of interest for us
		// in the above question, adjective: longest
		// nouns:
		// NN or NNS: lake
		// NNP or NNPS: Michigan
		StringTokenizer st = new StringTokenizer(question);
	
	
		MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
		

		String improperNoun = ""; // fill this String after parsing using
									// tokenizer -> NN or NNS
		String properNoun = ""; // will be used in the where clause
		String JJS = ""; // adjective
		Set<String> adjectives = new HashSet<>(); // fill this hashset after
													// parsing using tokenizer
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			String tag = tagger.tagString(word);
			System.out.println(word + " - " + tag);

			if (tag.contains("_NNP")) {
				properNoun = word;
			} else if (tag.contains("_NN") || tag.contains("_NNS")) {
				improperNoun = word;
			} else if (tag.contains("_JJS")) {
				JJS = word;
				adjectives.add(word);
			}
		}

		System.out.println("pn - " + properNoun);

		// iterate through all the NNP or NNPS and see if they are state, lake
		// or what?

		String whereType = determineTypeOfProperNoun(properNoun);
		// now i'd know that Michigan is a State. This will help us in
		// formulating sql query (where clause) -> where State='Michigan'

		System.out.println("Where type - " + whereType);

		Set<String> candidateAttributes = new HashSet<>();
		for (String adj : adjectives) {
			candidateAttributes.addAll(getPossibleCandidateAttributesForWord(adj)); // for
																					// longest,
																					// area
																					// and
																					// population
																					// will
																					// be
																					// returned.
			System.out.println();
			// this will help in formulating sql query -> max(area) or
			// max(population)
		}

		// candidateAttributes now contains all the possible attributes that
		// need to be considered.
		// in the given question, candidateAttiributes will contian: area,
		// population
		// sample query:
		// SELECT StateName
		// FROM [MtSaar2016].[dbo].[State]
		// where Area = (select max(Area) from [MtSaar2016].[dbo].[State])
		// or TotalPopulation = (select max(TotalPopulation)
		// from [MtSaar2016].[dbo].[State]);

		String queryTable = getPossibleCandidateQueryTableForWord(improperNoun); // now
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

		String mainAttribute = getMainAttributeForTable(queryTable);
		String whereAttributeName = getWhereAttributeForWhereType(whereType);
		String whereAttributeValue = getIdForName(properNoun, whereType);

		System.out.println("\n\n\n");
		System.out.println("question: " + question);
		System.out.println("improper noun: " + improperNoun);
		System.out.println("proper noun: " + properNoun);
		System.out.println("where type: " + whereType);
		System.out.println("where type attribute: " + whereAttributeName);
		System.out.println("where type value: " + whereAttributeValue);
		System.out.println("candidate attributes: " + candidateAttributes.toString());
		System.out.println("main attribute: " + mainAttribute);
		System.out.println("table: " + queryTable);
		// Class.forName("com.mysql.jdbc.Driver");
		// Connection conn =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/mtsaar2016?user=shuvro&password=password");
		// System.out.println("test");
		// Statement sta = conn.createStatement();
		// String Sql = "select * from lake where "+type+"="+pNoun;
		// ResultSet rs = sta.executeQuery(Sql);
		// while (rs.next()) {
		// System.out.println(rs.getString("LakeName"));
		// }
		// now that we know the main attribute, table name, and conditions,
		// formulate sql query
		// sqlQuery = ...

		sqlQuery = getSqlQuery(mainAttribute, queryTable, candidateAttributes, whereAttributeName, whereAttributeValue);
		System.out.println("\n\nsql: " + sqlQuery);
		System.out.println("result: ");
		getResultForSqlQuery(sqlQuery);
		return sqlQuery;
	}
	
	
	StanfordCoreNLP pipeline;
	public String getLemma(String text) {
		String lemma = "communities";
		Annotation document = pipeline.process(text);
		for (CoreMap sentence : document.get(SentencesAnnotation.class)) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				lemma += token.get(LemmaAnnotation.class) + " ";
			}
		}
		return lemma;
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
	 * sqlQuery: "select LakeName from lake where StateId = 1035;"
	 * 
	 * @param mainAttribute
	 * @param tableName
	 * @param candidateAttributes
	 * @param whereAttributeName
	 * @param whereAttributeValue
	 * @return
	 */
	private static String getSqlQuery(String mainAttribute, String tableName, Set<String> candidateAttributes,
			String whereAttributeName, String whereAttributeValue) {
		String sqlQuery = "select " + mainAttribute + " from " + tableName + " where " + whereAttributeName + " = "
				+ whereAttributeValue + ";";

		return sqlQuery;
	}

	private static void getResultForSqlQuery(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery(sql);

		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}
}
