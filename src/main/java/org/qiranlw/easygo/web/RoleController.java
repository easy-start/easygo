package org.qiranlw.easygo.web;

import org.qiranlw.easygo.annotation.Check;
import org.qiranlw.easygo.bean.PageBean;
import org.qiranlw.easygo.bean.Result;
import org.qiranlw.easygo.bean.RoleBean;
import org.qiranlw.easygo.bean.UserBean;
import org.qiranlw.easygo.form.RoleForm;
import org.qiranlw.easygo.form.UserForm;
import org.qiranlw.easygo.service.RoleService;
import org.qiranlw.easygo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiranlw
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public Result<List<RoleBean>> getRoleList() {
        RoleForm form = new RoleForm();
        form.setStatus(1);
        return Result.success(this.roleService.getRoleList(form));
    }

    @GetMapping("/page")
    public Result<PageBean<RoleBean>> getRolePage(RoleForm form) {
        return Result.success(this.roleService.getRolePage(form));
    }

    @GetMapping("/user/page")
    @Check(key = "roleId", ex = "form.roleId != nil && form.roleId != ''", msg = "角色ID不能为空")
    public Result<PageBean<UserBean>> getRoleUserPage(UserForm form) {
        return Result.success(this.userService.getUserPage(form));
    }

    @GetMapping("/{roleId}")
    public Result<RoleBean> getRoleById(@PathVariable Long roleId) {
        return Result.success(this.roleService.getRoleById(roleId));
    }

    @PreAuthorize("hasRole('system')")
    @PostMapping("/add")
    @Check(key = "roleCode", ex = "form.roleCode != nil && form.roleCode != ''", msg = "角色编码不能为空")
    @Check(key = "roleCode", ex = "form.roleCode != nil && form.roleCode != ''", msg = "角色编码不能为空")
    @Check(key = "roleCode", ex = "form.roleCode ~= /^[a-zA-Z][a-zA-Z0-9_]{5, 15}$/", msg = "角色编码必须是6到16位英文数字，且以英文字母开头！")
    @Check(key = "roleName", ex = "form.roleName != nil && form.roleName != ''", msg = "角色名称不能为空")
    @Check(key = "type", ex = "form.type != nil && form.type != ''", msg = "角色类型不能为空")
    @Check(key = "status", ex = "form.status != nil && form.status != ''", msg = "角色状态不能为空")
    public Result<RoleBean> addRole(@RequestBody RoleForm form) {
        return Result.success(this.roleService.addRole(form));
    }

    @PreAuthorize("hasRole('system')")
    @PostMapping("/update")
    @Check(key = "roleId", ex = "form.roleId != nil && form.roleId != ''", msg = "角色ID不能为空")
    @Check(key = "roleName", ex = "form.roleName != nil && form.roleName != ''", msg = "角色名称不能为空")
    @Check(key = "type", ex = "form.type != nil && form.type != ''", msg = "角色类型不能为空")
    @Check(key = "status", ex = "form.status != nil && form.status != ''", msg = "角色状态不能为空")
    public Result<RoleBean> updateRole(@RequestBody RoleForm form) {
        return Result.success(this.roleService.updateRole(form));
    }

    @PreAuthorize("hasRole('system')")
    @DeleteMapping("/delete/{roleId}")
    public Result<RoleBean> deleteRole(@PathVariable Long roleId) {
        return Result.success(this.roleService.deleteRole(roleId));
    }
}
