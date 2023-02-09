package com.gitHub.past.msg.dingding;

import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import com.gitHub.past.net.Http;

//钉钉服务
public class DingDingUtil {
    private static Log log = Log.get();

    private static String agentId = "2400472232";
    private static String appKey = "dingioye3svu1uw8i3yb";
    private static String appSecret = "atwf6fUa8QM7R5_0107RSuXU4KsweTUTA3sWRumCZpqRy2e7VQyKnh-ONiSV0oXo";

    private static String getTokenUrl = "https://oapi.dingtalk.com/gettoken?appkey=APPKEY&appsecret=APPSECRET";

    private static String postYingYongUrl = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token=ACCESS_TOKEN";


    public static void main(String[] args) {
        //到我的企业中=》企业ID=》拿到corpid
        //获取token
        String token = getToken();
        JSONObject jsonObject = JSONObject.parseObject(token);
        Object access_token = jsonObject.get("access_token");
        //设置ip百名单 应用管理=》自建应用 =》企业可信IP =》设置域名=》设置可以访问的ip
        //发送文本消息
        postYingYongUrl(String.valueOf(access_token),"");

    }

    private static String getToken(){
        //企业id在我的企业中
        getTokenUrl = getTokenUrl.replace("APPKEY",appKey);
        //每个应用单独一个SECRET
        getTokenUrl = getTokenUrl.replace("APPSECRET",appSecret);
        String s = Http.httpGet(getTokenUrl, "", "");
        log.info(s);
        return s;
    }

    private static String postYingYongUrl(String token,Object body){
        String s = JSONObject.toJSONString(JSONObject.parse("{\n" +
                "        \"msg\":{\n" +
                "                \"text\":{\n" +
                "                        \"content\":\"阿苏军的哦1231324415564968\"\n" +
                "                },\n" +
                "                \"msgtype\":\"text\"\n" +
                "        },\n" +
                "        \"agent_id\":\"2400472232\",\n" +
                "        \"dept_id_list\":\"1113016431816138\",\n" +
                "        \"userid_list\":\"1113016431816138\"\n" +
                "}"));
        postYingYongUrl = postYingYongUrl.replace("ACCESS_TOKEN", token);
        String s1 = Http.httpPost(postYingYongUrl, "", s, "");
        log.info(s1);
        return s1;
    }
}
