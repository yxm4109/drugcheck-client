package net.ucopy.drugcheck.entity;

import java.util.ArrayList;
import java.util.Map.Entry;

public class DrugInfo {

	public DrugInfo(String picUrl, BaseInfo baseInfo, String status) {
		super();
		this.picUrl = picUrl;
		this.status = status;
		this.baseInfo = baseInfo;
	}
	
	public DrugInfo(String picUrl, String status,ArrayList<Entry<String, String>> baseInfos) {
		super();
		this.picUrl = picUrl;
		this.status = status;
		this.baseInfos=baseInfos;
	}

	public String picUrl;// 图片地址
	public BaseInfo baseInfo;// 基础讯息
	public String status;// 目前流向
	
	public ArrayList<Entry<String, String>> baseInfos=null;//内部类用起来太复杂了，烦死了，用list吧！
	

	public class BaseInfo {

		public String commName;// 通用名
		public String dosageForm;// 剂型
		public String dosageSpecif;// 制剂规格
		public String packingSpecif;// 包装规格
		public String prodEnter;// 生产企业
		public String prodDate;// 生产日期
		public String batchNumber;// 产品批号
		public String validDate;// 有效期
		public String approvalNumber;// 批准文号

		public BaseInfo(String commName, String dosageForm, String dosageSpecif, String packingSpecif,
		        String prodEnter, String prodDate, String batchNumber, String validDate, String approvalNumber) {
			super();
			this.commName = commName;
			this.dosageForm = dosageForm;
			this.dosageSpecif = dosageSpecif;
			this.packingSpecif = packingSpecif;
			this.prodEnter = prodEnter;
			this.prodDate = prodDate;
			this.batchNumber = batchNumber;
			this.validDate = validDate;
			this.approvalNumber = approvalNumber;
		}

	}

}