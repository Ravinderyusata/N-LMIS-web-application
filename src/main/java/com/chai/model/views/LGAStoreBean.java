package com.chai.model.views;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="VIEW_INVENTORY_WAREHOUSES")
public class LGAStoreBean implements Serializable {
	@Id
	@Column(name="WAREHOUSE_ID") private Integer x_WAREHOUSE_ID;
	@Column(name="COMPANY_ID") private Integer x_COMPANY_ID;
	@Column(name="WAREHOUSE_CODE") private String x_WAREHOUSE_CODE;
	@Column(name="WAREHOUSE_NAME") private String x_WAREHOUSE_NAME;
	@Column(name="WAREHOUSE_DESCRIPTION") private String x_WAREHOUSE_DESCRIPTION;
	@Column(name="WAREHOUSE_TYPE") private String x_WAREHOUSE_TYPE;
	@Column(name="WAREHOUSE_TYPE_ID") private Integer x_WAREHOUSE_TYPE_ID;
	@Column(name="ADDRRESS1") private String x_ADDRRESS1;
	@Column(name="ADDRRESS2") private String x_ADDRRESS2;
	@Column(name="ADDRRESS3") private String x_ADDRRESS3;
	@Column(name="TELEPHONE_NUMBER") private Integer x_TELEPHONE_NUMBER;
	@Column(name="FANUMBER") private Integer x_FANUMBER;
	@Column(name="STATUS") private String x_STATUS;
	@Column(name="START_DATE") private Date x_START_DATE;
	@Column(name="END_DATE") private Date x_END_DATE;
	@Column(name="COUNTRY_NAME") private String x_COUNTRY_NAME;
	@Column(name="COUNTRY_ID") private Integer x_COUNTRY_ID;
	@Column(name="STATE_NAME") private String x_STATE_NAME;
	@Column(name="STATE_ID") private Integer x_STATE_ID;
	@Column(name="CREATED_BY") private Integer x_CREATED_BY;
	@Column(name=" CREATED_ON") private Date x_CREATED_ON;
	@Column(name=" UPDATED_BY") private Integer x_UPDATED_BY;
	@Column(name=" LAST_UPDATED_ON") private Date x_LAST_UPDATED_ON;
	@Column(name=" DEFAULT_ORDERING_WAREHOUSE_ID") private Integer x_DEFAULT_ORDERING_WAREHOUSE_ID;
	@Column(name=" DEFAULT_ORDERING_WAREHOUSE_NAME") private String x_DEFAULT_ORDERING_WAREHOUSE_NAME;
	@Column(name=" MTP") private Integer x_MTP;
	
