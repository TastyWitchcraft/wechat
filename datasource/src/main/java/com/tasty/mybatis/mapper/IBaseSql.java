package com.tasty.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface IBaseSql {

	@Select("SELECT nextval(#{seqName})")
	long nextval(@Param("seqName") String seqName);
	
} 