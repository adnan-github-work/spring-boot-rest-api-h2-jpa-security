package com.sample.person.repository;

import com.sample.person.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<PersonEntity, String> {

}

