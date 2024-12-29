package org.qiranlw.easygo.web;

import org.qiranlw.easygo.annotation.Check;
import org.qiranlw.easygo.bean.*;
import org.qiranlw.easygo.form.RoleForm;
import org.qiranlw.easygo.form.SetPasswordForm;
import org.qiranlw.easygo.form.UserForm;
import org.qiranlw.easygo.form.UserRoleForm;
import org.qiranlw.easygo.service.RoleService;
import org.qiranlw.easygo.service.UserService;
import org.qiranlw.easygo.utils.EasygoUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiranlw
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/page")
    public Result<PageBean<UserBean>> getUserPage(UserForm form) {
        return Result.success(this.userService.getUserPage(form));
    }

    @GetMapping("/{id}")
    public Result<UserBean> getUserById(@PathVariable Long id) {
        return Result.success(this.userService.getUserById(id));
    }

    @PreAuthorize("hasRole('system')")
    @PostMapping("/add")
    @Check(key = "username", ex = "form.username != nil && form.username != ''", msg = "登录名不能为空")
    @Check(key = "nickname", ex = "form.nickname != nil && form.nickname != ''", msg = "用户姓名不能为空")
    @Check(key = "telephone", ex = "form.telephone != nil && form.telephone != ''", msg = "手机号不能为空")
    @Check(key = "enabled", ex = "form.enabled != nil && form.enabled != ''", msg = "状态不能为空")
    public Result<UserBean> addUser(@RequestBody UserForm form) {
        return Result.success(this.userService.addUser(form));
    }

    @PreAuthorize("hasRole('system')")
    @PostMapping("/update")
    @Check(key = "userId", ex = "form.userId != nil && form.userId != ''", msg = "用户ID不能为空")
    @Check(key = "username", ex = "form.username != nil && form.username != ''", msg = "登录名不能为空")
    @Check(key = "nickname", ex = "form.nickname != nil && form.nickname != ''", msg = "用户姓名不能为空")
    @Check(key = "telephone", ex = "form.telephone != nil && form.telephone != ''", msg = "手机号不能为空")
    @Check(key = "enabled", ex = "form.enabled != nil && form.enabled != ''", msg = "状态不能为空")
    public Result<UserBean> updateUser(@RequestBody UserForm form) {
        return Result.success(this.userService.updateUserBySystem(form));
    }

    @PreAuthorize("hasRole('system')")
    @DeleteMapping("/{id}")
    public Result<UserBean> deleteUser(@PathVariable Long userId) {
        return Result.success(this.userService.deleteUser(userId));
    }

    /**
     * 管理员重置指定用户密码
     * @param userId 用户ID
     * @return 用户信息
     */
    @PreAuthorize("hasRole('system')")
    @PostMapping("/resetPassword/{userId}")
    public Result<UserBean> resetPassword(@PathVariable Long userId) {
        return Result.success(this.userService.resetPassword(userId));
    }

    /**
     * 管理员锁定/解锁用户
     * @param form 用户ID和锁定状态
     * @return 锁定/解锁的用户信息
     */
    @PreAuthorize("hasRole('system')")
    @PostMapping("/lock")
    @Check(key = "userId", ex = "form.userId != nil && form.userId != ''", msg = "用户ID不能为空")
    @Check(key = "locked", ex = "form.locked != nil && form.locked != ''", msg = "锁定状态不能为空")
    @Check(key = "locked", ex = "form.locked == 1 || form.locked == 0", msg = "锁定状态错误")
    public Result<UserBean> lockUser(UserForm form) {
        return Result.success(this.userService.lockUser(form));
    }

    @GetMapping("/roles")
    @Check(key = "userId", ex = "form.userId != nil && form.userId != ''", msg = "用户ID不能为空")
    public Result<List<RoleBean>> getUserRoles(RoleForm form) {
        return Result.success(this.roleService.getRoleList(form));
    }

    /**
     * 管理员用户添加角色
     * @param form 用户ID和角色ID
     * @return 新增的用户角色信息
     */
    @PreAuthorize("hasRole('system')")
    @PostMapping("/addRole")
    @Check(key = "userId", ex = "form.userId != nil && form.userId != ''", msg = "用户ID不能为空")
    @Check(key = "roleId", ex = "form.roleId != nil && form.roleId != ''", msg = "角色ID不能为空")
    public Result<UserRoleBean> addRole(UserRoleForm form) {
        return Result.success(this.userService.addRole(form));
    }

    /**
     * 管理员删除用户角色
     * @param form 用户ID和角色ID
     * @return 删除的用户角色信息
     */
    @PreAuthorize("hasRole('system')")
    @PostMapping("/deleteRole")
    @Check(key = "userId", ex = "form.userId != nil && form.userId != ''", msg = "用户ID不能为空")
    @Check(key = "roleId", ex = "form.roleId != nil && form.roleId != ''", msg = "角色ID不能为空")
    public Result<UserRoleBean> deleteRole(UserRoleForm form) {
        return Result.success(this.userService.deleteRole(form));
    }

    /**
     * 获取登录用户信息
     * @return 用户信息
     */
    @GetMapping("/current")
    public Result<UserBean> getCurrentUser() {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        return Result.success(this.userService.getUserById(userData.getUserId()));
    }

    /**
     * 更新登录用户信息
     * @param form 用户信息
     * @return 修改后用户信息
     */
    @PostMapping("/updateInfo")
    public Result<UserBean> updateInfo(@RequestBody UserForm form) {
        return Result.success(this.userService.updateUser(form));
    }

    /**
     * 用户修改密码
     * @param form 旧密码和新密码
     * @return 用户信息
     */
    @PostMapping("/setPassword")
    @Check(key = "oldPassword", ex = "form.oldPassword != nil && form.oldPassword != ''", msg = "旧密码不能为空")
    @Check(key = "newPassword", ex = "form.newPassword != nil && form.newPassword != ''", msg = "新密码不能为空")
    @Check(key = "rePassword", ex = "form.rePassword != nil && form.rePassword != ''", msg = "确认密码不能为空")
    public Result<UserBean> setPassword(@RequestBody SetPasswordForm form) {
        return Result.success(this.userService.setPassword(form));
    }
}
