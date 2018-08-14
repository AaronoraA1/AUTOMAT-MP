package main;


import java.util.ArrayList;

public class State {
    private ArrayList<Element> earthElements = new ArrayList<Element>();
    private ArrayList<Element> marsElements = new ArrayList<Element>();

    public State(ArrayList<Element> e,ArrayList<Element> m ){
        earthElements = e;
        marsElements = m;
    }


    public ArrayList<Element> getEarthElements() {
        return earthElements;
    }

    public void setEarthElements(ArrayList<Element> earthElements) {
        this.earthElements = earthElements;
    }



    public ArrayList<Element> getMarsElements() {
        return marsElements;
    }

    public void setMarsElements(ArrayList<Element> marsElements) {
        this.marsElements = marsElements;
    }
}
