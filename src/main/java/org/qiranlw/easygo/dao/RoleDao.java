package org.qiranlw.easygo.dao;

import org.qiranlw.easygo.bean.PageBean;
import org.qiranlw.easygo.bean.RoleBean;
import org.qiranlw.easygo.form.RoleForm;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author qiranlw
 */
@Repository
public class RoleDao extends BaseDao {

    public static final RowMapper<RoleBean> ROLE_ROW_MAPPER = new BeanPropertyRowMapper<>(RoleBean.class);

    private final JdbcTemplate jdbcTemplate;

    public RoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private static final String QUERY_SQL = """
    select t1.role_id, t1.role_code, t1.role_name, t1.type, t1.status, t1.description, t1.create_user_id, t1.create_time,
    t1.update_user_id, t1.update_time, t2.nickname as create_user_name, t3.nickname as update_user_name
    from sys_role t1
    left join sys_user t2 on t2.user_id = t1.create_user_id and t2.deleted = 0
    left join sys_user t3 on t3.user_id = t1.update_user_id and t3.deleted = 0
    """;

    private static final String INSERT_SQL = """
    insert into sys_role(role_code, role_name, type, status, description, create_user_id, create_time) values (?, ?, ?, ?, ?, ?, ?)
    """;

    public RoleBean selectById(Long roleId) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t1.role_id = ? ";
        return jdbcTemplate.queryForObject(sql, RoleDao.ROLE_ROW_MAPPER, roleId);
    }

    public RoleBean selectByCode(String code) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t1.role_code = ? ";
        return jdbcTemplate.queryForObject(sql, RoleDao.ROLE_ROW_MAPPER, code);
    }

    public List<RoleBean> selectRoleList(RoleForm form) {
        StringBuilder sql = new StringBuilder(QUERY_SQL);
        StringBuilder whereSql = new StringBuilder(" where t1.deleted = 0 ");
        List<Object> params = new ArrayList<>();
        if (form.getUserId() != null) {
            sql.append(" inner join sys_user_role t4 on t4.role_id = t1.role_id and t4.deleted = 0 ");
            whereSql.append(" and t4.user_id = ? ");
            params.add(form.getUserId());
        }
        if (StringUtils.hasText(form.getRoleCode())) {
            whereSql.append(" and t1.role_code like concat('%', ?,'%')");
            params.add(form.getRoleCode());
        }
        if (StringUtils.hasText(form.getRoleName())) {
            whereSql.append(" and t1.role_name like concat('%', ?,'%')");
            params.add(form.getRoleName());
        }
        if (form.getType() != null) {
            whereSql.append(" and t1.type = ? ");
            params.add(form.getType());
        }
        if (form.getStatus() != null) {
            whereSql.append(" and t1.status = ? ");
            params.add(form.getStatus());
        }
        whereSql.append(" order by t1.create_time desc ");
        if (params.isEmpty()) {
            return this.jdbcTemplate.query(sql.append(whereSql).toString(), RoleDao.ROLE_ROW_MAPPER);
        }
        return this.jdbcTemplate.query(sql.append(whereSql).toString(), RoleDao.ROLE_ROW_MAPPER, params.toArray());
    }

    public PageBean<RoleBean> selectRolePageByParams(RoleForm form) {
        StringBuilder countSql = new StringBuilder("select count(1) from sys_role t1 ");
        StringBuilder whereSql = new StringBuilder(" where t1.deleted = 0 ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(form.getRoleCode())) {
            whereSql.append(" and t1.role_code like concat('%', ?,'%')");
            params.add(form.getRoleCode());
        }
        if (StringUtils.hasText(form.getRoleName())) {
            whereSql.append(" and t1.role_name like concat('%', ?,'%')");
            params.add(form.getRoleName());
        }
        if (form.getType() != null) {
            whereSql.append(" and t1.type = ? ");
            params.add(form.getType());
        }
        if (form.getStatus() != null) {
            whereSql.append(" and t1.status = ? ");
            params.add(form.getStatus());
        }
        Long total = null;
        if (params.isEmpty()) {
            total = jdbcTemplate.queryForObject(countSql.append(whereSql).toString(), Long.class);
        } else {
            total = jdbcTemplate.queryForObject(countSql.append(whereSql).toString(), Long.class, params.toArray());
        }
        if (total == null || total == 0) {
            return PageBean.create(form.getPageNum(), form.getPageSize(), 0, Collections.emptyList());
        }
        whereSql.append("order by t1.create_time desc ");
        whereSql.append(" limit ? offset ? ");
        params.add(form.getPageSize());
        params.add(form.getStartNum());
        List<RoleBean> list = this.jdbcTemplate.query(QUERY_SQL + whereSql, RoleDao.ROLE_ROW_MAPPER, params.toArray());
        return PageBean.create(form.getPageNum(), form.getPageSize(), total, list);
    }

    public Long insert(RoleBean role) {
        return this.insertAndGetKey(INSERT_SQL, role.getRoleCode(), role.getRoleName(), role.getType(), role.getStatus(), role.getDescription(), role.getCreateUserId(), role.getCreateTime());
    }

    public int updateById(RoleBean role) {
        StringBuilder sql = new StringBuilder("update sys_user set ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(role.getRoleName())) {
            sql.append("role_name = ?, ");
            params.add(role.getRoleName());
        }
        if (role.getType() != null) {
            sql.append("type = ?, ");
            params.add(role.getType());
        }
        if (role.getStatus() != null) {
            sql.append("status = ?, ");
            params.add(role.getStatus());
        }
        if (StringUtils.hasText(role.getDescription())) {
            sql.append("description = ?, ");
            params.add(role.getDescription());
        }
        if (params.isEmpty()) {
            return 0;
        }
        sql.append("update_user_id = ?, update_time = ? ");
        params.add(role.getUpdateUserId());
        params.add(role.getUpdateTime());
        sql.append(" where user_id = ? ");
        params.add(role.getRoleId());
        return jdbcTemplate.update(sql.toString(), params.toArray());
    }

    public int deleteById(Long roleId) {
        String sql = "update sys_role set deleted = 1 where role_id = ?";
        return jdbcTemplate.update(sql, roleId);
    }
}
