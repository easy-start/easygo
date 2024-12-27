package org.qiranlw.easygo.dao;

import org.qiranlw.easygo.bean.UserRoleBean;
import org.qiranlw.easygo.form.UserRoleForm;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleDao extends BaseDao {

    public static final RowMapper<UserRoleBean> USER_ROLE_ROW_MAPPER = new BeanPropertyRowMapper<>(UserRoleBean.class);

    private final JdbcTemplate jdbcTemplate;

    public UserRoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    private static final String QUERY_SQL = """
    select t1.user_role_id, t1.user_id, t1.role_id, t2.nickname, t3.role_code, t3.role_name,
    t1.create_user_id, t1.create_time, t1.update_user_id, t1.update_time
    from sys_user_role t1
    inner join sys_user t2 on t2.user_id = t1.user_id and t2.deleted = 0
    inner join sys_role t3 on t3.role_id = t1.role_id and t3.deleted = 0
    """;

    public UserRoleBean selectById(Long id) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t1.user_role_id = ? ";
        return jdbcTemplate.queryForObject(sql, UserRoleDao.USER_ROLE_ROW_MAPPER, id);
    }

    public UserRoleBean selectByUserRole(UserRoleForm form) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t1.user_id = ? and t1.role_id = ? ";
        return jdbcTemplate.queryForObject(sql, UserRoleDao.USER_ROLE_ROW_MAPPER, form.getUserId(), form.getRoleId());
    }

    public List<UserRoleBean> selectByUserId(Long userId) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t3.status = 1 and t.user_id = ? ";
        return jdbcTemplate.query(sql, UserRoleDao.USER_ROLE_ROW_MAPPER, userId);
    }

    public int insert(UserRoleBean userRole) {
        String sql = "insert into sys_user_role (user_id, role_id, create_user_id, create_time) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, userRole.getUserId(), userRole.getRoleId(), userRole.getCreateUserId(), userRole.getCreateTime());
    }

    public int deleteById(Long userRoleId) {
        String sql = "update sys_user_role set deleted = 1 where user_role_id = ? ";
        return jdbcTemplate.update(sql, userRoleId);
    }

    public int deleteByUserRole(UserRoleBean userRole) {
        String sql = "update sys_user_role set deleted = 1 where user_id = ? and role_id = ? and deleted = 0 ";
        return jdbcTemplate.update(sql, userRole.getUserId(), userRole.getRoleId());
    }

    public int deleteByUserId(Long userId) {
        String sql = "update sys_user_role set deleted = 1 where user_id = ? and deleted = 0 ";
        return jdbcTemplate.update(sql, userId);
    }
}
