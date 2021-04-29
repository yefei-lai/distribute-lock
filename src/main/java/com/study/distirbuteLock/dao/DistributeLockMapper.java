package com.study.distirbuteLock.dao;

import com.study.distirbuteLock.model.DistributeLock;
import org.apache.ibatis.annotations.Param;

public interface DistributeLockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DistributeLock record);

    int insertSelective(DistributeLock record);

    DistributeLock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DistributeLock record);

    int updateByPrimaryKey(DistributeLock record);

    DistributeLock selectDistributeLock(@Param("bussinessCode") String bussinessCode);
}