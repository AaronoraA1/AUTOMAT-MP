package main;


import java.util.ArrayList;

public class State {


    private int turn;
    private ArrayList<String> earthElements = new ArrayList<String>();
    private ArrayList<String> marsElements = new ArrayList<String>();

    public State(ArrayList<String> e,ArrayList<String> m, int turn){
        earthElements = e;
        marsElements = m;
        this.turn = turn;
    }

    public boolean checkIfStateIsWin(){
        if (earthElements.isEmpty() && marsElements.size() == 5)
            return true;
        else
            return false;
    }
	
	public State moveRocket(String possibleMove){
        int tempturn;
        ArrayList<String> tempEarth = new ArrayList<String>();
        ArrayList<String> tempMars = new ArrayList<String>();

        if (turn == 0)
            tempturn = 1;
        else
            tempturn = 0;

        copyArray(marsElements, tempMars);
        copyArray(earthElements, tempEarth);

        for (int i = 0; i < possibleMove.length(); i++) {
            String item = possibleMove.substring(i, i + 1);
            if (turn==0) {
                if (tempEarth.remove(item))
                    tempMars.add(item);
                else
                    return null;
            }
            else {
                if (tempMars.remove(item))
                    tempEarth.add(item);
                else
                    return null;
            }
        }

        return new State(tempEarth, tempMars, tempturn);
    }
	
    public boolean checkIfPresentEarth(String str) {

        for(int i=0; i<getEarthElements().size(); i++){
            if(getEarthElements().get(i).equalsIgnoreCase(str))
                return true;
        }

        return false;
    }

    public boolean checkIfPresentMars(String str) {

        for(int i=0; i<getMarsElements().size(); i++){
            if(getMarsElements().get(i).equalsIgnoreCase(str))
                return true;
        }
        return false;
    }

    public boolean checkAllowedTravel()
    {
        boolean valid = true;

        if(((checkIfPresentEarth("H") || checkIfPresentEarth("P")) && checkIfPresentEarth("L") && turn ==1) ||
                ((checkIfPresentEarth("H") || checkIfPresentEarth("P")) && checkIfPresentEarth("L") && turn ==1 && checkIfPresentEarth("C")) ||
                ((checkIfPresentEarth("H") || checkIfPresentEarth("P")) && checkIfPresentEarth("L") && turn ==1 && checkIfPresentEarth("G"))){
//            System.out.println("                             hl earth");
            valid = false;
        }
        else if(((checkIfPresentEarth("H") || checkIfPresentEarth("P")) && checkIfPresentEarth("C")  && turn ==1) ||
                ((checkIfPresentEarth("H") || checkIfPresentEarth("P")) && checkIfPresentEarth("L") && turn ==1 && checkIfPresentEarth("L")) ||
                ((checkIfPresentEarth("H") || checkIfPresentEarth("P")) && checkIfPresentEarth("C")  && turn ==1 && checkIfPresentEarth("G"))){
//            System.out.println("                             hc earth");

            valid = false;
        }
        else if((checkIfPresentEarth("L") && checkIfPresentEarth("C") && turn ==1) ||
                (checkIfPresentEarth("L") && checkIfPresentEarth("C") && turn ==1 && checkIfPresentEarth("H")) ||
                (checkIfPresentEarth("L") && checkIfPresentEarth("C") && turn ==1 && checkIfPresentEarth("G"))){
//            System.out.println("                             lc earth");

            valid = false;
        }
        else if((checkIfPresentEarth("G") && checkIfPresentEarth("C") && turn ==1) ||
                (checkIfPresentEarth("G") && checkIfPresentEarth("C") && turn ==1 && checkIfPresentEarth("H")) ||
                (checkIfPresentEarth("G") && checkIfPresentEarth("C") && turn ==1 && checkIfPresentEarth("P")) ||
                (checkIfPresentEarth("G") && checkIfPresentEarth("C") && turn ==1 && checkIfPresentEarth("L"))){
//            System.out.println("                             gc earth");

            valid = false;
        }

        //--------------------------------------------------G-------------------------------------------------
        else if(((checkIfPresentMars("H") || checkIfPresentMars("P")) && checkIfPresentMars("L") && turn ==0) ||
                ((checkIfPresentMars("H") || checkIfPresentMars("P")) && checkIfPresentMars("L") && turn ==0 && checkIfPresentMars("C")) ||
                ((checkIfPresentMars("H") || checkIfPresentMars("P")) && checkIfPresentMars("L") && turn ==0 && checkIfPresentMars("G"))){
//            System.out.println("                             hl mars");

            valid = false;
        }
        else if(((checkIfPresentMars("H") || checkIfPresentMars("P")) && checkIfPresentMars("C")  && turn ==0) ||
                ((checkIfPresentMars("H") || checkIfPresentMars("P")) && checkIfPresentMars("L") && turn ==0 && checkIfPresentMars("L")) ||
                ((checkIfPresentMars("H") || checkIfPresentMars("P")) && checkIfPresentMars("C")  && turn ==0 && checkIfPresentMars("G"))){
//            System.out.println("                             hc mars");

            valid = false;
        }
        else if((checkIfPresentMars("L") && checkIfPresentMars("C") && turn ==0) ||
                (checkIfPresentMars("L") && checkIfPresentMars("C") && turn ==0 && checkIfPresentMars("H")) ||
                (checkIfPresentMars("L") && checkIfPresentMars("C") && turn ==0 && checkIfPresentMars("G"))){
//            System.out.println("                             lc mars");

            valid = false;
        }
        else if((checkIfPresentMars("G") && checkIfPresentMars("C") && turn ==0) ||
                (checkIfPresentMars("G") && checkIfPresentMars("C") && turn ==0 && checkIfPresentMars("H")) ||
                (checkIfPresentMars("G") && checkIfPresentMars("C") && turn ==0 && checkIfPresentMars("P")) ||
                (checkIfPresentMars("G") && checkIfPresentMars("C") && turn ==0 && checkIfPresentMars("L"))){
//            System.out.println("                             gc mars");

            valid = false;
        }
//        System.out.println("                                  valid : "+valid);

        return valid;
    }

    
    public boolean compareElements(State state)
    {
        ArrayList<String> tempState;

        //turn
        if (state.getTurn() != turn)
            return false;

        //earth
        tempState = state.getEarthElements();
        for (String element : earthElements)
        {	//array of strings tmp
            if (!tempState.contains(element))
                return false;
        }

        //mars
        tempState = state.getMarsElements();
        for (String element : marsElements)
        {
            if (!tempState.contains(element))
                return false;
        }

        return true;
    }
	
	private void copyArray(ArrayList<String> src, ArrayList<String> dest)
    {
        for(int i=0; i<src.size(); i++){
            dest.add(src.get(i));
        }
    }

    public ArrayList<String> getEarthElements() { return earthElements; }

    public void setEarthElements(ArrayList<String> earthElements) {
        this.earthElements = earthElements;
    }

    public ArrayList<String> getMarsElements() {
        return marsElements;
    }

    public void setMarsElements(ArrayList<String> marsElements) {
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
            System.out.println(earthElements.get(i));

        }

        System.out.println("MARS ELEMENTS : ");
        for(int i = 0; i < marsElements.size(); i++){
            System.out.println(marsElements.get(i));

        }
    }
}
