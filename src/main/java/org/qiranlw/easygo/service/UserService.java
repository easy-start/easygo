package org.qiranlw.easygo.service;

import org.qiranlw.easygo.bean.*;
import org.qiranlw.easygo.dao.RoleDao;
import org.qiranlw.easygo.dao.UserDao;
import org.qiranlw.easygo.dao.UserRoleDao;
import org.qiranlw.easygo.exception.ServiceException;
import org.qiranlw.easygo.form.SetPasswordForm;
import org.qiranlw.easygo.form.UserForm;
import org.qiranlw.easygo.form.UserRoleForm;
import org.qiranlw.easygo.utils.EasygoUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final UserRoleDao userRoleDao;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 根据ID查询用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    public UserBean getUserById(Long userId) {
        return userDao.selectById(userId);
    }

    /**
     * 分页查询用户信息
     * @param form 查询条件
     * @return 用户列表
     */
    public PageBean<UserBean> getUserPage(UserForm form) {
        return this.userDao.selectPageByParams(form);
    }

    /**
     * 获取用户角色列表
     * @param userId 用户ID
     * @return 用户角色信息列表
     */
    public List<UserRoleBean> queryUserRoles(Long userId) {
        return userRoleDao.selectByUserId(userId);
    }

    /**
     * 管理员添加用户
     * @param form 用户信息
     * @return 新增的用户信息
     */
    public UserBean addUser(UserForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        UserBean user = this.userDao.selectByUsername(form.getUsername());
        if (user != null) {
            throw new ServiceException(500, "登录名已存在");
        }
        LocalDateTime now = LocalDateTime.now();
        UserBean userInfo = new UserBean();
        userInfo.setUsername(form.getUsername());
        userInfo.setNickname(form.getNickname());
        userInfo.setPassword(this.passwordEncoder.encode(EasygoUtils.DEFAULT_PASSWORD));
        userInfo.setSex(form.getSex());
        userInfo.setAvatarUrl(form.getAvatarUrl());
        userInfo.setEmail(form.getEmail());
        userInfo.setTelephone(form.getTelephone());
        userInfo.setBirthday(form.getBirthday());
        userInfo.setDescription(form.getDescription());
        userInfo.setEnabled(form.getEnabled());
        userInfo.setAccountNonExpired(1);
        userInfo.setAccountNonLocked(1);
        userInfo.setCredentialsNonExpired(1);
        userInfo.setCreateUserId(userData.getUserId());
        userInfo.setCreateTime(now);
        Long userId = this.userDao.insert(userInfo);
        if (userId == null || userId == 0) {
            throw new ServiceException(ResultEnum.DATA_SAVE_FAILED);
        }
        UserBean userBean = this.userDao.selectById(userId);
        UserRoleBean userRole = new UserRoleBean();
        userRole.setUserId(userId);
        userRole.setRoleId(UserBean.DEFAULT_ROLE_ID);
        userRole.setCreateUserId(userData.getUserId());
        userRole.setCreateTime(now);
        int ret = this.userRoleDao.insert(userRole);
        if (ret == 0) {
            // TODO 新增用户添加默认角色失败
        }
        return userBean;
    }

    /**
     * 管理员修改用户信息
     * @param form 用户信息，包含用户ID
     * @return 用户信息
     */
    public UserBean updateUserBySystem(UserForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        UserBean user = userDao.selectById(form.getUserId());
        if (user == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        if (StringUtils.hasText(form.getNickname())) {
            user.setNickname(form.getNickname());
        }
        if (form.getSex() != null) {
            form.setSex(form.getSex());
        }
        if (StringUtils.hasText(form.getAvatarUrl())) {
            user.setAvatarUrl(form.getAvatarUrl());
        }
        if (StringUtils.hasText(form.getEmail())) {
            user.setEmail(form.getEmail());
        }
        if (StringUtils.hasText(form.getTelephone())) {
            user.setTelephone(form.getTelephone());
        }
        if (form.getBirthday() != null) {
            user.setBirthday(form.getBirthday());
        }
        if (StringUtils.hasText(form.getDescription())) {
            user.setDescription(form.getDescription());
        }
        if (form.getEnabled() != null) {
            user.setEnabled(form.getEnabled());
        }
        user.setUpdateUserId(userData.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        int ret = this.userDao.updateById(user);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        return user;
    }

    /**
     * 用户修改自己的信息
     * @param form 用户信息
     * @return 用户信息
     */
    public UserBean updateUser(UserForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        UserBean user = userDao.selectById(userData.getUserId());
        if (user == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        if (StringUtils.hasText(form.getNickname())) {
            user.setNickname(form.getNickname());
        }
        if (form.getSex() != null) {
            form.setSex(form.getSex());
        }
        if (StringUtils.hasText(form.getAvatarUrl())) {
            user.setAvatarUrl(form.getAvatarUrl());
        }
        if (StringUtils.hasText(form.getEmail())) {
            user.setEmail(form.getEmail());
        }
        if (StringUtils.hasText(form.getTelephone())) {
            user.setTelephone(form.getTelephone());
        }
        if (form.getBirthday() != null) {
            user.setBirthday(form.getBirthday());
        }
        if (StringUtils.hasText(form.getDescription())) {
            user.setDescription(form.getDescription());
        }
        user.setUpdateUserId(userData.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        int ret = this.userDao.updateById(user);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        return user;
    }

    /**
     * 管理员根据ID删除用户
     * @param userId 用户ID
     * @return 删除的用户信息
     */
    public UserBean deleteUser(Long userId) {
        UserBean user = userDao.selectById(userId);
        if (user == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        int ret = userDao.deleteById(user.getUserId());
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_DELETE_FAILED);
        }
        ret = userRoleDao.deleteByUserId(userId);
        if (ret == 0) {
            // TODO 删除用户角色失败
        }
        return user;
    }

    /**
     * 管理员重置用户密码
     * @param userId 用户ID
     * @return 用户信息
     */
    public UserBean resetPassword(Long userId) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        UserBean user = this.userDao.selectById(userId);
        if (user == null) {
            throw new ServiceException(500, "未找到用户");
        }
        user.setPassword(passwordEncoder.encode(EasygoUtils.DEFAULT_PASSWORD));
        user.setUpdateUserId(userData.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        int ret = this.userDao.updatePassword(user);
        if (ret == 0) {
            throw new ServiceException(500, "用户密码重置失败");
        }
        user.setPassword(null);
        return user;
    }

    /**
     * 用户修改密码
     * @param form 修改密码表单，需要有旧密码、新密码、确认密码
     * @return 用户信息
     */
    public UserBean setPassword(SetPasswordForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        UserBean user = this.userDao.selectById(userData.getUserId());
        if (user == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        boolean flag = this.passwordEncoder.matches(form.getOldPassword(), userData.getPassword());
        if (!flag) {
            throw new ServiceException(500, "旧密码错误");
        }
        if (!form.getNewPassword().equals(form.getRePassword())) {
            throw new ServiceException(500, "两次输入密码不一致");
        }
        user.setPassword(this.passwordEncoder.encode(form.getNewPassword()));
        user.setUpdateUserId(userData.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        int ret = this.userDao.updatePassword(user);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        user.setPassword(null);
        return user;
    }

    /**
     * 管理员锁定解锁用户
     * @param form 用户ID和锁定状态
     * @return 更新后用户信息
     */
    public UserBean lockUser(UserForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        UserBean user = this.userDao.selectById(userData.getUserId());
        if (user == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        user.setAccountNonLocked(form.getLocked());
        user.setUpdateUserId(userData.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        int ret = this.userDao.updateLockedStatus(user);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        return user;
    }

    public UserRoleBean addRole(UserRoleForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        UserBean user = this.userDao.selectById(form.getUserId());
        if (user == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        RoleBean role = this.roleDao.selectById(form.getRoleId());
        if (role == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        UserRoleBean userRole = this.userRoleDao.selectByUserRole(form);
        if (userRole != null) {
            return userRole;
        }
        userRole = new UserRoleBean();
        userRole.setUserId(form.getUserId());
        userRole.setRoleId(form.getRoleId());
        userRole.setCreateUserId(userData.getUserId());
        userRole.setCreateTime(LocalDateTime.now());
        int ret = this.userRoleDao.insert(userRole);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_SAVE_FAILED);
        }
        return null;
    }

    public UserRoleBean deleteRole(UserRoleForm form) {
        UserRoleBean userRole = this.userRoleDao.selectByUserRole(form);
        if (userRole == null) {
            return null;
        }
        UserRoleBean bean = new UserRoleBean();
        bean.setUserId(form.getUserId());
        bean.setRoleId(form.getRoleId());
        int ret = this.userRoleDao.deleteByUserRole(bean);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_DELETE_FAILED);
        }
        return userRole;
    }
}
