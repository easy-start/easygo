package org.qiranlw.easygo.bean;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiranlw
 */
public class UserRoleBean extends BasicBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 5381647756700704106L;

    private Long userRoleId;

    private Long userId;

    private String nickname;

    private Long roleId;

    private String roleCode;

    private String roleName;

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
