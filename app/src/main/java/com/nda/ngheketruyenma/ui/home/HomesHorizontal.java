package com.nda.ngheketruyenma.ui.home;

public class HomesHorizontal {
    private String author, content,image, name, source;

    public HomesHorizontal(){}
    public HomesHorizontal(String author, String content,String image, String name, String source) {
        this.author = author;
        this.content = content;
        this.name = name;
        this.image = image;
        this.source = source;
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
