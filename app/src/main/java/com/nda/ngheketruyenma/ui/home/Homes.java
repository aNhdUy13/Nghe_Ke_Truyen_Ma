package com.nda.ngheketruyenma.ui.home;

public class Homes {
    private String author, content,image, name,newStory, source;

    public Homes(){}
    public Homes(String author, String content,String image, String name,String newStory, String source) {
        this.author = author;
        this.content = content; // Chưa Cập Nhập Nội Dung
        this.name = name;
        this.newStory = newStory;
        this.image = image;
        this.source = source;
    }

    public String getNewStory() {
        return newStory;
    }

    public void setNewStory(String newStory) {
        this.newStory = newStory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
