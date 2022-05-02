package com.kkulpa.checkers.checkers;

import java.util.Objects;

public class Coordinates {

    private int columnIndex;
    private int rowIndex;

    public Coordinates(int columnIndex, int rowIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "columnIndex=" + columnIndex +
                ", rowIndex=" + rowIndex +
                '}';
    }

    public static boolean isCoordinateValid(int columnIndex, int rowIndex){
        return columnIndex >= 0 && columnIndex < 8 && rowIndex >= 0 && rowIndex < 8;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return getColumnIndex() == that.getColumnIndex() && getRowIndex() == that.getRowIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColumnIndex(), getRowIndex());
    }
}
