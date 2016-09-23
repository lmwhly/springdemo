package com.luoo.mywork.modules.portal.service;

import com.luoo.mywork.common.service.BaseService;
import com.luoo.mywork.modules.portal.dao.PortalInfoDao;
import com.luoo.mywork.modules.portal.dao.PortalItemDao;
import com.luoo.mywork.modules.portal.dao.PortalRefDao;
import com.luoo.mywork.modules.portal.dao.PortalWidgetDao;
import com.luoo.mywork.modules.portal.entity.PortalInfo;
import com.luoo.mywork.modules.portal.entity.PortalRef;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Luoo
 * @create 2016-09-23 16:33
 */

@Service
@Transactional(readOnly = true)
public class PortalService extends BaseService implements InitializingBean {

    @Autowired
    private PortalInfoDao portalInfoDao;
    @Autowired
    private PortalItemDao portalItemDao;
    @Autowired
    private PortalRefDao portalRefDao;
    @Autowired
    private PortalWidgetDao portalWidgetDao;

    public PortalRef createOrGetPortalRef(String userId) {
        PortalRef portalRefParam = new PortalRef();
        portalRefParam.setUserId(userId);
        PortalRef portalRef = portalRefDao.get(portalRefParam);

        if (portalRef == null) {
            PortalInfo portalInfoParam = new PortalInfo();
            portalInfoParam.setGlobalStatus("true");

            PortalInfo portalInfo = portalInfoDao.get(portalInfoParam);

            if (portalInfo == null) {
                return null;
            }

            portalRef = new PortalRef();
            portalRef.setPortalInfo(portalInfo);
            portalRef.setUserId(userId);
            portalRefDao.insert(portalRef);
            portalInfoDao.insert(portalInfo);
        }

        return portalRef;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
