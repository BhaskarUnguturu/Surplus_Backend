package com.surplus.fwm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surplus.fwm.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
