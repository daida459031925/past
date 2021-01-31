package com.gitHub.past;

public enum Invariable {
    /**
     * 枚举类型这个必须是一次性创建完成注意 第一个，第二个 每个之间用，号
     * 而返还值最重要的是toString
     */
    UTF_8("UTF-8"),
    ISO_8859_1("ISO-8859-1"),
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
    YYYYMMDD("yyyyMMdd"),
    HH_MM_SS("HH:mm:ss"),
    HH_MM("HH:mm"),

    EXIT("exit"),
    BIN_BASH("/bin/bash"),

    DATA("data"),
    STATUS("status"),
    MSG("msg"),


    EMPTY(""),
    COLON(":"),
    WHIPPTREE("-"),
    SPACE(" "),
    SPOT("."),
    CLASS("class"),
    JAR("jar"),
    DCLASS(".class"),
    XIEGANG("/"),
    ERROR("error"),

    /**消息头类型*/
    //是Http的实体首部字段，用于说明请求或返回的消息主体是用何种方式编码，在request header和response header里都存在
    CONTENT_TYPE("Content-Type"),
    //1）浏览器的原生form表单
    //2） 提交的数据按照 key1=val1&key2=val2 的方式进行编码，key和val都进行了URL转码
    a("application/x-www-form-urlencoded"),
    //常见的 POST 数据提交的方式。我们使用表单上传文件时，必须让 form 的 enctype 等于这个值
    //需要在表单中进行文件上传时，就需要使用该格式。常见的媒体格式是上传文件之时使用的
    b("multipart/form-data"),
    //是一种使用 HTTP 作为传输协议，XML 作为编码方式的远程调用规范
    c("text/xml"),
    //数据以纯文本形式(text/json/xml/html)进行编码，其中不含任何控件或格式字符
    d("text/plain"),
    //css类型
    d("text/css"),
    //html类型
    d("text/html"),
    //js类型
    d("application/x-javascript"),
    //image/*
    d("image/png jpg gif"),
    //.*（ 二进制流，不知道下载文件类型）
    d("application/octet-stream"),
    //这种方案，可以方便的提交复杂的结构化数据，特别适合 RESTful 的接口。
    // 各大抓包工具如 Chrome 自带的开发者工具、Firebug、Fiddler，
    // 都会以树形结构展示 JSON 数据，非常友好 消息主体是序列化后的 JSON 字符串
    APPLICATION_JSON("application/json;charset=UTF-8"),

    /**oauth2*/
    OAUTH("/oauth/**"),
    AUTHORIZE("/oauth/authorize"),
    CODE("code"),
    ORDER("order"),
    UNAUTHORIZED("Unauthorized"),
    INVALID_ACCESS_TOKEN("Invalid access token"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    ACCESS_IS_DENIED("Access is denied"),

    PAST("past"),
    PROPERTIES("properties"),
    POOL_MAX("poolMax")
    ;


    private final Object value;

    private Invariable(String value) {
        this.value = value;
    }
    private Invariable(Integer value) {
        this.value = value;
    }




    /**
     * 上面重写之类的并不重要重要的是在重写tostring上
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
