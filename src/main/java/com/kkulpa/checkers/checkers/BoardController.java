package com.kkulpa.checkers.checkers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

public class BoardController implements Initializable {

    @FXML
    private GridPane board;

    private Map<String, Figure> figureMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board.getChildren().removeAll(board.getChildren());

        //fillBoardWithPawns();
        figureMap.put("bp5", new Figure(FigureTypes.PAWN,FigureColor.BLACK,6,3,board,this,"bp5"));
        figureMap.put("rp4", new Figure(FigureTypes.PAWN, FigureColor.RED, 3,2, board,this, "rp4"));
        figureMap.put("rp3", new Figure(FigureTypes.PAWN, FigureColor.RED, 1,4, board,this, "rp3"));
        figureMap.put("rp2", new Figure(FigureTypes.PAWN, FigureColor.RED, 1,6, board,this, "rp2"));
        figureMap.put("rp1", new Figure(FigureTypes.PAWN, FigureColor.RED, 1,2, board,this, "rp1"));
        figureMap.put("rp11", new Figure(FigureTypes.PAWN, FigureColor.RED, 5,4, board,this, "rp11"));
        figureMap.put("bp2", new Figure(FigureTypes.PAWN,FigureColor.BLACK,0,7,board,this,"bp2"));
        figureMap.put("bp4", new Figure(FigureTypes.PAWN,FigureColor.BLACK,4,5,board,this,"bp4"));
        figureMap.put("rp9", new Figure(FigureTypes.PAWN, FigureColor.RED, 3,4, board,this, "rp9"));


        Figure selected = figureMap.get("rp11");
        selected.promote();
        selected = figureMap.get("bp2");
        selected.promote();

    }

    public void onPawnClick(MouseEvent e){
        //mark selected node for future actions
        Node clickedNode = (Node)e.getTarget();
        Figure selected = figureMap.get(clickedNode.getId());

        //deselect previous selection
        figureMap.values().stream()
                .filter(Figure::isSelected)
                .forEach(Figure::deselect);
        // select figure
        selected.select();

    }

    public void onMarkClicked(MouseEvent event){
        System.out.println("dziala");
    }

    public Figure getFigureFromMap(String id){
        return figureMap.get(id);
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
        figureMap.put("bp11", new Figure(FigureTypes.PAWN,FigureColor.BLACK,4,7,board,this,"bp111"));
        figureMap.put("bp12", new Figure(FigureTypes.PAWN,FigureColor.BLACK,6,7,board,this,"bp12"));
    }

}
