/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

/**
 * Created by user on 5/16/2016.
 */
public class  DataSync {

    @SerializedName("prevrecs")
    @Expose
    private Integer prevrecs;
    @SerializedName("sessionid")
    @Expose
    private String sessionid;
    @SerializedName("lastsynced")
    @Expose
    private Long lastsynced;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("tablename")
    @Expose
    private String tablename;
    @SerializedName("usercheck")
    @Expose
    private Integer usercheck;
    @SerializedName("paginationrecs")
    @Expose
    private Integer paginationrecs;
    @SerializedName("keynames")
    @Expose
    private String keynames;
    @SerializedName("columnlist")
    @Expose
    private String columnlist;
    @SerializedName("pushdataarray")
    @Expose
    private JSONArray pushdataarray;
    private String splfunction;
    @SerializedName("onebyonepush")
    @Expose
    private Boolean onebyonepush;

    /**
     * @return The prevrecs
     */
    public Integer getPrevrecs() {
        return prevrecs;
    }

    /**
     * @param prevrecs The prevrecs
     */
    public void setPrevrecs(Integer prevrecs) {
        this.prevrecs = prevrecs;
    }

    /**
     * @return The sessionid
     */
    public String getSessionid() {
        return sessionid;
    }

    /**
     * @param sessionid The sessionid
     */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * @return The lastsynced
     */
    public Long getLastsynced() {
        return lastsynced;
    }

    /**
     * @param lastsynced The lastsynced
     */
    public void setLastsynced(Long lastsynced) {
        this.lastsynced = lastsynced;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The displayname
     */
    public String getDisplayname() {
        return displayname;
    }

    /**
     * @param displayname The displayname
     */
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    /**
     * @return The tablename
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename The tablename
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    /**
     * @return The usercheck
     */
    public Integer getUsercheck() {
        return usercheck;
    }

    /**
     * @param usercheck The usercheck
     */
    public void setUsercheck(Integer usercheck) {
        this.usercheck = usercheck;
    }

    /**
     * @return The paginationrecs
     */
    public Integer getPaginationrecs() {
        return paginationrecs;
    }

    /**
     * @param paginationrecs The paginationrecs
     */
    public void setPaginationrecs(Integer paginationrecs) {
        this.paginationrecs = paginationrecs;
    }

    /**
     * @return The keynames
     */
    public String getKeynames() {
        return keynames;
    }

    /**
     * @param keynames The keynames
     */
    public void setKeynames(String keynames) {
        this.keynames = keynames;
    }

    /**
     * @return The columnlist
     */
    public String getColumnlist() {
        return columnlist;
    }

    /**
     * @param columnlist The columnlist
     */
    public void setColumnlist(String columnlist) {
        this.columnlist = columnlist;
    }

    /**
     *
     * @return
     * The pushdataarray
     */
    public JSONArray getPushdataarray() {
        return pushdataarray;
    }

    /**
     *
     * @param pushdataarray
     * The pushdataarray
     */
    public void setPushdataarray(JSONArray pushdataarray) {
        this.pushdataarray = pushdataarray;
    }

    public String getSplfunction() {
        return splfunction;
    }

    public void setSplfunction(String splfunction) {
        this.splfunction = splfunction;
    }

    public Boolean getOnebyonepush() {
        return onebyonepush;
    }

    public void setOnebyonepush(Boolean onebyonepush) {
        this.onebyonepush = onebyonepush;
    }
}


