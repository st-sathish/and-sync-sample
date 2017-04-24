package com.trucontactssync.model;


public abstract class AbstractBaseEntity {

    private int isDeleted;

    private String rowData;

    private String updatedAt;

    private String objectId;

    public String getRowData() {
        return rowData;
    }

    public void setRowData(String rowData) {
        this.rowData = rowData;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int isDeleted() {
        return isDeleted;
    }

    public void setDeleted(int deleted) {
        isDeleted = deleted;
    }
}
