package com.reldev.website.repositories;

import com.reldev.website.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c ORDER BY c.startDate DESC")
    public List<Course> findAllOrderedByDate();

}