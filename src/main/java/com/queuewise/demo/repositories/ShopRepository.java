package com.queuewise.demo.repositories;

import com.queuewise.demo.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("shops")
public interface ShopRepository extends JpaRepository<Shop, Integer> {
}
