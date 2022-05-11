package GenerateFamilyTree;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.EventDAO;
import DAO.PersonDAO;
import JsonNames.LocationData;
import JsonNames.Names;
import Model.Event;
import Model.Person;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class GenerateFakeFamily {
    private static Names fNames = null;
    private static Names mNames = null;
    private static Names sNames = null;
    private static LocationData locationData = null;

    // Gets random index in Locations Array
    Random random = new Random();
    //int randomNum = random.nextInt(locationData.getData().length);

    public GenerateFakeFamily() throws FileNotFoundException, DataAccessException {
        if (fNames == null) {
            // Reader for each JSON File
            Reader femaleNames = new FileReader("json/fnames.json");
            Reader maleNames = new FileReader("json/mnames.json");
            Reader surNames = new FileReader("json/snames.json");
            Reader locations = new FileReader("json/locations.json");

            // Turns each JSON array into a gson string
            Gson gson = new Gson();
            fNames = gson.fromJson(femaleNames, Names.class);
            mNames = gson.fromJson(maleNames, Names.class);
            sNames = gson.fromJson(surNames, Names.class);
            locationData = gson.fromJson(locations, LocationData.class);
        }
    }

    public Person recursiveFakeFamily(Person currentPerson, String username, int generations, String gender,
                                      int birthYear, Connection openConnection) throws DataAccessException, FileNotFoundException {
        EventDAO events = new EventDAO(openConnection);
        PersonDAO insertPerson = new PersonDAO(openConnection);
        Random randomVal = new Random();

        Person nextFemale = null;
        Person nextMale = null;

        if (generations > 0) {
            nextFemale = setPersonProperties(username, "f");
            nextMale = setPersonProperties(username, "m");

            // Recursive call to create the other people in the tree
            recursiveFakeFamily(nextFemale, username, generations - 1, "f",
                    birthYear - 25, openConnection);
            recursiveFakeFamily(nextMale, username, generations - 1, "m",
                    birthYear - 25, openConnection);

            String femaleID = nextFemale.getPersonID();
            String maleID = nextMale.getPersonID();

            nextFemale.setSpouseID(maleID);
            nextMale.setSpouseID(femaleID);
            nextFemale.setLastName(nextMale.getLastName());

            // Sets the MotherID and FatherID for the currentPerson
            currentPerson.setMotherID(nextFemale.getPersonID());
            currentPerson.setFatherID(nextMale.getPersonID());

            // Creating marriage event for the couple
            Event femaleMarriage = new Event();
            Event maleMarriage = new Event();

            // Used to get the same location for marriages
            float marriageLat = locationData.getData()[random.nextInt(locationData.getData().length)].getLatitude();
            float marriageLong = locationData.getData()[random.nextInt(locationData.getData().length)].getLongitude();
            String marriageCountry = locationData.getData()[random.nextInt(locationData.getData().length)].getCountry();
            String marriageCity = locationData.getData()[random.nextInt(locationData.getData().length)].getCity();

            // Sets Marriage Event Properties
            femaleMarriage = marriageEvent(femaleMarriage, femaleID, username, marriageLat, marriageLong,
                    marriageCountry, marriageCity, birthYear - 2);
            maleMarriage = marriageEvent(maleMarriage, maleID, username, marriageLat, marriageLong,
                    marriageCountry, marriageCity, birthYear - 2);

            // Adds Marriage Event to the database
            events.addEvent(femaleMarriage);
            events.addEvent(maleMarriage);

            // Inserts the new Person objects to the Database starting at the oldest generation (great-grandfather/great-grandmother)
            insertPerson.insert(nextFemale);
            insertPerson.insert(nextMale);
        }
//        Person returnPerson;

        // Sets new person's properties
        nextFemale = currentPerson;

        // Creating birth and death Events for the Person
        Event birth = new Event();
        Event death = new Event();
        birth = birth(birth, nextFemale.getPersonID(), username, birthYear);
        death = death(death, nextFemale.getPersonID(), username, birthYear);

        // Adds birth and death events to the Database for the new Person object
        events.addEvent(birth);
        events.addEvent(death);

        // Returns the User's Person object once the entire Family Tree has been created, before then the return doesn't actually do anything.
        return nextFemale;
    }


    // This sets the properties for the Person we will return
    public Person setPersonProperties(String username, String gender) {
        Person returnPerson = new Person();
        Random randomVal = new Random(); //Used to generate a random index in the names list

        returnPerson.setPersonID(UUID.randomUUID().toString());
        returnPerson.setAssociatedUsername(username);
        if (Objects.equals(gender, "f")) {
            returnPerson.setFirstName(fNames.getNamesList()[randomVal.nextInt(fNames.getNamesList().length)]);
            returnPerson.setLastName(sNames.getNamesList()[randomVal.nextInt(sNames.getNamesList().length)]);
        }
        else if (Objects.equals(gender, "m")) {
            returnPerson.setFirstName(mNames.getNamesList()[randomVal.nextInt(mNames.getNamesList().length)]);
            returnPerson.setLastName(sNames.getNamesList()[randomVal.nextInt(sNames.getNamesList().length)]);
        }
        returnPerson.setGender(gender);
        returnPerson.setFatherID(returnPerson.getFatherID());
        returnPerson.setMotherID(returnPerson.getMotherID());

        return returnPerson;
    }

    // TODO: Needs to take in birth year of user for the limit on when the marriage year can be.
    public Event marriageEvent(Event married, String personID, String username, float latitude, float longitude,
                               String country, String city, int marriageYear) {
        // Marriage events for Female and Male of same generation
        married.setEventID(UUID.randomUUID().toString());
        married.setAssociatedUsername(username);
        married.setPersonID(personID);
        married.setLatitude(latitude);
        married.setLongitude(longitude);
        married.setCountry(country);
        married.setCity(city);
        married.setEventType("marriage");
        married.setYear(marriageYear);

        return married;
    }


    public Event birth(Event birth, String personID, String username, int birthYear) {
        Random random = new Random();
        int randomNum = random.nextInt(locationData.getData().length);

        birth.setEventID(UUID.randomUUID().toString());
        birth.setAssociatedUsername(username);
        birth.setPersonID(personID);
        birth.setLatitude(locationData.getData()[randomNum].getLatitude());
        birth.setLongitude(locationData.getData()[randomNum].getLongitude());
        birth.setCountry(locationData.getData()[randomNum].getCountry());
        birth.setCity(locationData.getData()[randomNum].getCity());
        birth.setEventType("birth");
        birth.setYear(birthYear);

        return birth;
    }

    public Event death(Event death, String personID, String username, int birthYear) {
        Random random = new Random();
        int randomNum = random.nextInt(locationData.getData().length);

        death.setEventID(UUID.randomUUID().toString());
        death.setAssociatedUsername(username);
        death.setPersonID(personID);
        death.setLatitude(locationData.getData()[randomNum].getLatitude());
        death.setLongitude(locationData.getData()[randomNum].getLongitude());
        death.setCountry(locationData.getData()[randomNum].getCountry());
        death.setCity(locationData.getData()[randomNum].getCity());
        death.setEventType("death");
        death.setYear(birthYear + 85);

        return death;
    }

}
