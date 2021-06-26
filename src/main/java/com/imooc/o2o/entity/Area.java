package com.imooc.o2o.entity;

import java.util.Date;

public class Area {
	// ID
	private Integer areaId;
	// 名称
	private String areaName;
	// 权重, 排名靠前
	private Integer proiority;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date lastEditTime;
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getProiority() {
		return proiority;
	}
	public void setProiority(Integer proiority) {
		this.proiority = proiority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
