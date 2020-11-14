package com.gitHub.past.common;

import com.gitHub.past.Invariable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertyUtil {

    private static final Logger logger = SysFun.logger;
    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        //<!--第一种，通过类加载器进行获取properties文件流-- >
        try (InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream(
                Invariable.PAST.toString()+Invariable.SPOT.toString()+Invariable.PROPERTIES.toString())
        ) {
            //<!--第二种，通过类进行获取properties文件流-- >
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(in);
            logger.info("加载properties文件内容完成...........");
            logger.info("properties文件内容：" + props);
        } catch (FileNotFoundException e) {
            logger.info("jdbc.properties文件未找到");
        } catch (IOException e) {
            logger.info("出现IOException");
        } catch (Exception e){
            logger.info("未知异常");
        }
    }

    public static String getProperty(String key) {
        if (Objects.isNull(props)) { loadProps(); }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (Objects.isNull(props)) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
