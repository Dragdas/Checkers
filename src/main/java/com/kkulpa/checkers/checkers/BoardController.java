package com.kkulpa.checkers.checkers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

public class BoardController implements Initializable {

    @FXML
    private GridPane board;

    Map<String, Figure> figureMap = new HashMap<>();



    private Node selectedNode;
    private List<Node> selectionMarks = new ArrayList<>();

    //TODO wszystkie piony będą sie pojawiać jako obiekty Figure i będa zapisywane w mapie pionów
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board.getChildren().removeAll(board.getChildren());
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



    public void onPawnClick(MouseEvent e){




        //mark selected node for future actions
        Node clickedNode = (Node)e.getTarget();
        Figure selected = figureMap.get(clickedNode.getId());
        selectedNode = clickedNode; // ???

        //TODO zmienic architekture zeby funkcje on click wywyoływały metody w figurach



        System.out.println(selected.toString());


        //work out coordinate values
        Integer c = GridPane.getColumnIndex(clickedNode);
        Integer r = GridPane.getRowIndex(clickedNode);
        int targetColumnIndex  = c == null ? 0 : c;
        int targetRowIndex     = r == null ? 0 : r;

        //visually mark selected pawn
        selectionMarks.forEach(node -> board.getChildren().remove(node));
        Image selectedPawnImage = new Image("file:src/main/resources/Assets/move.png");
        ImageView selectedPawnImageView = new ImageView(selectedPawnImage);
        selectedPawnImageView.setOpacity(0.5);
        board.add(selectedPawnImageView, targetColumnIndex, targetRowIndex);
        selectionMarks.add(selectedPawnImageView);

        //mark possible moves




        Image possibleMovesImage = new Image("file:src/main/resources/Assets/movesThatCanBeSelected.png");
        ImageView imageView = new ImageView(possibleMovesImage);
        imageView.setOpacity(0.5);

        Image possibleMovesImage2 = new Image("file:src/main/resources/Assets/movesThatCanBeSelected.png");
        ImageView imageView2 = new ImageView(possibleMovesImage2);
        imageView2.setOpacity(0.5);

        //TODO trzeba dopisać opsobną metode to dodawania nowego noda do planszy
        //moveNodeToCoordinates(imageView, board, targetColumnIndex-1,targetRowIndex+1 );
        //moveNodeToCoordinates(imageView2, board, targetColumnIndex+1,targetRowIndex+1);
        board.add(imageView,targetColumnIndex-1,targetRowIndex+1);
        board.add(imageView2,targetColumnIndex+1,targetRowIndex+1);

        imageView2.setOnMouseClicked(this::onFieldClick);

        //TODO dopisać dodawanie do piona zielonego znaczka zaznaczenia

        //TODO dopisac funkcje on enter i on exit






    }

    public void onFieldClick(MouseEvent event){
        System.out.println("dziala");
        System.out.println("Selected pawn: " + selectedNode.getId());
    }



    // TODO to trzeba sprawdzic ale prawdopodobnie to powinno zwracać listę bo w jednej komurce moze byc więcej rzeczy
    private List<Node> getNodeByRowColumnIndex (final int column, final int row, GridPane gridPane) {
        List<Node> resultList = new ArrayList<>();
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            Integer c = GridPane.getColumnIndex(node);
            Integer r = GridPane.getRowIndex(node);
            int currentNodeColumnIndex  = c == null ? 0 : c;
            int currentNodeRowIndex     = r == null ? 0 : r;

            if(currentNodeRowIndex == row && currentNodeColumnIndex == column) {
                resultList.add(node);
            }
        }

        return resultList;
    }

    private void moveNodeToCoordinates(Node node, GridPane board, int column, int row){
        if (getNodeByRowColumnIndex(column, row, board).size() == 0 ) {
            GridPane.setColumnIndex(node, column);
            GridPane.setRowIndex(node,row);
        }

    }



}
