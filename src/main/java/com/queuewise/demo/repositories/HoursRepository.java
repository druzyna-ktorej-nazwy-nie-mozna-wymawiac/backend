package com.queuewise.demo.repositories;

import com.queuewise.demo.models.Hour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoursRepository extends JpaRepository<Hour, Integer> {

}
