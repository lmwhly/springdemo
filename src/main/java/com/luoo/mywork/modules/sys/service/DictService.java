/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/luoo/mywork">MyWork</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.luoo.mywork.common.service.CrudService;
import com.luoo.mywork.common.utils.CacheUtils;
import com.luoo.mywork.modules.sys.dao.DictDao;
import com.luoo.mywork.modules.sys.entity.Dict;
import com.luoo.mywork.modules.sys.utils.DictUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	public List<Dict> findNewTypeList(Map<String, Object> params, PageBounds pageBounds) {

		SqlSession sqlSession = sqlSessionFactory.openSession();

		params.put("DEL_FLAG_NORMAL","0");

		List<Dict> users = sqlSession.selectList("com.luoo.mywork.modules.sys.dao.DictDao.findList", params, pageBounds);

		return users;
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

}
