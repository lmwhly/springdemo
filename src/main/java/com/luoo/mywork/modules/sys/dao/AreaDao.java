/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/luoo/mywork">MyWork</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.dao;


import com.luoo.mywork.common.persistence.TreeDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author Luoo
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
