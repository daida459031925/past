package com.gitHub.past.msg.fwweixin.MsgTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//自定义模板
public class Custom {
//
//    /**
//     * touser : OPENID
//     * template_id : ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY
//     * url : http://weixin.qq.com/download
//     * miniprogram : {"appid":"xiaochengxuappid12345","pagepath":"index?foo=bar"}
//     * client_msg_id : MSG_000001
//     * data : {"first":{"value":"恭喜你购买成功！","color":"#173177"},"keyword1":{"value":"巧克力","color":"#173177"},"keyword2":{"value":"39.8元","color":"#173177"},"keyword3":{"value":"2014年9月22日","color":"#173177"},"remark":{"value":"欢迎再次购买！","color":"#173177"}}
//     * url和 miniprogram 都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。开发者可根据实际需要选择其中一种跳转方式即可。当用户的微信客户端版本不支持跳小程序时，将会跳转至url。
//     */
//
//    private String touser;
//    private String template_id;
//    private String url;
//    private MiniprogramBean miniprogram;
//    private String client_msg_id;
//    private DataBean data;
//
//    public String getTouser() {
//        return touser;
//    }
//
//    public void setTouser(String touser) {
//        this.touser = touser;
//    }
//
//    public String getTemplate_id() {
//        return template_id;
//    }
//
//    public void setTemplate_id(String template_id) {
//        this.template_id = template_id;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//        //跳转路径时将小程序设置null
//        miniprogram = null;
//    }
//
//    public MiniprogramBean getMiniprogram() {
//        return miniprogram;
//    }
//
//    public void setMiniprogram(MiniprogramBean miniprogram) {
//        this.miniprogram = miniprogram;
//        //设置小程序时候将url设置为null
//        url = null;
//    }
//
//    public String getClient_msg_id() {
//        return client_msg_id;
//    }
//
//    public void setClient_msg_id(String client_msg_id) {
//        this.client_msg_id = client_msg_id;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    //微信小程序
//    class MiniprogramBean {
//        /**
//         * appid : xiaochengxuappid12345
//         * pagepath : index?foo=bar
//         */
//        MiniprogramBean getMiniprogramBean1(){
//            return new MiniprogramBean();
//        }
//
//        MiniprogramBean setAppid(String appid){
//            this.appid = appid;
//            return this;
//        }
//
//        MiniprogramBean setPagepath(String pagepath){
//            this.pagepath = pagepath;
//            return this;
//        }
//
//        private String appid;
//        private String pagepath;
//
//    }
//
//
//    //文字模板
//    class DataBean extends HashMap<String, ValueBean> {
//        /**
//         * first : {"value":"恭喜你购买成功！","color":"#173177"}
//         * keyword1 : {"value":"巧克力","color":"#173177"}
//         * keyword2 : {"value":"39.8元","color":"#173177"}
//         * keyword3 : {"value":"2014年9月22日","color":"#173177"}
//         * remark : {"value":"欢迎再次购买！","color":"#173177"}
//         */
//
//    }
//
//    class ValueBean {
//        /**
//         * value : 恭喜你购买成功！
//         * color : #173177
//         */
//
//        private String value;
//        private String color;
//
//        public String getValue() {
//            return value;
//        }
//
//        public this setValue(String value) {
//            this.value = value;
//            return this;
//        }
//
//        public String getColor() {
//            return color;
//        }
//
//        public void setColor(String color) {
//            this.color = color;
//        }
//    }

}
