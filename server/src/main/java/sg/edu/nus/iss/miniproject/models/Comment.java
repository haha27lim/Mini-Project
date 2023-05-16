package sg.edu.nus.iss.miniproject.models;

import org.bson.Document;


import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    
    private String title;
    private String name;
    private String email;
    private String subject;
    private Integer rating;
    private String comment;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment [title=" + title + ", name=" + name + ", email=" + email + ", subject=" + subject + ", rating="
                + rating + ", comment=" + comment + "]";
    }

    public static Comment create(Document d) {
        Comment c = new Comment();
        c.setTitle(d.getString("title"));
        c.setName(d.getString("name"));
        c.setEmail(d.getString("email"));
        c.setSubject(d.getString("subject"));
        c.setRating(d.getInteger("rating"));
        c.setComment(d.getString("comment"));
        return c;
    }
    
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("title", getTitle())
            .add("name", getName())
            .add("email", getEmail())
            .add("subject", getSubject())
            .add("rating", getRating())
            .add("comment", getComment())
            .build();
    }

    public static Document toDocument (Comment comment) {
        Document document = new Document();
        document.put("title", comment.getTitle());
        document.put("name", comment.getName());
        document.put("email", comment.getEmail());
        document.put("subject", comment.getSubject());
        document.put("rating", comment.getRating());
        document.put("comment", comment.getComment());
        return document;
    } 
}
