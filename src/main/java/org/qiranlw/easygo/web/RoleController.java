package org.qiranlw.easygo.web;

import org.qiranlw.easygo.annotation.Check;
import org.qiranlw.easygo.bean.PageBean;
import org.qiranlw.easygo.bean.Result;
import org.qiranlw.easygo.bean.RoleBean;
import org.qiranlw.easygo.bean.UserBean;
import org.qiranlw.easygo.form.RoleForm;
import org.qiranlw.easygo.form.UserForm;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiranlw
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @GetMapping("/list")
    public Result<List<RoleBean>> getRoleList() {
        return null;
    }

    @GetMapping("/page")
    public Result<PageBean<RoleBean>> getRolePage(RoleForm form) {
        return null;
    }

    @GetMapping("/user/page")
    @Check(key = "roleId", ex = "form.roleId != nil && form.roleId != ''", msg = "角色ID不能为空")
    public Result<PageBean<UserBean>> getRoleUserPage(UserForm form) {
        return null;
    }

    @GetMapping("/{roleId}")
    public Result<RoleBean> getRoleById(@PathVariable Long roleId) {
        return null;
    }

    @PostMapping("/add")
    @Check(key = "roleCode", ex = "form.roleCode != nil && form.roleCode != ''", msg = "角色编码不能为空")
    @Check(key = "roleName", ex = "form.roleName != nil && form.roleName != ''", msg = "角色名称不能为空")
    @Check(key = "type", ex = "form.type != nil && form.type != ''", msg = "角色名称不能为空")
    @Check(key = "status", ex = "form.status != nil && form.status != ''", msg = "角色名称不能为空")
    public Result<RoleBean> addRole(@RequestBody RoleForm form) {
        return null;
    }

    @PostMapping("/update")
    @Check(key = "roleId", ex = "form.roleId != nil && form.roleId != ''", msg = "角色ID不能为空")
    public Result<RoleBean> updateRole(@RequestBody RoleForm form) {
        return null;
    }

    @DeleteMapping("/delete/{roleId}")
    public Result<RoleBean> deleteRole(@PathVariable Long roleId) {
        return null;
    }
}
