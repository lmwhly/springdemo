package com.luoo.mywork.modules.sys.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.sys.entity.Department;

import java.util.List;
import java.util.Map;

/**
 * @author Luoo
 * @create 2016-09-08 17:30
 */

@MyBatisDao
public interface DepartmentDao extends CrudDao<DepartmentDao> {

    public List<Department> findMyDepartment(Department user, Map<String, Object> params, PageBounds pageBounds);
}



