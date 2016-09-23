package com.luoo.mywork.modules.portal.entity;

import com.luoo.mywork.common.persistence.DataEntity;

/**
 * @author Luoo
 * @create 2016-09-23 15:46
 */

public class PortalRef extends DataEntity<PortalRef> {
    private static final long serialVersionUID = 0L;
    private Long id;

    private PortalInfo portalInfo;

    private String userId;

    private String tenantId;

    public PortalInfo getPortalInfo() {
        return portalInfo;
    }

    public void setPortalInfo(PortalInfo portalInfo) {
        this.portalInfo = portalInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
