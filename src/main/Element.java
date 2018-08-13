package main;

public class Element {
    private String type;
    private int location;

    public Element(String type, int location){
        setType(type);
        setLocation(location); //0 is earth, 1 is mars
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
