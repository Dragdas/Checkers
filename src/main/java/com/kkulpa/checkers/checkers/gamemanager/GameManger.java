package com.kkulpa.checkers.checkers.gamemanager;

import com.kkulpa.checkers.checkers.BoardController;
import com.kkulpa.checkers.checkers.figurecomponents.FigureColor;
import javafx.scene.text.Text;

public class GameManger {

    private FigureColor currentTurn;
    private BoardController boardController;
    private boolean isGameOver=false;
    private FigureColor winner = null;

    public GameManger(FigureColor currentTurn, BoardController boardController) {
        this.currentTurn = currentTurn;
        this.boardController = boardController;
    }

    public void endTurn(Text text){
        if(currentTurn == FigureColor.BLACK) {
            currentTurn = FigureColor.RED;
            text.setText("It's red's turn");
        }
        else {
            currentTurn = FigureColor.BLACK;
            text.setText("It's black's turn");
        }
        checkForVictory();

    }

    //TODO
    private void checkForVictory(){
        System.out.println("I dont know xD");

        //TODO no possible moves victory
        //TODO forced attack and pawn selections constrains to be added




        if(boardController.getFiguresCountByColour(currentTurn) == 0 ){
            isGameOver = true;
            winner = currentTurn == FigureColor.BLACK ? FigureColor.RED : FigureColor.BLACK;
            System.out.println("Player in " + winner + " won! GZ");
        }



    }




    public FigureColor getCurrentTurn() {
        return currentTurn;
    }
}
