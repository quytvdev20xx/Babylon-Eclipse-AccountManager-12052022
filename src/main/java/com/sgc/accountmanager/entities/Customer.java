package com.sgc.accountmanager.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity// This tells Hibernate to make a table out of this class
@Table(name="users")
public class Customer implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userid", nullable = false)
	private int userId;
	@Column(name = "username")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "email")
	private String email;
	@Column(name = "updatetime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date  updateTime;
	@Column(name = "created_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@Column(name = "block")
	private boolean block;
	@Column(name = "providerid")
	private int providerId;
	@Column(name = "reg_phone")
	private String reg_phone;
	@Column(name = "type")
	private byte type;
	@Column(name = "ime")
	private String ime;
	@Column(name = "ip")
	private String ip;
	@Column(name = "server_id")
	private int serverID;
	@Column(name = "game_id")
	private int gameID;
	
	@Column(name = "facebookid")
	private String facebookId;
	
	@Column(name = "aliasname")
	private String aliasName;
	
	@Column(name = "dateofbirthday")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateOfBirthday;
	
	@Column(name = "lastlogouttime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastLogoutTime;
	@Column(name = "lastlogintime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp lastLoginTime;
	@Column(name = "lastplayserver")
	private int lastPlayServer;
	private String token;
	private int diamond;

	//===================//
	
	public String getAliasName() {
		return aliasName;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getLastPlayServer() {
		return lastPlayServer;
	}

	public void setLastPlayServer(int lastPlayServer) {
		this.lastPlayServer = lastPlayServer;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Date getDateOfBirthday() {
		return dateOfBirthday;
	}

	public void setDateOfBirthday(Date dateOfBirthday) {
		this.dateOfBirthday = dateOfBirthday;
	}

	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(Date lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDiamond() {
		return diamond;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public synchronized void setDiamond(int diamond) {
		this.diamond = diamond;
	}



	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public String getReg_phone() {
		return reg_phone;
	}

	public void setReg_phone(String reg_phone) {
		this.reg_phone = reg_phone;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	public int getServerID() {
		return serverID;
	}

	public void setServerID(int serverID) {
		this.serverID = serverID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public synchronized void subDiamod(int diadiamondmod, byte type, String name) {
		this.diamond -= diamond;
		if (this.diamond < 0) {
			this.diamond = 0;
		}
		//setChange(true);
		//moneyLog.append(type, diamond, name);
		//2021/07/21
		//actionLogs.append(type, 0, -diamond, 0, this.userId);
	}

	public synchronized void addDiamond(int diamond,  String name) {
		this.diamond += diamond;
		//setChange(true);
		//moneyLog.append(type, diamond, name);
		//2021/07/21
		//actionLogs.append(type, 0, diamond, 0, this.userId);
	}

}
