package com.gitHub.past.characterEncoding;

import com.gitHub.past.Invariable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.function.BiFunction;

public class CharEncoding {

    /**
     *spring boot中可能会出现乱麻 所以项目开发之出在 application.properties
     * spring.http.encoding.force=true
     * spring.http.encoding.charset=UTF-8
     * spring.http.encoding.enabled=true
     * server.tomcat.uri-encoding=UTF-8
     * 添加以上配置后 此时拦截器中返回的中文已经不乱码了，但是controller中返回的数据依旧乱码
     *
     *
     * //字符编码过滤器
     * @WebFilter(urlPatterns = "/*",filterName = "CharacterEncodingFilter")
     * public class CharacterEncodingFilter implements Filter{
     *     @Override
     *     public void init(FilterConfig filterConfig) throws ServletException {
     *     }
     *
     *     @Override
     *     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
     *         HttpServletRequest request = (HttpServletRequest) servletRequest;
     *         HttpServletResponse response = (HttpServletResponse) servletResponse;
     *         request.setCharacterEncoding("UTF-8");
     *         response.setCharacterEncoding("UTF-8");
     *
     *         filterChain.doFilter(request , response);
     *     }
     *     @Override
     *     public void destroy() {
     *     }
     * }
     *
     * 修改controller的@RequestMapping
     * 修改如下：
     * produces="text/plain;charset=UTF-8"
     *
     * @Configuration
     * public class CustomMVCConfiguration extends WebMvcConfigurerAdapter {
     *
     *     @Bean
     *     public HttpMessageConverter<String> responseBodyConverter() {
     *         StringHttpMessageConverter converter = new StringHttpMessageConverter(
     *                 Charset.forName("UTF-8"));
     *         return converter;
     *     }
     *
     *     @Override
     *     public void configureMessageConverters(
     *             List<HttpMessageConverter<?>> converters) {
     *         super.configureMessageConverters(converters);
     *         converters.add(responseBodyConverter());
     *     }
     *
     *     @Override
     *     public void configureContentNegotiation(
     *             ContentNegotiationConfigurer configurer) {
     *         configurer.favorPathExtension(false);
     *     }
     * }
     *
     *
     * // 基于Springboot2.0.1.RELEASE
     *
     * import com.simply.zuozuo.interceptor.HandleInterceptorImpl;
     * import org.springframework.context.annotation.Bean;
     * import org.springframework.context.annotation.Configuration;
     * import org.springframework.http.converter.HttpMessageConverter;
     * import org.springframework.http.converter.StringHttpMessageConverter;
     * import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
     * import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
     * import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
     * import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
     *
     * import java.nio.charset.Charset;
     * import java.util.List;
     *
     * @Configuration
     * public class WebMvcConfig extends WebMvcConfigurationSupport {
     *
     *     @Bean
     *     public HttpMessageConverter<String> responseBodyConverter() {
     *         return new StringHttpMessageConverter(Charset.forName("UTF-8"));
     *     }
     *
     *     @Override
     *     public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
     *         converters.add(responseBodyConverter());
     *         // 这里必须加上加载默认转换器，不然bug玩死人，并且该bug目前在网络上似乎没有解决方案
     *         // 百度，谷歌，各大论坛等。你可以试试去掉。
     *         addDefaultHttpMessageConverters(converters);
     *     }
     *
     *     @Override
     *     public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
     *         configurer.favorPathExtension(false);
     *     }
     *
     * }
     *
     * 以上是基于新项目添加的配置类    因为公司都是老项目  或者说是完全没考虑这个情况下的新项目  这个时候需要在页面参数进行编码
     *
     *
     * 传统模式下tomcat在项目外面直接修改tomcat配置  （根据公司来 有些公司不乐意）
     * <Connector port="8080" protocol="HTTP/1.1"
     *  connectionTimeout="2000"
     *  redirectPort="8443"
     *  URIEncoding="UTF-8"
     *  maxThreads="3000"
     *  compression="on" compressableMimeType="text/html,text/xml"
     *  maxPostSize="0"
     * />
     *
     * request.setCharacterEncoding(charset)设置编码，然后通过request.getParameter获得正确的数据。
     *
     * <filter>
     *     <filter-name>Set Character Encoding</filter-name>
     *     <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
     *     <init-param>
     *       <param-name>encoding</param-name>
     *       <param-value>UTF-8</param-value>
     *     </init-param>
     *     <init-param>
     *       <param-name>forceEncoding</param-name>
     *       <param-value>true</param-value>
     *     </init-param>
     *   </filter>
     *
     *String b1 =  new String(str.getBytes("gbk"),"gbk");传统解码方式  但是你需要自己知道页面是什么编码在去解决
     *
     *  更多的情况是你接手了个老项目 一个项目一个多G 你哪里知道他到底怎么搞的会乱马 所以最直接且不影响其他功能的方法
     *
     *
     * js中 的编码解码
     *
     *
     * 1)escape 和 unescape
     * 原理：对除ASCII字母、数字、标点符号 @ * _ + - . / 以外的其他字符进行编码。
     * 编码:
     * eg:escape('http://www.baidu.com?name=zhang@xiao@jie&order=你好')
     * res:"http%3A//www.baidu.com%3Fname%3Dzhang@xiao@jie%26order%3D%u4F60%u597D"
     * 解码：
     * eg:unescape("http%3A//www.baidu.com%3Fname%3Dzhang@xiao@jie%26order%3D%u4F60%u597D")
     * res:"http://www.baidu.com?name=zhang@xiao@jie&order=你好"
     * 2)encodeURI 和 decodeURI
     * 原理：返回编码为有效的统一资源标识符 (URI) 的字符串，不会被编码的字符：! @ # $ & * ( ) = : / ; ? + '
     * 　　 encodeURI()是Javascript中真正用来对URL编码的函数。
     * 编码：
     * eg:encodeURI('http://www.baidu.com?name=zhang@xiao@jie&order=你好')
     * 　　　 res:"http://www.baidu.com?name=zhang@xiao@jie&order=%E4%BD%A0%E5%A5%BD"
     * 解码：
     * eg:decodeURI("http://www.baidu.com?name=zhang@xiao@jie&order=%E4%BD%A0%E5%A5%BD")
     * 　　　 res:"http://www.baidu.com?name=zhang@xiao@jie&order=你好"
     * 3)encodeURIComponent 和 decodeURIComponent
     * 原理：对URL的组成部分进行个别编码，而不用于对整个URL进行编码
     * 编码：
     * eg:encodeURIComponent('http://www.baidu.com?name=zhang@xiao@jie&order=1')
     * 　　 res:"http%3A%2F%2Fwww.baidu.com%3Fname%3Dzhang%40xiao%40jie%26order%3D1"
     * 解码：
     * eg:decodeURIComponent("http%3A%2F%2Fwww.baidu.com%3Fname%3Dzhang%40xiao%40jie%26order%3D1")
     * 　　 res:"http://www.baidu.com?name=zhang@xiao@jie&order=1"
     *
     * 大多数情况下我们只对中文进行编码就行了
     *
     * 但是对于可能要传入 html的话估计需要用到
     * //Html编码获取Html转义实体
     * function htmlEncode(value){
     *   return $('<div/>').text(value).html();
     * }
     * //Html解码获取Html实体
     * function htmlDecode(value){
     *   return $('<div/>').html(value).text();
     * }
     * 示例
     * $("<div />").text("<>").html()
     * "&lt;&gt;"
     * $("<div />").html("&lt;&gt;").text()
     * "<>"
     *
     * Character	Entity Number	Entity Name	Description
     * " 	        &#34; 	            &quot;  	quotation mark
     * ' 	        &#39; 	            &apos;  (does not work in IE) 	apostrophe
     * & 	        &#38; 	            &amp;  	ampersand
     * < 	        &#60; 	            &lt;  	less-than
     * > 	        &#62; 	            &gt;  	greater-than
     *
     * 但是无法对'' 和 "" 进行 编码  所以需要对需要的内容进行编码  在网络上没有收集到比较好的 所以自己写吧
     * 应为有 jquery的 html和text 帮助 所以
     * 可能还有\t
     *      //编码
     *     function html_encode(str)
     *     {
     *         var s = "";
     *         if (str.length == 0) return "";
     *         s = str.replace(/\n/g, "<br>");
     *         s = $("<div />").text(s).html();
     *         s = s.replace(/\'/g, "&#39;");
     *         s = s.replace(/\"/g, "&#34;");
     *         return s;
     *     }
     *     //解码
     *     function html_decode(str)
     *     {
     *         var s = "";
     *         if (str.length == 0) return "";
     *         s = str.replace(/<br>/g, "\n");
     *         s = $("<div />").html(s).text();
     *         s = s.replace(/&#39;/g, "\'");
     *         s = s.replace(/&#34;/g, "\"");
     *         return s;
     *     }
     *
     *     spring中的可以进行转化  spring-web  本项目就不引入了只给示例
     *     HtmlUtils.htmlEscapeDecimal("'\"<>戴达");
     * 		HtmlUtils.htmlEscapeDecimal("'\"<>戴达","");
     * 		HtmlUtils.htmlEscape("'\"<>戴达");
     * 		HtmlUtils.htmlEscape("'\"<>戴达","");
     * 		HtmlUtils.htmlEscapeHex("'\"<>戴达");
     * 		HtmlUtils.htmlEscapeHex("'\"<>戴达","");
     * 		HtmlUtils.htmlUnescape("&#39;&quot;&lt;&gt;戴达");
     *
     * org.apache.commons.lang3 中的字符串转化也比较专业常用的话spring 就可以完成
     * StringEscapeUtils
     *
     */

    //将application/x-www-form-urlencoded字符串转换成普通字符串
    //采用UTF-8字符集进行解码    只能解决URL %XX  这种形式的编码
    public static BiFunction<String,Invariable,String> decoding =  (s, type)-> {
        try {
            return URLDecoder.decode(s, type.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    };

    // 将普通字符串转换成application/x-www-form-urlencoded字符串
    //采用utf-8字符集     只能解决URL %XX  这种形式的编码
    public static BiFunction<String,Invariable,String> codeing =  (s,type)-> {
        try {
            return URLEncoder.encode(s, type.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    };



}
