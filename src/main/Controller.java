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
    ImageView one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve;
    @FXML
    ImageView arrow1, arrow2, arrow3, arrow4, arrow5, arrow6, arrow7, arrow8, arrow9, arrow10, arrow11, arrow12, arrow13, arrow14, arrow15, arrow16;

    @FXML
    ImageView scientistSpaceship;

    @FXML
    Pane previousStatePane, currentStatePane;

    @FXML
    ScrollPane hintPane;
    @FXML
    Button hintButton;

    @FXML
    Label alertsLabel;
    @FXML
    Button launchButton;

    public int turn = 0; // CURRENT LOCATION : 0 = EARTH, 1 = MARS
    public Element[] rocketElements = new Element[2]; // SCIENTIST ASSUMED TO BE ONBOARD ALREADY
    public Element h1, h2, l, c, g; // ALL ELEMENTS
    public ArrayList<State> finalStates = new ArrayList<State>();
    public ArrayList<String> earthStates = new ArrayList<String>();
    public ArrayList<String> marsStates = new ArrayList<String>();

    /*-----------------------BFS ELEMENTS-----------------------------------*/
    public String[] possibleMoves = {"HP", "HL", "HC", "HG", "LC", "LG", "CG", "PL", "PC", "PG", "H", "P", "C", "L", "G"};
    public ArrayList <SearchNode> queueList = new ArrayList<SearchNode>();
    public ArrayList <SearchNode> solutionList = new ArrayList<SearchNode>();
    public SearchNode rootNode;

    /////////////////////////////////BFS FUNCTIONS////////////////////////////////////////

    public void BFSSearch() {

        solutionList = new ArrayList<SearchNode>(); // Initialize solutions to zero
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
                                solutionList.add(child);
                           }
                        }
                    }
                }
            }
        }
        printParents();
    }

    public void printParents(){
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
    private void initFinalStates(){
        ArrayList<String> es = new ArrayList<String>();
        ArrayList<String> ms = new ArrayList<String>();

        System.out.println("INITIALIZE");

        es.add("Human 1");
        es.add("Human 2");
        es.add("Lion");
        es.add("Cow");
        es.add("Grain");

        finalStates.add(new State(es,ms, 0 ));

        ArrayList<String> es2 = new ArrayList<String>();
        ArrayList<String> ms2 = new ArrayList<String>();

        es2.add("Human 1");
        es2.add("Human 2");
        ms2.add("Lion");
        ms2.add("Cow");
        es2.add("Grain");

        finalStates.add(new State(es2,ms2, 1));

        ArrayList<String> es3 = new ArrayList<String>();
        ArrayList<String> ms3 = new ArrayList<String>();

        es3.add("Human 1");
        es3.add("Human 2");
        es3.add("Lion");
        ms3.add("Cow");
        es3.add("Grain");

        finalStates.add(new State(es3, ms3, 0));


        ArrayList<String> es4 = new ArrayList<String>();
        ArrayList<String> ms4 = new ArrayList<String>();

        ms4.add("Human 1");
        ms4.add("Human 2");
        es4.add("Lion");
        ms4.add("Cow");
        es4.add("Grain");

        finalStates.add(new State(es4, ms4, 1));

        ArrayList<String> es5 = new ArrayList<String>();
        ArrayList<String> ms5 = new ArrayList<String>();

        ms5.add("Human 1");
        ms5.add("Human 2");
        es5.add("Lion");
        es5.add("Cow");
        es5.add("Grain");

        finalStates.add(new State(es5, ms5, 0));

        ArrayList<String> es6 = new ArrayList<String>();
        ArrayList<String> ms6 = new ArrayList<String>();

        ms6.add("Human 1");
        ms6.add("Human 2");
        es6.add("Lion");
        ms6.add("Cow");
        ms6.add("Grain");

        finalStates.add(new State(es6, ms6, 1));


        ArrayList<String> es7 = new ArrayList<String>();
        ArrayList<String> ms7 = new ArrayList<String>();

        ms7.add("Human 1");
        ms7.add("Human 2");
        es7.add("Lion");
        es7.add("Cow");
        ms7.add("Grain");

        finalStates.add(new State(es7, ms7, 0));

        ArrayList<String> es8 = new ArrayList<String>();
        ArrayList<String> ms8 = new ArrayList<String>();

        ms8.add("Human 1");
        ms8.add("Human 2");
        ms8.add("Lion");
        ms8.add("Cow");
        ms8.add("Grain");

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

        alertsLabel.setText(" ");
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

        clearAutomata();
    }

    public State getElements() {
        earthStates.clear();
        marsStates.clear();

        //human1
        if(h1.getLocation()==0){
            earthStates.add("Human 1");
        }
        else {
            marsStates.add("Human 1");
        }
        //human2
        if(h2.getLocation()==0){
            earthStates.add("Human 2");
        }
        else {
            marsStates.add("Human 2");
        }
        //lion
        if(l.getLocation()==0){
            earthStates.add("Lion");
        }
        else {
            marsStates.add("Lion");
        }
        //grain
        if(g.getLocation()==0){
            earthStates.add("Grain");
        }
        else {
            marsStates.add("Grain");
        }
        //cow
        if(c.getLocation()==0){
            earthStates.add("Cow");
        }
        else {
            marsStates.add("Cow");
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
        updateSolutionStates(getElements());

        if (alertsLabel.getText().toString().equals("GAME OVER!")){
            clearAutomata();
        }
    }

    public void getHint(){
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
            s+= ""+ state.getEarthElements().get(i);
            s+= "\n";
        }

        s+= "\nMARS :\n";

        for(int i=0; i<state.getMarsElements().size(); i++){
            s+= ""+ state.getMarsElements().get(i);
            s+= "\n";
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 12; -fx-text-alignment: center");
        previousStatePane.getChildren().add(label);
    }

    public void displayCurrentStatePane(State state){
        String s = "\nEARTH :\n";

        for(int i=0; i<state.getEarthElements().size(); i++){
            s+= ""+ state.getEarthElements().get(i);
            s+= "\n";
        }

        s+= "\nMARS :\n";

        for(int i=0; i<state.getMarsElements().size(); i++){
            s+= ""+ state.getMarsElements().get(i);
            s+= "\n";
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 12; -fx-text-alignment: center");
        currentStatePane.getChildren().add(label);
    }
    public void displaySolution(State state){
        String s = "\n EARTH : \n";

        for(int i=0; i<state.getEarthElements().size(); i++){
            s+= " "+ state.getEarthElements().get(i);
            s+= "\n";
        }

        s+= "\n MARS : \n";

        for(int i=0; i<state.getMarsElements().size(); i++){
            s+= " "+ state.getMarsElements().get(i);
            s+= "\n";
        }

        Label label = new Label (s);
        label.setStyle("-fx-font-size: 15;");
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

    int prev = 0;
    public void updateSolutionStates(State state){

        if (state.checkIfPresentEarth("Human 1") && state.checkIfPresentEarth( "Human 2") &&  state.checkIfPresentEarth("Grain")
                && state.checkIfPresentMars("Lion") && state.checkIfPresentMars("Cow")){
            prev = 1;
            arrow1.setOpacity(1);
            two.setOpacity(1);
        } //ok
        else if (state.checkIfPresentEarth("Human 1") && state.checkIfPresentEarth("Human 2") &&  state.checkIfPresentEarth("Grain")
                && state.checkIfPresentEarth("Lion") && state.checkIfPresentMars("Cow")){
            if (prev == 1)
                arrow2.setOpacity(1);
            if (prev == 9)
                arrow15.setOpacity(1);
            prev = 2;
            three.setOpacity(1);
        }
        else if (state.checkIfPresentMars("Human 1") && state.checkIfPresentMars("Human 2") &&  state.checkIfPresentEarth("Grain")
                && state.checkIfPresentEarth("Lion") && state.checkIfPresentMars("Cow")){
            if (prev == 2)
                arrow3.setOpacity(1);
            prev = 3;
            four.setOpacity(1);
        }
        else if (state.checkIfPresentMars("Human 1") && state.checkIfPresentMars("Human 2") &&  state.checkIfPresentEarth("Grain")
                && state.checkIfPresentEarth("Lion") && state.checkIfPresentEarth("Cow")){
            if (prev == 3)
                arrow4.setOpacity(1);
            if (prev == 11)
                arrow16.setOpacity(1);
            prev = 4;
            five.setOpacity(1);
        }
        else if (state.checkIfPresentMars("Human 1") && state.checkIfPresentMars("Human 2") &&  state.checkIfPresentMars("Grain")
                && state.checkIfPresentEarth("Lion") && state.checkIfPresentMars("Cow")){
            if (prev == 4)
                arrow5.setOpacity(1);
            prev = 5;
            six.setOpacity(1);
        }
        else if (state.checkIfPresentMars("Human 1") && state.checkIfPresentMars("Human 2") &&  state.checkIfPresentMars("Grain")
                && state.checkIfPresentEarth("Lion") && state.checkIfPresentEarth("Cow")){
            if (prev == 5)
                arrow6.setOpacity(1);
            if (prev == 11)
                arrow14.setOpacity(1);
            prev = 6;
            seven.setOpacity(1);
        }
        else if (state.checkIfPresentMars("Human 1") && state.checkIfPresentMars("Human 2") &&  state.checkIfPresentMars("Grain")
                && state.checkIfPresentMars("Lion")  && state.checkIfPresentMars("Cow")){
            if (prev == 6)
                arrow7.setOpacity(1);
            prev = 7;
            eight.setOpacity(1);
        }
        else if (state.checkIfPresentEarth("Human 1") && state.checkIfPresentEarth("Human 2") &&  state.checkIfPresentEarth("Grain")
                && state.checkIfPresentMars("Lion")  && state.checkIfPresentEarth("Cow")){
            if (prev == 1)
                arrow8.setOpacity(1);
            prev = 8;
            nine.setOpacity(1);
        }
        else if (state.checkIfPresentEarth("Human 1") && state.checkIfPresentEarth("Human 2") &&  state.checkIfPresentMars("Grain")
                && state.checkIfPresentMars("Lion")  && state.checkIfPresentMars("Cow")){
            if (prev == 8)
                arrow9.setOpacity(1);
            if (prev == 2)
                arrow10.setOpacity(1);
            prev = 9;
            ten.setOpacity(1);
        }
        else if (state.checkIfPresentEarth("Human 1")&& state.checkIfPresentEarth("Human 2") &&  state.checkIfPresentMars("Grain")
                && state.checkIfPresentMars("Lion")  && state.checkIfPresentEarth("Cow")){
            if (prev == 9)
                arrow11.setOpacity(1);
            prev = 10;
            eleven.setOpacity(1);
        }
        else if (state.checkIfPresentMars("Human 1") && state.checkIfPresentMars("Human 2") &&  state.checkIfPresentMars("Grain")
                && state.checkIfPresentMars("Lion")  && state.checkIfPresentEarth("Cow")){
            if (prev == 10)
                arrow12.setOpacity(1);
            if (prev == 4)
                arrow13.setOpacity(1);
            prev = 11;
            twelve.setOpacity(1);
        }

    }
}
