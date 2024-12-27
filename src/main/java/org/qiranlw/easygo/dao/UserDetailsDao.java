package org.qiranlw.easygo.dao;

import org.qiranlw.easygo.bean.UserDetailsBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDetailsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDetailsBean selectByUsername(String username) {
        String sql = "select t.* from sys_user t where t.deleted = 0 and t.username = ? ";
        return this.jdbcTemplate.queryForObject(sql, new RowMapper<UserDetailsBean>() {
            @Override
            public UserDetailsBean mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserDetailsBean user = new UserDetailsBean();
                user.setUserId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getInt("sex"));
                user.setAvatarUrl(rs.getString("avatar_url"));
                user.setEmail(rs.getString("email"));
                user.setTelephone(rs.getString("telephone"));
                user.setDescription(rs.getString("description"));
                user.setAccountNonExpired(rs.getInt("account_non_expired") > 0);
                user.setAccountNonLocked(rs.getInt("account_non_locked") > 0);
                user.setCredentialsNonExpired(rs.getInt("credentials_non_expired") > 0);
                user.setEnabled(rs.getInt("enabled") > 0);
                return user;
            }
        }, username);
    }
}
