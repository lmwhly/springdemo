package com.luoo.mywork.modules.portal.entity;

import com.luoo.mywork.common.persistence.DataEntity;

/**
 * @author Luoo
 * @create 2016-09-23 15:46
 */

public class PortalItem extends DataEntity<PortalItem> {
    private static final long serialVersionUID = 0L;
    private Long id;

    private PortalInfo portalInfo;

    private PortalWidget portalWidget;

    private String name;

    private Integer columnIndex;

    private Integer rowIndex;

    private String data;

    private String tenantId;

    public PortalInfo getPortalInfo() {
        return portalInfo;
    }

    public void setPortalInfo(PortalInfo portalInfo) {
        this.portalInfo = portalInfo;
    }

    public PortalWidget getPortalWidget() {
        return portalWidget;
    }

    public void setPortalWidget(PortalWidget portalWidget) {
        this.portalWidget = portalWidget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
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
}
