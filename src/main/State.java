package main;


import java.util.ArrayList;

public class State {


    private int turn;
    private ArrayList<Element> earthElements = new ArrayList<Element>();
    private ArrayList<Element> marsElements = new ArrayList<Element>();

    public State(ArrayList<Element> e,ArrayList<Element> m, int turn){
        earthElements = e;
        marsElements = m;
        this.turn = turn;
    }

    public State(){
        earthElements = null;
        marsElements = null;
        turn = 0;

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

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void printState(){
        System.out.println("EARTH ELEMENTS : ");
        for(int i = 0; i < earthElements.size(); i++){
            System.out.println(earthElements.get(i).getType());

        }

        System.out.println("MARS ELEMENTS : ");
        for(int i = 0; i < marsElements.size(); i++){
            System.out.println(marsElements.get(i).getType());

        }
    }
}
