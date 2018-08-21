package main;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.w3c.dom.NodeList;

import java.lang.reflect.Array;
import java.util.*;

public class Controller {

    @FXML
    Button earthHuman1Button, earthHuman2Button, earthCowButton, earthLionButton, earthGrainButton;
    @FXML
    ImageView earthHuman1Pic, earthHuman2Pic, earthCowPic, earthLionPic, earthGrainPic;

    @FXML
    Button marsHuman1Button, marsHuman2Button, marsCowButton, marsLionButton,  marsGrainButton;
    @FXML
    ImageView marsHuman1Pic, marsHuman2Pic, marsCowPic, marsLionPic, marsGrainPic;

    @FXML
    ImageView scientistSpaceship;

    @FXML
    Pane previousStatePane, currentStatePane;

    @FXML
    ScrollPane solutionPane;
    @FXML
    ScrollPane hintPane;
    @FXML
    Button hintButton;

    @FXML
    Label alertsLabel;
    @FXML
    Button launchButton;

    public int turn = 0; // CURRENT LOCATION : 0 = EARTH, 1 = MARS
    public int BFSturn = 0; // CURRENT LOCATION : 0 = EARTH, 1 = MARS
    public Element[] rocketElements = new Element[2]; // SCIENTIST ASSUMED TO BE ONBOARD ALREADY
    public Element h1, h2, l, c, g; // ALL ELEMENTS
    public ArrayList<State> finalStates = new ArrayList<State>();
    public ArrayList<Element> earthStates = new ArrayList<Element>();
    public ArrayList<Element> marsStates = new ArrayList<Element>();
    public ArrayList<String> removeListMars = new ArrayList<String>();
    public ArrayList<String> removeListEarth = new ArrayList<String>();


    /*-----------------------BFS ELEMENTS-----------------------------------*/
    public ArrayList<SearchNode> queueList = new ArrayList<SearchNode>();
    public ArrayList<SearchNode> nodeList = new ArrayList<SearchNode>();
    public ArrayList<SearchNode> solutionList;
    //public ArrayList<SearchNode>
    public String[] possibleMoves = {"HP", "HL", "HC", "HG", "LC", "LG", "CG", "PL", "PC", "PG", "H", "P", "C", "L", "G"};


    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void initialize(){
        h1 = new Element("Human 1", 0); // 0 = EARTH, 1 = MARS
        h2 = new Element("Human 2", 0);
        l = new Element("Lion", 0);
        c = new Element("Cow", 0);
        g = new Element("Grain", 0);

        marsHuman1Pic.setOpacity(0.2);       //DISABLE ALL MARS ELEMENTS
        marsHuman1Button.setDisable(true);
        marsHuman2Pic.setOpacity(0.2);
        marsHuman2Button.setDisable(true);
        marsCowPic.setOpacity(0.2);
        marsLionButton.setDisable(true);
        marsLionPic.setOpacity(0.2);
        marsCowButton.setDisable(true);
        marsGrainPic.setOpacity(0.2);
        marsGrainButton.setDisable(true);

        initButtons();
        initFinalStates();
    }


    /////////////////////////////////BFS FUNCTIONS////////////////////////////////////////
    public void BFSSearch(){


        boolean winner = false;
        System.out.println("SEARCHING FOR SOLUTION");
        reset();
        turn = 0;
        marsStates.clear();
        earthStates.clear();


        solutionList = new ArrayList<SearchNode>();

        SearchNode rootNode = new SearchNode(finalStates.get(0));
        nodeList.add(rootNode);

        queueList.add(rootNode);

        System.out.println("QUEUE SIZE : " + queueList.size());
        int i =0;
        while(!queueList.isEmpty()){
            System.out.println("-------------------------------------------------------NEXT DEPTH-------------------------------------------------------");



            SearchNode currNode = queueList.remove(0);
            System.out.println("QUEUE SIZE : " + queueList.size());
            System.out.println("DEPTH: " + currNode.getDepth() + " STATES: ");
            currNode.getState().printState();


            System.out.println("WINNING STATES SO FAR: " + solutionList.size());
            if(solutionList.size()>0) {
                printParents();
            }


            for(String x : possibleMoves){
                System.out.println("------------------------------NEXT POSSIBLE MOVE--------------------------------------");

                System.out.println("CHECKING MOVE : " + x);
                State nextState = new State();
                nextState = isMovePossible(x, currNode.getState());

                if(nextState !=null && checkState(nextState)){
                    System.out.println("STATE IS ACCEPTABLE");
                    SearchNode child = new SearchNode(nextState);

                    child.setDepth(currNode.getDepth() + 1);
                    child.setParent(currNode);

                    //check if ancestor
                    if(!isAncestor(child, nodeList)) {
                        System.out.println("ANCESTOR IS ACCEPTABLE");
                        currNode.getChildren().add(child);
                        i++;
                        System.out.println("NUMBER OF NODES " + i);
                        nodeList.add(child);

                        if (!isAncestor(child, solutionList) && checkIfStateIsWin(child)) {
                            System.out.println("Solution has been found");
                            if(!isAncestor(child, solutionList)) {
                                solutionList.add(child);
                            }
                            winner = true;

                        } else {
                            queueList.add(child);
                            System.out.println("Adding State");
                        }

                    }
                }

            }
            i++;
        }
    }

