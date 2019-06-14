package com.tasty.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ITestPunish {
	
	List<Map<String, Object>> qryPunishById(@Param("punishId") String userId);
}
