package org.qiranlw.easygo.dao;

import org.qiranlw.easygo.bean.DictTypeBean;
import org.qiranlw.easygo.bean.PageBean;
import org.qiranlw.easygo.form.DictTypeForm;
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
public class DictTypeDao extends BaseDao {

    public static final RowMapper<DictTypeBean> DICT_TYPE_ROW_MAPPER = new BeanPropertyRowMapper<>(DictTypeBean.class);

    private final JdbcTemplate jdbcTemplate;

    public DictTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    private static final String QUERY_SQL = """
    select t1.dict_type_id, t1.type_code, t1.type_name, t1.data_type, t1.status, t1.description, t1.create_user_id,
    t1.create_time, t1.update_user_id, t1.update_time, t2.nickname as create_user_name, t3.nickname as update_user_name
    from sys_dict_type t1
    left join sys_user t2 on t2.user_id = t1.create_user_id and t2.deleted = 0
    left join sys_user t3 on t3.user_id = t1.update_user_id and t3.deleted = 0
    """;

    private static final String INSERT_SQL = """
    insert into sys_dict_type(type_code, type_name, data_type, status, description, create_user_id, create_time
    ) values(?, ?, ?, ?, ?, ?, ?)
    """;

    public DictTypeBean selectById(Long dictTypeId) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t1.dict_type_id = ? ";
        return this.jdbcTemplate.queryForObject(sql, DICT_TYPE_ROW_MAPPER, dictTypeId);
    }

    public DictTypeBean selectByCode(String typeCode) {
        String sql = QUERY_SQL + " where t1.deleted = 0 and t1.type_code = ? ";
        return this.jdbcTemplate.queryForObject(sql, DICT_TYPE_ROW_MAPPER, typeCode);
    }

    public PageBean<DictTypeBean> selectPageByParams(DictTypeForm form) {
        StringBuilder countSql = new StringBuilder("select count(1) from sys_dict_type t where t.deleted = 0 ");
        StringBuilder whereSql = new StringBuilder(" where t.deleted = 0 ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(form.getTypeCode())) {
            whereSql.append(" and t.type_code like concat('%', ?, '%') ");
            params.add(form.getTypeCode());
        }
        if (StringUtils.hasText(form.getTypeName())) {
            whereSql.append(" and t.type_name like concat('%', ?, '%') ");
            params.add(form.getTypeName());
        }
        if (form.getDataType() == null || form.getDataType() == 0) {
            whereSql.append(" and t.data_type = ? ");
            params.add(form.getDataType());
        }
        Long total = this.jdbcTemplate.queryForObject(countSql.append(whereSql).toString(), Long.class, params.toArray());
        if (total == null || total == 0) {
            return PageBean.create(form.getPageNum(), form.getPageSize(), 0, Collections.emptyList());
        }
        whereSql.append(" order by t.create_time desc ");
        whereSql.append(" limit ? offset ? ");
        params.add(form.getPageSize());
        params.add(form.getStartNum());
        List<DictTypeBean> list = this.jdbcTemplate.query(QUERY_SQL + whereSql, DICT_TYPE_ROW_MAPPER, params.toArray());
        return PageBean.create(form.getPageNum(), form.getPageSize(), total, list);
    }

    public Long insert(DictTypeBean bean) {
        return this.insertAndGetKey(INSERT_SQL, bean.getTypeCode(), bean.getTypeName(), bean.getDataType(),
                bean.getStatus(), bean.getDescription(), bean.getCreateUserId(), bean.getCreateTime());
    }

    public int updateById(DictTypeBean bean) {
        StringBuilder sql = new StringBuilder("update sys_dict_type set ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(bean.getTypeName())) {
            sql.append("type_name = ?, ");
            params.add(bean.getTypeName());
        }
        if (bean.getStatus() != null) {
            sql.append("status = ?, ");
            params.add(bean.getStatus());
        }
        if (StringUtils.hasText(bean.getDescription())) {
            sql.append("description = ?, ");
            params.add(bean.getDescription());
        }
        if (params.isEmpty()) {
            return 0;
        }
        sql.append("update_user_id = ?, update_time = ? ");
        params.add(bean.getUpdateUserId());
        params.add(bean.getUpdateTime());
        sql.append(" where dict_type_id = ?");
        params.add(bean.getDictTypeId());
        return this.jdbcTemplate.update(sql.toString(), params.toArray());
    }

    public int deleteById(Long id) {
        String sql = "update sys_dict_type set deleted = 1 where dict_type_id = ?";
        return this.jdbcTemplate.update(sql, id);
    }
}
