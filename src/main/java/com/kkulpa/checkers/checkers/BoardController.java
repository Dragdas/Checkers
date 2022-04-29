package com.kkulpa.checkers.checkers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BoardController {

    @FXML
    private GridPane board;

    @FXML
    private ImageView redPawn1;


    public void onPawnClick(MouseEvent e){

        ObservableList<Node> childrens = board.getChildren();
        Node clickedNode = null;
        if(e.getTarget() instanceof Node){
            clickedNode = (Node)e.getTarget();
        }

//        for(Node node : childrens){
//            if(node.equals(clickedNode)){
//
//            }
//
//
//        }



        System.out.println("clicked " + clickedNode.getId());



    }


}
