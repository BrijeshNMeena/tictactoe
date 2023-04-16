package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {

    private Button[][] grid = new Button[3][3];

    private int scoreOfX = 0, scoreOfO = 0;
    private Label score_X, score_O;

    private boolean playerXTurn = true;

    private void buttonClicked(Button button) {
        if(button.getText().equals("")) {
            if(playerXTurn) {
                button.setText("X");
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold; -fx-background-color : #ADD8E6;");
            }
            else {
                button.setText("O");
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold; -fx-background-color : #00ff00;");
            }
            playerXTurn = !playerXTurn;
            checkWinner();
        }
    }
    private void checkWinner() {
        boolean winnerFound = false;
        String winner = "";
        for (int i = 0; i < 3; i++) {
            if(!grid[i][0].getText().isEmpty() &&
                    grid[i][0].getText().equals(grid[i][1].getText()) &&
                    grid[i][0].getText().equals(grid[i][2].getText())
            ) {
                winner = grid[i][0].getText();
                winnerFound = true;
            }
            else if(!grid[0][i].getText().isEmpty() &&
                    grid[0][i].getText().equals(grid[1][i].getText()) &&
                    grid[0][i].getText().equals(grid[2][i].getText())
            ) {
                winner = grid[i][0].getText();
                winnerFound = true;
            }
        }
        if(!grid[1][1].getText().isEmpty() &&
                grid[0][0].getText().equals(grid[1][1].getText()) &&
                grid[0][0].getText().equals(grid[2][2].getText())
        ) {
            winner = grid[1][1].getText();
            winnerFound = true;
        }
        else if(!grid[1][1].getText().isEmpty() &&
                grid[0][2].getText().equals(grid[1][1].getText()) &&
                grid[0][2].getText().equals(grid[2][0].getText())
        ) {
            winner = grid[1][1].getText();
            winnerFound = true;
        }

        if(winnerFound) {
            showWinner(winner);
            resetBoard();
            updateScores(winner);
        }

        boolean isTie = true;
        for(Button[] row : grid) {
            for (Button button : row) {
                if (button.getText().isEmpty())
                    isTie = false;
            }
        }

        if (isTie) {
            resetBoard();
            showTieDialog();
        }
    }

    private void resetBoard() {
        for(Button[] row : grid) {
            for (Button button : row) {
                button.setText("");
                button.setStyle("");
            }
        }
    }

    private void updateScores(String winner) {
        if(winner.equals("X")) {
            scoreOfX++;
            score_X.setText("Player X : " + scoreOfX);
        }
        else {
            scoreOfO++;
            score_O.setText("Player O : " + scoreOfO);
        }
    }
    private void showWinner(String player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Congratulation " + player + " ! You won the game");
        alert.setTitle("Winner!");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    private void showTieDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("It's a Tie");
        alert.setTitle("Tie");
        alert.setHeaderText("");
        alert.showAndWait();
    }
    private BorderPane createContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        //title
        Label title = new Label("Tic Tac Toe");
        title.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;");
        title.setPadding(new Insets(20));
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        //board
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;");
                button.setOnAction(event->buttonClicked(button));
                grid[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

        root.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        //score
        HBox scores = new HBox(20);
        scores.setAlignment(Pos.CENTER);
        scores.setPadding(new Insets(20));
        score_X = new Label("Player X : 0");
        score_X.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");
        score_O = new Label("Player O : 0");
        score_O.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");
        scores.getChildren().addAll(score_X, score_O);

        root.setBottom(scores);

        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}