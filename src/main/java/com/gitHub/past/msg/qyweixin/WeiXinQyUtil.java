package com.gitHub.past.msg.qyweixin;

import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import com.gitHub.past.net.Http;

//企业微信
public class WeiXinQyUtil {

    private static Log log = Log.get();
    private static String getTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
    private static String postYingYongUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

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
        getTokenUrl = getTokenUrl.replace("ID","ww27c09a97c6827cea");
        //每个应用单独一个SECRET
        getTokenUrl = getTokenUrl.replace("SECRET","Q-cX60MGqnciM2pob0Nrwi5ox-3m7sYud2I4OX46VTE");
        String s = Http.httpGet(getTokenUrl, "", "");
        log.info(s);
        return s;
    }

    private static String postYingYongUrl(String token,Object body){
        String s = JSONObject.toJSONString(JSONObject.parse("{\n" +
                "   \"touser\" : \"@all\",\n" +
                "   \"toparty\" : \"@all\",\n" +
                "   \"totag\" : \"@all\",\n" +
                "   \"msgtype\" : \"text\",\n" +
                "   \"agentid\" : 1,\n" +
                "   \"text\" : {\n" +
                "       \"content\" : \"你的快递已到，请携带工卡前往邮件中心领取。\\n出发前可查看<a href=\\\"http://work.weixin.qq.com\\\">邮件中心视频实况</a>，聪明避开排队。\"\n" +
                "   },\n" +
                "   \"safe\":0,\n" +
                "   \"enable_id_trans\": 0,\n" +
                "   \"enable_duplicate_check\": 0,\n" +
                "   \"duplicate_check_interval\": 1800\n" +
                "}"));
        postYingYongUrl = postYingYongUrl.replace("ACCESS_TOKEN", token);
        String s1 = Http.httpPost(postYingYongUrl, "", s, "");
        log.info(s1);
        return s1;
    }
}
