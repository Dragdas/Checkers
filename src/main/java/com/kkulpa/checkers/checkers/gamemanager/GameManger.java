package com.kkulpa.checkers.checkers.gamemanager;

import com.kkulpa.checkers.checkers.ai.AiOpponent;
import com.kkulpa.checkers.checkers.BoardController;
import com.kkulpa.checkers.checkers.figurecomponents.FigureColor;
import javafx.scene.text.Text;

import java.util.List;

import static com.kkulpa.checkers.checkers.figurecomponents.FigureColor.*;

public class GameManger {

    private FigureColor currentTurn;
    private BoardController boardController;
    private Text turnIndicator;
    private boolean isGameOver=false;
    private FigureColor winner = null;

    public GameManger(FigureColor startingColour, BoardController boardController) {
        this.currentTurn = startingColour;
        this.boardController = boardController;
        this.turnIndicator = boardController.getTurnIndicator();
    }

    public void endTurn(){
        if(currentTurn == BLACK) {
            currentTurn = RED;
            turnIndicator.setText("It's red's turn");
        }
        else {
            currentTurn = BLACK;
            turnIndicator.setText("It's black's turn");
        }
        checkForVictory();


        if (winner != null){
            turnIndicator.setText("PLAYER WITH " + winner + " PIECES WON! GZ");
            return;
        }

        if( currentTurn == RED)
            AiOpponent.executeTurn(boardController);

    }

    private void checkForVictory(){
        //no possible moves win condition
        long numberOfPossibleActions = boardController.getFigures().stream()
                .filter(figure -> figure.getFigureColor() == currentTurn)
                .mapToLong(figure -> figure.getPossibleAttacksCount() + figure.getPossibleMovesCount())
                .sum();

        // no more figures win condition or out of moves win condition
        if(boardController.getFiguresCountByColour(currentTurn) == 0 || numberOfPossibleActions == 0 ){
            isGameOver = true;
            winner = currentTurn == BLACK ? RED : BLACK;
        }
    }

    public boolean isFigureClickValid(String figureId){
        //forced attack logic
        List<String> possibleAttackersIDs = boardController.getFigures().stream()
                .filter(figure -> figure.getFigureColor() == currentTurn)
                .flatMap(figure -> figure.findPossibleAttacks().stream())
                .map(possibleAttack -> possibleAttack.getAttacker().getId())
                .toList();

        if ( possibleAttackersIDs.size() > 0 ){
            return possibleAttackersIDs.contains(figureId);
        }

        // general move logic
        if ( figureId.startsWith("r") && currentTurn == RED)
            return true;
        if ( figureId.startsWith("b") && currentTurn == BLACK)
            return true;

        return false;
    }

    public FigureColor getCurrentTurn() {
        return currentTurn;
    }
}
