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



    public void onPawnClick(MouseEvent e){
        System.out.println(" ");

        Node clickedNode = (Node)e.getTarget();

        Integer c = GridPane.getColumnIndex(clickedNode);
        Integer r = GridPane.getRowIndex(clickedNode);

        int targerColumnIndex  = c == null ? 0 : c;
        int targetRowIndex     = r == null ? 0 : r;

        System.out.println("clicked " + clickedNode.getId());
        System.out.println("Adress: " + targerColumnIndex + " " + targetRowIndex);









    }



    private Node getNodeByRowColumnIndex ( final int column, final int row, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            Integer c = GridPane.getColumnIndex(node);
            Integer r = GridPane.getRowIndex(node);
            int currentNodeColumnIndex  = c == null ? 0 : c;
            int currentNodeRowIndex     = r == null ? 0 : r;

            if(currentNodeRowIndex == row && currentNodeColumnIndex == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    private void moveNodeToCoordinates(Node node, GridPane board, int column, int row){
        if (getNodeByRowColumnIndex(column, row, board) == null ) {
            GridPane.setColumnIndex(node, column);
            GridPane.setRowIndex(node,row);
        }

    }


}
