package com.kkulpa.checkers.checkers;

import javafx.scene.image.ImageView;

public class Mark {

    private String id;
    private ImageView imageView;
    private Coordinates coordinates;
    private MarkTypes markType;

    public Mark(String id, ImageView imageView, Coordinates coordinates, MarkTypes markType) {
        this.id = id;
        this.imageView = imageView;
        this.coordinates = coordinates;
        this.markType = markType;
    }

    public String getId() {
        return id;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public MarkTypes getMarkType() {
        return markType;
    }
}
