package com.queuewise.demo.repositories;

import com.queuewise.demo.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
}
