package com.java110.api.bmo.inspectionTask;

import com.alibaba.fastjson.JSONObject;
import com.java110.api.bmo.IApiBaseBMO;
import com.java110.core.context.DataFlowContext;

public interface IInspectionTaskBMO extends IApiBaseBMO {


    /**
     * 添加活动
     * @param paramInJson
     * @param dataFlowContext
     * @return
     */
     JSONObject addInspectionTask(JSONObject paramInJson, DataFlowContext dataFlowContext);

    /**
     * 添加活动信息
     *
     * @param paramInJson     接口调用放传入入参
     * @param dataFlowContext 数据上下文
     * @return 订单服务能够接受的报文
     */
     JSONObject updateInspectionTask(JSONObject paramInJson, DataFlowContext dataFlowContext);

    /**
     * 删除活动
     *
     * @param paramInJson     接口调用放传入入参
     * @param dataFlowContext 数据上下文
     * @return 订单服务能够接受的报文
     */
     JSONObject deleteInspectionTask(JSONObject paramInJson, DataFlowContext dataFlowContext);



}
