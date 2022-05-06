package com.kkulpa.checkers.checkers.AI;

import com.kkulpa.checkers.checkers.BoardController;
import com.kkulpa.checkers.checkers.figurecomponents.Figure;
import com.kkulpa.checkers.checkers.figurecomponents.PossibleAttack;
import com.kkulpa.checkers.checkers.figurecomponents.PossibleMove;
import java.util.List;
import java.util.Random;

import static com.kkulpa.checkers.checkers.figurecomponents.FigureColor.RED;

public class AiOpponent {

    public static void executeTurn(BoardController boardController) {

        List<Figure> figures = boardController.getFigures().stream().filter(figure -> figure.getFigureColor() == RED).toList();

        List<PossibleAttack> possibleAttacks = figures.stream()
                .flatMap(figure -> figure.findPossibleAttacks().stream())
                .toList();

        List<PossibleMove> possibleMoves = figures.stream()
                .flatMap(figure -> figure.getPossibleMoves().stream()
                                    .map(coordinates -> new PossibleMove(figure, coordinates))
                                    .toList().stream())
                .toList();

        //TODO mozna zrobic madrzejsze AI
        //TODO sprobowac trigerowac ruch przez event

        Random rand = new Random();
        if (possibleAttacks.size() > 0) {
            PossibleAttack aiAttack = possibleAttacks.get(rand.nextInt(possibleAttacks.size()));
            boardController.executeAiAttack(aiAttack);
        } else if (possibleMoves.size() > 0) {
            PossibleMove aiMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            boardController.executeAiMove(aiMove);
        }
    }

    public static PossibleAttack generateAttackWhenForced(Figure attackingFigure){
        Random rand = new Random();
        return attackingFigure.findPossibleAttacks().get(rand.nextInt(attackingFigure.possibleAttacksCount()));
    }
}
