package one.tmbrms.readingsns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import one.tmbrms.readingsns.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByUserId(int userId);

    @Query("SELECT MAX(m.id) + 1 FROM Message m")
    int nextId();
}
    
