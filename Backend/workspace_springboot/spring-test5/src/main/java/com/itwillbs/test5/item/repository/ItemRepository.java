package com.itwillbs.test5.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwillbs.test5.item.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
