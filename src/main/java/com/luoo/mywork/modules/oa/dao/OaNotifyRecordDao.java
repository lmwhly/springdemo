/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/luoo">MyWork</a> All rights reserved.
 */
package com.luoo.mywork.modules.oa.dao;

import java.util.List;

import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.oa.entity.OaNotifyRecord;

/**
 * 通知通告记录DAO接口
 * @author Luoo
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyRecordDao extends CrudDao<OaNotifyRecord> {

	/**
	 * 插入通知记录
	 * @param oaNotifyRecordList
	 * @return
	 */
	public int insertAll(List<OaNotifyRecord> oaNotifyRecordList);
	
	/**
	 * 根据通知ID删除通知记录
	 * @param oaNotifyId 通知ID
	 * @return
	 */
	public int deleteByOaNotifyId(String oaNotifyId);
	
}