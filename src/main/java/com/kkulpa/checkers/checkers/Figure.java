package com.kkulpa.checkers.checkers;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Figure {

    private FigureTypes figureType;
    private FigureColor figureColor;
    private int columnCoordinate;
    private int rowCoordinate;
    private Image figureImage;
    private ImageView figureImageView;
    private GridPane board;
    private String id;
    private boolean isSelected = false;
    private BoardController boardController;

    List<Node> marks = new ArrayList<>();


    public Figure(FigureTypes figureType, FigureColor figureColor, int columnCoordinate, int rowCoordinate, GridPane board, BoardController boardController, String id) {
        this.figureType = figureType;
        this.figureColor = figureColor;
        this.columnCoordinate = columnCoordinate;
        this.rowCoordinate = rowCoordinate;
        this.board = board;
        this.id = id;
        this.boardController =boardController;

        if (figureColor == FigureColor.RED){
        this.figureImage = new Image("file:src/main/resources/Assets/RedPawn.png");
        }
        else{
            this.figureImage = new Image("file:src/main/resources/Assets/BlackPawn.png");
        }
        figureImageView = new ImageView(figureImage);
        figureImageView.setOnMouseClicked(this.boardController::onPawnClick);

        figureImageView.setId(id);
        board.add(figureImageView, this.columnCoordinate, rowCoordinate);

    }

    @Override
    public String toString() {
        return "Figure{" +
                "figureType=" + figureType +
                ", figureColor=" + figureColor +
                ", columntCoordinate=" + columnCoordinate +
                ", rowCoordinate=" + rowCoordinate +
                ", id='" + id + '\'' +
                '}';
    }

    public boolean isSelected() {
        return isSelected;
    }


    //Mark this figure as selected and draws possible moves
    public void select(){
        isSelected = true;

        board.getChildren().remove(figureImageView);
        Image selectedPawnImage = new Image("file:src/main/resources/Assets/move.png");
        ImageView selectedPawnImageView = new ImageView(selectedPawnImage);
        selectedPawnImageView.setOpacity(0.5);
        marks.add(selectedPawnImageView);
        board.add(selectedPawnImageView, columnCoordinate, rowCoordinate);
        board.add(figureImageView, columnCoordinate, rowCoordinate);

        markPossibleMoves(findPossibleMoves());

    }

    public void deselect(){
        board.getChildren().removeAll(marks);
        isSelected = false;

    }

    public List<Node> getMarks(){
        return marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Figure)) return false;
        Figure figure = (Figure) o;
        return Objects.equals(id, figure.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //TODO update attack case
    private List<Coordinates> findPossibleMoves(){
        List<Coordinates> result = new ArrayList<>();

        // case red figure
        if(figureColor == FigureColor.RED){
            if(isCellEmpty(columnCoordinate - 1,rowCoordinate + 1))
                result.add(new Coordinates(columnCoordinate - 1, rowCoordinate + 1));
            if(isCellEmpty(columnCoordinate + 1,rowCoordinate + 1))
                result.add(new Coordinates(columnCoordinate + 1,rowCoordinate + 1));

            if(figureType == FigureTypes.KING){
                if(isCellEmpty(columnCoordinate - 1,rowCoordinate - 1))
                    result.add(new Coordinates(columnCoordinate - 1, rowCoordinate - 1));
                if(isCellEmpty(columnCoordinate + 1,rowCoordinate - 1))
                    result.add(new Coordinates(columnCoordinate + 1,rowCoordinate - 1));
            }
        }

        // case black figure
        if(figureColor == FigureColor.BLACK){
            if(isCellEmpty(columnCoordinate - 1,rowCoordinate - 1))
                result.add(new Coordinates(columnCoordinate - 1, rowCoordinate - 1));
            if(isCellEmpty(columnCoordinate + 1,rowCoordinate - 1))
                result.add(new Coordinates(columnCoordinate + 1,rowCoordinate - 1));

            if(figureType == FigureTypes.KING){
                if(isCellEmpty(columnCoordinate - 1,rowCoordinate + 1))
                    result.add(new Coordinates(columnCoordinate - 1, rowCoordinate + 1));
                if(isCellEmpty(columnCoordinate + 1,rowCoordinate + 1))
                    result.add(new Coordinates(columnCoordinate + 1,rowCoordinate + 1));
            }
        }

        return result.stream()
                .filter(coordinates -> coordinates.getColumnIndex() < 8 && coordinates.getColumnIndex() >= 0 )
                .filter(coordinates -> coordinates.getRowIndex() >= 0 && coordinates.getRowIndex() < 8)
                .collect(Collectors.toList());
    }

    private void markPossibleMoves(List<Coordinates>coordinates ){

        coordinates.forEach(coordinate -> {

        Image possibleMovesImage = new Image("file:src/main/resources/Assets/movesThatCanBeSelected.png");
        ImageView imageView = new ImageView(possibleMovesImage);
        imageView.setOpacity(0.5);
        imageView.setOnMouseClicked(boardController::onMarkClicked);
        board.add(imageView,coordinate.getColumnIndex(),coordinate.getRowIndex());
        marks.add(imageView);
        });



    }


    private boolean isCellEmpty(int columnIndex, int rowIndex){
        List<Node> nodesByRowColumnIndex = getNodesByRowColumnIndex(columnIndex, rowIndex, board);
        return nodesByRowColumnIndex.size() == 0;
    }

    private List<Node> getNodesByRowColumnIndex (final int column, final int row, GridPane gridPane) {
        List<Node> resultList = new ArrayList<>();
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
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
        if (getNodesByRowColumnIndex(column, row, board).size() == 0 ) {
            GridPane.setColumnIndex(node, column);
            GridPane.setRowIndex(node,row);
        }

    }

    public void promote() {
        figureType = FigureTypes.KING;
        if(figureColor == FigureColor.RED){
            Image kingsImage = new Image("file:src/main/resources/Assets/RedKing.png");
            figureImageView.setImage(kingsImage);
        }
        if(figureColor == FigureColor.BLACK){
            Image kingsImage = new Image("file:src/main/resources/Assets/BlackKing.png");
            figureImageView.setImage(kingsImage);
        }
    }


}


//
//    Integer c = GridPane.getColumnIndex(clickedNode);
//    Integer r = GridPane.getRowIndex(clickedNode);
//    int targetColumnIndex  = c == null ? 0 : c;
//    int targetRowIndex     = r == null ? 0 : r;