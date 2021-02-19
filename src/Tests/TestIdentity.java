package Tests;


import com.github.javafaker.Faker;

// Class taken from old globals file, used for identity storage and testing.

public class TestIdentity {

	//constants section
	public enum Theme {DEFAULT, ANCIENT, APP, BEER, BOOK, CAT, COMMERCE, COMPANY, DOG, DRAGONBALL, EDUCATOR, ESPORTS, FUNNYNAME, GAMEOFTHRONES, HARRYPOTTER, HITCHHIKERSGUIDE, HOBBIT, HOWIMETMOTHER, LEAGUEOFLEGENDS, LEBOWSKI, LOTR, OVERWATCH, POKEMON, RICKANDMORTY, ROCKBAND, SPACE, STARTREK, SUPERHERO, TWINPEAKS, WITCHER, ZELDA, RANDOM};

	//variables section
	public String firstName;
	public String middleName;
	public String lastName;
	public String street;
	public String address2;
	public String city;
	public String county;
	public String regionState;
	public String country;
	public String postZip;
	public String email;
	public String phone;
	public String dateOfBirth;
	public String taxSSN;
	
	//constructors
	public TestIdentity() {
		
		this.firstName = "Firstname";
		this.middleName = "Middlename";
		this.lastName = "Lastname";
		this.street = "1 Street Rd.";
		this.address2 = "";
		this.city = "Anytown";
		this.county = "Lines";
		this.regionState = "AN";
		this.country = "USA";
		this.postZip = "12345";
		this.email = "user@server.domain";
		this.phone = "(555) 555-5555";
		this.dateOfBirth = "Jan 01, 2001";
		this.taxSSN = "123-45-6789";
		
	}
	
