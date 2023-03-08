package com.gitHub.past.msg.fwweixin;

import com.alibaba.fastjson.JSONObject;
import com.gitHub.past.log.Log;
import com.gitHub.past.msg.fwweixin.MsgTemplate.Custom;
import com.gitHub.past.net.Http;
import com.gitHub.past.web.AccessToken;

//微信服务号
public class WeiXinFwUtil {

//    private static String appId = "wx697f35002b64629a";
//    private static String appsecret = "5247147da4ade2b714fa5c56f479d915";

    //兼济物连
    private static String appId = "wx590d4839d2ef0ed6";
    private static String appsecret = "9c4a29538a8c8794a29ca3ce20e92d9c";

    private static String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private static String postYingYongUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


    public static void main(String[] args) {
        //获取token
        String token = getToken();
//        AccessToken accessToken = JSONObject.parseObject(token,AccessToken.class);
//        //发送文本消息
//        Custom custom = new Custom();
//        custom.setTouser("");
//        custom.setTemplate_id("");
//        custom.setUrl("");
//        custom.setClient_msg_id("");
//
//
//        custom.setMiniprogram(custom.getMiniprogram());
//
//        DataBean dataBean = new DataBean();
//        Custom.ValueBean valueBean = new Custom.ValueBean();
//        valueBean.setValue("asdasdasd");
//        valueBean.setColor();
//
//        dataBean.put("",)
//        custom.setData();
//
//
//        postYingYongUrl(accessToken.getAccessToken(),"");
    }

    private static String getToken(){
        //企业id在我的企业中
        getTokenUrl = getTokenUrl.replace("APPID",appId);
        //每个应用单独一个SECRET
        getTokenUrl = getTokenUrl.replace("APPSECRET",appsecret);
        String s = Http.httpGet(getTokenUrl, "", "");
        Log.info(s);
        return s;
    }

    private static String postYingYongUrl(String token,Object body){
        String s = JSONObject.toJSONString(JSONObject.parseObject("      {\n" +
                "           \"touser\":\"om5BC58x-MLXSxTQ5mdk3lejR4As\",\n" +
                "           \"template_id\":\"XgpVNy_XvN7rTdJsx2qpw15ZTAoIF2bPCKS4toHjy6Y\",\n" +
                "           \"data\":{\n" +
                "                   \"date\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }\n"));
        postYingYongUrl = postYingYongUrl.replace("ACCESS_TOKEN", token);
        String s1 = Http.httpPost(postYingYongUrl, "", s, "");
        Log.info(s1);
        return s1;
    }
}
