package Result;

/**
 * This Class returns the JSON String of the Client's request for a specific Person object
 * */
public class PersonIDResult {

    public PersonIDResult () { }

    /** Username associated to requested Person object */
    private String associatedUsername;
    /** PersonID associated to requested Person object */
    private String personID;
    /** First name associated to requested Person object */
    private String firstName;
    /** Last name associated to requested Person object */
    private String lastName;
    /** Gender of requested Person object */
    private String gender;
    /** FatherID associated to requested Person object (optional) */
    private String fatherID;
    /** MotherID associated to requested Person object (optional) */
    private String motherID;
    /** SpouseID associated to requested Person object (optional) */
    private String spouseID;

    private String message;
    /** Tells us whether the Client's request was successful or not */
    private boolean success;

    /**
     * This function will return the JSON String which will then be sent to the Client
     * whether there request was successful or not
     *
     * @param associatedUsername Username associated to requested Person
     * @param personID ID of the person requested
     * @param firstName first name of the person requested
     * @param lastName last name of the person requested
     * @param gender gender of the person requested
     * @param fatherID fatherID of the person requested
     * @param motherID motherID of the person requested
     * @param spouseID spouseID of the person requested
     * @param success Returns true or false depending on outcome of request
     *
     * @return Returns the JSON String of person object if the retrieval is successful
     * */
    public PersonIDResult(String associatedUsername, String personID, String firstName, String lastName,
                         String gender, String fatherID, String motherID, String spouseID, boolean success) {

        /** Setting the requested username equal to the username in the database */
        this.associatedUsername = associatedUsername;
        /** Setting the requested personID equal to the personID in the database */
        this.personID = personID;
        /** Setting the first name equal to the first name of the person in the database */
        this.firstName = firstName;
        /** Setting the last name equal to the last name of the person in the database */
        this.lastName = lastName;
        /** Setting the gender equal to the gender of the person in the database */
        this.gender = gender;
        /** Setting the fatherID equal to the fatherID of the person in the database */
        this.fatherID = fatherID;
        /** Setting the motherID equal to the motherID of the person in the database */
        this.motherID = motherID;
        /** Setting the spouseID equal to the spouseID of the person in the database */
        this.spouseID = spouseID;
        /** True or False string if person is returned or not */
        this.success = success;
    }

    public PersonIDResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
