package org.qiranlw.easygo.service;

import org.qiranlw.easygo.bean.*;
import org.qiranlw.easygo.dao.DictDataDao;
import org.qiranlw.easygo.dao.DictTypeDao;
import org.qiranlw.easygo.exception.ServiceException;
import org.qiranlw.easygo.form.DictDataForm;
import org.qiranlw.easygo.form.DictTypeForm;
import org.qiranlw.easygo.utils.EasygoUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qiranlw
 */
@Service
public class DictService {

    private final DictTypeDao dictTypeDao;

    private final DictDataDao dictDataDao;

    public DictService(DictTypeDao dictTypeDao, DictDataDao dictDataDao) {
        this.dictTypeDao = dictTypeDao;
        this.dictDataDao = dictDataDao;
    }

    public DictTypeBean getDictTypeById(Long dictTypeId) {
        return this.dictTypeDao.selectById(dictTypeId);
    }

    public DictDataBean getDictDataById(Long dictDataId) {
        return this.dictDataDao.selectById(dictDataId);
    }

    public DictTypeBean getDictTypeByCode(String typeCode) {
        return this.dictTypeDao.selectByCode(typeCode);
    }

    public PageBean<DictTypeBean> getDictTypePage(DictTypeForm form) {
        return this.dictTypeDao.selectPageByParams(form);
    }

    public List<DictDataBean> getDictDataByParams(DictDataForm form) {
        return this.dictDataDao.selectListByParams(form);
    }

    public List<DictDataBean> getDictDataByCode(String typeCode) {
        return this.dictDataDao.selectListByCode(typeCode);
    }

    public DictTypeBean addDictType(DictTypeForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        DictTypeBean bean = this.dictTypeDao.selectByCode(form.getTypeCode());
        if (bean != null) {
            throw new ServiceException(ResultEnum.DATA_ALREADY_EXISTS.getCode(), "字典编码已存在");
        }
        DictTypeBean dictType = new DictTypeBean();
        dictType.setTypeCode(form.getTypeCode());
        dictType.setTypeName(form.getTypeName());
        dictType.setDataType(form.getDataType());
        dictType.setStatus(form.getStatus());
        dictType.setDescription(form.getDescription());
        dictType.setCreateUserId(userData.getUserId());
        dictType.setCreateTime(LocalDateTime.now());
        Long dictTypeId = this.dictTypeDao.insert(dictType);
        if (dictTypeId == null || dictTypeId == 0) {
            throw new ServiceException(ResultEnum.DATA_SAVE_FAILED);
        }
        dictType.setDictTypeId(dictTypeId);
        return dictType;
    }

    public DictTypeBean updateDictType(DictTypeForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        DictTypeBean bean = this.dictTypeDao.selectById(form.getDictTypeId());
        if (bean == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        if (StringUtils.hasText(form.getTypeName())) {
            bean.setTypeName(form.getTypeName());
        }
        if (form.getStatus() != null) {
            bean.setStatus(form.getStatus());
        }
        if (StringUtils.hasText(form.getDescription())) {
            bean.setDescription(form.getDescription());
        }
        bean.setUpdateUserId(userData.getUserId());
        bean.setUpdateTime(LocalDateTime.now());
        int ret = this.dictTypeDao.updateById(bean);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        return bean;
    }

    public DictTypeBean deleteDictTypeById(Long dictTypeId) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        DictTypeBean bean = this.dictTypeDao.selectById(dictTypeId);
        if (bean == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        int ret = this.dictTypeDao.deleteById(dictTypeId);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        ret = this.dictDataDao.deleteByCode(bean.getTypeCode());
        return bean;
    }

    public DictDataBean addDictData(DictDataForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        DictTypeBean dictType = this.dictTypeDao.selectByCode(form.getTypeCode());
        if (dictType == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        DictDataBean bean = this.dictDataDao.selectByCodeAndValue(form);
        if (bean != null) {
            throw new ServiceException(ResultEnum.DATA_ALREADY_EXISTS.getCode(), "字典值已存在");
        }
        DictDataBean dictData = new DictDataBean();
        dictData.setTypeCode(form.getTypeCode());
        dictData.setDataLabel(form.getDataLabel());
        dictData.setDataValue(form.getDataValue());
        if (DictTypeBean.DICT_TYPE_TREE == dictType.getDataType().intValue()) {
            if (StringUtils.hasText(form.getParentValue())) {
                dictData.setParentValue("0");
            } else {
                form.setDataValue(form.getParentValue());
                bean = this.dictDataDao.selectByCodeAndValue(form);
                if (bean == null) {
                    throw new ServiceException(ResultEnum.DATA_NOT_FOUND.getCode(), "父节点未找到");
                }
                dictData.setParentValue(form.getParentValue());
            }
        }
        dictData.setDataSort(form.getDataSort());
        dictData.setDescription(form.getDescription());
        dictData.setCreateUserId(userData.getUserId());
        dictData.setCreateTime(LocalDateTime.now());
        Long dictDataId = this.dictDataDao.insert(dictData);
        dictData.setDictDataId(dictDataId);
        return dictData;
    }

    public DictDataBean updateDictData(DictDataForm form) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        DictDataBean dictData = this.dictDataDao.selectById(form.getDictDataId());
        if (dictData == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        if (StringUtils.hasText(form.getDataLabel())) {
            dictData.setDataLabel(form.getDataLabel());
        }
        if (form.getDataSort() != null) {
            dictData.setDataSort(form.getDataSort());
        }
        if (form.getStatus() != null) {
            dictData.setStatus(form.getStatus());
        }
        if (StringUtils.hasText(form.getDescription())) {
            dictData.setDescription(form.getDescription());
        }
        dictData.setUpdateUserId(userData.getUserId());
        dictData.setUpdateTime(LocalDateTime.now());
        int ret = this.dictDataDao.updateById(dictData);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        return dictData;
    }

    public DictDataBean deleteDictDataById(Long dictDataId) {
        UserDetailsBean userData = EasygoUtils.getUserDetails();
        DictDataBean dictData = this.dictDataDao.selectById(dictDataId);
        if (dictData == null) {
            throw new ServiceException(ResultEnum.DATA_NOT_FOUND);
        }
        int ret = this.dictDataDao.deleteById(dictDataId);
        if (ret == 0) {
            throw new ServiceException(ResultEnum.DATA_UPDATE_FAILED);
        }
        return dictData;
    }
}
