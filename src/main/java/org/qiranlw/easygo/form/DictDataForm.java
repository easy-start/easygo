package org.qiranlw.easygo.form;

/**
 * @author qiranlw
 */
public class DictDataForm {

    private Long dictDataId;

    private String typeCode;

    private String dataLabel;

    private String dataValue;

    private String parentValue;

    private Integer dataSort;

    private Integer status;

    private String description;

    public Long getDictDataId() {
        return dictDataId;
    }

    public void setDictDataId(Long dictDataId) {
        this.dictDataId = dictDataId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getParentValue() {
        return parentValue;
    }

    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    public Integer getDataSort() {
        return dataSort;
    }

    public void setDataSort(Integer dataSort) {
        this.dataSort = dataSort;
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
