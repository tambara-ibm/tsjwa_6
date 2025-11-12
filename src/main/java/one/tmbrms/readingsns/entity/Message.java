package one.tmbrms.readingsns.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Message {
    @Id
    public int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "isbn")
    public Book book;

    public String content;
    public String timestamp;

    public Message(int id, User user, Book book, String content, String timestamp) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Message() {
        // Default constructor for JPA
    }

    public String toCsv() {
        return String.format("%d,%s,%s", id, content, timestamp.toString());
    }
}
