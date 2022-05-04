package com.kkulpa.checkers.checkers.gamemanager;

import com.kkulpa.checkers.checkers.BoardController;
import com.kkulpa.checkers.checkers.figurecomponents.FigureColor;
import com.kkulpa.checkers.checkers.figurecomponents.PossibleAttack;
import javafx.scene.text.Text;

import java.util.List;

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

        //TODO some play new game or exit popup
        if (winner != null)
            text.setText("Player with " + winner + " pieces won! GZ");
    }

    private void checkForVictory(){
        //no possible moves win condition
        long numberOfPossibleAttacks = boardController.getFigures().stream()
                .filter(figure -> figure.getFigureColor() == currentTurn)
                .mapToLong(figure -> figure.possibleAttacksCount() + figure.possibleMovesCount())
                .sum();

        // no more figures win condition or out of moves win condition
        if(boardController.getFiguresCountByColour(currentTurn) == 0 || numberOfPossibleAttacks == 0 ){
            isGameOver = true;
            winner = currentTurn == FigureColor.BLACK ? FigureColor.RED : FigureColor.BLACK;
            System.out.println("Player in " + winner + " won! GZ");
        }
    }

    public boolean isFigureClickValid(String id){
        //forced attack logic
        List<String> possibleAttackersIDs = boardController.getFigures().stream()
                .filter(figure -> figure.getFigureColor() == currentTurn)
                .flatMap(figure -> figure.findPossibleAttacks().stream())
                .map(possibleAttack -> possibleAttack.getAttacker().getId())
                .toList();

        if ( possibleAttackersIDs.size() > 0 ){
            return possibleAttackersIDs.contains(id);
        }

        // general move logic
        if ( id.startsWith("r") && currentTurn == FigureColor.RED)
            return true;
        if ( id.startsWith("b") && currentTurn == FigureColor.BLACK)
            return true;

        return false;
    }


    public FigureColor getCurrentTurn() {
        return currentTurn;
    }
}
