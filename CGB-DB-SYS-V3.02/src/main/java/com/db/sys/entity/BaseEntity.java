package com.db.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{
	private static final long serialVersionUID = -1898901377220449504L;
	private String createdUser;
	private String modifiedUser;
	private Date createdTime;
	private Date modifiedTime;
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	@Override
	public String toString() {
		return "BaseEntity [createdUser=" + createdUser + ", modifiedUser=" + modifiedUser + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + "]";
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
}
