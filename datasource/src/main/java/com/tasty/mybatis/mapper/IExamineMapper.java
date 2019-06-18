package com.tasty.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.tasty.mybatis.entity.ExaminePO;
import org.apache.ibatis.annotations.Param;

public interface IExamineMapper {

	int insert(ExaminePO po);

	List<ExaminePO> queryByLatterId(@Param("letterId") long letterId);

	ExaminePO queryById(@Param("examineId") long examineId);
}