    public boolean checkShort(SearchNode n) {
        int count = 1;
        SearchNode parent = n.getParent();
        while (parent != null) {
            parent = parent.getParent();
            count++;
            }
        if(count == 8){
             return true;
        }
        else
            return false;
    }

    public void printArrayList(List<String> str) {

        for(int k=0; k<str.size(); k++){
            System.out.println("                    "+str.get(k));
        }

    }

    public boolean isAncestor(SearchNode child, ArrayList<SearchNode> list) {
        boolean lock1 = false;
        boolean lock2 = false;
        boolean lock3 = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getState().getEarthElements().equals(child.getState().getEarthElements())) {
                lock1 = true;

            }
            if (list.get(i).getState().getMarsElements().equals(child.getState().getMarsElements())) {
                lock2 = true;
            }

            if (list.get(i).getState().getTurn() == child.getState().getTurn()) {
                lock3 = true;
            } else
                lock3 = false;


        }
        if(lock1 && lock2){
            return true;
        }
        else
            return false;
    }

    public boolean checkIfStateIsWin(SearchNode c){
        if(c.getState().getEarthElements().isEmpty()){
            return true;
        }
        return false;
    }

    public void printParents(){
        ArrayList<SearchNode> tempList = new ArrayList<SearchNode>();

        System.out.println("_------------------PRINTING SOLUTION TRACE----------------------");
        for(int i = 0; i<solutionList.size(); i++){
            System.out.println("----------------------------NEXT SOLUTION------------------------------------");
                tempList.clear();
                SearchNode currNode = solutionList.get(i);
                tempList.add(currNode);
                SearchNode parent = currNode.getParent();
                while(parent != null){
                    tempList.add(parent);
                    parent = parent.getParent();
                }
            Collections.reverse(tempList);
            for(int k = 0; k < tempList.size(); k++){
                System.out.println("----------------------------------PARENT-------------------------------------");
                tempList.get(k).getState().printState();
            }
        }

    }


    public boolean checkIfPresentEarth(State s, String str) {

        for(int i=0; i<s.getEarthElements().size(); i++){
            if(s.getEarthElements().get(i).getType().equalsIgnoreCase(str))
                return true;
        }

        return false;
    }

    public boolean checkIfPresentMars(State s, String str) {

        for(int i=0; i<s.getMarsElements().size(); i++){
            if(s.getMarsElements().get(i).getType().equalsIgnoreCase(str))
                return true;
        }
        return false;
    }


    public boolean checkState(State s){
        System.out.println("NEXT STATE: "  );
        s.printState();
        System.out.println("                                  BFSturn : "+BFSturn);
        boolean valid = true;

        if(((checkIfPresentEarth(s, "Human 1") || checkIfPresentEarth(s, "Human 2")) && checkIfPresentEarth(s, "Lion") && BFSturn ==1) ||
           ((checkIfPresentEarth(s, "Human 1") || checkIfPresentEarth(s, "Human 2")) && checkIfPresentEarth(s, "Lion") && BFSturn ==1 && checkIfPresentEarth(s, "Cow")) ||
           ((checkIfPresentEarth(s, "Human 1") || checkIfPresentEarth(s, "Human 2")) && checkIfPresentEarth(s, "Lion") && BFSturn ==1 && checkIfPresentEarth(s, "Grain"))){
            System.out.println("                             hl earth");
            valid = false;
        }
        else if(((checkIfPresentEarth(s, "Human 1") || checkIfPresentEarth(s, "Human 2")) && checkIfPresentEarth(s, "Cow")  && BFSturn ==1) ||
                ((checkIfPresentEarth(s, "Human 1") || checkIfPresentEarth(s, "Human 2")) && checkIfPresentEarth(s, "Lion") && BFSturn ==1 && checkIfPresentEarth(s, "Lion")) ||
                ((checkIfPresentEarth(s, "Human 1") || checkIfPresentEarth(s, "Human 2")) && checkIfPresentEarth(s, "Cow")  && BFSturn ==1 && checkIfPresentEarth(s, "Grain"))){
            System.out.println("                             hc earth");

            valid = false;
        }
        else if((checkIfPresentEarth(s, "Lion") && checkIfPresentEarth(s, "Cow") && BFSturn ==1) ||
                (checkIfPresentEarth(s, "Lion") && checkIfPresentEarth(s, "Cow") && BFSturn ==1 && checkIfPresentEarth(s, "Human 1")) ||
                (checkIfPresentEarth(s, "Lion") && checkIfPresentEarth(s, "Cow") && BFSturn ==1 && checkIfPresentEarth(s, "Grain"))){
            System.out.println("                             lc earth");

            valid = false;
        }
        else if((checkIfPresentEarth(s, "Grain") && checkIfPresentEarth(s, "Cow") && BFSturn ==1) ||
                (checkIfPresentEarth(s, "Grain") && checkIfPresentEarth(s, "Cow") && BFSturn ==1 && checkIfPresentEarth(s, "Human 1")) ||
                (checkIfPresentEarth(s, "Grain") && checkIfPresentEarth(s, "Cow") && BFSturn ==1 && checkIfPresentEarth(s, "Human 2")) ||
                (checkIfPresentEarth(s, "Grain") && checkIfPresentEarth(s, "Cow") && BFSturn ==1 && checkIfPresentEarth(s, "Lion"))){
            System.out.println("                             gc earth");

            valid = false;
        }

        //--------------------------------------------------GRAIN-------------------------------------------------
        else if(((checkIfPresentMars(s, "Human 1") || checkIfPresentMars(s, "Human 2")) && checkIfPresentMars(s, "Lion") && BFSturn ==0) ||
                ((checkIfPresentMars(s, "Human 1") || checkIfPresentMars(s, "Human 2")) && checkIfPresentMars(s, "Lion") && BFSturn ==0 && checkIfPresentMars(s, "Cow")) ||
                ((checkIfPresentMars(s, "Human 1") || checkIfPresentMars(s, "Human 2")) && checkIfPresentMars(s, "Lion") && BFSturn ==0 && checkIfPresentMars(s, "Grain"))){
            System.out.println("                             hl mars");

            valid = false;
        }
        else if(((checkIfPresentMars(s, "Human 1") || checkIfPresentMars(s, "Human 2")) && checkIfPresentMars(s, "Cow")  && BFSturn ==0) ||
                ((checkIfPresentMars(s, "Human 1") || checkIfPresentMars(s, "Human 2")) && checkIfPresentMars(s, "Lion") && BFSturn ==0 && checkIfPresentMars(s, "Lion")) ||
                ((checkIfPresentMars(s, "Human 1") || checkIfPresentMars(s, "Human 2")) && checkIfPresentMars(s, "Cow")  && BFSturn ==0 && checkIfPresentMars(s, "Grain"))){
                        System.out.println("                             hc mars");

            valid = false;
        }
        else if((checkIfPresentMars(s, "Lion") && checkIfPresentMars(s, "Cow") && BFSturn ==0) ||
                (checkIfPresentMars(s, "Lion") && checkIfPresentMars(s, "Cow") && BFSturn ==0 && checkIfPresentMars(s, "Human 1")) ||
                (checkIfPresentMars(s, "Lion") && checkIfPresentMars(s, "Cow") && BFSturn ==0 && checkIfPresentMars(s, "Grain"))){
            System.out.println("                             lc mars");

            valid = false;
        }
        else if((checkIfPresentMars(s, "Grain") && checkIfPresentMars(s, "Cow") && BFSturn ==0) ||
                (checkIfPresentMars(s, "Grain") && checkIfPresentMars(s, "Cow") && BFSturn ==0 && checkIfPresentMars(s, "Human 1")) ||
                (checkIfPresentMars(s, "Grain") && checkIfPresentMars(s, "Cow") && BFSturn ==0 && checkIfPresentMars(s, "Human 2")) ||
                (checkIfPresentMars(s, "Grain") && checkIfPresentMars(s, "Cow") && BFSturn ==0 && checkIfPresentMars(s, "Lion"))){
            System.out.println("                             gc mars");

            valid = false;
        }
        System.out.println("                                  valid : "+valid);

        return valid;

    }

    public State isMovePossible(String x, State s){
        Element firstElement = null;
        Element secondElement = null;

        boolean lock1=false;
        boolean lock2=false;

        ArrayList<Element> tempEarthList = new ArrayList<Element>();
        ArrayList<Element> tempMarsList = new ArrayList<Element>();

        for(int i=0; i< s.getEarthElements().size(); i++){
            tempEarthList.add(s.getEarthElements().get(i));
        }

        for(int i=0; i< s.getMarsElements().size(); i++){
            tempMarsList.add(s.getMarsElements().get(i));
        }

        State tempState = new State(tempEarthList, tempMarsList, s.getTurn());

        int tempTurn = s.getTurn();


        for(int i = 0; i < 6; i++){
            if(x.charAt(0) == 'H'){
                firstElement = new Element("Human 1", 0);
            }
            else if(x.charAt(0) == 'P'){
                firstElement = new Element("Human 2", 0);
            }
            else if(x.charAt(0) == 'L'){
                firstElement = new Element("Lion", 0);
            }
            else if(x.charAt(0) == 'C'){
                firstElement = new Element("Cow", 0);
            }
            else if(x.charAt(0) == 'G'){
                firstElement = new Element("Grain", 0);
            }
        }
        if(x.length() > 1) {
            for (int i = 0; i < 6; i++) {
                if (x.charAt(1) == 'H') {
                    secondElement = new Element("Human 1", 0);
                } else if (x.charAt(1) == 'P') {
                    secondElement = new Element("Human 2", 0);
                } else if (x.charAt(1) == 'L') {
                    secondElement = new Element("Lion", 0);
                } else if (x.charAt(1) == 'C') {
                    secondElement = new Element("Cow", 0);
                } else if (x.charAt(1) == 'G') {
                    secondElement = new Element("Grain", 0);
                }
            }
        }
        else
            secondElement = new Element("Nooo", 0);

        System.out.println("isMovePossible FIRST ELEMENT: " + firstElement.getType());
        System.out.println("isMovePossible SECOND ELEMENT: " + secondElement.getType());

        if(tempTurn ==0){
            tempTurn = 1;

            for(int i = 0; i<tempState.getEarthElements().size(); i++) {
                if (firstElement.getType() == tempState.getEarthElements().get(i).getType()) {
                    Element e = tempState.getEarthElements().get(i);
                    tempState.getMarsElements().add(e);
                    System.out.println("isMovePossible - EARTH -> MARS: " + e.getType());
                    System.out.println("REMOVE INDEX: " + i);
                    removeListEarth.add(tempState.getEarthElements().get(i).getType());
                    lock1 = true;
                    break;
                } else
                    lock1 = false;
            }


            if(secondElement.getType().compareToIgnoreCase("mgabobo") != 0) {
                for (int i = 0; i < tempState.getEarthElements().size(); i++) {
                    if (secondElement.getType() == tempState.getEarthElements().get(i).getType()) {
                        Element e = tempState.getEarthElements().get(i);
                        tempState.getMarsElements().add(e);
                        System.out.println("isMovePossible - EARTH -> MARS: " + e.getType());
                        System.out.println("REMOVE INDEX: " + i);
                        removeListEarth.add(tempState.getEarthElements().get(i).getType());
                        lock2 = true;
                        break;

                    } else
                        lock2 = false;
                }
            }
            for(int i = 0; i < removeListEarth.size(); i++){
                int index = getEarthElementFromType(removeListEarth.get(i), tempState);
                if (index!=-1){
                    tempState.getEarthElements().remove(index);
                }
            }
            removeListEarth.clear();
        }
        else {
            tempTurn = 0;


            for(int i = 0; i<tempState.getMarsElements().size(); i++) {
                if (firstElement.getType() == tempState.getMarsElements().get(i).getType()) {
                    Element e = tempState.getMarsElements().get(i);
                    tempState.getEarthElements().add(e);
                    System.out.println("isMovePossible - MARS -> EARTH: " + e.getType());
                    removeListMars.add(tempState.getMarsElements().get(i).getType());
                    lock1 = true;
                    break;
                } else
                    lock1 = false;

            }

            if(secondElement != null) {
                for (int i = 0; i < tempState.getMarsElements().size(); i++) {
                    if (secondElement.getType() == tempState.getMarsElements().get(i).getType()) {
                        Element e = tempState.getMarsElements().get(i);
                        tempState.getEarthElements().add(e);
                        System.out.println("isMovePossible - MARS -> EARTH: " + e.getType());
                        removeListMars.add(tempState.getMarsElements().get(i).getType());
                        lock2 = true;
                        break;
                    } else
                        lock2 = false;
                }
            }

            for(int i = 0; i < removeListMars.size(); i++){
                int index = getMarsElementFromType(removeListMars.get(i), tempState);
                if (index!=-1){
                    tempState.getMarsElements().remove(index);
                }
            }

            removeListMars.clear();
        }

        BFSturn = tempTurn;

        if(!lock1 && !lock2)
            return null;
        else
            return new State(tempState.getEarthElements(), tempState.getMarsElements(), tempTurn);
    }

    public int getEarthElementFromType(String str, State s){
        for(int i=0; i<s.getEarthElements().size(); i++){
            if (s.getEarthElements().get(i).getType().equalsIgnoreCase(str)){
                return i;
            }
        }

        return -1;

    }

    public int getMarsElementFromType(String str, State s){
        for(int i=0; i<s.getMarsElements().size(); i++){
            if (s.getMarsElements().get(i).getType().equalsIgnoreCase(str)){
                return i;
            }
        }

        return -1;

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    private void initButtons() {
            earthHuman1Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(h1);
                }
            });
            earthHuman2Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(h2);
                }
            });
            earthCowButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(c);
                }
            });
            earthLionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                   load(l);
                }
            });
            earthGrainButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(g);
                }
            });

            marsHuman1Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(h1);
                }
            });
            marsHuman2Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(h2);
                }
            });
            marsLionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(l);
                }
            });
            marsCowButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(c);
                }
            });
            marsGrainButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    load(g);
                }
            });
    }
    private void initFinalStates(){
        ArrayList<Element> es = new ArrayList<Element>();
        ArrayList<Element> ms = new ArrayList<Element>();

        System.out.println("INITIALIZE");

        es.add(h1);
        es.add(h2);
        es.add(l);
        es.add(c);
        es.add(g);
        finalStates.add(new State(es,ms, 0 ));

        ArrayList<Element> es2 = new ArrayList<Element>();
        ArrayList<Element> ms2 = new ArrayList<Element>();

        es2.add(h1);
        es2.add(h2);
        es2.add(g);
        ms2.add(l);
        ms2.add(c);
        finalStates.add(new State(es2,ms2, 1));

        ArrayList<Element> es3 = new ArrayList<Element>();
        ArrayList<Element> ms3 = new ArrayList<Element>();

        es3.add(h1);
        es3.add(h2);
        es3.add(g);
        es3.add(l);
        ms3.add(c);
        finalStates.add(new State(es3, ms3, 0));


        ArrayList<Element> es4 = new ArrayList<Element>();
        ArrayList<Element> ms4 = new ArrayList<Element>();
        ms4.add(h1);
        ms4.add(h2);
        es4.add(g);
        es4.add(l);
        ms4.add(c);
        finalStates.add(new State(es4, ms4, 1));

        ArrayList<Element> es5 = new ArrayList<Element>();
        ArrayList<Element> ms5 = new ArrayList<Element>();
        ms5.add(h1);
        ms5.add(h2);
        es5.add(g);
        es5.add(l);
        es5.add(c);
        finalStates.add(new State(es5, ms5, 0));

        ArrayList<Element> es6 = new ArrayList<Element>();
        ArrayList<Element> ms6 = new ArrayList<Element>();
        ms6.add(h1);
        ms6.add(h2);
        ms6.add(g);
        es6.add(l);
        ms6.add(c);
        finalStates.add(new State(es6, ms6, 1));


        ArrayList<Element> es7 = new ArrayList<Element>();
        ArrayList<Element> ms7 = new ArrayList<Element>();
        ms7.add(h1);
        ms7.add(h2);
        ms7.add(g);
        es7.add(l);
        es7.add(c);
        finalStates.add(new State(es7, ms7, 0));

        ArrayList<Element> es8 = new ArrayList<Element>();
        ArrayList<Element> ms8 = new ArrayList<Element>();
        ms8.add(h1);
        ms8.add(h2);
        ms8.add(g);
        ms8.add(l);
        ms8.add(c);
        finalStates.add(new State(es8, ms8, 1));



    }

    public void load(Element e){
        if (isLoadValid(e)) {
            if (rocketElements[0] == null) {
                alertsLabel.setText(e.getType());
                rocketElements[0] = e;
            } else if (rocketElements[1] == null){
                alertsLabel.setText(rocketElements[0].getType()+ " and " + e.getType() );
                rocketElements[1] = e;
            } else {
                alertsLabel.setText("Rocket Full!!");
            }
        }
    }

    private boolean isLoadValid(Element e) {

        for(int i = 0; i < rocketElements.length; i++) {
            if (rocketElements[i] != null) {
                if (e.getType().equals("Human 1") && rocketElements[i].getType().equals("Human 1")) {
                    alertsLabel.setText("INVALID");
                    return false;
                } else if (e.getType().equals("Human 2") && rocketElements[i].getType().equals("Human 2")) {
                    alertsLabel.setText("INVALID");
                    return false;
                } else if (e.getType().equals("Lion") && rocketElements[i].getType().equals("Lion")) {
                    alertsLabel.setText("INVALID");
                    return false;
                } else if (e.getType().equals("Cow") && rocketElements[i].getType().equals("Cow")) {
                    alertsLabel.setText("INVALID");
                    return false;
                } else if (e.getType().equals("Grain") && rocketElements[i].getType().equals("Grain")) {
                    alertsLabel.setText("INVALID");
                    return false;
                }
            }
        }
        return true;
    }

    public void reset(){

        rocketElements[0] = null;
        rocketElements[1] = null;

        solutionPane.setContent(new Label(""));
        previousStatePane.getChildren().clear();
        currentStatePane.getChildren().clear();

        h1.setLocation(0);
        h2.setLocation(0);
        l.setLocation(0);
        g.setLocation(0);
        c.setLocation(0);

        if (turn == 1)
            moveRocketToEarth();

        earthHuman1Pic.setOpacity(1.0);      //ENABLE ALL EARTH ELEMENTS
        earthHuman1Button.setDisable(false);
        earthHuman2Pic.setOpacity(1.0);
        earthHuman2Button.setDisable(false);
        earthCowPic.setOpacity(1.0);
        earthCowButton.setDisable(false);
        earthLionPic.setOpacity(1.0);
        earthLionButton.setDisable(false);
        earthGrainPic.setOpacity(1.0);
        earthGrainButton.setDisable(false);

        marsHuman1Pic.setOpacity(0.2);       //DISABLE ALL MARS ELEMENTS
        marsHuman1Button.setDisable(true);
        marsHuman2Pic.setOpacity(0.2);
        marsHuman2Button.setDisable(true);
        marsCowPic.setOpacity(0.2);
        marsLionButton.setDisable(true);
        marsLionPic.setOpacity(0.2);
        marsCowButton.setDisable(true);
        marsGrainPic.setOpacity(0.2);
        marsGrainButton.setDisable(true);

    }

    public State getElements() {
        earthStates.clear();
        marsStates.clear();

        //human1
        if(h1.getLocation()==0){
            earthStates.add(h1);
        }
        else {
            marsStates.add(h1);
        }
        //human2
        if(h2.getLocation()==0){
            earthStates.add(h2);
        }
        else {
            marsStates.add(h2);
        }
        //lion
        if(l.getLocation()==0){
            earthStates.add(l);
        }
        else {
            marsStates.add(l);
        }
        //grain
        if(g.getLocation()==0){
            earthStates.add(g);
        }
        else {
            marsStates.add(g);
        }
        //cow
        if(c.getLocation()==0){
            earthStates.add(c);
        }
        else {
            marsStates.add(c);
        }
        return new State(earthStates, marsStates, turn);
    }

    public void launch() {

        solutionPane.setContent(new Label(""));
        previousStatePane.getChildren().clear();
        currentStatePane.getChildren().clear();

        displayPrevStatePane(getElements());
        if (turn == 0){
            turn = 1;
            disableTravellingElements();
            moveRocketToMars();

            if(checkElementsInBothPlanets()){
                rocketElements[0] = null;
                rocketElements[1] = null;

                if (checkWinningState()){
                    alertsLabel.setText("YOU WON!");
                }
            }
            else {
                alertsLabel.setText("GAME OVER!");
            }

        } else {
            turn = 0;
            disableTravellingElements();
            moveRocketToEarth();

            if(checkElementsInBothPlanets()){
                rocketElements[0] = null;
                rocketElements[1] = null;

                if (checkWinningState()){
                    alertsLabel.setText("YOU WON!");
                }
            }
            else {
                alertsLabel.setText("GAME OVER!");
            }
        }
        displayCurrentStatePane(getElements());
    }

    public void getSolution(){
        System.out.println("GETTING HINT");
        State currentState = getElements();

        int i;
        System.out.println(finalStates.size());
        for(i = 0; i<finalStates.size(); i++){
            boolean lock1 =false;
            boolean lock2 = false;
            if(i == 0){
                lock2 =true;
            }
            System.out.println("ITERATING " + i);

            System.out.println(finalStates.get(i).getEarthElements().size());
            System.out.println(finalStates.get(i).getMarsElements().size());

            if(currentState.getEarthElements().size() == finalStates.get(i).getEarthElements().size()) {
                for (int j = 0; j < currentState.getEarthElements().size(); j++) {
                    if (!finalStates.get(i).getEarthElements().contains(currentState.getEarthElements().get(j))) {
                        lock1 = false;
                        System.out.println("LOCK 1 FALSE");
                        break;
                    } else {
                        lock1 = true;
                        System.out.println("LOCK 1 TRUE");
                    }
                }
            }
            if(currentState.getMarsElements().size() == finalStates.get(i).getMarsElements().size()) {
                for (int j = 0; j < currentState.getMarsElements().size(); j++) {
                    if (!finalStates.get(i).getMarsElements().contains(currentState.getMarsElements().get(j))) {
                        lock2 = false;
                        System.out.println("LOCK 2 FALSE");
                        break;
                    } else {
                        lock2 = true;
                        System.out.println("LOCK 2 TRUE");
                    }
                }
            }
            if(lock1 && lock2){
                System.out.println("SHOWING LOCK");
                displaySolution(finalStates.get(i + 1));
                break;
            }
        }

    }


    public boolean checkElementsInBothPlanets(){
        boolean valid = true;

        if((h1.getLocation()==1 || h2.getLocation()==1) && l.getLocation() ==1 && turn==0){
            valid = false;
        }
        else if((h1.getLocation()==1 || h2.getLocation()==1) && c.getLocation() ==1 && turn==0){
            valid = false;
        }
        else if(l.getLocation() ==1 && c.getLocation()==1 && turn==0){
            valid = false;
        }
        else if(g.getLocation() ==1 && c.getLocation()==1 && turn==0){
            valid = false;
        }
        else if((h1.getLocation()==0 || h2.getLocation()==0) && l.getLocation() ==0 && turn==1){
            valid = false;
        }
        else if((h1.getLocation()==0 || h2.getLocation()==0) && c.getLocation() ==0 && turn==1){
            valid = false;
        }
        else if(l.getLocation() ==0 && c.getLocation()==0 && turn==1){
            valid = false;
        }
        else if(g.getLocation() ==0 && c.getLocation()==0 && turn==1){
            valid = false;
        }
        return valid;
    }

    public boolean checkWinningState() {
        return (h1.getLocation()==1  && h2.getLocation()==1 &&
                 l.getLocation()==1  &&  g.getLocation()==1 &&
                 c.getLocation()==1);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////// <-- GUI FUNCTIONS

    public void moveRocketToMars(){
        TranslateTransition transitn = new TranslateTransition(Duration.millis(4000), scientistSpaceship);
        TranslateTransition transitn2 = new TranslateTransition(Duration.millis(4000), launchButton);
        transitn.setByX(260);
        transitn2.setByX(260);
        transitn.setRate(4);
        transitn2.setRate(4);
        transitn.play();
        transitn2.play();
    }
    public void moveRocketToEarth(){
        TranslateTransition transitn = new TranslateTransition(Duration.millis(4000), scientistSpaceship);
        TranslateTransition transitn2 = new TranslateTransition(Duration.millis(4000), launchButton);
        transitn.setByX(-260);
        transitn2.setByX(-260);
        transitn.setRate(4);
        transitn2.setRate(4);
        transitn.play();
        transitn2.play();
    }

    public void displayPrevStatePane(State state){
        String s = "\nEARTH :\n";

        for(int i=0; i<state.getEarthElements().size(); i++){
            s+= ""+ state.getEarthElements().get(i).getType();
            s+= "\n";
        }

        s+= "\nMARS :\n";

        for(int i=0; i<state.getMarsElements().size(); i++){
            s+= ""+ state.getMarsElements().get(i).getType();
            s+= "\n";
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 12; -fx-text-alignment: center");
        previousStatePane.getChildren().add(label);
    }

    public void displayCurrentStatePane(State state){
        String s = "\nEARTH :\n";

        for(int i=0; i<state.getEarthElements().size(); i++){
            s+= ""+ state.getEarthElements().get(i).getType();
            s+= "\n";
        }

        s+= "\nMARS :\n";

        for(int i=0; i<state.getMarsElements().size(); i++){
            s+= ""+ state.getMarsElements().get(i).getType();
            s+= "\n";
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 12; -fx-text-alignment: center");
        currentStatePane.getChildren().add(label);
    }
    public void displaySolution(State state){
        String s = "\n EARTH : \n";

        for(int i=0; i<state.getEarthElements().size(); i++){
            s+= " "+ state.getEarthElements().get(i).getType();
            s+= "\n";
        }

        s+= "\n MARS : \n";

        for(int i=0; i<state.getMarsElements().size(); i++){
            s+= " "+ state.getMarsElements().get(i).getType();
            s+= "\n";
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 15;");
        solutionPane.setContent(label);
    }

    public void disableTravellingElements(){
        if (turn == 1) {
            for (int i = 0; i < rocketElements.length; i++) {
                if (rocketElements[i] != null) {
                    rocketElements[i].setLocation(1);
                    if (rocketElements[i].getType().equals("Human 1")) {
                        earthHuman1Pic.setOpacity(0.2);
                        earthHuman1Button.setDisable(true);
                        marsHuman1Pic.setOpacity(1.0);
                        marsHuman1Button.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Human 2")) {
                        earthHuman2Pic.setOpacity(0.2);
                        earthHuman2Button.setDisable(true);
                        marsHuman2Pic.setOpacity(1.0);
                        marsHuman2Button.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Lion")) {
                        earthLionPic.setOpacity(0.2);
                        earthLionButton.setDisable(true);
                        marsLionPic.setOpacity(1.0);
                        marsLionButton.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Cow")) {
                        earthCowPic.setOpacity(0.2);
                        earthCowButton.setDisable(true);
                        marsCowPic.setOpacity(1.0);
                        marsCowButton.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Grain")) {
                        earthGrainPic.setOpacity(0.2);
                        earthGrainButton.setDisable(true);
                        marsGrainPic.setOpacity(1.0);
                        marsGrainButton.setDisable(false);
                    }
                }
            }
        } else {
            for (int i = 0; i < rocketElements.length; i++) {
                if (rocketElements[i] != null) {
                    rocketElements[i].setLocation(0);
                    if (rocketElements[i].getType().equals("Human 1")) {
                        marsHuman1Pic.setOpacity(0.2);
                        marsHuman1Button.setDisable(true);
                        earthHuman1Pic.setOpacity(1.0);
                        earthHuman1Button.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Human 2")) {
                        marsHuman2Pic.setOpacity(0.2);
                        marsHuman2Button.setDisable(true);
                        earthHuman2Pic.setOpacity(1.0);
                        earthHuman2Button.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Lion")) {
                        marsLionPic.setOpacity(0.2);
                        marsLionButton.setDisable(true);
                        earthLionPic.setOpacity(1.0);
                        earthLionButton.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Cow")) {
                        marsCowPic.setOpacity(0.2);
                        marsCowButton.setDisable(true);
                        earthCowPic.setOpacity(1.0);
                        earthCowButton.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Grain")) {
                        marsGrainPic.setOpacity(0.2);
                        marsGrainButton.setDisable(true);
                        earthGrainPic.setOpacity(1.0);
                        earthGrainButton.setDisable(false);
                    }
                }
            }
        }
        alertsLabel.setText("");
    }
}
