package net.ucopy.drugcheck.entity;

import java.io.Serializable;

public class DrugInfo extends UCopyObject implements Serializable {

    public String barCode;//药品的电子监管码

    public String picUrl;// 图片地址
    public String status;// 目前流向

    public String commName;// 通用名
    public String dosageForm;// 剂型
    public String dosageSpecif;// 制剂规格
    public String packingSpecif;// 包装规格
    public String prodEnter;// 生产企业
    public String prodDate;// 生产日期
    public String batchNumber;// 产品批号
    public String validDate;// 有效期
    public String approvalNumber;// 批准文号


    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public void setDosageSpecif(String dosageSpecif) {
        this.dosageSpecif = dosageSpecif;
    }

    public void setPackingSpecif(String packingSpecif) {
        this.packingSpecif = packingSpecif;
    }

    public void setProdEnter(String prodEnter) {
        this.prodEnter = prodEnter;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getStatus() {
        return status;
    }

    public String getCommName() {
        return commName;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public String getDosageSpecif() {
        return dosageSpecif;
    }

    public String getPackingSpecif() {
        return packingSpecif;
    }

    public String getProdEnter() {
        return prodEnter;
    }

    public String getProdDate() {
        return prodDate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getValidDate() {
        return validDate;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }


}