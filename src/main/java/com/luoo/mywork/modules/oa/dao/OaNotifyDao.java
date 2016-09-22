/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/luoo">MyWork</a> All rights reserved.
 */
package com.luoo.mywork.modules.oa.dao;

import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 * @author Luoo
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyDao extends CrudDao<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify);
	
}