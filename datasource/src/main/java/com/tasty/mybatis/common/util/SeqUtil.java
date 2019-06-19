package com.tasty.mybatis.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tasty.mybatis.mapper.IBaseSql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SeqUtil {
	
	@Autowired
	private IBaseSql baseSql;
	
	public static SeqUtil getInst(){
		return SpringUtil.getBean(SeqUtil.class);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public long getSequence(String seqName){
		return baseSql.nextval(seqName);
	}

}
