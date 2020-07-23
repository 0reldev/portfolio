package com.reldev.website.repositories;

import com.reldev.website.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}

/*
TODO: add a query to find all the stuff ordered by date*/
