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

        es.add(h1);
        es.add(h2);
        es.add(l);
        es.add(c);
        es.add(g);
        finalStates.add(new State(es, ms));
        es.clear();
        ms.clear();

        es.add(h1);
        es.add(h2);
        es.add(g);
        ms.add(l);
        ms.add(c);
        finalStates.add(new State(es, ms));
        es.clear();
        ms.clear();

        es.add(h1);
        es.add(h2);
        es.add(g);
        es.add(l);
        ms.add(c);
        finalStates.add(new State(es, ms));
        es.clear();
        ms.clear();

        ms.add(h1);
        ms.add(h2);
        es.add(g);
        es.add(l);
        ms.add(c);
        finalStates.add(new State(es, ms));
        es.clear();
        ms.clear();

        ms.add(h1);
        ms.add(h2);
        es.add(g);
        es.add(l);
        es.add(c);
        finalStates.add(new State(es, ms));
        es.clear();
        ms.clear();

        ms.add(h1);
        ms.add(h2);
        ms.add(g);
        es.add(l);
        ms.add(c);
        finalStates.add(new State(es, ms));
        es.clear();
        ms.clear();

        ms.add(h1);
        ms.add(h2);
        ms.add(g);
        es.add(l);
        es.add(c);
        finalStates.add(new State(es, ms));
        es.clear();
        ms.clear();

        ms.add(h1);
        ms.add(h2);
        ms.add(g);
        ms.add(l);
        ms.add(c);
        finalStates.add(new State(es, ms));
        ms.clear();
        ms.clear();

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

    public void displayCurrentStatePane(State state){
        String s = "EARTH: ";

        for(int i=0; i<state.getEarthElements().size(); i++){
            s+= state.getEarthElements().get(i).getType();
            s+= ", ";
        }

        s+= "\n MARS: ";

        for(int i=0; i<state.getMarsElements().size(); i++){
            s+= state.getMarsElements().get(i).getType();
            s+= ", ";
        }

        Label label = new Label (s);
        currentStatePane.setContent(label);
    }

    public void displayPrevStatePane(State state){
        String s = "EARTH: ";

        for(int i=0; i<state.getEarthElements().size(); i++){
            s+= state.getEarthElements().get(i).getType();
            s+= ", ";
        }

        s+= "\n MARS: ";

        for(int i=0; i<state.getMarsElements().size(); i++){
            s+= state.getMarsElements().get(i).getType();
            s+= ", ";
        }

        Label label = new Label (s);
        previousStatePane.setContent(label);
    }

    public void launch() {
        displayPrevStatePane(getElements());
        if (turn == 0){
            turn = 1;
            disableTravellingElements();
            moveRocketToMars();

            //DO CHECKING HERE FOR RESTRICTIONS WHEN LEFT ALONE WITHOUT SCIENTIST
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

    public void showHint(){
        //AI AARON!!!
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
