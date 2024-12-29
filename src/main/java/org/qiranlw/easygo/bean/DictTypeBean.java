package org.qiranlw.easygo.bean;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiranlw
 */
public class DictTypeBean extends BasicBean implements Serializable {

    @Serial
    private static final long serialVersionUID = -4533272990117943495L;

    public static final int DICT_TYPE_LIST = 1;

    public static final int DICT_TYPE_TREE = 2;

    private Long dictTypeId;

    private String typeCode;

    private String typeName;

    private Integer dataType;

    private Integer status;

    private String description;

    public Long getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
