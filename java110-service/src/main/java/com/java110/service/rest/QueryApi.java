package com.java110.service.rest;

import com.java110.common.constant.ResponseConstant;
import com.java110.common.factory.DataQueryFactory;
import com.java110.common.util.ResponseTemplateUtil;
import com.java110.core.base.controller.BaseController;
import com.java110.entity.service.DataQuery;
import com.java110.service.smo.IQueryServiceSMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询服务
 * Created by wuxw on 2018/4/20.
 */
@RestController
public class QueryApi extends BaseController {

    @Autowired
    private IQueryServiceSMO queryServiceSMOImpl;

    @RequestMapping(path = "/queryApi/query",method= RequestMethod.GET)
    public String queryGet(HttpServletRequest request) {
        return ResponseTemplateUtil.createBusinessResponseJson(ResponseConstant.RESULT_CODE_ERROR,"不支持Get方法请求").toJSONString();
    }

    /**
     * {
     "bId":"12345678",
     "serviceCode": "querycustinfo",
     "serviceName": "查询客户",
     "remark": "备注",
     "datas": {
     "params": {
     //这个做查询时的参数
     }
     //这里是具体业务
     }
     }
     * @param businessInfo
     * @return
     */
    @RequestMapping(path = "/queryApi/query",method= RequestMethod.POST)
    public String servicePost(@RequestBody String businessInfo) {
        try {
            DataQuery dataQuery = DataQueryFactory.newInstance().builder(businessInfo);
            initConfig(dataQuery);
            queryServiceSMOImpl.commonQueryService(dataQuery);
            return dataQuery.getResponseInfo().toJSONString();
        }catch (Exception e){
            logger.error("请求订单异常",e);
            return ResponseTemplateUtil.createBusinessResponseJson(ResponseConstant.RESULT_CODE_ERROR,e.getMessage()+e).toJSONString();
        }
    }

    /**
     * 初始化配置
     * @param dataQuery
     */
    private void initConfig(DataQuery dataQuery){
        dataQuery.setServiceSql(DataQueryFactory.getServiceSql(dataQuery));
    }

    public IQueryServiceSMO getQueryServiceSMOImpl() {
        return queryServiceSMOImpl;
    }

    public void setQueryServiceSMOImpl(IQueryServiceSMO queryServiceSMOImpl) {
        this.queryServiceSMOImpl = queryServiceSMOImpl;
    }
}
