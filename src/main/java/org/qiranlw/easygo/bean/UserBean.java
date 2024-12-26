package org.qiranlw.easygo.bean;

import java.time.LocalDate;

/**
 * @author qiranlw
 */
public class UserBean extends BasicBean {

    /**
     * 用户信息主键ID
     */
    private Long userId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 用户简介
     */
    private String description;

    /**
     * 账号是否过期
     */
    private Integer accountNonExpired;

    /**
     * 账号是否锁定
     */
    private Integer accountNonLocked;

    /**
     * 证书是否过期
     */
    private Integer credentialsNonExpired;

    /**
     * 是否启用
     */
    private Integer enabled;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Integer accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Integer getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Integer accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Integer getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Integer credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
