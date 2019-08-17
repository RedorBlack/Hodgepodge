package com.red.webflux.dao;

import com.red.webflux.model.RedDemo;
import com.red.webflux.model.RedDemoExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RedDemoMapper {
    int countByExample(RedDemoExample example);

    int deleteByExample(RedDemoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RedDemo record);

    int insertSelective(RedDemo record);

    List<RedDemo> selectByExample(RedDemoExample example);

    RedDemo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RedDemo record, @Param("example") RedDemoExample example);

    int updateByExample(@Param("record") RedDemo record, @Param("example") RedDemoExample example);

    int updateByPrimaryKeySelective(RedDemo record);

    int updateByPrimaryKey(RedDemo record);

    @Select("select * from red_demo")
    List<RedDemo> findAll();
}