package com.election.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.election.model.Vote;

@Repository
public interface voteRepository extends JpaRepository<Vote,Integer>{
  Vote findByName(String name);
}
