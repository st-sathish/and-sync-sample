/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.model;

public class Country extends AbstractBaseEntity {
    private String objectid;
    private String updatedAt;
    private String rowdata;
    private String parseobjid;
    private String countryname;

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRowdata() {
        return rowdata;
    }

    public void setRowdata(String rowdata) {
        this.rowdata = rowdata;
    }

    public String getParseobjid() {
        return parseobjid;
    }

    public void setParseobjid(String parseobjid) {
        this.parseobjid = parseobjid;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }
}
