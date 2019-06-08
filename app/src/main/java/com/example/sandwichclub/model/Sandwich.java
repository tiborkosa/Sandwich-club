package com.example.sandwichclub.model;

import android.text.TextUtils;
import java.util.List;

/**
 *  Sandwich object for a single sandwich data
 */
public class Sandwich {

    /**
     * class fields
     */
    private String mainName;
    private List<String> alsoKnownAs;
    private String placeOfOrigin;
    private String description;
    private String image;
    private List<String> ingredients;

    Sandwich(){}

    /**
     *  Main constructor for setting fields
     * @param mainName
     * @param alsoKnownAs
     * @param placeOfOrigin
     * @param description
     * @param image
     * @param ingredients
     */
    public Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients) {
        this.mainName = mainName;
        this.alsoKnownAs = alsoKnownAs;
        this.placeOfOrigin = placeOfOrigin;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
    }

    /**
     * getters and setters for all fields
     */
    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Sandwich{" +
                ", mainName='" + mainName + '\'' +
                ", alsoKnownAs=" + TextUtils.join(", ", this.alsoKnownAs) +
                ", placeOfOrigin='" + placeOfOrigin + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", ingredients=" + TextUtils.join(", ", this.ingredients) +
                '}';
    }
}

