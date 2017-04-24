/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VCard {
    @SerializedName("objectId")
    @Expose
    private String objectid;
    @SerializedName("updatedAt")
    @Expose
    private Long updatedAt;
    @SerializedName("rowData")
    @Expose
    private String rowdata;
    private String parseobjid;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("isdeleted")
    @Expose
    private int isdeleted;
    @SerializedName("vcardid")
    @Expose
    private String vcardid;

    @SerializedName("inviteid")
    @Expose
    private String inviteid;
    private byte[] imagedata;
    private String asrowdatareceived;

    @SerializedName("cardtype")
    @Expose
    private String cardtype;

    private transient int noOfVcard = 0;

    public void setNoOfVcard(int noOfVcard) {
        this.noOfVcard = noOfVcard;
    }

    public int getNoOfVcard() {
        return this.noOfVcard;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getVcardid() {
        return vcardid;
    }

    public void setVcardid(String vcardid) {
        this.vcardid = vcardid;
    }

    public String getInviteid() {
        return inviteid;
    }

    public void setInviteid(String inviteid) {
        this.inviteid = inviteid;
    }

    public byte[] getImagedata() {
        return imagedata;
    }

    public void setImagedata(byte[] imagedata) {
        this.imagedata = imagedata;
    }

    public String getAsrowdatareceived() {
        return asrowdatareceived;
    }

    public void setAsrowdatareceived(String asrowdatareceived) {
        this.asrowdatareceived = asrowdatareceived;
    }
    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }
}
