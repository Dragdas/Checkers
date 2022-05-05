package com.kkulpa.checkers.checkers.AI;

import com.kkulpa.checkers.checkers.BoardController;
import com.kkulpa.checkers.checkers.figurecomponents.Figure;
import com.kkulpa.checkers.checkers.figurecomponents.PossibleAttack;
import com.kkulpa.checkers.checkers.figurecomponents.PossibleMove;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AiOpponent {



    public static void executeTurn(List<Figure> figures, BoardController boardController) {

        List<PossibleAttack> possibleAttacks = figures.stream()
                .flatMap(figure -> figure.findPossibleAttacks().stream())
                .toList();

        List<PossibleMove> possibleMoves = figures.stream()
                .flatMap(figure -> figure.getPossibleMoves().stream()
                                    .map(coordinates -> new PossibleMove(figure, coordinates))
                                    .toList().stream()
                ).toList();


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


}
