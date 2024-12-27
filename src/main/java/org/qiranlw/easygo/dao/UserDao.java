package org.qiranlw.easygo.dao;

import org.qiranlw.easygo.bean.PageBean;
import org.qiranlw.easygo.bean.UserBean;
import org.qiranlw.easygo.form.UserForm;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDao extends BaseDao {

    public static final RowMapper<UserBean> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(UserBean.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    private static final String QUERY_SQL = """
    select t1.user_id, t1.username, t1.nickname, t1.sex, t1.avatar_url, t1.email, t1.telephone, t1.birthday, t1.description,
    t1.account_non_expired, t1.account_non_locked, t1.credentials_non_expired, t1.enabled, t1.create_user_id, t1.create_time,
    t1.update_user_id, t1.update_time, t2.nickname as create_user_name, t3.nickname as update_user_name
    from sys_user t1
    left join sys_user t2 on t2.user_id = t.create_user_id and t2.deleted = 0
    left join sys_user t3 on t3.user_id = t.update_user_id and t3.deleted = 0
    """;

    private static final String INSERT_SQL = """
    insert into sys_user (username, nickname, password, sex, avatar_url, email, telephone, birthday, description,
    account_non_expired, account_non_locked, credentials_non_expired, enabled, create_user_id, create_time
    ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    public UserBean selectById(Long userId) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t1.user_id = ? ";
        return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, userId);
    }

    public UserBean selectByUsername(String username) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t1.username = ? ";
        return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, username);
    }

    public PageBean<UserBean> selectPageByParams(UserForm form) {
        StringBuilder sql = new StringBuilder(QUERY_SQL);
        StringBuilder countSql = new StringBuilder("select count(1) from sys_user t1 ");
        StringBuilder whereSql = new StringBuilder(" where t1.deleted = 0 ");
        List<Object> params = new ArrayList<>();
        if (form.getRoleId() != null) {
            countSql.append(" left join sys_user_role t4 on on t4.user_id = t1.user_id and t4.deleted = 0 ");
            sql.append(" left join sys_user_role t4 on on t4.user_id = t1.user_id and t4.deleted = 0 ");
            whereSql.append(" and t4.role_id = ? ");
            params.add(form.getRoleId());
        }
        if (StringUtils.hasText(form.getNickname())) {
            whereSql.append(" and t1.nickname like concat('%', ?, '%') ");
            params.add(form.getNickname());
        }
        if (StringUtils.hasText(form.getUsername())) {
            whereSql.append(" and t1.username like concat('%', ?, '%') ");
            params.add(form.getUsername());
        }
        if (StringUtils.hasText(form.getTelephone())) {
            whereSql.append(" and t1.telephone like concat('%', ?, '%') ");
            params.add(form.getTelephone());
        }
        Long total = null;
        if (params.isEmpty()) {
            total = jdbcTemplate.queryForObject(countSql.toString(), Long.class);
        } else {
            total = jdbcTemplate.queryForObject(countSql.append(whereSql).toString(), Long.class, params.toArray());
        }
        if (total == null || total == 0) {
            return PageBean.create(form.getPageNum(), form.getPageSize(), 0, Collections.emptyList());
        }
        whereSql.append(" limit ? offset ? ");
        params.add(form.getPageSize());
        params.add(form.getStartNum());
        whereSql.append("order by t1.create_time desc ");
        List<UserBean> list = this.jdbcTemplate.query(sql.append(whereSql).toString(), UserDao.USER_ROW_MAPPER, params.toArray());
        return PageBean.create(form.getPageNum(), form.getPageSize(), total, list);
    }

    public Long insert(UserBean user) {
        return this.insertAndGetKey(INSERT_SQL, user.getUsername(), user.getNickname(), user.getPassword(), user.getSex(),
                user.getAvatarUrl(), user.getEmail(), user.getTelephone(), user.getBirthday(), user.getDescription(),
                user.getAccountNonExpired(), user.getAccountNonLocked(), user.getCredentialsNonExpired(), user.getEnabled(),
                user.getCreateUserId(), user.getCreateTime());
    }

    public int updateById(UserBean user) {
        StringBuilder sql = new StringBuilder("update sys_user set ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(user.getNickname())) {
            sql.append("nickname = ?, ");
            params.add(user.getNickname());
        }
        if (user.getSex() != null) {
            sql.append("sex = ?, ");
            params.add(user.getSex());
        }
        if (StringUtils.hasText(user.getAvatarUrl())) {
            sql.append("avatar_url = ?, ");
            params.add(user.getAvatarUrl());
        }
        if (StringUtils.hasText(user.getEmail())) {
            sql.append("email = ?, ");
            params.add(user.getEmail());
        }
        if (StringUtils.hasText(user.getTelephone())) {
            sql.append("telephone = ?, ");
            params.add(user.getTelephone());
        }
        if (StringUtils.hasText(user.getDescription())) {
            sql.append("description = ?, ");
            params.add(user.getDescription());
        }
        if (user.getEnabled() != null) {
            sql.append("enabled = ?, ");
            params.add(user.getEnabled());
        }
        if (params.isEmpty()) {
            return 0;
        }
        sql.append("update_user_id = ?, update_time = ? ");
        params.add(user.getUpdateUserId());
        params.add(user.getUpdateTime());
        sql.append(" where user_id = ? ");
        params.add(user.getUserId());
        return jdbcTemplate.update(sql.toString(), params.toArray());
    }

    public int updatePassword(UserBean userBean) {
        String sql = "update sys_user set password = ?, update_user_id = ?, update_time = ? where user_id = ?";
        return jdbcTemplate.update(sql, userBean.getPassword(), userBean.getUpdateUserId(), userBean.getUpdateTime(), userBean.getUserId());
    }

    public int updateLockedStatus(UserBean userBean) {
        String sql = "update sys_user set account_non_locked = ?, update_user_id = ?, update_time = ? where user_id = ?";
        return jdbcTemplate.update(sql, userBean.getAccountNonLocked(), userBean.getUpdateUserId(), userBean.getUpdateTime(), userBean.getUserId());
    }

    public int deleteById(Long userId) {
        String sql = "update sys_user set deleted = '1' where user_id = ? ";
        return jdbcTemplate.update(sql, userId);
    }
}
