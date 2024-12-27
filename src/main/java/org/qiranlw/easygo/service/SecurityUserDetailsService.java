package org.qiranlw.easygo.service;

import org.qiranlw.easygo.bean.UserDetailsBean;
import org.qiranlw.easygo.bean.UserRoleBean;
import org.qiranlw.easygo.dao.UserDetailsDao;
import org.qiranlw.easygo.dao.UserRoleDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    /**
     * 角色默认前缀
     */
    private final static String defaultRolePrefix = "ROLE_";

    private final UserDetailsDao userDetailsDao;

    private final UserRoleDao userRoleDao;

    public SecurityUserDetailsService(UserDetailsDao userDetailsDao, UserRoleDao userRoleDao) {
        this.userDetailsDao = userDetailsDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsBean userInfo = userDetailsDao.selectByUsername(username);
        if (userInfo == null) {
            return null;
        }
        List<UserRoleBean> roles = userRoleDao.selectByUserId(userInfo.getUserId());
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        for (UserRoleBean role : roles) {
            authoritySet.add(new SimpleGrantedAuthority(defaultRolePrefix + role.getRoleCode()));
        }
        // 添加角色信息
        userInfo.setAuthorities(authoritySet);
        // TODO 添加权限数据
        return userInfo;
    }
}
