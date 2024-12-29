package org.qiranlw.easygo.web;

import org.qiranlw.easygo.annotation.Check;
import org.qiranlw.easygo.bean.DictDataBean;
import org.qiranlw.easygo.bean.DictTypeBean;
import org.qiranlw.easygo.bean.PageBean;
import org.qiranlw.easygo.bean.Result;
import org.qiranlw.easygo.form.DictDataForm;
import org.qiranlw.easygo.form.DictTypeForm;
import org.qiranlw.easygo.service.DictService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiranlw
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    private final DictService dictService;

    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping("/type/page")
    public Result<PageBean<DictTypeBean>> getDictTypePage(DictTypeForm form) {
        return Result.success(this.dictService.getDictTypePage(form));
    }

    @GetMapping("/type/{dictTypeId}")
    public Result<DictTypeBean> getDictTypeById(@PathVariable Long dictTypeId) {
        return Result.success(this.dictService.getDictTypeById(dictTypeId));
    }

    @GetMapping("/data/list")
    public Result<List<DictDataBean>> getDictDataList(DictDataForm form) {
        return Result.success(this.dictService.getDictDataByParams(form));
    }

    @GetMapping("/data/{typeCode}")
    public Result<List<DictDataBean>> getDictDataByCode(String typeCode) {
        return Result.success(this.dictService.getDictDataByCode(typeCode));
    }

    @PostMapping("/type/add")
    @Check(key = "typeCode", ex = "form.typeCode != nil && form.typeCode != ''", msg = "字典编码不能为空")
    @Check(key = "typeCode", ex = "form.typeCode ~= /^[a-zA-Z][a-zA-Z0-9]{5, 15}$/", msg = "字典编码必须是6到16位英文数字，且以英文字母开头")
    @Check(key = "typeName", ex = "form.typeName != nil && form.typeName != ''", msg = "字典名称不能为空")
    @Check(key = "dataType", ex = "form.dataType != nil && form.dataType != ''", msg = "字典类型不能为空")
    @Check(key = "status", ex = "form.status != nil && form.status != ''", msg = "字典状态不能为空")
    public Result<DictTypeBean> addDictType(@RequestBody DictTypeForm form) {
        return Result.success(this.dictService.addDictType(form));
    }

    @PostMapping("/type/update")
    @Check(key = "dictTypeId", ex = "form.dictTypeId != nil && form.dictTypeId != ''", msg = "字典ID不能为空")
    @Check(key = "typeCode", ex = "form.typeCode != nil && form.typeCode != ''", msg = "字典编码不能为空")
    @Check(key = "typeCode", ex = "form.typeCode ~= /^[a-zA-Z][a-zA-Z0-9]{5, 15}$/", msg = "字典编码必须是6到16位英文数字，且以英文字母开头")
    @Check(key = "typeName", ex = "form.typeName != nil && form.typeName != ''", msg = "字典名称不能为空")
    @Check(key = "dataType", ex = "form.dataType != nil && form.dataType != ''", msg = "字典类型不能为空")
    @Check(key = "status", ex = "form.status != nil && form.status != ''", msg = "字典状态不能为空")
    public Result<DictTypeBean> updateDictType(@RequestBody DictTypeForm form) {
        return Result.success(this.dictService.updateDictType(form));
    }

    @DeleteMapping("/type/{dictTypeId}")
    public Result<DictTypeBean> deleteDictType(@PathVariable Long dictTypeId) {
        return Result.success(this.dictService.deleteDictTypeById(dictTypeId));
    }

    @PostMapping("/data/add")
    @Check(key = "typeCode", ex = "form.typeCode != nil && form.typeCode != ''", msg = "字典编码不能为空")
    @Check(key = "dataLabel", ex = "form.dataLabel != nil && form.dataLabel != ''", msg = "数据名称不能为空")
    @Check(key = "dataValue", ex = "form.dataValue != nil && form.dataValue != ''", msg = "数据值不能为空")
    @Check(key = "dataSort", ex = "form.dataSort != nil && form.dataSort != ''", msg = "序号不能为空")
    @Check(key = "status", ex = "form.status != nil && form.status != ''", msg = "状态不能为空")
    public Result<DictDataBean> addDictData(@RequestBody DictDataForm form) {
        return Result.success(this.dictService.addDictData(form));
    }

    @PostMapping("/data/update")
    @Check(key = "dictDataId", ex = "form.dictDataId != nil && form.dictDataId != ''", msg = "数据ID不能为空")
    @Check(key = "dataLabel", ex = "form.dataLabel != nil && form.dataLabel != ''", msg = "数据名称不能为空")
    @Check(key = "dataSort", ex = "form.dataSort != nil && form.dataSort != ''", msg = "序号不能为空")
    @Check(key = "status", ex = "form.status != nil && form.status != ''", msg = "状态不能为空")
    public Result<DictDataBean> updateDictData(@RequestBody DictDataForm form) {
        return Result.success(this.dictService.updateDictData(form));
    }

    @DeleteMapping("/data/{dictDataId}")
    public Result<DictDataBean> deleteDictData(@PathVariable Long dictDataId) {
        return Result.success(this.dictService.deleteDictDataById(dictDataId));
    }
}
