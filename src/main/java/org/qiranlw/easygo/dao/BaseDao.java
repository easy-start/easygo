package org.qiranlw.easygo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;

/**
 * @author qiranlw
 */
public abstract class BaseDao {

    abstract JdbcTemplate getJdbcTemplate();

    protected Long insertAndGetKey(String sql, Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int ret = this.getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i=0;i<params.length;i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps;
        }, keyHolder);
        if (ret == 0) {
            return null;
        }
        if (keyHolder.getKey() == null) {
            return null;
        }
        return keyHolder.getKey().longValue();
    }
}
