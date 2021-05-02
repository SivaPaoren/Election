package com.election.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.election.model.Comment;

@Repository
public interface commentRepository extends JpaRepository<Comment,Integer> {
 
}
