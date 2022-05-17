package com.gitHub.past.common;

public class IpAddr {
//
//    <!-- https://mvnrepository.com/artifact/eu.bitwalker/UserAgentUtils -->
//        <dependency>
//            <groupId>eu.bitwalker</groupId>
//            <artifactId>UserAgentUtils</artifactId>
//            <version>1.21</version>
//        </dependency>
//
//    UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
//    String clientType = userAgent.getOperatingSystem().getDeviceType().toString();
//        LOGGER.info("clientType = " + clientType);   //客户端类型  手机、电脑、平板
//    String os = userAgent.getOperatingSystem().getName();
//        LOGGER.info("os = " + os);    //操作系统类型
//    String ip = IpUtil.getIpAddr(request);
//        LOGGER.info("ip = " + ip);    //请求ip
//    String browser = userAgent.getBrowser().toString();
//        LOGGER.info("browser = " + browser);    浏览器类型
//
//    //其中ip的获取方式
//    public static String getIpAddr(HttpServletRequest request) {
//
//        String ip = request.getHeader("x-forwarded-for");
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("X-Real-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("http_client_ip");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        // 如果是多级代理，那么取第一个ip为客户ip
//        if (ip != null && ip.indexOf(",") != -1) {
//            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
//        }
//        if("0:0:0:0:0:0:0:1".equals(ip)){
//            ip = "127.0.0.1";
//        }
//        return ip;
//    }
}
