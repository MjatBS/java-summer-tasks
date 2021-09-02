package main.java;

public class Lot {
    public final String title;
    public final int startingPrice;
    private int presentPrice;

    public Lot(String title, int startingPrice){
        this.title = title;
        this.startingPrice = startingPrice;
        presentPrice = startingPrice;
    }

    public void setPresentPrice(int presentPrice) {
        this.presentPrice = presentPrice;
    }

    public int getPresentPrice(){
        return presentPrice;
    }

    @Override
    public String toString() {
        return "title: "+ title + "\n"
                + "starting price: " + startingPrice;
    }
}