	//methods
	public String toString() { // formatted output
		
		//variables section
		String assembly = new String();
		
		//begin toString
		assembly += firstName;
		assembly += " " + middleName;
		assembly += " " + lastName;
		assembly += "\n" + street;
		assembly += "\n" + address2;
		assembly += "\n" + city;
		assembly += "\n" + county;
		assembly += "\n" + regionState;
		assembly += "\n" + country;
		assembly += "\n" + postZip;
		assembly += "\n" + email;
		assembly += "\n" + phone;
		assembly += "\n" + dateOfBirth;
		assembly += "\n" + taxSSN;
		return assembly;
		
	} //end toString
	
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		return (int)(Math.random() * ((max - min) + 1)) + min;
	}
	
	public static TestIdentity GenerateUSID (TestIdentity newID) { // generates a new identity with random values using default US locale
		
		//variables section
		Faker fakeData = new Faker();
		
		//begin GenerateUSID
		newID.firstName = fakeData.name().firstName();
		newID.middleName = fakeData.name().nameWithMiddle(); // temporary fix, will have a line which extracts the middle name from this randomly generated item
		newID.lastName = fakeData.name().lastName();
		newID.street = fakeData.address().streetAddress();
		newID.address2 = fakeData.address().secondaryAddress();
		newID.city = fakeData.address().city();
		newID.regionState = fakeData.address().stateAbbr();
		newID.country = fakeData.address().countryCode();
		newID.postZip = fakeData.address().zipCode();
		//newID.county = fakeData.address().countyByZipCode(newID.postZip.substring(0, 5)); // for some reason, java wants to treat the end index as the end location (index + 1)
		newID.email = fakeData.internet().emailAddress();
		newID.phone = fakeData.phoneNumber().phoneNumber();
		newID.dateOfBirth = fakeData.date().birthday(21, 92).toString();
		//newID.taxSSN = fakeData.idNumber().ssnValid();
		
		return newID;
		
	}//end GenerateUSID
	
	public static TestIdentity ThemeAlterator (TestIdentity rawID, Theme select) { // generates an obviously fictional 
		
		//variables section
		Faker fakeData = new Faker();
		
		switch (select) {
		case DEFAULT:{ // commanded default
			return rawID;
		}
		case ANCIENT:{
			switch (getRandomNumberInRange(0,3)) {
			case 0:{
				rawID.firstName = fakeData.ancient().god();
				break;
			}
			case 1:{
				rawID.firstName = fakeData.ancient().hero();
				break;
			}
			case 2:{
				rawID.firstName = fakeData.ancient().primordial();
				break;
			}
			case 3:{
				rawID.firstName = fakeData.ancient().titan();
				break;
			}
			}
			return rawID;
		}
		case APP:{
			rawID.firstName = fakeData.app().author();
			rawID.middleName = fakeData.app().name();
			return rawID;
		}
		case BEER:{
			rawID.lastName = fakeData.beer().name();
			return rawID;
		}
		case BOOK:{
			rawID.firstName = fakeData.book().author();
			rawID.address2 = fakeData.book().genre();
			rawID.lastName = fakeData.book().title();
			return rawID;
		}
		case CAT:{
			rawID.firstName = fakeData.cat().name();
			rawID.middleName = fakeData.cat().breed();
			rawID.lastName = fakeData.cat().registry();
			return rawID;
		}
		case COMMERCE:{
			rawID.middleName = fakeData.commerce().productName();
			return rawID;
		}
		case COMPANY:{
			rawID.firstName = fakeData.company().name();
			rawID.middleName = fakeData.company().profession();
			rawID.lastName = fakeData.company().industry();
			rawID.address2 = fakeData.company().bs();
			return rawID;
		}
		case DOG:{
			rawID.firstName = fakeData.dog().name();
			rawID.lastName = fakeData.dog().breed();
			rawID.address2 = fakeData.dog().memePhrase();
			return rawID;
		}
		case DRAGONBALL:{
			rawID.firstName = fakeData.dragonBall().character();
			return rawID;
		}
		case EDUCATOR:{
			rawID.address2 = fakeData.educator().campus();
			rawID.middleName = fakeData.educator().course();
			switch (getRandomNumberInRange(0,1)) {
			case 0:{
				rawID.city = fakeData.educator().secondarySchool();
				break;
			}
			case 1:{
				rawID.city = fakeData.educator().university();
				break;
			}
			}
			return rawID;
		}
		case ESPORTS:{
			rawID.firstName = fakeData.esports().player();
			rawID.lastName = fakeData.esports().team();
			rawID.address2 = fakeData.esports().league();
			return rawID;
		}
		case FUNNYNAME:{
			rawID.firstName = fakeData.funnyName().name();
			return rawID;
		}
		case GAMEOFTHRONES:{
			rawID.firstName = fakeData.gameOfThrones().character();
			rawID.middleName = fakeData.gameOfThrones().dragon();
			rawID.lastName = fakeData.gameOfThrones().house();
			rawID.city = fakeData.gameOfThrones().city();
			return rawID;
		}
		case HARRYPOTTER:{
			rawID.firstName = fakeData.harryPotter().character();
			rawID.lastName = fakeData.harryPotter().book();
			rawID.city = fakeData.harryPotter().location();
			return rawID;
		}
		case HITCHHIKERSGUIDE:{
			rawID.firstName = fakeData.hitchhikersGuideToTheGalaxy().character();
			rawID.lastName = fakeData.hitchhikersGuideToTheGalaxy().specie();
			rawID.street = fakeData.hitchhikersGuideToTheGalaxy().location();
			rawID.address2 = fakeData.hitchhikersGuideToTheGalaxy().starship();
			rawID.city = fakeData.hitchhikersGuideToTheGalaxy().planet();
			return rawID;
		}
		case HOBBIT:{
			rawID.firstName = fakeData.hobbit().character();
			rawID.lastName = fakeData.hobbit().thorinsCompany();
			rawID.city = fakeData.hobbit().location();
			return rawID;
		}
		case HOWIMETMOTHER:{
			rawID.firstName = fakeData.howIMetYourMother().character();
			return rawID;
		}
		case LEAGUEOFLEGENDS:{
			rawID.firstName = fakeData.leagueOfLegends().champion();
			rawID.lastName = fakeData.leagueOfLegends().summonerSpell();
			rawID.city = fakeData.leagueOfLegends().location();
			return rawID;
		}
		case LEBOWSKI:{
			rawID.firstName = fakeData.lebowski().character();
			rawID.lastName = fakeData.lebowski().actor();
			return rawID;
		}
		case LOTR:{
			rawID.firstName = fakeData.lordOfTheRings().character();
			rawID.city = fakeData.lordOfTheRings().location();
			return rawID;
		}
		case OVERWATCH:{
			rawID.firstName = fakeData.overwatch().hero();
			rawID.city = fakeData.overwatch().location();
			return rawID;
		}
		case POKEMON:{
			rawID.firstName = fakeData.pokemon().name();
			rawID.city = fakeData.pokemon().location();
			return rawID;
		}
		case RICKANDMORTY:{
			rawID.firstName = fakeData.rickAndMorty().character();
			rawID.city = fakeData.rickAndMorty().location();
			return rawID;
		}
		case ROCKBAND:{
			rawID.firstName = fakeData.rockBand().name();
			return rawID;
		}
		case SPACE:{
			rawID.country = fakeData.space().galaxy();
			rawID.county = fakeData.space().constellation();
			rawID.city = fakeData.space().star();
			rawID.address2 = fakeData.space().starCluster();
			rawID.firstName = fakeData.space().nasaSpaceCraft();
			rawID.lastName = fakeData.space().agency();
			rawID.middleName = fakeData.space().company();
			rawID.regionState = fakeData.space().agencyAbbreviation();
			return rawID;
		}
		case STARTREK:{
			rawID.firstName = fakeData.starTrek().character();
			rawID.lastName = fakeData.starTrek().specie();
			rawID.city = fakeData.starTrek().location();
			return rawID;
		}
		case SUPERHERO:{
			rawID.firstName = fakeData.superhero().prefix();
			rawID.middleName = fakeData.superhero().name();
			rawID.lastName = fakeData.superhero().suffix();
			return rawID;
		}
		case TWINPEAKS:{
			rawID.firstName = fakeData.twinPeaks().character();
			rawID.city = fakeData.twinPeaks().location();
			return rawID;
		}
		case WITCHER:{
			rawID.firstName = fakeData.witcher().character();
			rawID.lastName = fakeData.witcher().school();
			rawID.city = fakeData.witcher().location();
			return rawID;
		}
		case ZELDA:{
			rawID.firstName = fakeData.zelda().character();
			rawID.lastName = fakeData.zelda().game();
			return rawID;
		}
		case RANDOM:{
			return ThemeAlterator(rawID, Theme.values()[getRandomNumberInRange(0, 30)]); // enum is 31 items in length, so this calls the ThemeAlterator with any theme other than random.
		}
		default:{ // fail-safe 
			return rawID;
		}
		}

	}
	
}//end PersonalIdentity
