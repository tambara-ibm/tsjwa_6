package one.tmbrms.readingsns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import one.tmbrms.readingsns.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByMessageId(int messageId);

    @Query("SELECT COALESCE(MAX(c.id), 0) + 1 FROM Comment c")
    int nextId();
}

// Made with Bob
