package com.java110.api.listener.parkingSpace;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java110.api.bmo.parkingSpace.IParkingSpaceBMO;
import com.java110.api.listener.AbstractServiceApiDataFlowListener;
import com.java110.utils.constant.BusinessTypeConstant;
import com.java110.utils.constant.CommonConstant;
import com.java110.utils.constant.ResponseConstant;
import com.java110.utils.constant.ServiceCodeConstant;
import com.java110.utils.exception.ListenerExecuteException;
import com.java110.utils.util.Assert;
import com.java110.core.annotation.Java110Listener;
import com.java110.core.context.DataFlowContext;
import com.java110.entity.center.AppService;
import com.java110.event.service.api.ServiceDataFlowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * @ClassName SaveParkingSpaceListener
 * @Description 保存小区楼信息
 * @Author wuxw
 * @Date 2019/4/26 14:51
 * @Version 1.0
 * add by wuxw 2019/4/26
 **/

@Java110Listener("saveParkingSpaceListener")
public class SaveParkingSpaceListener extends AbstractServiceApiDataFlowListener {


    private static Logger logger = LoggerFactory.getLogger(SaveParkingSpaceListener.class);

    @Autowired
    private IParkingSpaceBMO parkingSpaceBMOImpl;

    @Override
    public String getServiceCode() {
        return ServiceCodeConstant.SERVICE_CODE_SAVE_PARKING_SPACE;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public void soService(ServiceDataFlowEvent event) {

        logger.debug("ServiceDataFlowEvent : {}", event);

        DataFlowContext dataFlowContext = event.getDataFlowContext();
        AppService service = event.getAppService();

        String paramIn = dataFlowContext.getReqData();

        //校验数据
        validate(paramIn);
        JSONObject paramObj = JSONObject.parseObject(paramIn);

        HttpHeaders header = new HttpHeaders();
        dataFlowContext.getRequestCurrentHeaders().put(CommonConstant.HTTP_ORDER_TYPE_CD, "D");
        JSONArray businesses = new JSONArray();


        //添加小区楼
        businesses.add(parkingSpaceBMOImpl.addParkingSpace(paramObj));


        ResponseEntity<String> responseEntity = parkingSpaceBMOImpl.callService(dataFlowContext, service.getServiceCode(), businesses);

        dataFlowContext.setResponseEntity(responseEntity);

    }


    /**
     * 数据校验
     * <p>
     * name:'',
     * age:'',
     * link:'',
     * sex:'',
     * remark:''
     *
     * @param paramIn "communityId": "7020181217000001",
     *                "memberId": "3456789",
     *                "memberTypeCd": "390001200001"
     */
    private void validate(String paramIn) {
        Assert.jsonObjectHaveKey(paramIn, "communityId", "未包含小区ID");
        Assert.jsonObjectHaveKey(paramIn, "num", "请求报文中未包含age");
        Assert.jsonObjectHaveKey(paramIn, "area", "请求报文中未包含name");
        Assert.jsonObjectHaveKey(paramIn, "userId", "请求报文中未包含userId");
        Assert.jsonObjectHaveKey(paramIn, "paId", "请求报文中未包含停车场信息");
    }


    @Override
    public int getOrder() {
        return 0;
    }

}