	public LGAStoreBean() {
	}
	public Integer getX_WAREHOUSE_ID() {
		return x_WAREHOUSE_ID;
	}
	public void setX_WAREHOUSE_ID(Integer x_WAREHOUSE_ID) {
		this.x_WAREHOUSE_ID = x_WAREHOUSE_ID;
	}
	public Integer getX_COMPANY_ID() {
		return x_COMPANY_ID;
	}
	public void setX_COMPANY_ID(Integer x_COMPANY_ID) {
		this.x_COMPANY_ID = x_COMPANY_ID;
	}
	public String getX_WAREHOUSE_CODE() {
		return x_WAREHOUSE_CODE;
	}
	public void setX_WAREHOUSE_CODE(String x_WAREHOUSE_CODE) {
		this.x_WAREHOUSE_CODE = x_WAREHOUSE_CODE;
	}
	public String getX_WAREHOUSE_NAME() {
		return x_WAREHOUSE_NAME;
	}
	public void setX_WAREHOUSE_NAME(String x_WAREHOUSE_NAME) {
		this.x_WAREHOUSE_NAME = x_WAREHOUSE_NAME;
	}
	public String getX_WAREHOUSE_DESCRIPTION() {
		return x_WAREHOUSE_DESCRIPTION;
	}
	public void setX_WAREHOUSE_DESCRIPTION(String x_WAREHOUSE_DESCRIPTION) {
		this.x_WAREHOUSE_DESCRIPTION = x_WAREHOUSE_DESCRIPTION;
	}
	public String getX_WAREHOUSE_TYPE() {
		return x_WAREHOUSE_TYPE;
	}
	public void setX_WAREHOUSE_TYPE(String x_WAREHOUSE_TYPE) {
		this.x_WAREHOUSE_TYPE = x_WAREHOUSE_TYPE;
	}
	public Integer getX_WAREHOUSE_TYPE_ID() {
		return x_WAREHOUSE_TYPE_ID;
	}
	public void setX_WAREHOUSE_TYPE_ID(Integer x_WAREHOUSE_TYPE_ID) {
		this.x_WAREHOUSE_TYPE_ID = x_WAREHOUSE_TYPE_ID;
	}
	public String getX_ADDRRESS1() {
		return x_ADDRRESS1;
	}
	public void setX_ADDRRESS1(String x_ADDRRESS1) {
		this.x_ADDRRESS1 = x_ADDRRESS1;
	}
	public String getX_ADDRRESS2() {
		return x_ADDRRESS2;
	}
	public void setX_ADDRRESS2(String x_ADDRRESS2) {
		this.x_ADDRRESS2 = x_ADDRRESS2;
	}
	public String getX_ADDRRESS3() {
		return x_ADDRRESS3;
	}
	public void setX_ADDRRESS3(String x_ADDRRESS3) {
		this.x_ADDRRESS3 = x_ADDRRESS3;
	}
	public Integer getX_TELEPHONE_NUMBER() {
		return x_TELEPHONE_NUMBER;
	}
	public void setX_TELEPHONE_NUMBER(Integer x_TELEPHONE_NUMBER) {
		this.x_TELEPHONE_NUMBER = x_TELEPHONE_NUMBER;
	}
	public Integer getX_FANUMBER() {
		return x_FANUMBER;
	}
	public void setX_FANUMBER(Integer x_FANUMBER) {
		this.x_FANUMBER = x_FANUMBER;
	}
	public String getX_STATUS() {
		return x_STATUS;
	}
	public void setX_STATUS(String x_STATUS) {
		this.x_STATUS = x_STATUS;
	}
	public Date getX_START_DATE() {
		return x_START_DATE;
	}
	public void setX_START_DATE(Date x_START_DATE) {
		this.x_START_DATE = x_START_DATE;
	}
	public Date getX_END_DATE() {
		return x_END_DATE;
	}
	public void setX_END_DATE(Date x_END_DATE) {
		this.x_END_DATE = x_END_DATE;
	}
	public String getX_COUNTRY_NAME() {
		return x_COUNTRY_NAME;
	}
	public void setX_COUNTRY_NAME(String x_COUNTRY_NAME) {
		this.x_COUNTRY_NAME = x_COUNTRY_NAME;
	}
	public Integer getX_COUNTRY_ID() {
		return x_COUNTRY_ID;
	}
	public void setX_COUNTRY_ID(Integer x_COUNTRY_ID) {
		this.x_COUNTRY_ID = x_COUNTRY_ID;
	}
	public String getX_STATE_NAME() {
		return x_STATE_NAME;
	}
	public void setX_STATE_NAME(String x_STATE_NAME) {
		this.x_STATE_NAME = x_STATE_NAME;
	}
	public Integer getX_STATE_ID() {
		return x_STATE_ID;
	}
	public void setX_STATE_ID(Integer x_STATE_ID) {
		this.x_STATE_ID = x_STATE_ID;
	}
	public Integer getX_CREATED_BY() {
		return x_CREATED_BY;
	}
	public void setX_CREATED_BY(Integer x_CREATED_BY) {
		this.x_CREATED_BY = x_CREATED_BY;
	}
	public Date getX_CREATED_ON() {
		return x_CREATED_ON;
	}
	public void setX_CREATED_ON(Date x_CREATED_ON) {
		this.x_CREATED_ON = x_CREATED_ON;
	}
	public Integer getX_UPDATED_BY() {
		return x_UPDATED_BY;
	}
	public void setX_UPDATED_BY(Integer x_UPDATED_BY) {
		this.x_UPDATED_BY = x_UPDATED_BY;
	}
	public Date getX_LAST_UPDATED_ON() {
		return x_LAST_UPDATED_ON;
	}
	public void setX_LAST_UPDATED_ON(Date x_LAST_UPDATED_ON) {
		this.x_LAST_UPDATED_ON = x_LAST_UPDATED_ON;
	}
	public Integer getX_DEFAULT_ORDERING_WAREHOUSE_ID() {
		return x_DEFAULT_ORDERING_WAREHOUSE_ID;
	}
	public void setX_DEFAULT_ORDERING_WAREHOUSE_ID(Integer x_DEFAULT_ORDERING_WAREHOUSE_ID) {
		this.x_DEFAULT_ORDERING_WAREHOUSE_ID = x_DEFAULT_ORDERING_WAREHOUSE_ID;
	}
	public String getX_DEFAULT_ORDERING_WAREHOUSE_CODE() {
		return x_DEFAULT_ORDERING_WAREHOUSE_NAME;
	}
	public void setX_DEFAULT_ORDERING_WAREHOUSE_CODE(String x_DEFAULT_ORDERING_WAREHOUSE_NAME) {
		this.x_DEFAULT_ORDERING_WAREHOUSE_NAME = x_DEFAULT_ORDERING_WAREHOUSE_NAME;
	}
	public Integer getX_MTP() {
		return x_MTP;
	}
	public void setX_MTP(Integer x_MTP) {
		this.x_MTP = x_MTP;
	}

}
