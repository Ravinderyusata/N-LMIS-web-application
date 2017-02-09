package com.chai.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ADM_USERS")
public class UserBean {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "USER_ID")
	private Integer x_USER_ID;
	@Column(name = "WAREHOUSE_ID")
	private Integer x_WAREHOUSE_ID;
	@Column(name = "COMPANY_ID")
	private Integer x_COMPANY_ID;
	@Column(name = "EMPLOYEE_ID")
	private Integer x_EMPLOYEE_ID;
	@Column(name = "FIRST_NAME")
	private String x_FIRST_NAME;
	@Column(name = "MIDDLE_NAME")
	private String x_MIDDLE_NAME;
	@Column(name = "LAST_NAME")
	private String x_LAST_NAME;
	@Column(name = "PASSWORD")
	private String x_PASSWORD;
	@Column(name = "LOGIN_NAME")
	private String x_LOGIN_NAME;
	@Column(name = "ACTIVATED")
	private String x_ACTIVATED;
	@Column(name = "ACTIVATED_BY")
	private Integer x_ACTIVATED_BY;
	@Column(name = "ACTIVATED_ON")
	private Date x_ACTIVATED_ON;
	@Column(name = "USER_TYPE_ID")
	private Integer x_USER_TYPE_ID;
	@Column(name = "STATUS")
	private String x_STATUS;
	@Column(name = "START_DATE")
	private Date x_START_DATE;
	@Column(name = "END_DATE")
	private Date x_END_DATE;
	@Column(name = "CREATED_BY")
	private Integer x_CREATED_BY;
	@Column(name = "CREATED_ON")
	private Date x_CREATED_ON;
	@Column(name = "UPDATED_BY")
	private Integer x_UPDATED_BY;
	@Column(name = "LAST_UPDATED_ON")
	private Date x_LAST_UPDATED_ON;
	@Column(name = "EMAIL")
	private String x_EMAIL;
	@Column(name = "TELEPHONE_NUMBER")
	private String x_TELEPHONE_NUMBER;
	@Column(name = "SYNC_FLAG")
	private String x_SYNC_FLAG;

	public UserBean() {
	}

	public Integer getX_USER_ID() {
		return x_USER_ID;
	}

	public void setX_USER_ID(Integer x_USER_ID) {
		this.x_USER_ID = x_USER_ID;
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

	public Integer getX_EMPLOYEE_ID() {
		return x_EMPLOYEE_ID;
	}

	public void setX_EMPLOYEE_ID(Integer x_EMPLOYEE_ID) {
		this.x_EMPLOYEE_ID = x_EMPLOYEE_ID;
	}

	public String getX_FIRST_NAME() {
		return x_FIRST_NAME;
	}

	public void setX_FIRST_NAME(String x_FIRST_NAME) {
		this.x_FIRST_NAME = x_FIRST_NAME;
	}

	public String getX_MIDDLE_NAME() {
		return x_MIDDLE_NAME;
	}

	public void setX_MIDDLE_NAME(String x_MIDDLE_NAME) {
		this.x_MIDDLE_NAME = x_MIDDLE_NAME;
	}

	public String getX_LAST_NAME() {
		return x_LAST_NAME;
	}

	public void setX_LAST_NAME(String x_LAST_NAME) {
		this.x_LAST_NAME = x_LAST_NAME;
	}

	public String getX_PASSWORD() {
		return x_PASSWORD;
	}

	public void setX_PASSWORD(String x_PASSWORD) {
		this.x_PASSWORD = x_PASSWORD;
	}

	public String getX_LOGIN_NAME() {
		return x_LOGIN_NAME;
	}

	public void setX_LOGIN_NAME(String x_LOGIN_NAME) {
		this.x_LOGIN_NAME = x_LOGIN_NAME;
	}

	public String getX_ACTIVATED() {
		return x_ACTIVATED;
	}

	public void setX_ACTIVATED(String x_ACTIVATED) {
		this.x_ACTIVATED = x_ACTIVATED;
	}

	public Integer getX_ACTIVATED_BY() {
		return x_ACTIVATED_BY;
	}

	public void setX_ACTIVATED_BY(Integer x_ACTIVATED_BY) {
		this.x_ACTIVATED_BY = x_ACTIVATED_BY;
	}

	public Date getX_ACTIVATED_ON() {
		return x_ACTIVATED_ON;
	}

	public void setX_ACTIVATED_ON(Date x_ACTIVATED_ON) {
		this.x_ACTIVATED_ON = x_ACTIVATED_ON;
	}

	public Integer getX_USER_TYPE_ID() {
		return x_USER_TYPE_ID;
	}

	public void setX_USER_TYPE_ID(Integer x_USER_TYPE_ID) {
		this.x_USER_TYPE_ID = x_USER_TYPE_ID;
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

	public String getX_EMAIL() {
		return x_EMAIL;
	}

	public void setX_EMAIL(String x_EMAIL) {
		this.x_EMAIL = x_EMAIL;
	}

	public String getX_TELEPHONE_NUMBER() {
		return x_TELEPHONE_NUMBER;
	}

	public void setX_TELEPHONE_NUMBER(String x_TELEPHONE_NUMBER) {
		this.x_TELEPHONE_NUMBER = x_TELEPHONE_NUMBER;
	}

	public String getX_SYNC_FLAG() {
		return x_SYNC_FLAG;
	}

	public void setX_SYNC_FLAG(String x_SYNC_FLAG) {
		this.x_SYNC_FLAG = x_SYNC_FLAG;
	}

}
