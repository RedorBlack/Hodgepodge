package com.red.webflux.repository;
import com.red.webflux.model.JpaDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @Author: Red
 * @Descpription:
 * @Date: 16:06 2019/8/16
 */
@Repository
public interface JpaDemoRepository extends JpaRepository<JpaDemo, Long> {




}
