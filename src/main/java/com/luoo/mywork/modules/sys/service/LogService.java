/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/luoo/mywork">MyWork</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.service.CrudService;
import com.luoo.mywork.common.utils.DateUtils;
import com.luoo.mywork.common.utils.StringUtils;
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
 * @author Luoo
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    public Page<Log> findPage(Page<Log> page, Log log) {

        // 设置默认时间范围，默认当前月
        if (log.getBeginDate() == null){
            log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (log.getEndDate() == null){
            log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
        }

        return super.findPage(page, log);

    }

    public List<Log> findNewTypeList(Map<String, Object> params, PageBounds pageBounds) {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        params.put("DEL_FLAG_NORMAL", "0");
        params.put("TYPE_EXCEPTION", "2");


        // 设置默认时间范围，默认当前月
        if (StringUtils.isBlank((String)params.get("beginDate"))) {
            params.put("beginDate", DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));

        }
        if (StringUtils.isBlank((String)params.get("endDate"))) {
            params.put("endDate", DateUtils.addMonths((Date) params.get("beginDate"), 1));
        }


        List<Log> logs = sqlSession.selectList("com.luoo.mywork.modules.sys.dao.LogDao.findList", params, pageBounds);

        return logs;
    }


}
