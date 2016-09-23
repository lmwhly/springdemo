package com.luoo.mywork.modules.portal.entity;

import com.luoo.mywork.common.persistence.DataEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Luoo
 * @create 2016-09-23 15:46
 */

public class PortalWidget extends DataEntity<PortalWidget> {
    private static final long serialVersionUID = 0L;
    private Long id;

    private String name;

    private String url;

    private String data;

    private String tenantId;

    private Set<PortalItem> portalItems = new HashSet<PortalItem>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
}
