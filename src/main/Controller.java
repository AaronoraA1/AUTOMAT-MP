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
    ImageView one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, finalCircle;
    @FXML
    ImageView arrow1, arrow2, arrow3, arrow4, arrow5, arrow6, arrow7, arrow8, arrow9, arrow10, arrow11, arrow12, arrow13, arrow14, arrow15, arrow16;
    @FXML
    ImageView arrow17, arrow18, arrow19, arrow20, arrow21, arrow22, arrow23, arrow24, arrow25, arrow26;

    @FXML
    Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14;

    @FXML
    ImageView scientistSpaceship;

    @FXML
    Pane previousStatePane, currentStatePane;

    @FXML
    ScrollPane hintPane;
    @FXML
    Button hintButton;

    @FXML
    Label alertsLabel, conflictLabel;
    @FXML
    Button launchButton;

    public int turn = 0; // CURRENT LOCATION : 0 = EARTH, 1 = MARS
    public Element[] rocketElements = new Element[2]; // SCIENTIST ASSUMED TO BE ONBOARD ALREADY
    public Element h1, h2, l, c, g; // ALL ELEMENTS
    public ArrayList<String> earthStates = new ArrayList<String>();
    public ArrayList<String> marsStates = new ArrayList<String>();
    public ArrayList<String> conflictElements = new ArrayList<String>();

    /*-----------------------BFS ELEMENTS-----------------------------------*/
    public String[] possibleMoves = {"HP", "HL", "HC", "HG", "LC", "LG", "CG", "PL", "PC", "PG", "H", "P", "C", "L", "G"};
    public ArrayList <SearchNode> queueList = new ArrayList<SearchNode>();
    public ArrayList <SearchNode> shortsolutionList = new ArrayList<SearchNode>();
    public ArrayList <SearchNode> longsolutionList = new ArrayList<SearchNode>();

    public SearchNode rootNode;

    /////////////////////////////////BFS FUNCTIONS////////////////////////////////////////

    public void BFSSearch() {

        shortsolutionList = new ArrayList<SearchNode>(); // Initialize solutions to zero
        longsolutionList = new ArrayList<SearchNode>(); // Initialize solutions to zero
        ArrayList<String> earth = new ArrayList<String>();
        earth.add("H");
        earth.add("P");
        earth.add("C");
        earth.add("G");
        earth.add("L");

        State inits = new State( earth, new ArrayList<String>(), 0);
        rootNode = new SearchNode(inits);
        rootNode.setDepth(0);
        queueList.add(rootNode);

        while (!queueList.isEmpty()) {
            SearchNode currNode = queueList.remove(0);
            for (String move : possibleMoves) {
                State currState = currNode.getState().moveRocket(move);

                if (currState != null && currState.checkAllowedTravel()) {
                    SearchNode child = new SearchNode(currState);
                    child.setParent(currNode);
                    child.setDepth(currNode.getDepth()+1);

                    if (!child.isAncestor()) {
                        currNode.getChildren().add(child);

                        if (child.getState().checkIfStateIsWin() == false) {
                            queueList.add(child);
                        }
                        else {
                            if(checkShort(child)) {
                                shortsolutionList.add(child);
                           }
                           else {
                                longsolutionList.add(child);
                            }
                        }
                    }
                }
            }
        }
        printParents(shortsolutionList);
        printParents(longsolutionList);
    }

    public void printParents(ArrayList<SearchNode> solutionList){
        ArrayList<SearchNode> tempList = new ArrayList<SearchNode>();
        System.out.println("NUMBER OF SOLUTIONS: "+solutionList.size());
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

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void initialize(){
        h1 = new Element("Human 1", 0); // 0 = EARTH, 1 = MARS
        h2 = new Element("Human 2", 0);
        l = new Element("Lion", 0);
        c = new Element("Cow", 0);
        g = new Element("Grain", 0);

        marsHuman1Pic.setOpacity(0);       //DISABLE ALL MARS ELEMENTS
        marsHuman1Button.setDisable(true);
        marsHuman2Pic.setOpacity(0);
        marsHuman2Button.setDisable(true);
        marsCowPic.setOpacity(0);
        marsLionButton.setDisable(true);
        marsLionPic.setOpacity(0);
        marsCowButton.setDisable(true);
        marsGrainPic.setOpacity(0);
        marsGrainButton.setDisable(true);

        initButtons();
//        initFinalStates();

        BFSSearch();
    }

    /////////////////////////////////////GUI FUNCTIONS///////////////////////////////////////////
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

        prev = 0;

        rocketElements[0] = null;
        rocketElements[1] = null;

        alertsLabel.setText(" ");
        conflictLabel.setText(" ");
        hintPane.setContent(new Label(""));
        previousStatePane.getChildren().clear();
        currentStatePane.getChildren().clear();

        h1.setLocation(0);
        h2.setLocation(0);
        l.setLocation(0);
        g.setLocation(0);
        c.setLocation(0);

        if (turn == 1)
            moveRocketToEarth();

        turn = 0;

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

        marsHuman1Pic.setOpacity(0);       //DISABLE ALL MARS ELEMENTS
        marsHuman1Button.setDisable(true);
        marsHuman2Pic.setOpacity(0);
        marsHuman2Button.setDisable(true);
        marsCowPic.setOpacity(0);
        marsLionButton.setDisable(true);
        marsLionPic.setOpacity(0);
        marsCowButton.setDisable(true);
        marsGrainPic.setOpacity(0);
        marsGrainButton.setDisable(true);

        clearAutomata();
        conflictElements.clear();
    }

    public State getElements() {
        earthStates.clear();
        marsStates.clear();

        //human1
        if(h1.getLocation()==0){
            earthStates.add("H");
        }
        else {
            marsStates.add("H");
        }
        //human2
        if(h2.getLocation()==0){
            earthStates.add("P");
        }
        else {
            marsStates.add("P");
        }
        //lion
        if(l.getLocation()==0){
            earthStates.add("L");
        }
        else {
            marsStates.add("L");
        }
        //grain
        if(g.getLocation()==0){
            earthStates.add("G");
        }
        else {
            marsStates.add("G");
        }
        //cow
        if(c.getLocation()==0){
            earthStates.add("C");
        }
        else {
            marsStates.add("C");
        }
        return new State(earthStates, marsStates, turn);
    }

    public void launch() {

        hintPane.setContent(new Label(""));
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
                String s= "Conflicting Elements: \n";

                for(int i=0; i<conflictElements.size(); i++){
                    s+= conflictElements.get(i)+"\n";
                }
                conflictLabel.setText(s);

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
                String s= "Conflicting Elements: \n";

                for(int i=0; i<conflictElements.size(); i++){
                    s+= conflictElements.get(i)+"\n";
                }
                conflictLabel.setText(s);

                alertsLabel.setText("GAME OVER!");
            }
        }
        updateSolutionStates(getElements());
        displayCurrentStatePane(getElements());

        if (conflictElements.size()>0){
            showAutomata();
        }
    }

    public void getHint(){
        System.out.println("GETTING HINT");
        State currentState = getElements();

        if(lookForShortSolution(currentState)){

        }
        else {
            lookForLongSolution(currentState);
        }
    }


    public boolean checkElementsInBothPlanets(){
        boolean valid = true;

        if((h1.getLocation()==1 || h2.getLocation()==1) && l.getLocation() ==1 && turn==0){
            conflictElements.add("Human 1");
            conflictElements.add("Lion");
            valid = false;
        }
        else if((h1.getLocation()==1 || h2.getLocation()==1) && c.getLocation() ==1 && turn==0){
            conflictElements.add("Human 1");
            conflictElements.add("Cow");
            valid = false;
        }
        else if(l.getLocation() ==1 && c.getLocation()==1 && turn==0){
            conflictElements.add("Cow");
            conflictElements.add("Lion");
            valid = false;
        }
        else if(g.getLocation() ==1 && c.getLocation()==1 && turn==0){
            conflictElements.add("Grain");
            conflictElements.add("Cow");
            valid = false;
        }
        else if((h1.getLocation()==0 || h2.getLocation()==0) && l.getLocation() ==0 && turn==1){
            conflictElements.add("Human 1");
            conflictElements.add("Lion");
            valid = false;
        }
        else if((h1.getLocation()==0 || h2.getLocation()==0) && c.getLocation() ==0 && turn==1){
            conflictElements.add("Human 1");
            conflictElements.add("Cow");
            valid = false;
        }
        else if(l.getLocation() ==0 && c.getLocation()==0 && turn==1){
            conflictElements.add("Cow");
            conflictElements.add("Lion");
            valid = false;
        }
        else if(g.getLocation() ==0 && c.getLocation()==0 && turn==1){
            conflictElements.add("Grain");
            conflictElements.add("Cow");
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
//            s+= ""+ state.getEarthElements().get(i);
//            s+= "\n";


            if(state.getEarthElements().get(i).equalsIgnoreCase("H")){
                s+= "HUMAN 1\n";
            }
            if(state.getEarthElements().get(i).equalsIgnoreCase("P")){
                s+= "HUMAN 2\n";
            }
            if(state.getEarthElements().get(i).equalsIgnoreCase("C")){
                s+= "COW\n";
            }
            if(state.getEarthElements().get(i).equalsIgnoreCase("G")){
                s+= "GRAIN\n";
            }
            if(state.getEarthElements().get(i).equalsIgnoreCase("L")){
                s+= "LION\n";
            }
        }

        s+= "\nMARS :\n";

        for(int i=0; i<state.getMarsElements().size(); i++){
//            s+= ""+ state.getMarsElements().get(i);
//            s+= "\n";

            if(state.getMarsElements().get(i).equalsIgnoreCase("H")){
                s+= "HUMAN 1\n";
            }
            if(state.getMarsElements().get(i).equalsIgnoreCase("P")){
                s+= "HUMAN 2\n";
            }
            if(state.getMarsElements().get(i).equalsIgnoreCase("C")){
                s+= "COW\n";
            }
            if(state.getMarsElements().get(i).equalsIgnoreCase("G")){
                s+= "GRAIN\n";
            }
            if(state.getMarsElements().get(i).equalsIgnoreCase("L")){
                s+= "LION\n";
            }
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 12; -fx-text-alignment: center");
        previousStatePane.getChildren().add(label);
    }

    public void displayCurrentStatePane(State state){
        String s = "\nEARTH :\n";

        for(int i=0; i<state.getEarthElements().size(); i++){
            if(state.getEarthElements().get(i).equalsIgnoreCase("H")){
                s+= "HUMAN 1\n";
            }
            if(state.getEarthElements().get(i).equalsIgnoreCase("P")){
                s+= "HUMAN 2\n";
            }
            if(state.getEarthElements().get(i).equalsIgnoreCase("C")){
                s+= "COW\n";
            }
            if(state.getEarthElements().get(i).equalsIgnoreCase("G")){
                s+= "GRAIN\n";
            }
            if(state.getEarthElements().get(i).equalsIgnoreCase("L")){
                s+= "LION\n";
            }
        }

        s+= "\nMARS :\n";

        for(int i=0; i<state.getMarsElements().size(); i++){

            if(state.getMarsElements().get(i).equalsIgnoreCase("H")){
                s+= "HUMAN 1\n";
            }
            if(state.getMarsElements().get(i).equalsIgnoreCase("P")){
                s+= "HUMAN 2\n";
            }
            if(state.getMarsElements().get(i).equalsIgnoreCase("C")){
                s+= "COW\n";
            }
            if(state.getMarsElements().get(i).equalsIgnoreCase("G")){
                s+= "GRAIN\n";
            }
            if(state.getMarsElements().get(i).equalsIgnoreCase("L")){
                s+= "LION\n";
            }
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 12; -fx-text-alignment: center");
        currentStatePane.getChildren().add(label);
    }

    public boolean lookForShortSolution (State currState){
        System.out.println("LOOKING FOR SOLUTION");
        ArrayList<SearchNode> tempList = new ArrayList<SearchNode>();

        for(int i = 0; i<shortsolutionList.size(); i++){
            tempList.clear();
            SearchNode currNode = shortsolutionList.get(i);

            tempList.add(currNode);
            SearchNode parent = currNode.getParent();
            while(parent != null){
                tempList.add(parent);
                parent = parent.getParent();
            }
            Collections.reverse(tempList);

            currState.printState();
            System.out.println("COMPARED TO.. ");
            for(int k = 0; k < tempList.size(); k++){
                tempList.get(k).getState().printState();

                if (tempList.get(k).getState().compareElements(currState)){
                    displaySolution(tempList.get(k+1).getState(), tempList.get(k).getState());
                    return true;
//                    break;
                }
            }
        }
        System.out.println("DID NOT FIND A HINT");
        return false;
    }

    public boolean lookForLongSolution (State currState){
        System.out.println("LOOKING FOR SOLUTION");
        ArrayList<SearchNode> tempList = new ArrayList<SearchNode>();

        for(int i = 0; i<longsolutionList.size(); i++){
            tempList.clear();
            SearchNode currNode = longsolutionList.get(i);

            tempList.add(currNode);
            SearchNode parent = currNode.getParent();
            while(parent != null){
                tempList.add(parent);
                parent = parent.getParent();
            }
            Collections.reverse(tempList);

            currState.printState();
            System.out.println("COMPARED TO.. ");
            for(int k = 0; k < tempList.size(); k++){
                tempList.get(k).getState().printState();

                if (tempList.get(k).getState().compareElements(currState)){
                    displaySolution(tempList.get(k+1).getState(), tempList.get(k).getState());
                    return true;
//                    break;
                }
            }
        }
        System.out.println("DID NOT FIND A HINT");
        return false;
    }

    public void displaySolution(State suggestion, State currState){
        ArrayList<String> moveElements = new ArrayList<String>();
        boolean transfer = false;

        if(turn == 0){ //PAPUNTANG MARS
            for(int i=0; i<suggestion.getMarsElements().size(); i++){
                for (int k=0; k<currState.getMarsElements().size(); k++){
                    if(suggestion.getMarsElements().get(i).equalsIgnoreCase(currState.getMarsElements().get(k))){
                        transfer = false;
                        break;
                    }
                    else {
                        transfer = true;
                    }
                }
                if (transfer){
                    moveElements.add(suggestion.getMarsElements().get(i));
                    transfer = false;
                }
            }
        }
        else { //PAPUNTANG EARTH
            for(int i=0; i<suggestion.getEarthElements().size(); i++){
                for (int k=0; k<currState.getEarthElements().size(); k++){
                    if(suggestion.getEarthElements().get(i).equalsIgnoreCase(currState.getEarthElements().get(k))){
                        transfer = false;
                        break;
                    }
                    else {
                        transfer = true;
                    }
                }
                if (transfer){
                    moveElements.add(suggestion.getEarthElements().get(i));
                    transfer = false;
                }
            }

        }


        String s = "\n\n  Move : \n";

        for(int i=0; i<moveElements.size(); i++){
            if(moveElements.get(i).equalsIgnoreCase("H")){
                s+= "    HUMAN 1\n";
            }
            if(moveElements.get(i).equalsIgnoreCase("P")){
                s+= "    HUMAN 2\n";
            }
            if(moveElements.get(i).equalsIgnoreCase("C")){
                s+= "    COW\n";
            }
            if(moveElements.get(i).equalsIgnoreCase("G")){
                s+= "    GRAIN\n";
            }
            if(moveElements.get(i).equalsIgnoreCase("L")){
                s+= "    LION\n";
            }
        }

        if (turn == 0) {
            s+= "  to Mars";
        }
        else {
            s+= "  to Earth";
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 15; ");
        hintPane.setContent(label);
    }

    public void clearAutomata(){

        two.setOpacity(0);
        three.setOpacity(0);
        four.setOpacity(0);
        five.setOpacity(0);
        six.setOpacity(0);
        seven.setOpacity(0);
        eight.setOpacity(0);
        nine.setOpacity(0);
        ten.setOpacity(0);
        eleven.setOpacity(0);
        twelve.setOpacity(0);
        finalCircle.setOpacity(0);

        arrow1.setOpacity(0);
        arrow2.setOpacity(0);
        arrow3.setOpacity(0);
        arrow4.setOpacity(0);
        arrow5.setOpacity(0);
        arrow6.setOpacity(0);
        arrow7.setOpacity(0);
        arrow8.setOpacity(0);
        arrow9.setOpacity(0);
        arrow10.setOpacity(0);
        arrow11.setOpacity(0);
        arrow12.setOpacity(0);
        arrow13.setOpacity(0);
        arrow14.setOpacity(0);
        arrow15.setOpacity(0);
        arrow16.setOpacity(0);
        arrow17.setOpacity(0);
        arrow18.setOpacity(0);
        arrow19.setOpacity(0);
        arrow20.setOpacity(0);
        arrow21.setOpacity(0);
        arrow22.setOpacity(0);
        arrow23.setOpacity(0);
        arrow24.setOpacity(0);
        arrow25.setOpacity(0);
        arrow26.setOpacity(0);

        label1.setOpacity(0);
        label2.setOpacity(0);
        label3.setOpacity(0);
        label4.setOpacity(0);
        label5.setOpacity(0);
        label6.setOpacity(0);
        label7.setOpacity(0);
        label8.setOpacity(0);
        label9.setOpacity(0);
        label10.setOpacity(0);
        label11.setOpacity(0);
        label12.setOpacity(0);
        label13.setOpacity(0);
        label14.setOpacity(0);
    }

    public void showAutomata(){

        two.setOpacity(1);
        three.setOpacity(1);
        four.setOpacity(1);
        five.setOpacity(1);
        six.setOpacity(1);
        seven.setOpacity(1);
        eight.setOpacity(1);
        nine.setOpacity(1);
        ten.setOpacity(1);
        eleven.setOpacity(1);
        twelve.setOpacity(1);
        finalCircle.setOpacity(1);

        arrow1.setOpacity(1);
        arrow2.setOpacity(1);
        arrow3.setOpacity(1);
        arrow4.setOpacity(1);
        arrow5.setOpacity(1);
        arrow6.setOpacity(1);
        arrow7.setOpacity(1);
        arrow8.setOpacity(1);
        arrow9.setOpacity(1);
        arrow10.setOpacity(1);
        arrow11.setOpacity(1);
        arrow12.setOpacity(1);
        arrow13.setOpacity(1);
        arrow14.setOpacity(1);
        arrow15.setOpacity(1);
        arrow16.setOpacity(1);
        arrow17.setOpacity(1);
        arrow18.setOpacity(1);
        arrow19.setOpacity(1);
        arrow20.setOpacity(1);
        arrow21.setOpacity(1);
        arrow22.setOpacity(1);
        arrow23.setOpacity(1);
        arrow24.setOpacity(1);
        arrow25.setOpacity(1);
        arrow26.setOpacity(1);

        label1.setOpacity(1);
        label2.setOpacity(1);
        label3.setOpacity(1);
        label4.setOpacity(1);
        label5.setOpacity(1);
        label6.setOpacity(1);
        label7.setOpacity(1);
        label8.setOpacity(1);
        label9.setOpacity(1);
        label10.setOpacity(1);
        label11.setOpacity(1);
        label12.setOpacity(1);
        label13.setOpacity(1);
        label14.setOpacity(1);
    }

    public void disableTravellingElements(){
        if (turn == 1) {
            for (int i = 0; i < rocketElements.length; i++) {
                if (rocketElements[i] != null) {
                    rocketElements[i].setLocation(1);
                    if (rocketElements[i].getType().equals("Human 1")) {
                        earthHuman1Pic.setOpacity(0);
                        earthHuman1Button.setDisable(true);
                        marsHuman1Pic.setOpacity(1.0);
                        marsHuman1Button.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Human 2")) {
                        earthHuman2Pic.setOpacity(0);
                        earthHuman2Button.setDisable(true);
                        marsHuman2Pic.setOpacity(1.0);
                        marsHuman2Button.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Lion")) {
                        earthLionPic.setOpacity(0);
                        earthLionButton.setDisable(true);
                        marsLionPic.setOpacity(1.0);
                        marsLionButton.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Cow")) {
                        earthCowPic.setOpacity(0);
                        earthCowButton.setDisable(true);
                        marsCowPic.setOpacity(1.0);
                        marsCowButton.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Grain")) {
                        earthGrainPic.setOpacity(0);
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
                        marsHuman1Pic.setOpacity(0);
                        marsHuman1Button.setDisable(true);
                        earthHuman1Pic.setOpacity(1.0);
                        earthHuman1Button.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Human 2")) {
                        marsHuman2Pic.setOpacity(0);
                        marsHuman2Button.setDisable(true);
                        earthHuman2Pic.setOpacity(1.0);
                        earthHuman2Button.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Lion")) {
                        marsLionPic.setOpacity(0);
                        marsLionButton.setDisable(true);
                        earthLionPic.setOpacity(1.0);
                        earthLionButton.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Cow")) {
                        marsCowPic.setOpacity(0);
                        marsCowButton.setDisable(true);
                        earthCowPic.setOpacity(1.0);
                        earthCowButton.setDisable(false);
                    }
                    if (rocketElements[i].getType().equals("Grain")) {
                        marsGrainPic.setOpacity(0);
                        marsGrainButton.setDisable(true);
                        earthGrainPic.setOpacity(1.0);
                        earthGrainButton.setDisable(false);
                    }
                }
            }
        }
        alertsLabel.setText("");
    }

    int prev = 0;
    public void updateSolutionStates(State state){
        if (state.checkIfPresentEarth("H") && state.checkIfPresentEarth( "P") &&  state.checkIfPresentEarth("G")
                && state.checkIfPresentMars("L") && state.checkIfPresentMars("C")){
            if (prev == 2) {
                arrow17.setOpacity(1);
                label2.setOpacity(1);
            }
            if (prev == 8) {
                arrow22.setOpacity(1);
                label8.setOpacity(1);
            }
            else {
                arrow1.setOpacity(1);
                label1.setOpacity(1);
            }
            prev = 1;
            two.setOpacity(1);
        } //ok
        else if (state.checkIfPresentEarth("H") && state.checkIfPresentEarth("P") &&  state.checkIfPresentEarth("G")
                && state.checkIfPresentEarth("L") && state.checkIfPresentMars("C")){
            if (prev == 1) {
                arrow2.setOpacity(1);
                label2.setOpacity(1);
            }
            if (prev == 9) {
                arrow15.setOpacity(1);
                label10.setOpacity(1);
            }
            if (prev == 3) {
                arrow18.setOpacity(1);
                label3.setOpacity(1);
            }
            prev = 2;
            three.setOpacity(1);
        }
        else if (state.checkIfPresentMars("H") && state.checkIfPresentMars("P") &&  state.checkIfPresentEarth("G")
                && state.checkIfPresentEarth("L") && state.checkIfPresentMars("C")){
            if (prev == 2) {
                arrow3.setOpacity(1);
                label3.setOpacity(1);
            }
            if (prev == 4) {
                arrow19.setOpacity(1);
                label4.setOpacity(1);
            }
            prev = 3;
            four.setOpacity(1);
        }
        else if (state.checkIfPresentMars("H") && state.checkIfPresentMars("P") &&  state.checkIfPresentEarth("G")
                && state.checkIfPresentEarth("L") && state.checkIfPresentEarth("C")){
            if (prev == 3) {
                arrow4.setOpacity(1);
                label4.setOpacity(1);
            }
            if (prev == 11) {
                arrow16.setOpacity(1);
                label13.setOpacity(1);
            }
            if (prev == 5) {
                arrow20.setOpacity(1);
                label5.setOpacity(1);
            }
            prev = 4;
            five.setOpacity(1);
        }
        else if (state.checkIfPresentMars("H") && state.checkIfPresentMars("P") &&  state.checkIfPresentMars("G")
                && state.checkIfPresentEarth("L") && state.checkIfPresentMars("C")){
            if (prev == 4) {
                arrow5.setOpacity(1);
                label5.setOpacity(1);
            }
            if (prev == 6) {
                arrow21.setOpacity(1);
                label6.setOpacity(1);
            }
            prev = 5;
            six.setOpacity(1);
        }
        else if (state.checkIfPresentMars("H") && state.checkIfPresentMars("P") &&  state.checkIfPresentMars("G")
                && state.checkIfPresentEarth("L") && state.checkIfPresentEarth("C")){
            if (prev == 5) {
                arrow6.setOpacity(1);
                label6.setOpacity(1);
            }
            if (prev == 11) {
                arrow14.setOpacity(1);
                label14.setOpacity(1);
            }
            prev = 6;
            seven.setOpacity(1);
        }
        else if (state.checkIfPresentMars("H") && state.checkIfPresentMars("P") &&  state.checkIfPresentMars("G")
                && state.checkIfPresentMars("L")  && state.checkIfPresentMars("C")){
            if (prev == 6) {
                arrow7.setOpacity(1);
                label7.setOpacity(1);
            }
            prev = 7;
            eight.setOpacity(1);
            finalCircle.setOpacity(1);
        }
        else if (state.checkIfPresentEarth("H") && state.checkIfPresentEarth("P") &&  state.checkIfPresentEarth("G")
                && state.checkIfPresentMars("L")  && state.checkIfPresentEarth("C")){
            if (prev == 1) {
                arrow8.setOpacity(1);
                label8.setOpacity(1);
            }
            if (prev == 9) {
                arrow23.setOpacity(1);
                label9.setOpacity(1);
            }
            prev = 8;
            nine.setOpacity(1);
        }
        else if (state.checkIfPresentEarth("H") && state.checkIfPresentEarth("P") &&  state.checkIfPresentMars("G")
                && state.checkIfPresentMars("L")  && state.checkIfPresentMars("C")){
            if (prev == 8) {
                arrow9.setOpacity(1);
                label9.setOpacity(1);
            }
            if (prev == 2) {
                arrow10.setOpacity(1);
                label10.setOpacity(1);
            }
            if (prev == 10) {
                arrow24.setOpacity(1);
                label11.setOpacity(1);
            }
            prev = 9;
            ten.setOpacity(1);
        }
        else if (state.checkIfPresentEarth("H")&& state.checkIfPresentEarth("P") &&  state.checkIfPresentMars("G")
                && state.checkIfPresentMars("L")  && state.checkIfPresentEarth("C")){
            if (prev == 9) {
                arrow11.setOpacity(1);
                label11.setOpacity(1);
            }
            if (prev == 11) {
                arrow25.setOpacity(1);
                label12.setOpacity(1);
            }
            prev = 10;
            eleven.setOpacity(1);
        }
        else if (state.checkIfPresentMars("H") && state.checkIfPresentMars("P") &&  state.checkIfPresentMars("G")
                && state.checkIfPresentMars("L")  && state.checkIfPresentEarth("C")){
            if (prev == 10) {
                arrow12.setOpacity(1);
                label12.setOpacity(1);
            }
            if (prev == 4) {
                arrow13.setOpacity(1);
                label13.setOpacity(1);
            }
            if (prev == 6) {
                arrow26.setOpacity(1);
                label14.setOpacity(1);
            }
            prev = 11;
            twelve.setOpacity(1);
        }

    }
}
