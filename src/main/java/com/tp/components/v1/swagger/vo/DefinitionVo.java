package com.tp.components.v1.swagger.vo;

import com.tp.components.v1.swagger.dto.ResultData;

import java.util.List;

/**
 * 定义的类型
 */
public class DefinitionVo{

    private List<ResultData> resultDataList;

    public List<ResultData> getResultDataList() {
        return resultDataList;
    }

    public void setResultDataList(List<ResultData> resultDataList) {
        this.resultDataList = resultDataList;
    }
}
