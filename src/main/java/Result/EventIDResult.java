package Result;

/**
 * This Class will return the JSON String Result of the Client's request for a specific Event.
 * */
public class EventIDResult {

    public EventIDResult() { }

    /** Username associated to requested Event object */
    private String associatedUsername;
    /** EventID associated to requested Event object */
    private String eventID;
    /** EventID associated to requested Event object */
    private String personID;
    /** latitude associated to requested Event object */
    private float latitude;
    /** longitude associated to requested Event object */
    private float longitude;
    /** country of requested Event object */
    private String country;
    /** city of requested Event object */
    private String city;
    /** event type of request Event object */
    private String eventType;
    /** year requested Event occurred */
    private int year;
    /** Tells us whether the Client's request was successful or not */
    private boolean success;

    private String message;

    /**
     * This function will return the JSON String which will then be sent to the Client
     * whether there request was successful or not
     *
     * @param associatedUsername Username associated to requested Event
     * @param eventID eventID of the Event request
     * @param personID personID of person associated to the Event requested
     * @param latitude latitude of the location of event
     * @param longitude longitude of the location of event
     * @param country country of the location of event
     * @param city city of the location of event
     * @param eventType specifies what kind of event it was (death, baptism, birth, etc.)
     * @param year Year the event occurred
     * @param success Returns true or false depending on outcome of request
     *
     * @return Returns the JSON String result if successful with the Event Object info
     * */
    public EventIDResult(String associatedUsername, String eventID, String personID, float latitude, float longitude,
                         String country, String city, String eventType, int year, boolean success) {

        /** Setting the requested username equal to the username in the database */
        this.associatedUsername = associatedUsername;
        /** Setting the requested eventID equal to the eventID in the database */
        this.eventID = eventID;
        /** Setting the requested personID equal to the personID in the database */
        this.personID = personID;
        /** Setting the latitude equal to the latitude of the event in the database */
        this.latitude = latitude;
        /** Setting the longitude equal to the longitude of the event in the database */
        this.longitude = longitude;
        /** Setting the country equal to the country of the event in the database  */
        this.country = country;
        /** Setting the city equal to the city of the event in the database */
        this.city = city;
        /** Setting the event type equal to the event type of the event in the database */
        this.eventType = eventType;
        /** Setting the year equal to the year of the event in the database */
        this.year = year;
        /** True or False string if event is returned or not */
        this.success = success;
    }

    public EventIDResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
