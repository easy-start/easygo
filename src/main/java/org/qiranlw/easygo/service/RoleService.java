package org.qiranlw.easygo.service;

import org.qiranlw.easygo.bean.PageBean;
import org.qiranlw.easygo.bean.ResultEnum;
import org.qiranlw.easygo.bean.RoleBean;
import org.qiranlw.easygo.bean.UserDetailsBean;
import org.qiranlw.easygo.dao.RoleDao;
import org.qiranlw.easygo.exception.ServiceException;
import org.qiranlw.easygo.form.RoleForm;
import org.qiranlw.easygo.utils.EasygoUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public PageBean<RoleBean> getRolePage(RoleForm form) {
        return this.roleDao.selectRolePageByParams(form);
    }

    public RoleBean getRoleById(Long roleId) {
        return this.roleDao.selectById(roleId);
    }

    public RoleBean addRole(RoleForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        RoleBean bean = this.roleDao.selectByCode(form.getRoleCode());
        if (bean != null) {
            throw new ServiceException(500, "角色编码已存在");
        }
        RoleBean role = new RoleBean();
        role.setRoleCode(form.getRoleCode());
        role.setRoleName(form.getRoleName());
        role.setType(form.getType());
        role.setStatus(form.getStatus());
        role.setDescription(form.getDescription());
        role.setCreateUserId(userData.getUserId());
        role.setCreateTime(LocalDateTime.now());
        Long roleId = this.roleDao.insert(role);
        if (roleId == null || roleId == 0) {
            throw new ServiceException(ResultEnum.DATA_SAVE_FAILED);
        }
        return this.roleDao.selectById(roleId);
    }

    public RoleBean updateRole(RoleForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        RoleBean role = roleDao.selectById(form.getRoleId());
        if (role == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        if (StringUtils.hasText(form.getRoleName())) {
            role.setRoleName(form.getRoleName());
        }
        if (form.getType() != null) {
            role.setType(form.getType());
        }
        if (form.getStatus() != null) {
            role.setStatus(form.getStatus());
        }
        if (StringUtils.hasText(form.getDescription())) {
            role.setDescription(form.getDescription());
        }
        role.setUpdateUserId(userData.getUserId());
        role.setUpdateTime(LocalDateTime.now());
        int ret = this.roleDao.updateById(role);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        return role;
    }

    public RoleBean deleteRole(Long roleId) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        RoleBean role = roleDao.selectById(roleId);
        if (role == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        if (RoleBean.SYSTEM_ROLE_TYPE == role.getType()) {
            throw new ServiceException(500, "系统角色不能删除");
        }
        if (RoleBean.DISABLED_ROLE_STATUS == role.getStatus()) {
            throw new ServiceException(500, "请先禁用角色");
        }
        // TODO 如果角色有关联用户无法删除角色
        int ret = roleDao.deleteById(roleId);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        // TODO 删除其他数据关联角色数据
        return role;
    }

    public List<RoleBean> getRoleList(RoleForm form) {
        return this.roleDao.selectRoleList(form);
    }
}
