/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.sys.dao;


import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDefine;

import java.util.List;

/**
 * 单表增删改查DAO接口
 * @author zhangcan
 * @version 2016-07-22
 */
@MyBatisDao
public interface SysStaffAssessTemplateDefineDao extends CrudDao<SysStaffAssessTemplateDefine> {

    void deleteSel(List<String> ids);
}