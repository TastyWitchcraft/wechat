package com.tasty.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface ILetterVisitMapper {

	int insert(Map<String, Object> params);

	List<Map<String, Object>> query(Map<String, Object> params);

	int count(Map<String, Object> params);
}
