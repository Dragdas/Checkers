package com.kkulpa.checkers.checkers.figurecomponents;

public class PossibleMove {

    private Figure figure;
    private Coordinates coordinatesAfterMove;

    public PossibleMove(Figure figure, Coordinates coordinatesAfterMove) {
        this.figure = figure;
        this.coordinatesAfterMove = coordinatesAfterMove;
    }

    public Figure getFigure() {
        return figure;
    }

    public Coordinates getCoordinatesAfterMove() {
        return coordinatesAfterMove;
    }

    @Override
    public String toString() {
        return "Move figure " +
                "if =" + figure.getId() +
                ", to :" + coordinatesAfterMove +
                '}';
    }
}
