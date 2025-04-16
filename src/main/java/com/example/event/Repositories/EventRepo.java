package com.example.event.Repositories;

import com.example.event.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
//    public Event findEventById(Long idEvent);

    List<Event> findByTitleContainingIgnoreCase(String title);

//    List<Event> findByTitle(String title);
//    public Event findByidEvent(Long id);
//    @Query("SELECT e FROM Event e " +
//            "WHERE e IN (SELECT f.event FROM Feedback f GROUP BY f.event " +
//            "ORDER BY AVG(f.rating) DESC) " +
//            "AND SIZE(e.feedbacks) > 0 " +
//            "ORDER BY (SELECT AVG(f.rating) FROM Feedback f WHERE f.event = e) DESC")
//    List<Event> findTopRatedEventsdsd();

    List<Event> findByCluster(Integer cluster);


    @Query("SELECT e FROM Event e " +
            "JOIN e.feedbacks f " +
            "GROUP BY e.idEvent " +
            "ORDER BY AVG(f.rating) DESC")
    List<Event> findTopRatedEvent();

}
