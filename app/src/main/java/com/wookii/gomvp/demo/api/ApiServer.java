package com.wookii.gomvp.demo.api;

import com.wookii.gomvp.demo.bean.HttpResult;
import com.wookii.gomvp.demo.bean.SecretKeyBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wuchen on 2018/1/5.
 */

public interface ApiServer {

    String URL_CONTRACT_NET = "http://assit-gateway-yf.7fresh.com";

    @FormUrlEncoded
    @POST("gateway")
    Observable<HttpResult<Object>> getMethods(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("gateway")
    Observable<HttpResult<SecretKeyBean>> getSecretKey(@FieldMap Map<String,Object> map);

    @GET("login")
    Observable<HttpResult<Object>> login(@Query("body") String string);


    interface Method {
        String METHOD_USER = "org.UserService.";

        /**
         * 通讯录
         */
        String METHOD_USER_NEW = "XstoreAssistantUserInfoProvider.";
        String METHOD_CONTRACT_All = METHOD_USER_NEW +"listAllUsers";
        String METHOD_CONTRACT_SEARCH = METHOD_USER_NEW +"getBasicUsers";

        String METHOD_USER_GROUP = "XstoreAssistantUserGroupProvider.";
        String METHOD_ADD_FREQUENT_CONTACTS = METHOD_USER_GROUP + "addUserInGroupByUser";
        String METHOD_DELETE_FREQUENT_CONTACTS = METHOD_USER_GROUP + "deleteUserFromGroupByUser";
        String METHOD_IS_FREQUENT_CONTACTS = METHOD_USER_GROUP + "isFrequentContacts";

        String METHOD_WORKFLOW = "XstoreAssistantAppProvider.";
        String METHOD_WORKFLOW_MAIN = METHOD_WORKFLOW + "getWorkListData";
        String METHOD_VERSION_CHECK = METHOD_WORKFLOW + "getAppVersionControl";
        String METHOD_MARKET_DATA = METHOD_WORKFLOW + "getWorkbenchDataByUserPin";

        String METHOD_USER_INFO = METHOD_USER +"getUserByPin";
        /**
         * 消息
         */
        String METHOD_NOTICE = "XstoreAssistantMessageProvider.";
        String METHOD_NOTICE_LIST = METHOD_NOTICE + "getMyMessageList";
        String METHOD_NOTICE_BODY = METHOD_NOTICE + "getMessageBodyById";
        String METHOD_NOTICE_COUNT = METHOD_NOTICE + "getMessageCount";
        String METHOD_NOTICE_TYPE = METHOD_NOTICE + "getMessageItemList";


        /**
         *
         */
        String METHOD_CONFIG =  "XstoreAssistantAppConfigProvider.";
        String METHOD_MESSAGE_CONFIG = METHOD_CONFIG + "getMessageConfigList";
        String METHOD_CHANGE_CONFIG = METHOD_CONFIG + "setUserConfig";
        String METHOD_APP_CONFIG = METHOD_CONFIG + "getAppConfigSummary";

        String METHOD_GET_SECRETKEY = "XstoreAssistantFrontAppServiceProvider.getSecretKey";
    }

    interface Parameter {
        int PARAMETER_NOTICE_READ = 1;
        int PARAMETER_NOTICE_UNREAD = 0;
    }

    interface Code {
        /**
         * token 失效
         */
        String CODE_EXPIRY_TOKEN = "A0013";
    }
}
