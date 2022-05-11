package Model;

/**
 * This class will create a person and add them to a family tree which is associated to the current User.
 * Handles the generations of a family tree and gives each person their own personal ID.
 * */
public class Person {

    /** Username of the current User which is the child, grandchild, etc. of the new person */
    private String associatedUsername;
    /** This will create a personID for this person and associate it to the User based on their username */
    private String personID;
    /** Creates a first name for the person in the family tree. */
    private String firstName;
    /** Creates a last name for the person in the family tree. */
    private String lastName;
    /** Assigns a gender for the person in the family tree. If Father (M), if mother (F) */
    private String gender;
    /** Gets or assigns ID to their father */
    private String fatherID;
    /** Gets or assigns ID to their mother */
    private String motherID;
    /** Gets or assigns the ID to their spouse */
    private String spouseID;

    /** Person constructor in order to create new Person objects and add them to the family tree
     *
     * @param personID the person's personID
     * @param associatedUsername the person's associated username
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @param gender the person's gender
     * @param fatherID the person's fatherID
     * @param motherID the person's motherID
     * @param spouseID the person's spouseID
     *
     * */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender,
                  String fatherID, String motherID, String spouseID) {

        /** Sets the personID in the database equal to the new Person object's ID */
        this.personID = personID;
        /** Get the username of the User whose family tree this person pertains to */
        this.associatedUsername = associatedUsername;
        /** Sets the first name in the database equal to the new Person object's first name */
        this.firstName = firstName;
        /** Sets the last name in the database equal to the new Person object's last name */
        this.lastName = lastName;
        /** Sets gender of the new Person object equal to the person in the database */
        this.gender = gender;
        /** Sets the fatherID from the database equal to this Person object's fatherID */
        this.fatherID = fatherID;
        /** Sets the motherID from the database equal to this Person object's motherID */
        this.motherID = motherID;
        /** Sets the spouseID from the database equal to this Person object's spouseID */
        this.spouseID = spouseID;
    }

    public Person() { }

    public String getPersonID() { return personID; }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Person) {
            Person oUser = (Person) obj;
            return oUser.getPersonID().equals(getPersonID()) && oUser.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oUser.getFirstName().equals(getFirstName()) && oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) && oUser.getFatherID().equals(getFatherID()) &&
                    oUser.getMotherID().equals(getMotherID()) && oUser.getSpouseID().equals(getSpouseID());
        }
        else {
            return false;
        }
    }
}
