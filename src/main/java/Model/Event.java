package Model;

import java.util.Objects;

/**
 * This class will create new events and associate them to a Person and User based on their username
 * */
public class Event {

    /** Username of the User or Person that this event is associated to */
    private String associatedUsername;
    /** Creates a unique eventID that can help identify what event this is */
    private String eventID;
    /** personID of the person this event is associated with */
    private String personID;
    /** The latitude of where the event occurred */
    private float latitude;
    /** The longitude of where the event occurred */
    private float longitude;
    /** The name of the country where the event occurred */
    private String country;
    /** The name of the city where the event occurred */
    private String city;
    /** This will be the identifier of what kind of event it was (i.e. christening, baptism, death, etc.) */
    private String eventType;
    /** The year that the event occurred */
    private int year;

    /** Event constructor to create a new Event object and associate it to a Person or User
     *
     * @param personID the event's personID to whom this event is associated
     * @param eventID event's unique ID
     * @param associatedUsername the event's username to whom this event is associated
     * @param latitude event's latitude at location
     * @param longitude event's longitude at location
     * @param country country where event occurred
     * @param city city where event occurred
     * @param eventType specifies what kind of event it was (christening, marriage, baptism, death, etc.)
     * @param year year the event occurred
     *
     * */
    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year) {

        /** Sets the eventID in database equal to this eventID */
        this.eventID = eventID;
        /** Associates the username of the person or user in database to this event */
        this.associatedUsername = associatedUsername;
        /** Sets the personID from the database equal to this personID */
        this.personID = personID;
        /** Sets the latitude in database equal to this latitude */
        this.latitude = latitude;
        /** Sets the longitude in database equal to this longitude */
        this.longitude = longitude;
        /** Sets the country in database equal to this country */
        this.country = country;
        /** Sets the city in database equal to this city */
        this.city = city;
        /** Sets the event type in database equal to this event type */
        this.eventType = eventType;
        /** Sets the year in database equal to this year */
        this.year = year;
    }

    /** Extra constructor for Event */
    public Event() { }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Event oEvent = (Event) obj;
        return Objects.equals(eventID, oEvent.eventID) && Objects.equals(associatedUsername, oEvent.associatedUsername) &&
                Objects.equals(personID, oEvent.personID) && Objects.equals(latitude, oEvent.latitude) && Objects.equals(longitude, oEvent.longitude) &&
                Objects.equals(country, oEvent.country) && Objects.equals(city, oEvent.city) && Objects.equals(eventType, oEvent.eventType) &&
                Objects.equals(year, oEvent.year);
    }
}
