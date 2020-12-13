package com.gitHub.past.web;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Logger;

public class AccessToken implements Serializable {
    private static final long serialVersionUID = -2314126520825184887L;


    /**
     * access_token : 63de6c71-672f-418c-80eb-0c9abc95b67c
     * token_type : bearer
     * refresh_token : 8495d597-0560-4598-95ef-143c0855363c
     * expires_in : 43199
     * scope : select
     * "error": "invalid_grant",
     * "error_description": "用户名或密码错误"
     */

    /**
     * get/set方法名字必须与返回内容相同 写成下划线模式 为了统一模式都用驼峰方法所以又加上驼峰get/set
     */
    private String accessToken;//token
    private String tokenType;//这个不知道
    private String refreshToken;//刷新的token
    private int expiresIn;//时间
    private String scope;//作用范围
    private LocalDateTime addTime;//添加这条数据的时间
    private LocalDateTime endTime;//token结束时间

    public String getAccess_token() {
        return accessToken;
    }

    public void setAccess_token(String access_token) {
        this.accessToken = access_token;
    }

    public String getToken_type() {
        return tokenType;
    }

    public void setToken_type(String token_type) {
        this.tokenType = token_type;
    }

    public String getRefresh_token() {
        return refreshToken;
    }

    public void setRefresh_token(String refresh_token) {
        this.refreshToken = refresh_token;
    }

    public int getExpires_in() {
        return expiresIn;
    }

    public void setExpires_in(int expires_in) {
        this.expiresIn = expires_in;
        addTime = LocalDateTime.now();
        //默认为12小时
        this.endTime = addTime.plusSeconds(this.expiresIn);
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        addTime = LocalDateTime.now();
        //默认为12小时
        this.endTime = addTime.plusSeconds(this.expiresIn);
    }

    /**
     * 判断当前tonken是否失效 提前5分钟判断 未来可以放在数据库中返回
     */
    public boolean isAfter(){
        //提前5分钟防止出现token出现失效间隙
        LocalDateTime now = LocalDateTime.now().plusMonths(-5);
        if(Objects.isNull(endTime)){
            Logger.getGlobal().info("请检查时间是否设置");
            return false;
        }
        boolean after = now.isEqual(endTime) || now.isAfter(endTime);
        StringBuilder append = new StringBuilder()
                .append("\n当前token：").append(accessToken)
                .append("\n是否刷新:").append(after)
                .append("\n剩余时间:").append(expiresIn)
                .append("\n结束时间:").append(endTime)
                ;
        Logger.getGlobal().info(append.toString());
        return after;
    }


    @Override
    public String toString() {
        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                ", addTime=" + addTime +
                ", endTime=" + endTime +
                '}';
    }
}


