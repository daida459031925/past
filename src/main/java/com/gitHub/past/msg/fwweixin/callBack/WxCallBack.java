package com.gitHub.past.msg.fwweixin.callBack;


import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;

public class WxCallBack {
    //配置对应的appid
    private static String WX_APPID = "";
    //配置对应的appsecret
    private static String WX_APPSECRET = "";
    //回调url
    private static String WX_CALL_BACK_URL = "";
    //服务器 公众号==>基本配置==>服务器配置==>场景用户绑定公众号或者取消绑定时返回信息（测试实际上什么操作感觉都走了这个方法）
    //这个token值要和服务器配置一致
    private static String WX_CALL_BACK_TOKEN = "";

    //获取微信权限时需要传的参数目前不知道什么意思
    private static String scope = "snsapi_userinfo";
    //获取微信权限时需要传的参数目前不知道什么意思
    private static String state = "123#wechat_redirect";

    //通过@RequestParam获取对应数据，也是服务器验证是否通过回调的基础
    public void regardCallBack(
            HttpServletRequest request, HttpServletResponse response
            ,String signature,String timestamp
            ,String nonce,String echostr,String openid) throws IOException {
        // 接收微信服务器以Get请求发送的4个参数
        PrintWriter out = response.getWriter();
        if (checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);        // 校验通过，原样返回echostr参数内容
        } else {
            System.out.println("不是微信发来的请求！");
        }
    }
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String token = "331dbe5a"; //这个token值要和服务器配置一致
        String[] arr = new String[]{token, timestamp, nonce};
        // 排序
        Arrays.sort(arr);
        // 生成字符串
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        // sha1加密
        String temp = getSHA1String(content.toString());

        return temp.equals(signature); // 与微信传递过来的签名进行比较
    }

    private static String getSHA1String(String data) {
        // 使用commons codec生成sha1字符串
        return DigestUtils.shaHex(data);
    }


    //公众号配置位置 公众号==>功能设置==>网页授权域名
    //微信授权登录
    public void wxLogin(HttpServletRequest request,
                        HttpServletResponse response,
                        WxMpService weixinService) throws IOException {
        WxOAuth2Service oAuth2Service = weixinService.getOAuth2Service();
        //不知道当前这个使用mp工具类后还需要么
//        String redirect_uri = URLEncoder.encode(ym, "UTF-8");
        String snsapi_userinfo = oAuth2Service.buildAuthorizationUrl(WX_CALL_BACK_URL, scope, state);
        response.sendRedirect(snsapi_userinfo);
    }

    //根据传入的值WX_CALL_BACK_URL回调路径可以执行获取用户openid以及code
    public String wxLoginCallBack(HttpServletRequest request, HttpServletResponse response
            ,WxMpService weixinService) throws WxErrorException {
        //获取回调地址中返回的code
        String code = request.getParameter("code");
        WxOAuth2Service oAuth2Service = weixinService.getOAuth2Service();
        WxOAuth2AccessToken accessToken = oAuth2Service.getAccessToken(WX_APPID, WX_APPSECRET, code);
        //拿到定应的openid 然后可以根据自己业务将参数带出走自己业务
        //response.sendRedirect(WX_YM+"/cas/qr/wxmp/wxMpGroup?gid="+gid+"&openid="+openid);
        return accessToken.getOpenId();
    }
}
