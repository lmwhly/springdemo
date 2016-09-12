/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/luoo/mywork">MyWork</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.luoo.mywork.common.service.CrudService;
import com.luoo.mywork.common.utils.DateUtils;
import com.luoo.mywork.modules.sys.dao.LogDao;
import com.luoo.mywork.modules.sys.entity.Log;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日志Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<Log> findNewTypeList(Map<String, Object> params, PageBounds pageBounds) {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        params.put("DEL_FLAG_NORMAL", "0");

        // 设置默认时间范围，默认当前月
        if (params.get("beginDate") == null) {
            params.put("beginDate", DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));

        }
        if (params.get("endDate") == null) {
            params.put("endDate", DateUtils.addMonths((Date) params.get("beginDate"), 1));
        }


        List<Log> logs = sqlSession.selectList("com.luoo.mywork.modules.sys.dao.LogDao.findList", params, pageBounds);

        return logs;
    }


}
