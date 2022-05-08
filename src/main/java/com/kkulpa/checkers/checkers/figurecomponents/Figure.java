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

import static com.kkulpa.checkers.checkers.figurecomponents.Coordinates.isCoordinateWithinTheBoard;
import static com.kkulpa.checkers.checkers.figurecomponents.FigureColor.*;
import static com.kkulpa.checkers.checkers.figurecomponents.FigureTypes.*;

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
    private List<Node> marksImageView = new ArrayList<>();
    private Map<String, Mark> markMap = new HashMap<>();


    public Figure(FigureTypes figureType, FigureColor figureColor, int columnCoordinate, int rowCoordinate, GridPane board, BoardController boardController, String id) {
        this.figureType = figureType;
        this.figureColor = figureColor;
        this.columnCoordinate = columnCoordinate;
        this.rowCoordinate = rowCoordinate;
        this.board = board;
        this.id = id;
        this.boardController =boardController;

        if (figureColor == RED){
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
    
    //Marks this figure as selected and draws possible moves
    public void select(boolean isForcedToAttack){
        isSelected = true;
        markAsSelected();

        markPossibleAttacks(findPossibleAttacks());
        if (!isForcedToAttack && possibleAttacksCount() == 0)
            markPossibleMoves(getPossibleMoves());
    }

    public void attackFigure(Mark attackMark){
        //check if input is valid
        if(attackMark.getMarkType() != MarkTypes.ATTACK && attackMark.getEnemy() != null)
            return;

        attackMark.getEnemy().kill();
        moveFigure(attackMark.getCoordinates());
    }

    public void attackFigure(Figure enemy, Coordinates coordinatesAfterAttack){
        enemy.kill();
        moveFigure(coordinatesAfterAttack);
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

        if(figureColor == BLACK && figureType == PAWN && rowCoordinate == 0)
            promote();
        if(figureColor == RED && figureType == PAWN && rowCoordinate == 7)
            promote();
    }

    public void deselect(){
        board.getChildren().removeAll(marksImageView);
        markMap.clear();
        marksImageView.clear();
        isSelected = false;
    }

    public int possibleMovesCount(){
        return getPossibleMoves().size();
    }

    public int possibleAttacksCount(){
        return findPossibleAttacks().size();
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

    public long getAttackMarksCount(){
        return markMap.values().stream().filter(mark -> mark.getMarkType() == MarkTypes.ATTACK).count();
    }

    public boolean isSelected() {
        return isSelected;
    }
    
    public List<PossibleAttack> findPossibleAttacks(){
        List<PossibleAttack> result = new ArrayList<>();

        //case redFigures
        if(figureColor == RED){
            PossibleAttack leftFrontAttack = generateAttackIfPossible("b",-1,+1);
            if(leftFrontAttack != null)
                result.add(leftFrontAttack);

            PossibleAttack rightFrontAttack = generateAttackIfPossible("b",1,+1);
            if(rightFrontAttack != null)
                result.add(rightFrontAttack);

            if(figureType == KING){
                PossibleAttack leftBackAttack = generateAttackIfPossible("b",-1,-1);
                if(leftBackAttack != null)
                    result.add(leftBackAttack);

                PossibleAttack rightBackAttack = generateAttackIfPossible("b",1,-1);
                if(rightBackAttack != null)
                    result.add(rightBackAttack);
            }
        }

        //case blackFigures
        if(figureColor == BLACK){
            PossibleAttack leftFrontAttack = generateAttackIfPossible("r",-1,-1);
            if(leftFrontAttack != null)
                result.add(leftFrontAttack);

            PossibleAttack rightFrontAttack = generateAttackIfPossible("r",1,-1);
            if(rightFrontAttack != null)
                result.add(rightFrontAttack);

            if(figureType == KING){
                PossibleAttack leftBackAttack = generateAttackIfPossible("r",-1,+1);
                if(leftBackAttack != null)
                    result.add(leftBackAttack);

                PossibleAttack rightBackAttack = generateAttackIfPossible("r",1,+1);
                if(rightBackAttack != null)
                    result.add(rightBackAttack);
            }
        }

        return result;
    }
    
    private PossibleAttack generateAttackIfPossible(String enemyColourLetter, int columnOffset, int rowOffset) {
        Coordinates coordinatesOfEnemyCell       = new Coordinates(columnCoordinate + columnOffset,rowCoordinate + rowOffset );
        Coordinates coordinatesOfCellBehindEnemy = new Coordinates(columnCoordinate + (columnOffset*2), rowCoordinate + (rowOffset*2));
        //Guard clause to validate inputs
        if(!(  isCellOccupiedByEnemy(coordinatesOfEnemyCell.getColumnIndex() , coordinatesOfEnemyCell.getRowIndex())
            && isCellEmpty(coordinatesOfCellBehindEnemy.getColumnIndex(), coordinatesOfCellBehindEnemy.getRowIndex())
            && isCoordinateWithinTheBoard(coordinatesOfCellBehindEnemy.getColumnIndex(), coordinatesOfCellBehindEnemy.getRowIndex()))
        )
        return null;

        List<Node> enemyNode = getNodesByRowColumnIndex(coordinatesOfEnemyCell.getColumnIndex(), coordinatesOfEnemyCell.getRowIndex(),board)
                                .stream()
                                .filter(node -> node.getId().startsWith(enemyColourLetter))
                                .toList();

        Figure enemyFigure = boardController.getFigureFromMap( enemyNode.get(0).getId() );

        return new PossibleAttack(this, enemyFigure, coordinatesOfCellBehindEnemy);
    }

    public List<Coordinates> getPossibleMoves(){
        List<Coordinates> result = new ArrayList<>();

        // case red figure
        if(figureColor == RED){
            if(isCellEmpty(columnCoordinate - 1,rowCoordinate + 1))
                result.add(new Coordinates(columnCoordinate - 1, rowCoordinate + 1));
            if(isCellEmpty(columnCoordinate + 1,rowCoordinate + 1))
                result.add(new Coordinates(columnCoordinate + 1,rowCoordinate + 1));

            if(figureType == KING){
                if(isCellEmpty(columnCoordinate - 1,rowCoordinate - 1))
                    result.add(new Coordinates(columnCoordinate - 1, rowCoordinate - 1));
                if(isCellEmpty(columnCoordinate + 1,rowCoordinate - 1))
                    result.add(new Coordinates(columnCoordinate + 1,rowCoordinate - 1));
            }
        }

        // case black figure
        if(figureColor == BLACK){
            if(isCellEmpty(columnCoordinate - 1,rowCoordinate - 1))
                result.add(new Coordinates(columnCoordinate - 1, rowCoordinate - 1));
            if(isCellEmpty(columnCoordinate + 1,rowCoordinate - 1))
                result.add(new Coordinates(columnCoordinate + 1,rowCoordinate - 1));

            if(figureType == KING){
                if(isCellEmpty(columnCoordinate - 1,rowCoordinate + 1))
                    result.add(new Coordinates(columnCoordinate - 1, rowCoordinate + 1));
                if(isCellEmpty(columnCoordinate + 1,rowCoordinate + 1))
                    result.add(new Coordinates(columnCoordinate + 1,rowCoordinate + 1));
            }
        }

        return result.stream()
                .filter(coordinates -> isCoordinateWithinTheBoard(coordinates.getColumnIndex(), coordinates.getRowIndex()) )
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

    private void markAsSelected() {
        board.getChildren().remove(figureImageView);
        Image selectedPawnImage = new Image("file:src/main/resources/Assets/move.png");
        ImageView selectedPawnImageView = new ImageView(selectedPawnImage);
        selectedPawnImageView.setOpacity(0.5);
        selectedPawnImageView.setId("selection Mark");
        marksImageView.add(selectedPawnImageView);
        board.add(selectedPawnImageView, columnCoordinate, rowCoordinate);
        board.add(figureImageView, columnCoordinate, rowCoordinate);
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
                    if (figureColor == RED) {
                        return node.getId().startsWith("b");
                    }
                    if ((figureColor == BLACK)) {
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

    private void promote() {
        figureType = KING;
        if(figureColor == RED){
            Image kingsImage = new Image("file:src/main/resources/Assets/RedKing.png");
            figureImageView.setImage(kingsImage);
        }
        if(figureColor == BLACK){
            Image kingsImage = new Image("file:src/main/resources/Assets/BlackKing.png");
            figureImageView.setImage(kingsImage);
        }
    }

}


