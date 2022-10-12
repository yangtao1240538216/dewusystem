package com.example.dewu.dataobject;

import com.example.dewu.model.User;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class UserDO {
    /**
     * 主键
     */
    private long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String pwd;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     *创建时间
     */
    private LocalDateTime gmtCreated;

    /**
     *修改时间
     */
    private LocalDateTime gmtModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * DO 转换为 Model
     * @return User
     */
    public User toModel() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}