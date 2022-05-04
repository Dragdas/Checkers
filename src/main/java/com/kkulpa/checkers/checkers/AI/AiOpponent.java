package com.kkulpa.checkers.checkers.AI;

import com.kkulpa.checkers.checkers.figurecomponents.Figure;
import com.kkulpa.checkers.checkers.figurecomponents.PossibleAttack;
import com.kkulpa.checkers.checkers.figurecomponents.PossibleMove;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AiOpponent {



    public static void executeTurn(List<Figure> figures){

        List<PossibleAttack> possibleAttacks = figures.stream()
                .flatMap(figure -> figure.findPossibleAttacks().stream())
                .toList();

        List<PossibleMove> possibleMoves = figures.stream()
                .flatMap(figure -> figure.getPossibleMoves().stream()
                                    .map(coordinates -> new PossibleMove(figure, coordinates))
                                    .toList().stream()
                ).toList();


        Random rand = new Random();
        if(possibleAttacks.size() > 0){
            System.out.println("execute attack " + possibleAttacks.get(rand.nextInt(possibleAttacks.size())));
        }else if (possibleMoves.size() > 0 ){
            System.out.println("execute move " + possibleMoves.get(rand.nextInt(possibleMoves.size())));
        }


    }


}
