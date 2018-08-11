package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    Button earthHuman1Button;
    @FXML
    Button earthHuman2Button;
    @FXML
    Button earthGoatButton;
    @FXML
    Button earthLionButton;
    @FXML
    Button earthGrainButton;
    @FXML
    Button earthScientistButton;



    @FXML
    Button marsHuman1Button;
    @FXML
    Button marsHuman2Button;
    @FXML
    Button marsGoatButton;
    @FXML
    Button marsLionButton;
    @FXML
    Button marsGrainButton;
    @FXML
    Button marsScientistButton;


    @FXML
    Button launchButton;

    @FXML
    Text alert;



    public int turn = 0;
    public Element[] earthElements = new Element[6];
    public Element[] rocketElements = new Element[4];
    public Element[] marsElements = new Element[6];

    public void initialize(){
        earthElements[0] = new Element("Human");
        earthElements[1] = new Element("Human");
        earthElements[2] = new Element("Lion");
        earthElements[3] = new Element("Cow");
        earthElements[4] = new Element("Grain");
        earthElements[5] = new Element("Scientist");

        marsElements[0] = null;
        marsElements[1] = null;
        marsElements[2] = null;
        marsElements[3] = null;
        marsElements[4] = null;
        marsElements[5] = null;

        rocketElements[0] = null;
        rocketElements[1] = null;
        rocketElements[2] = null;
        rocketElements[3] = null;

        initButtons();

    }

    private void initButtons() {

        earthHuman1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(new Element("human"));
            }
        });

        earthHuman2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(new Element("human"));
            }
        });

        earthGoatButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(new Element("goat"));
            }
        });

        earthLionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(new Element("lion"));
            }
        });

        earthGrainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(new Element("grain"));
            }
        });

        earthScientistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(new Element("scientist"));
            }
        });
    }
    public void launch() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (turn == 0) {
                    if (marsElements[j] == null) {
                        marsElements[j] = rocketElements[i];
                        rocketElements[i] = null;
                        turn = 1;
                        System.out.println("LAUNCHED! ROCKET IS NOW ON MARS");
                        print();
                    }
                } else {
                    if (earthElements[j] != null) {
                        earthElements[j] = rocketElements[i];
                        rocketElements[i] = null;
                        turn = 0;
                        System.out.println("LAUNCHED! ROCKET IS NOW ON EARTH");
                        print();
                    }
                }



            }
        }
    }
    public void print(){
        for(int i = 0; i < 4; i++){
            if(rocketElements[i] == null){
                System.out.println("null \n");
            }
            else
                System.out.println(rocketElements[i].getType() + "\n");
        }
    }

    public void load(Element e){
        for(int i = 0; i < 4; i++){
            if(rocketElements[i] == null) {
                if (isLoadValid(e)) {
                    //System.out.println("rocket slot is available");
                    rocketElements[i] = e;
                    alert.setText(e.getType() + " loaded");
                    print();
                    break;
                }
            }
        }

    }

    private boolean isLoadValid(Element e ) {
        int count = 0;
        for(int i = 0; i < 4; i++) {
            if (rocketElements[i] != null) {
                if (e.getType().compareToIgnoreCase("human") == 0) {
                    if (rocketElements[i].getType().compareToIgnoreCase("human") == 0) {
                        count++;
                    }
                    if (count == 2) {
                        alert.setText("INVALID");
                        return false;
                    }
                } else if (e.getType().compareToIgnoreCase("goat") == 0) {
                    if (rocketElements[i].getType().compareToIgnoreCase("goat") == 0) {
                        alert.setText("INVALID");
                        return false;
                    }
                } else if (e.getType().compareToIgnoreCase("lion") == 0) {
                    if (rocketElements[i].getType().compareToIgnoreCase("lion") == 0) {
                        alert.setText("INVALID");
                        return false;
                    }
                } else if (e.getType().compareToIgnoreCase("grain") == 0) {
                    if (rocketElements[i].getType().compareToIgnoreCase("grain") == 0) {
                        alert.setText("INVALID");
                        return false;
                    }
                } else if (e.getType().compareToIgnoreCase("scientist") == 0) {
                    if (rocketElements[i].getType().compareToIgnoreCase("scientist") == 0) {
                        alert.setText("INVALID");
                        return false;
                    }
                }
            }


        }
        return true;
    }


}
