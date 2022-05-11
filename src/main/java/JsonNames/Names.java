package JsonNames;

public class Names {
    private String[] data;

    public Names(String[] namesList)  {
        this.data = namesList;
    }

    public String[] getNamesList() {
        return this.data;
    }

    public void setNamesList(String[] namesList) {
        this.data = namesList;
    }
}
