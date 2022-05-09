package com.kkulpa.checkers.checkers.figurecomponents;

import com.kkulpa.checkers.checkers.BoardController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.kkulpa.checkers.checkers.figurecomponents.FigureColor.RED;

public class DisplayElementsFactory {

    private static final Image redPawnImage =    new Image("file:src/main/resources/Assets/RedPawn.png");
    private static final Image blackPawnImage =  new Image("file:src/main/resources/Assets/BlackPawn.png");
    private static final Image attackMarkImage = new Image("file:src/main/resources/Assets/attackMove.png");
    private static final Image moveMarkImage =   new Image("file:src/main/resources/Assets/movesThatCanBeSelected.png");
    private static final Image selectMarkImage = new Image("file:src/main/resources/Assets/move.png");

    public static ImageView getPawnImageView(FigureColor color, String id, BoardController boardController){
        Image image;
        if (color == RED){
            image = redPawnImage;
        }
        else{
            image  = blackPawnImage;
        }
        ImageView imageView = new ImageView(image);
        imageView.setOnMouseClicked(boardController::onPawnClick);
        imageView.setId(id);

        return imageView;

    }

    public static ImageView getAttackMarkImageView(String id, int idIncrement, BoardController boardController){
        ImageView imageView = new ImageView(attackMarkImage);
        imageView.setOpacity(0.5);
        imageView.setId(id + " AttackMark " + idIncrement);
        imageView.setOnMouseClicked(boardController::onMarkClicked);

        return imageView;
    }

    public static ImageView getMoveMarkImageView(String id, int idIncrement, BoardController boardController){

        ImageView imageView = new ImageView(moveMarkImage);
        imageView.setOpacity(0.5);
        imageView.setId(id + " MoveMark " + idIncrement);
        imageView.setOnMouseClicked(boardController::onMarkClicked);

        return imageView;
    }

    public static ImageView getSelectMarkImageView(){
        ImageView selectedPawnImageView = new ImageView(selectMarkImage);
        selectedPawnImageView.setOpacity(0.5);
        selectedPawnImageView.setId("selection Mark");

        return selectedPawnImageView;
    }

}
