package com.kkulpa.checkers.checkers;

import com.kkulpa.checkers.checkers.figurecomponents.*;
import com.kkulpa.checkers.checkers.gamemanager.GameManger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class BoardController implements Initializable {

    @FXML
    private GridPane board;

    @FXML
    private Text turnIndicator;

    private Map<String, Figure> figureMap = new HashMap<>();
    private Figure selectedFigure;
    private GameManger gameManger;
    private boolean isAfterAttack = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board.getChildren().removeAll(board.getChildren());
        gameManger = new GameManger(FigureColor.BLACK, this);
        fillBoardWithPawns();
//        figureMap.put("bp5", new Figure(FigureTypes.PAWN,FigureColor.BLACK,6,3,board,this,"bp5"));
//        figureMap.put("rp4", new Figure(FigureTypes.PAWN, FigureColor.RED, 3,2, board,this, "rp4"));
//        figureMap.put("rp3", new Figure(FigureTypes.PAWN, FigureColor.RED, 1,4, board,this, "rp3"));
//        figureMap.put("rp2", new Figure(FigureTypes.PAWN, FigureColor.RED, 1,6, board,this, "rp2"));
//        figureMap.put("rp1", new Figure(FigureTypes.PAWN, FigureColor.RED, 1,2, board,this, "rp1"));
//        figureMap.put("rp11", new Figure(FigureTypes.PAWN, FigureColor.RED, 5,4, board,this, "rp11"));
//        figureMap.put("bp2", new Figure(FigureTypes.PAWN,FigureColor.BLACK,0,7,board,this,"bp2"));
//        figureMap.put("bp4", new Figure(FigureTypes.PAWN,FigureColor.BLACK,4,5,board,this,"bp4"));
//        figureMap.put("rp9", new Figure(FigureTypes.PAWN, FigureColor.RED, 3,4, board,this, "rp9"));
//
//
//        Figure selected = figureMap.get("rp11");
//        selected.promote();
//        selected = figureMap.get("bp2");
//        selected.promote();

    }

    //TODO turn control & win conditions
    // TODO ai

    public void onPawnClick(MouseEvent e){
        if (isAfterAttack)
            return;

        //mark selected node for future actions
        Node clickedNode = (Node)e.getTarget();
        selectedFigure = figureMap.get(clickedNode.getId());

        if(selectedFigure.getFigureColor() == gameManger.getCurrentTurn()) {
            //deselect previous selection
            figureMap.values().stream()
                    .filter(Figure::isSelected)
                    .forEach(Figure::deselect);
            // select figure
            selectedFigure.select(false);
        }

    }

    public void onMarkClicked(MouseEvent event){

        Node clickedNode = (Node)event.getTarget();
        Mark selectedMark = selectedFigure.getMarkByID(clickedNode.getId());

        if( selectedMark.getMarkType() == MarkTypes.ATTACK ){
            selectedFigure.attackFigure(selectedMark);
            figureMap.remove(selectedMark.getEnemy().getId());
            isAfterAttack = true;
            selectedFigure.select(true);
            if(selectedFigure.getAttackMarksCount() == 0){
                selectedFigure.deselect();
                gameManger.endTurn(turnIndicator);
                isAfterAttack = false;
            }



        }else if (selectedMark.getMarkType() == MarkTypes.MOVE){
            selectedFigure.moveFigure(selectedMark.getCoordinates());
            gameManger.endTurn(turnIndicator);
        }

    }

    public Figure getFigureFromMap(String id){
        return figureMap.get(id);
    }

    public long getFiguresCountByColour(FigureColor color){
        return figureMap.values().stream().filter(figure -> figure.getFigureColor() == color).count();
    }

    private void fillBoardWithPawns(){
        figureMap.put("rp1", new Figure(FigureTypes.PAWN, FigureColor.RED, 1,0, board,this, "rp1"));
        figureMap.put("rp2", new Figure(FigureTypes.PAWN, FigureColor.RED, 3,0, board,this, "rp2"));
        figureMap.put("rp3", new Figure(FigureTypes.PAWN, FigureColor.RED, 5,0, board,this, "rp3"));
        figureMap.put("rp4", new Figure(FigureTypes.PAWN, FigureColor.RED, 7,0, board,this, "rp4"));
        figureMap.put("rp5", new Figure(FigureTypes.PAWN, FigureColor.RED, 0,1, board,this, "rp5"));
        figureMap.put("rp6", new Figure(FigureTypes.PAWN, FigureColor.RED, 2,1, board,this, "rp6"));
        figureMap.put("rp7", new Figure(FigureTypes.PAWN, FigureColor.RED, 4,1, board,this, "rp7"));
        figureMap.put("rp8", new Figure(FigureTypes.PAWN, FigureColor.RED, 6,1, board,this, "rp8"));
        figureMap.put("rp9", new Figure(FigureTypes.PAWN, FigureColor.RED, 1,2, board,this, "rp9"));
        figureMap.put("rp10", new Figure(FigureTypes.PAWN, FigureColor.RED, 3,2, board,this, "rp10"));
        figureMap.put("rp11", new Figure(FigureTypes.PAWN, FigureColor.RED, 5,2, board,this, "rp11"));
        figureMap.put("rp12", new Figure(FigureTypes.PAWN, FigureColor.RED, 7,2, board,this, "rp12"));

        figureMap.put("bp1", new Figure(FigureTypes.PAWN,FigureColor.BLACK,0,5,board,this,"bp1"));
        figureMap.put("bp2", new Figure(FigureTypes.PAWN,FigureColor.BLACK,2,5,board,this,"bp2"));
        figureMap.put("bp3", new Figure(FigureTypes.PAWN,FigureColor.BLACK,4,5,board,this,"bp3"));
        figureMap.put("bp4", new Figure(FigureTypes.PAWN,FigureColor.BLACK,6,5,board,this,"bp4"));
        figureMap.put("bp5", new Figure(FigureTypes.PAWN,FigureColor.BLACK,1,6,board,this,"bp5"));
        figureMap.put("bp6", new Figure(FigureTypes.PAWN,FigureColor.BLACK,3,6,board,this,"bp6"));
        figureMap.put("bp7", new Figure(FigureTypes.PAWN,FigureColor.BLACK,5,6,board,this,"bp7"));
        figureMap.put("bp8", new Figure(FigureTypes.PAWN,FigureColor.BLACK,7,6,board,this,"bp8"));
        figureMap.put("bp9", new Figure(FigureTypes.PAWN,FigureColor.BLACK,0,7,board,this,"bp9"));
        figureMap.put("bp10", new Figure(FigureTypes.PAWN,FigureColor.BLACK,2,7,board,this,"bp10"));
        figureMap.put("bp11", new Figure(FigureTypes.PAWN,FigureColor.BLACK,4,7,board,this,"bp11"));
        figureMap.put("bp12", new Figure(FigureTypes.PAWN,FigureColor.BLACK,6,7,board,this,"bp12"));
    }

}
