package com.kkulpa.checkers.checkers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BoardController {

    @FXML
    private GridPane board;

    @FXML
    private ImageView selectedIcon;





    private int selectedColumn = 0;
    private int selectedRow = 0;



    public void onPawnClick(MouseEvent e){
        System.out.println(" ");

        Node clickedNode = (Node)e.getTarget();

        Integer c = GridPane.getColumnIndex(clickedNode);
        Integer r = GridPane.getRowIndex(clickedNode);

        int targerColumnIndex  = c == null ? 0 : c;
        int targetRowIndex     = r == null ? 0 : r;

        System.out.println("clicked " + clickedNode.getId());
        System.out.println("Adress: " + targerColumnIndex + " " + targetRowIndex);

        Image selectedPawnImage = new Image("file:src/main/resources/Assets/movesThatCanBeSelected.png");
        ImageView imageView = new ImageView(selectedPawnImage);
        imageView.setOpacity(0.5);

        Image selectedPawnImage2 = new Image("file:src/main/resources/Assets/movesThatCanBeSelected.png");
        ImageView imageView2 = new ImageView(selectedPawnImage2);
        imageView2.setOpacity(0.5);

        board.add(imageView,targerColumnIndex-1,targetRowIndex+1);
        board.add(imageView2,targerColumnIndex+1,targetRowIndex+1);

        imageView2.setOnMouseClicked(this::onFieldClick);

        System.out.println(clickedNode.getLayoutX() );









    }

    public void onFieldClick(MouseEvent event){
        System.out.println("dziala");
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
