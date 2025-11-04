package com.itwillbs.test5.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwillbs.test5.item.entity.Item;
import com.itwillbs.test5.item.entity.ItemImg;

@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

}
