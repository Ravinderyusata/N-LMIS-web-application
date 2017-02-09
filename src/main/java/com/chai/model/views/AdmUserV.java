package com.chai.model.views;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ADM_USERS_V")
public class AdmUserV implements Serializable {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "USER_ID")
	private Integer x_USER_ID;
	@Column(name = "TELEPHONE_NUMBER")
	private String x_TELEPHONE_NUMBER;
	@Column(name = "STATUS")
	private String x_STATUS;
	@Column(name = "START_DATE")
	private Date x_START_DATE;
	@Column(name = "ROLE_NAME")
	private String x_ROLE_NAME;
	@Column(name = "ROLE_ID")
	private Integer x_ROLE_ID;
	@Column(name = "ROLE_DETAILS")
	private String x_ROLE_DETAILS;
	@Column(name = "PASSWORD")
	private String x_PASSWORD;
	@Column(name = "MIDDLE_NAME")
	private String x_MIDDLE_NAME;
	@Column(name = "LOGIN_NAME")
	private String x_LOGIN_NAME;
	@Column(name = "LAST_NAME")
	private String x_LAST_NAME;
	@Column(name = "FIRST_NAME")
	private String x_FIRST_NAME;
	@Column(name = "END_DATE")
	private Date x_END_DATE;
	@Column(name = "EMAIL")
	private String x_EMAIL;
	@Column(name = "COMPANY_ID")
	private Integer x_COMPANY_ID;
	@Column(name = "ACTIVATED_ON")
	private Date x_ACTIVATED_ON;
	@Column(name = "ACTIVATED_BY")
	private Integer x_ACTIVATED_BY;
	@Column(name = "WAREHOUSE_NAME")
	private String x_WAREHOUSE_NAME;
	@Column(name = "WAREHOUSE_ID")
	private Integer x_WAREHOUSE_ID;
	@Column(name = "USER_TYPE_NAME")
	private String x_USER_TYPE_NAME;
	@Column(name = "USER_TYPE_ID")
	private Integer x_USER_TYPE_ID;
	@Column(name = "USER_TYPE_DESCRIPTION")
	private String x_USER_TYPE_DESCRIPTION;
	@Column(name = "USER_TYPE_CODE")
	private String x_USER_TYPE_CODE;

	public AdmUserV() {
	}

	public Integer getX_USER_ID() {
		return x_USER_ID;
	}

	public void setX_USER_ID(Integer x_USER_ID) {
		this.x_USER_ID = x_USER_ID;
	}

	public String getX_TELEPHONE_NUMBER() {
		return x_TELEPHONE_NUMBER;
	}

	public void setX_TELEPHONE_NUMBER(String x_TELEPHONE_NUMBER) {
		this.x_TELEPHONE_NUMBER = x_TELEPHONE_NUMBER;
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

	public String getX_ROLE_NAME() {
		return x_ROLE_NAME;
	}

	public void setX_ROLE_NAME(String x_ROLE_NAME) {
		this.x_ROLE_NAME = x_ROLE_NAME;
	}

	public Integer getX_ROLE_ID() {
		return x_ROLE_ID;
	}

	public void setX_ROLE_ID(Integer x_ROLE_ID) {
		this.x_ROLE_ID = x_ROLE_ID;
	}

	public String getX_ROLE_DETAILS() {
		return x_ROLE_DETAILS;
	}

	public void setX_ROLE_DETAILS(String x_ROLE_DETAILS) {
		this.x_ROLE_DETAILS = x_ROLE_DETAILS;
	}

	public String getX_PASSWORD() {
		return x_PASSWORD;
	}

	public void setX_PASSWORD(String x_PASSWORD) {
		this.x_PASSWORD = x_PASSWORD;
	}

	public String getX_MIDDLE_NAME() {
		return x_MIDDLE_NAME;
	}

	public void setX_MIDDLE_NAME(String x_MIDDLE_NAME) {
		this.x_MIDDLE_NAME = x_MIDDLE_NAME;
	}

	public String getX_LOGIN_NAME() {
		return x_LOGIN_NAME;
	}

	public void setX_LOGIN_NAME(String x_LOGIN_NAME) {
		this.x_LOGIN_NAME = x_LOGIN_NAME;
	}

	public String getX_LAST_NAME() {
		return x_LAST_NAME;
	}

	public void setX_LAST_NAME(String x_LAST_NAME) {
		this.x_LAST_NAME = x_LAST_NAME;
	}

	public String getX_FIRST_NAME() {
		return x_FIRST_NAME;
	}

	public void setX_FIRST_NAME(String x_FIRST_NAME) {
		this.x_FIRST_NAME = x_FIRST_NAME;
	}

	public Date getX_END_DATE() {
		return x_END_DATE;
	}

	public void setX_END_DATE(Date x_END_DATE) {
		this.x_END_DATE = x_END_DATE;
	}

	public String getX_EMAIL() {
		return x_EMAIL;
	}

	public void setX_EMAIL(String x_EMAIL) {
		this.x_EMAIL = x_EMAIL;
	}

	public Integer getX_COMPANY_ID() {
		return x_COMPANY_ID;
	}

	public void setX_COMPANY_ID(Integer x_COMPANY_ID) {
		this.x_COMPANY_ID = x_COMPANY_ID;
	}

	public Date getX_ACTIVATED_ON() {
		return x_ACTIVATED_ON;
	}

	public void setX_ACTIVATED_ON(Date x_ACTIVATED_ON) {
		this.x_ACTIVATED_ON = x_ACTIVATED_ON;
	}

	public Integer getX_ACTIVATED_BY() {
		return x_ACTIVATED_BY;
	}

	public void setX_ACTIVATED_BY(Integer x_ACTIVATED_BY) {
		this.x_ACTIVATED_BY = x_ACTIVATED_BY;
	}

	public String getX_WAREHOUSE_NAME() {
		return x_WAREHOUSE_NAME;
	}

	public void setX_WAREHOUSE_NAME(String x_WAREHOUSE_NAME) {
		this.x_WAREHOUSE_NAME = x_WAREHOUSE_NAME;
	}

	public Integer getX_WAREHOUSE_ID() {
		return x_WAREHOUSE_ID;
	}

	public void setX_WAREHOUSE_ID(Integer x_WAREHOUSE_ID) {
		this.x_WAREHOUSE_ID = x_WAREHOUSE_ID;
	}

	public String getX_USER_TYPE_NAME() {
		return x_USER_TYPE_NAME;
	}

	public void setX_USER_TYPE_NAME(String x_USER_TYPE_NAME) {
		this.x_USER_TYPE_NAME = x_USER_TYPE_NAME;
	}

	public Integer getX_USER_TYPE_ID() {
		return x_USER_TYPE_ID;
	}

	public void setX_USER_TYPE_ID(Integer x_USER_TYPE_ID) {
		this.x_USER_TYPE_ID = x_USER_TYPE_ID;
	}

	public String getX_USER_TYPE_DESCRIPTION() {
		return x_USER_TYPE_DESCRIPTION;
	}

	public void setX_USER_TYPE_DESCRIPTION(String x_USER_TYPE_DESCRIPTION) {
		this.x_USER_TYPE_DESCRIPTION = x_USER_TYPE_DESCRIPTION;
	}

	public String getX_USER_TYPE_CODE() {
		return x_USER_TYPE_CODE;
	}

	public void setX_USER_TYPE_CODE(String x_USER_TYPE_CODE) {
		this.x_USER_TYPE_CODE = x_USER_TYPE_CODE;
	}

}
