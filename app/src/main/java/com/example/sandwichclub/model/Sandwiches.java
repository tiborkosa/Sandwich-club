package com.example.sandwichclub.model;

import android.text.TextUtils;

import java.util.List;

/**
 * Sandwiches class will hold the name of all sandwiches for simplicity
 *
 */
public class Sandwiches {

    private List<String> sandwiches = null;

    public Sandwiches(){}
    /**
     * Constructor
     * @param sandwiches receives all sandwich names
     */
    public Sandwiches(List<String> sandwiches){
        this.sandwiches = sandwiches;
    }

    /**
     *
     * @return all sandwiches
     */
    public List<String> getSandwiches(){
        return this.sandwiches;
    }

    /**
     *
     * @param position the position of the selected sandwich
     * @return the selected sandwich name or an empty string
     */
    public String getOne(int position) {
        if(this.sandwiches == null || position < 0 || position > this.sandwiches.size() -1 ) return "";

        return this.sandwiches.get(position);
    }

    @Override
    public String toString() {
        return TextUtils.join(", ", this.sandwiches);
    }
}
