package com.kkulpa.checkers.checkers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Figure {

    private FigureTypes figureType;
    private FigureColor figureColor;
    private int columntCoordinate;
    private int rowCoordinate;
    private Image figureImage;
    private ImageView figureImageView;
    private GridPane board;
    private String id;
    private boolean isSelected = false;
    private BoardController bc;

    public Figure(FigureTypes figureType, FigureColor figureColor, int columnCoordinate, int rowCoordinate, GridPane board, BoardController boardController, String id) {
        this.figureType = figureType;
        this.figureColor = figureColor;
        this.columntCoordinate = columnCoordinate;
        this.rowCoordinate = rowCoordinate;
        this.board = board;
        this.id = id;
        this.bc=boardController;

        if (figureColor == FigureColor.RED){
        this.figureImage = new Image("file:src/main/resources/Assets/RedPawn.png");
        }
        else{
            this.figureImage = new Image("file:src/main/resources/Assets/BlackPawn.png");
        }
        figureImageView = new ImageView(figureImage);
        figureImageView.setOnMouseClicked(boardController::onPawnClick);
        figureImageView.setId(id);
        board.add(figureImageView, columntCoordinate, rowCoordinate);

    }

    @Override
    public String toString() {
        return "Figure{" +
                "figureType=" + figureType +
                ", figureColor=" + figureColor +
                ", columntCoordinate=" + columntCoordinate +
                ", rowCoordinate=" + rowCoordinate +
                ", id='" + id + '\'' +
                '}';
    }
}
