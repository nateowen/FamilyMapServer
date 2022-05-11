package Request;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * This class will handle requests to load a certain User's database
 * and will clear the current database and return the specified User's family tree.
 * */
public class LoadRequest {
    /** User object used to verify identity */
    private User[] users;
    /** Person object used to return the family tree generations and all persons in the tree */
    private Person[] persons;
    /** Event object used to return all the events in the User's family tree */
    private Event[] events;

    public LoadRequest() { }

    /** LoadRequest Constructor used to create an Object when Client is wanting to load a certain User's tree
     *
     * @param users User object used to match the User in the database with the request Load User info
     * @param persons Person object used to get the family tree for the specific User requested
     * @param events Event object used to load all the events associated with that User and their family tree
     * */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events =  events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
