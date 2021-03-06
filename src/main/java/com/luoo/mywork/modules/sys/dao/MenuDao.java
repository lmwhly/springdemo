/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/luoo/mywork">MyWork</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.dao;


import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.sys.entity.Menu;

import java.util.List;

/**
 * 菜单DAO接口
 * @author Luoo
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
