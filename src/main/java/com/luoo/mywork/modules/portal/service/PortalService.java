package com.luoo.mywork.modules.portal.service;

import com.luoo.mywork.common.mapper.BeanMapper;
import com.luoo.mywork.common.service.BaseService;
import com.luoo.mywork.common.utils.StringUtils;
import com.luoo.mywork.modules.portal.dao.PortalInfoDao;
import com.luoo.mywork.modules.portal.dao.PortalItemDao;
import com.luoo.mywork.modules.portal.dao.PortalRefDao;
import com.luoo.mywork.modules.portal.dao.PortalWidgetDao;
import com.luoo.mywork.modules.portal.entity.PortalInfo;
import com.luoo.mywork.modules.portal.entity.PortalItem;
import com.luoo.mywork.modules.portal.entity.PortalRef;
import com.luoo.mywork.modules.portal.entity.PortalWidget;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;


/**
 * @author Luoo
 * @create 2016-09-23 16:33
 */

@Service
@Transactional(readOnly = true)
public class PortalService extends BaseService implements InitializingBean {

    private BeanMapper beanMapper = new BeanMapper();

    @Autowired
    private PortalInfoDao portalInfoDao;
    @Autowired
    private PortalItemDao portalItemDao;
    @Autowired
    private PortalRefDao portalRefDao;
    @Autowired
    private PortalWidgetDao portalWidgetDao;

    public List<Integer> getColumnIndexesByPortalInfoId(PortalInfo portalInfo) {
        return portalItemDao.getColumnIndexesByPortalInfoId(portalInfo);
    }

    public List<PortalItem> getPortalItemByPortalInfoIdAndColumnIndex(PortalItem portalItem) {
        return portalItemDao.getPortalItemByPortalInfoIdAndColumnIndex(portalItem);
    }

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
            savePortalRef(portalRef);
            savePortalInfo(portalInfo);
        }

        return portalRef;
    }


    public PortalInfo copyOrGetPortalInfo(String userId) {
        PortalRef portalRef = this.createOrGetPortalRef(userId);
        PortalInfo portalInfo = null;

        if (portalRef != null) {
            portalInfo = portalRef.getPortalInfo();

            if (userId.equals(portalInfo.getUserId())) {
                return portalInfo;
            }
        }

        PortalInfo targetPortalInfo = new PortalInfo();

        if (portalInfo != null) {
            beanMapper.copy(portalInfo, targetPortalInfo);
        }

        targetPortalInfo.setUserId(userId);
        targetPortalInfo.setId(null);
        targetPortalInfo.setPortalItems(new HashSet<PortalItem>());
        targetPortalInfo.setPortalRefs(new HashSet<PortalRef>());
        savePortalInfo(targetPortalInfo);

        PortalRef targetPortalRef = new PortalRef();
        targetPortalRef.setPortalInfo(targetPortalInfo);
        targetPortalRef.setUserId(userId);
        savePortalRef(targetPortalRef);
        portalRefDao.delete(portalRef);

        if (portalInfo != null) {
            for (PortalItem portalItem : portalInfo.getPortalItems()) {
                PortalItem targetPortalItem = new PortalItem();
                beanMapper.copy(portalItem, targetPortalItem);
                targetPortalItem.setPortalInfo(targetPortalInfo);
                targetPortalItem.setId(null);

                savePortalItem(targetPortalItem);
            }
        }

        return targetPortalInfo;
    }

    public List<PortalWidget> getAllPortalWidgets() {
        return portalWidgetDao.findAllList(new PortalWidget());
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }


    public PortalWidget getPortalWidgetById(Long portalWidgetId) {

        return portalWidgetDao.get(portalWidgetId+"");
    }

    public Integer getMinColumnIndexOfPortalItemByPortalInfoId(PortalInfo portalInfo) {
        return portalItemDao.getMinColumnIndexOfPortalItemByPortalInfoId(portalInfo);
    }


    public Long getCountOfPortalItemByPortalInfoIdAndColumnIndex(PortalItem portalItem) {
        return portalItemDao.getCountOfPortalItemByPortalInfoIdAndColumnIndex(portalItem);
    }

    public PortalItem createOrGetPortalItem(PortalInfo portalInfo,
                                            String portalItemId) {
        PortalItem portalItem = portalItemDao.get(portalItemId);
        portalItem.setPortalInfo(portalInfo);
        PortalItem targetPortalItem = portalItemDao.get(portalItem);

        return targetPortalItem;
    }

    @Transactional(readOnly = false)
    public void savePortalItem(PortalItem portalItem) {
        // 保存业务数据
        if (StringUtils.isBlank(portalItem.getId())){
            portalItem.preInsert();
            portalItemDao.insert(portalItem);
        }else{
            portalItem.preUpdate();
            portalItemDao.update(portalItem);
        }
    }


    @Transactional(readOnly = false)
    public void savePortalInfo(PortalInfo portalInfo) {
        // 保存业务数据
        if (StringUtils.isBlank(portalInfo.getId())){
            portalInfo.preInsert();
            portalInfoDao.insert(portalInfo);
        }else{
            portalInfo.preUpdate();
            portalInfoDao.update(portalInfo);
        }
    }

    @Transactional(readOnly = false)
    public void savePortalRef( PortalRef  portalRef) {
        // 保存业务数据
        if (StringUtils.isBlank(portalRef.getId())){
            portalRef.preInsert();
            portalRefDao.insert(portalRef);
        }else{
            portalRef.preUpdate();
            portalRefDao.update(portalRef);
        }
    }

    @Transactional(readOnly = false)
    public void removePortalItem(PortalItem portalItem) {
        portalItemDao.delete(portalItem);
    }
}
