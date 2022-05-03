package com.kkulpa.checkers.checkers.figurecomponents;

import com.kkulpa.checkers.checkers.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.kkulpa.checkers.checkers.figurecomponents.Coordinates.isCoordinateValid;

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

    List<Node> marksImageView = new ArrayList<>();
    Map<String, Mark> markMap = new HashMap<>();


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



    //Mark this figure as selected and draws possible moves
    public void select(boolean isForcedToAttack){
        isSelected = true;

        board.getChildren().remove(figureImageView);
        Image selectedPawnImage = new Image("file:src/main/resources/Assets/move.png");
        ImageView selectedPawnImageView = new ImageView(selectedPawnImage);
        selectedPawnImageView.setOpacity(0.5);
        selectedPawnImageView.setId("selection Mark");
        marksImageView.add(selectedPawnImageView);
        board.add(selectedPawnImageView, columnCoordinate, rowCoordinate);
        board.add(figureImageView, columnCoordinate, rowCoordinate);

        markPossibleAttacks(findPossibleAttacks());
        if (!isForcedToAttack)
            markPossibleMoves(findPossibleMoves());


    }

    public void attackFigure(Mark mark){
        if(mark.getMarkType() != MarkTypes.ATTACK && mark.getEnemy() != null)
            return;

        mark.getEnemy().kill();
        moveFigure(mark.getCoordinates());

    }

    public void kill(){
        board.getChildren().remove(figureImageView);
    }

    public void moveFigure(Coordinates c){
        Node node = this.figureImageView;

        deselect();
        GridPane.setColumnIndex(node, c.getColumnIndex());
        GridPane.setRowIndex(node,c.getRowIndex());
        columnCoordinate = c.getColumnIndex();
        rowCoordinate = c.getRowIndex();

        if(figureColor == FigureColor.BLACK && figureType == FigureTypes.PAWN && rowCoordinate == 0)
            promote();
        if(figureColor == FigureColor.RED && figureType == FigureTypes.PAWN && rowCoordinate == 7)
            promote();

    }


    public void deselect(){
        board.getChildren().removeAll(marksImageView);
        markMap.clear();
        marksImageView.clear();
        isSelected = false;

    }

    public int possibleMovesCount(){
        return findPossibleMoves().size();
    }

    public int possibleAttacksCount(){
        return findPossibleAttacks().size();
    }

    public List<Node> getMarksImageView(){
        return marksImageView;
    }

    public String getId() {
        return id;
    }

    public FigureColor getFigureColor() {
        return figureColor;
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

    public Mark getMarkByID(String id){
        return markMap.get(id);
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

    public int getMarksCount(){
        return marksImageView.size();
    }

    public long getAttackMarksCount(){
        return markMap.values().stream().filter(mark -> mark.getMarkType() == MarkTypes.ATTACK).count();
    }

    public boolean isSelected() {
        return isSelected;
    }

    private List<PossibleAttack> findPossibleAttacks(){
        List<PossibleAttack> result = new ArrayList<>();

        //case redFigures
        if(figureColor == FigureColor.RED){
            PossibleAttack leftFrontAttack = findAttack("b",-1,+1);
            if(leftFrontAttack != null)
                result.add(leftFrontAttack);

            PossibleAttack rightFrontAttack = findAttack("b",1,+1);
            if(rightFrontAttack != null)
                result.add(rightFrontAttack);

            if(figureType == FigureTypes.KING){
                PossibleAttack leftBackAttack = findAttack("b",-1,-1);
                if(leftBackAttack != null)
                    result.add(leftBackAttack);

                PossibleAttack rightBackAttack = findAttack("b",1,-1);
                if(rightBackAttack != null)
                    result.add(rightBackAttack);
            }
        }

        //case blackFigures
        if(figureColor == FigureColor.BLACK){
            PossibleAttack leftFrontAttack = findAttack("r",-1,-1);
            if(leftFrontAttack != null)
                result.add(leftFrontAttack);

            PossibleAttack rightFrontAttack = findAttack("r",1,-1);
            if(rightFrontAttack != null)
                result.add(rightFrontAttack);

            if(figureType == FigureTypes.KING){
                PossibleAttack leftBackAttack = findAttack("r",-1,+1);
                if(leftBackAttack != null)
                    result.add(leftBackAttack);

                PossibleAttack rightBackAttack = findAttack("r",1,+1);
                if(rightBackAttack != null)
                    result.add(rightBackAttack);
            }
        }

        return result;
    }

    private PossibleAttack findAttack(String enemyColourLetter, int columnAxis, int rowAxis) {
        if(        isCellOccupiedByEnemy(columnCoordinate + columnAxis , rowCoordinate + rowAxis)
                && isCoordinateValid(columnCoordinate + (columnAxis*2), rowCoordinate + (rowAxis*2))
                && isCellEmpty(columnCoordinate + (columnAxis*2), rowCoordinate + (rowAxis*2))
        ){

                List<Node> enemyNode = getNodesByRowColumnIndex(columnCoordinate + columnAxis, rowCoordinate + rowAxis,board)
                        .stream().filter(node -> node.getId().startsWith(enemyColourLetter))
                        .toList();

                if(enemyNode.size() > 1)
                    System.out.println("To much enemy in cell " + (columnCoordinate + columnAxis) + " "+ (rowCoordinate + rowAxis) );

                Figure enemyFigure = boardController.getFigureFromMap( enemyNode.get(0).getId() );

                PossibleAttack attack = new PossibleAttack(this, enemyFigure, new Coordinates(columnCoordinate + (columnAxis*2), rowCoordinate + (rowAxis*2)));

                return attack;
            }
            return null;
    }

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

    private void markPossibleAttacks(List<PossibleAttack> attacks){

        AtomicInteger i = new AtomicInteger(0);

        attacks.stream()
                    .forEach(possibleAttack -> {
                    Image possibleAttackImage = new Image("file:src/main/resources/Assets/attackMove.png");
                    ImageView imageView = new ImageView(possibleAttackImage);
                    imageView.setOpacity(0.5);
                    imageView.setId(this.id + " AttackMark " + i.intValue());
                    i.incrementAndGet();
                    imageView.setOnMouseClicked(boardController::onMarkClicked);
                    board.add(imageView,possibleAttack.getAfterAttackCoordinates().getColumnIndex(), possibleAttack.getAfterAttackCoordinates().getRowIndex());
                    marksImageView.add(imageView);
                    Mark mark = new Mark(imageView.getId(), imageView, possibleAttack.getAfterAttackCoordinates(), MarkTypes.ATTACK, possibleAttack.getEnemy());
                    markMap.put(mark.getId(), mark);
                });
            }

    private void markPossibleMoves(List<Coordinates>coordinates ){

        AtomicInteger i = new AtomicInteger(0);

        coordinates.forEach(coordinate -> {
        Image possibleMovesImage = new Image("file:src/main/resources/Assets/movesThatCanBeSelected.png");
        ImageView imageView = new ImageView(possibleMovesImage);
        imageView.setOpacity(0.5);
        imageView.setId(this.id + " MoveMark " + i.intValue());
        i.incrementAndGet();
        imageView.setOnMouseClicked(boardController::onMarkClicked);
        board.add(imageView,coordinate.getColumnIndex(),coordinate.getRowIndex());
        marksImageView.add(imageView);
        Mark mark = new Mark(imageView.getId(), imageView, coordinate, MarkTypes.MOVE);
        markMap.put(mark.getId(), mark);
        });

    }


    private boolean isCellEmpty(int columnIndex, int rowIndex){
        if (getNodesByRowColumnIndex(columnIndex, rowIndex, board).size() == 0)
            return true;

        List<Node> nodesByRowColumnIndex = getNodesByRowColumnIndex(columnIndex, rowIndex, board).stream()
                .filter(Objects::nonNull)
                .filter( node -> !node.getId().contains("Mark")).toList();
        return nodesByRowColumnIndex.size() == 0 ;
    }

    private boolean isCellOccupiedByEnemy(int columnIndex, int rowIndex){
        List<Node> nodesByRowColumnIndex = getNodesByRowColumnIndex(columnIndex, rowIndex, board);
        if(nodesByRowColumnIndex.size()==0)
            return false;

        List<Node> collect = nodesByRowColumnIndex.stream().filter(node -> {
                    if( node.getId() == null)
                        return false;
                    if (figureColor == FigureColor.RED) {
                        return node.getId().startsWith("b");
                    }
                    if ((figureColor == FigureColor.BLACK)) {
                        return node.getId().startsWith("r");
                    }
                    return false;
                }
        ).toList();

        return collect.size() > 0;
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



    //TODO change to private
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


