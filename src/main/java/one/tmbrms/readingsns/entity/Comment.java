package one.tmbrms.readingsns.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
    @Id
    public int id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    public Message message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public String content;
    public String timestamp;

    public Comment(int id, Message message, User user, String content, String timestamp) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Comment() {
        // Default constructor for JPA
    }
}

// Made with Bob
