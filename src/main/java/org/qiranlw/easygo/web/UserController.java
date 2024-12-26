package org.qiranlw.easygo.web;

import org.qiranlw.easygo.bean.PageBean;
import org.qiranlw.easygo.bean.Result;
import org.qiranlw.easygo.bean.RoleBean;
import org.qiranlw.easygo.bean.UserBean;
import org.qiranlw.easygo.form.RoleForm;
import org.qiranlw.easygo.form.SetPasswordForm;
import org.qiranlw.easygo.form.UserForm;
import org.qiranlw.easygo.form.UserRoleForm;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiranlw
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/page")
    public Result<PageBean<UserBean>> getUserPage(UserForm form) {
        return null;
    }

    @GetMapping("/{id}")
    public Result<UserBean> getUserById(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/add")
    public Result<UserBean> addUser(@RequestBody UserForm form) {
        return null;
    }

    @PostMapping("/update")
    public Result<UserBean> updateUser(@RequestBody UserForm form) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Result<UserBean> deleteUser(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/resetPassword/{userId}")
    public Result<UserBean> resetPassword(@PathVariable Long userId) {
        return null;
    }

    @GetMapping("/roles")
    public Result<List<RoleBean>> getUserRoles(RoleForm form) {
        return null;
    }

    @PostMapping("/addRole")
    public Result<String> addRole(UserRoleForm form) {
        return null;
    }

    @PostMapping("/deleteRole")
    public Result<String> deleteRole(UserRoleForm form) {
        return null;
    }

    @GetMapping("/current")
    public Result<UserBean> getCurrentUser() {
        return null;
    }

    @PostMapping("/updateCurrent")
    public Result<UserBean> updateCurrent(@RequestBody UserForm form) {
        return null;
    }

    @PostMapping("/setPassword")
    public Result<UserBean> setPassword(@RequestBody SetPasswordForm form) {
        return null;
    }
}
