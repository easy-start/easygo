package org.qiranlw.easygo.bean;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiranlw
 */
public class RoleBean extends BasicBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 3449102337885661186L;

    public static int SYSTEM_ROLE_TYPE = 1;

    public static int DISABLED_ROLE_STATUS = 0;

    private Long roleId;

    private String roleCode;

    private String roleName;

    private Integer type;

    private Integer status;

    private String description;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
