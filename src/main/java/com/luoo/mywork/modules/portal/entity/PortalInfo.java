package com.luoo.mywork.modules.portal.entity;

import com.luoo.mywork.common.persistence.DataEntity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Luoo
 * @create 2016-09-23 15:42
 */

public class PortalInfo extends DataEntity<PortalInfo> {
    private static final long serialVersionUID = 1L;
    
    private String name;

    private String description;

    private String userId;

    private Date createTime;

    private String columnLayout;

    private String sharedStatus;

    private String globalStatus;

    private String tenantId;

    private Set<PortalItem> portalItems = new HashSet<PortalItem>(0);

    private Set<PortalRef> portalRefs = new HashSet<PortalRef>(0);



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getColumnLayout() {
        return columnLayout;
    }

    public void setColumnLayout(String columnLayout) {
        this.columnLayout = columnLayout;
    }

    public String getSharedStatus() {
        return sharedStatus;
    }

    public void setSharedStatus(String sharedStatus) {
        this.sharedStatus = sharedStatus;
    }

    public String getGlobalStatus() {
        return globalStatus;
    }

    public void setGlobalStatus(String globalStatus) {
        this.globalStatus = globalStatus;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Set<PortalItem> getPortalItems() {
        return portalItems;
    }

    public void setPortalItems(Set<PortalItem> portalItems) {
        this.portalItems = portalItems;
    }

    public Set<PortalRef> getPortalRefs() {
        return portalRefs;
    }

    public void setPortalRefs(Set<PortalRef> portalRefs) {
        this.portalRefs = portalRefs;
    }
}
