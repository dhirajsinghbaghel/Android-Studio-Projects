package com.example.auth_application.modal;

public class ListModal {

    private  int image = 0;
    private  String title = "";
   private String price = "";
    private String duration ="";
    private  String description = "";

    public  ListModal(int image, String title, String price, String duration, String description){
            this.image = image;
            this.price = price;
            this.title = title;
            this.duration = duration;
            this.description = description;

    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }
}
