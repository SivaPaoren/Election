package com.election.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.election.model.Civilian;

@Repository
public interface electionRepository extends JpaRepository<Civilian,Integer>{
  public Civilian findByName(String username); 
}
