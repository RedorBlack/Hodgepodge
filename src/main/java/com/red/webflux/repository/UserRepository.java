package com.red.webflux.repository;

import com.red.webflux.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 15:22 2019/8/19
 */
@Repository
public interface UserRepository extends CrudRepository<UserInfo, Integer> {
    /**
     * 查询
     * @param username
     * @return
     */
    UserInfo findByUsername(String username);



}
