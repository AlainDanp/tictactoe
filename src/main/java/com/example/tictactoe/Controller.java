package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Text winnerText;

    @FXML
    private Text playerXScore;

    @FXML
    private Text playerOScore;

    private int playerTurn = 0;
    private int playerXWins = 0;
    private int playerOWins = 0;

    private MediaPlayer mediaPlayer;

    ArrayList<Button> buttons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media sound = new Media(getClass().getResource("Bibi Babydoll - Automotivo Bibi Fogosa (INSTRUMENTAL).mp3").toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        winnerText.setText("Tic-Tac-Toe");
        buttons.forEach(button -> button.setStyle("-fx-background-color: #11334b"));
    }
    @FXML
    void resetScores(ActionEvent event) {
        playerXScore.setText("0");
        playerOScore.setText("0");
    }

    public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            setPlayerSymbol(button);
            button.setDisable(true);
            checkIfGameIsOver();
        });
    }
    public void stopM() {
        // Arrêter la musique
        mediaPlayer.stop();
    }

    public void setPlayerSymbol(Button button){
        if(playerTurn % 2 == 0){
            button.setText("X");
            playerTurn = 1;
        } else{
            button.setText("O");
            playerTurn = 0;
        }
    }


    public void checkIfGameIsOver() {
        ArrayList<Integer> winningButtonIndices = new ArrayList<>();

        // Vérifier les conditions de victoire
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            // Gagnant X
            if (line.equals("XXX")) {
                winnerText.setText("X a gagné !");
                playerXWins++;
                playerXScore.setText(Integer.toString(playerXWins));
                switch (a) {
                    case 0 -> winningButtonIndices.addAll(Arrays.asList(0, 1, 2));
                    case 1 -> winningButtonIndices.addAll(Arrays.asList(3, 4, 5));
                    case 2 -> winningButtonIndices.addAll(Arrays.asList(6, 7, 8));
                    case 3 -> winningButtonIndices.addAll(Arrays.asList(0, 4, 8));
                    case 4 -> winningButtonIndices.addAll(Arrays.asList(2, 4, 6));
                    case 5 -> winningButtonIndices.addAll(Arrays.asList(0, 3, 6));
                    case 6 -> winningButtonIndices.addAll(Arrays.asList(1, 4, 7));
                    case 7 -> winningButtonIndices.addAll(Arrays.asList(2, 5, 8));
                }
                break;
            }
            // Gagnant O
            else if (line.equals("OOO")) {
                winnerText.setText("O a gagné !");
                playerOWins++;
                playerOScore.setText(Integer.toString(playerOWins));
                switch (a) {
                    case 0 -> winningButtonIndices.addAll(Arrays.asList(0, 1, 2));
                    case 1 -> winningButtonIndices.addAll(Arrays.asList(3, 4, 5));
                    case 2 -> winningButtonIndices.addAll(Arrays.asList(6, 7, 8));
                    case 3 -> winningButtonIndices.addAll(Arrays.asList(0, 4, 8));
                    case 4 -> winningButtonIndices.addAll(Arrays.asList(2, 4, 6));
                    case 5 -> winningButtonIndices.addAll(Arrays.asList(0, 3, 6));
                    case 6 -> winningButtonIndices.addAll(Arrays.asList(1, 4, 7));
                    case 7 -> winningButtonIndices.addAll(Arrays.asList(2, 5, 8));
                }
                break;
            }
        }
        // Modifier la couleur de fond des boutons gagnants en rouge
        winningButtonIndices.forEach(index -> buttons.get(index).setStyle("-fx-background-color: red"));

        // Vérifier si le jeu est terminé
        if (!winningButtonIndices.isEmpty()) {
            // Désactiver tous les boutons
            buttons.forEach(button -> button.setDisable(true));
        } else {
            // Vérifier l'égalité (tous les boutons sont désactivés et aucun gagnant)
            boolean tousLesBoutonsDesactives = buttons.stream().allMatch(Button::isDisabled);
            if (tousLesBoutonsDesactives) {
                winnerText.setText("Égalité !");
            }
        }
    }



}



