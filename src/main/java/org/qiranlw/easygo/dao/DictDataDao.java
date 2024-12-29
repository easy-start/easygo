package org.qiranlw.easygo.dao;

import org.qiranlw.easygo.bean.DictDataBean;
import org.qiranlw.easygo.form.DictDataForm;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiranlw
 */
public class DictDataDao extends BaseDao {

    public static final RowMapper<DictDataBean> DICT_DATA_ROW_MAPPER = new BeanPropertyRowMapper<>(DictDataBean.class);

    private final JdbcTemplate jdbcTemplate;

    public DictDataDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    private static final String QUERY_SQL = """
    select t1.type_code, t1.data_label, t1.data_value, t1.parent_value, t1.data_sort, t1.status, t1.description, t1.create_user_id,
    t1.create_time, t1.update_user_id, t1.update_time, t2.nickname as create_user_name, t3.nickname as update_user_name
    from sys_dict_data t1
    left join sys_user t2 on t2.user_id = t1.create_user_id and t2.deleted = 0
    left join sys_user t3 on t3.user_id = t1.update_user_id and t3.deleted = 0
    """;

    private static final String INSERT_SQL = """
    insert into sys_dict_data(type_code, data_label, data_value, parent_value, data_sort, status, description, create_user_id, create_time
    ) values(?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    public DictDataBean selectById(Long dictDataId) {
        String whereSql = " where t1.deleted = 0 and t1.dict_data_id = ? ";
        return this.jdbcTemplate.queryForObject(QUERY_SQL + whereSql, DICT_DATA_ROW_MAPPER, dictDataId);
    }

    public DictDataBean selectByCodeAndValue(DictDataForm form) {
        String whereSql = " where t1.deleted = 0 and t1.type_code = ? and t1.data_value = ? ";
        return this.jdbcTemplate.queryForObject(QUERY_SQL + whereSql, DICT_DATA_ROW_MAPPER, form.getTypeCode(), form.getDataValue());
    }

    public List<DictDataBean> selectListByCode(String typeCode) {
        String whereSql = " where t1.deleted = 0 and t1.type_code = ? and t1.status = 1 order by t1.parent_value asc, t1.data_sort asc ";
        return this.jdbcTemplate.query(QUERY_SQL + whereSql, DICT_DATA_ROW_MAPPER, typeCode);
    }

    public List<DictDataBean> selectListByParams(DictDataForm form) {
        StringBuilder whereSql = new StringBuilder(" where t1.deleted = 0 ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(form.getTypeCode())) {
            whereSql.append(" and t1.type_code = ? ");
            params.add(form.getTypeCode());
        }
        if (StringUtils.hasText(form.getDataLabel())) {
            whereSql.append("and t1.data_label like concat('%', ?, '%') ");
            params.add(form.getDataLabel());
        }
        whereSql.append(" order by t1.parent_value asc, t1.data_sort asc ");
        return this.jdbcTemplate.query(QUERY_SQL + whereSql, DICT_DATA_ROW_MAPPER, params.toArray());
    }

    public Long insert(DictDataBean bean) {
        return this.insertAndGetKey(INSERT_SQL, bean.getTypeCode(), bean.getDataLabel(), bean.getDataValue(), bean.getParentValue(),
                bean.getDataSort(), bean.getStatus(), bean.getDescription(), bean.getCreateUserId(), bean.getCreateTime());
    }

    public int updateById(DictDataBean bean) {
        StringBuilder sql = new StringBuilder("update sys_dict_data set ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(bean.getDataLabel())) {
            sql.append(" data_label = ?, ");
            params.add(bean.getDataLabel());
        }
        if (StringUtils.hasText(bean.getDataValue())) {
            sql.append(" data_value = ?, ");
            params.add(bean.getDataValue());
        }
        if (bean.getDataSort() != null) {
            sql.append(" data_sort = ?, ");
            params.add(bean.getDataSort());
        }
        if (bean.getStatus() != null) {
            sql.append(" status = ?, ");
            params.add(bean.getStatus());
        }
        if (StringUtils.hasText(bean.getDescription())) {
            sql.append(" description = ?, ");
            params.add(bean.getDescription());
        }
        if (params.isEmpty()) {
            return 0;
        }
        sql.append(" update_user_id = ?, update_time = ? ");
        params.add(bean.getUpdateUserId());
        params.add(bean.getUpdateTime());
        sql.append(" where dict_data_id = ?");
        params.add(bean.getDictDataId());
        return this.jdbcTemplate.update(sql.toString(), params.toArray());
    }

    public int deleteById(Long id) {
        String sql = "update sys_dict_data set deleted = '1' where dict_data_id = ?";
        return this.jdbcTemplate.update(sql, id);
    }

    public int deleteByCode(String typeCode) {
        String sql = "update sys_dict_data set deleted = '1' where type_code = ?";
        return this.jdbcTemplate.update(sql, typeCode);
    }
}
