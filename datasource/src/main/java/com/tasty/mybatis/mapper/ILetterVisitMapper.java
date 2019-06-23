package com.tasty.mybatis.mapper;

import com.tasty.mybatis.entity.LetterVisitPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ILetterVisitMapper {

	int insert(LetterVisitPO po);

	List<LetterVisitPO> query(Map<String, Object> params);

	int count(Map<String, Object> params);

	LetterVisitPO queryById(@Param("letterId") long letterId);

	int updateById(Map<String, Object> param);

	int updateSatisfiedById(Map<String, Object> param);
}
