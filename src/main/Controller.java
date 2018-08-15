package main;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

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
    ScrollPane previousStatePane, currentStatePane;

    @FXML
    ScrollPane solutionPane;
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
    public ArrayList<Element> earthStates = new ArrayList<Element>();
    public ArrayList<Element> marsStates = new ArrayList<Element>();

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
        finalStates.add(new State(es,ms));

        ArrayList<Element> es2 = new ArrayList<Element>();
        ArrayList<Element> ms2 = new ArrayList<Element>();

        es2.add(h1);
        es2.add(h2);
        es2.add(g);
        ms2.add(l);
        ms2.add(c);
        finalStates.add(new State(es2,ms2));

        ArrayList<Element> es3 = new ArrayList<Element>();
        ArrayList<Element> ms3 = new ArrayList<Element>();

        es3.add(h1);
        es3.add(h2);
        es3.add(g);
        es3.add(l);
        ms3.add(c);
        finalStates.add(new State(es3, ms3));


        ArrayList<Element> es4 = new ArrayList<Element>();
        ArrayList<Element> ms4 = new ArrayList<Element>();
        ms4.add(h1);
        ms4.add(h2);
        es4.add(g);
        es4.add(l);
        ms4.add(c);
        finalStates.add(new State(es4, ms4));

        ArrayList<Element> es5 = new ArrayList<Element>();
        ArrayList<Element> ms5 = new ArrayList<Element>();
        ms5.add(h1);
        ms5.add(h2);
        es5.add(g);
        es5.add(l);
        es5.add(c);
        finalStates.add(new State(es5, ms5));

        ArrayList<Element> es6 = new ArrayList<Element>();
        ArrayList<Element> ms6 = new ArrayList<Element>();
        ms6.add(h1);
        ms6.add(h2);
        ms6.add(g);
        es6.add(l);
        ms6.add(c);
        finalStates.add(new State(es6, ms6));


        ArrayList<Element> es7 = new ArrayList<Element>();
        ArrayList<Element> ms7 = new ArrayList<Element>();
        ms7.add(h1);
        ms7.add(h2);
        ms7.add(g);
        es7.add(l);
        es7.add(c);
        finalStates.add(new State(es7, ms7));

        ArrayList<Element> es8 = new ArrayList<Element>();
        ArrayList<Element> ms8 = new ArrayList<Element>();
        ms8.add(h1);
        ms8.add(h2);
        ms8.add(g);
        ms8.add(l);
        ms8.add(c);
        finalStates.add(new State(es8, ms8));



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
        return new State(earthStates, marsStates);
    }

    public void launch() {
        displayPrevStatePane(getElements());
        if (turn == 0){
            turn = 1;
            disableTravellingElements();
            moveRocketToMars();

            if(checkElementsInBothPlanets()){
                rocketElements[0] = null;
                rocketElements[1] = null;

                if (checkWinningState()){
                    alertsLabel.setText("WINNER!!! -kris");
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
                    alertsLabel.setText("WINNER!!! -kris");
                }
            }
            else {
                alertsLabel.setText("GAME OVER!");
            }
        }
        displayCurrentStatePane(getElements());
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
                displayHint(finalStates.get(i + 1));
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
        transitn.setByX(260);
        transitn.setRate(4);
        transitn.setInterpolator(Interpolator.LINEAR);
        transitn.play();
    }
    public void moveRocketToEarth(){
        TranslateTransition transitn = new TranslateTransition(Duration.millis(4000), scientistSpaceship);
        transitn.setByX(-260);
        transitn.setRate(4);
        transitn.setInterpolator(Interpolator.LINEAR);
        transitn.play();
    }

    public void displayPrevStatePane(State state){
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
        previousStatePane.setContent(label);
    }

    public void displayCurrentStatePane(State state){
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
        currentStatePane.setContent(label);
    }
    public void displayHint(State state){
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
