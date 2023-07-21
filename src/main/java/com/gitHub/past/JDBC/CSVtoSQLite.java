package com.gitHub.past.JDBC;

import cn.hutool.core.util.ReflectUtil;

import java.beans.Transient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//csv导出数据未sqlite
public class CSVtoSQLite {

    private static final Logger logger = Logger.getLogger("CSVtoSQLite");

    private static final String rowSeparate = "\\{\\{row87793891}}";
    private static final String colSeparate = "\\{\\{col87793891}}";

    public static void main(String[] args) throws IOException {
        List<String> list = saveSqliteDb("/tmp/0000003.db", "/tmp");
        logger.info(list.toString());
    }

    // sqliteDbPath : /path/to/your/database.db
    // FilePath : /home/sga/log
    public static List<String> saveSqliteDb(String sqliteDbPath,String FilePath) throws IOException {
        Path path = Paths.get(sqliteDbPath);
        boolean exists = Files.exists(path);
        if(exists){
            return new LinkedList<>();
        }
        // SQLite数据库连接信息
        String url = "jdbc:sqlite:"+sqliteDbPath;
        // 要导入的多个CSV文件列表
        List<String> files = findCSVFiles(FilePath);
        try {
            // 连接SQLite数据库
            Connection conn = DriverManager.getConnection(url);

            // 循环处理每个CSV文件
            for (String file : files) {
                // 从文件名中提取表名
                String tableName = file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf("."));
                logger.info(tableName);

                List<List<String>> datas = new LinkedList<>();
                //获取原始数据，只有原始数据才可以满足自定义处理的要求
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder sb = new StringBuilder();
                    String record;
                    while ((record = reader.readLine()) != null) {
                        // 处理每行原始数据
                        sb.append(record);
                    }
                    String[] split = sb.toString().split(rowSeparate);
                    for (int i = 0; i < split.length; i++) {
                        String[] split1 = split[i].split(colSeparate);
                        datas.add(Arrays.stream(split1).collect(Collectors.toList()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<String> headers = datas.get(0);

                // 构造CREATE TABLE语句
                StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                        .append(tableName).append(" (");
                for (String header : headers) {
                    createTableQuery.append(header).append(" TEXT,");
                }
                createTableQuery.setLength(createTableQuery.length() - 1); // 去除最后一个逗号
                createTableQuery.append(")");

                // 创建表
                conn.createStatement().execute(createTableQuery.toString());

                // 构造INSERT语句
                StringBuilder insertQuery = new StringBuilder("INSERT INTO ").append(tableName)
                        .append(" VALUES (");
                for (int i = 0; i < headers.size(); i++) {
                    insertQuery.append("?,");
                }
                insertQuery.setLength(insertQuery.length() - 1); // 去除最后一个逗号
                insertQuery.append(")");

                // 准备插入语句
                PreparedStatement stmt = conn.prepareStatement(insertQuery.toString());

                // 读取CSV文件的数据行并插入到数据库中
                for (int i = 1; i < datas.size(); i++) {
                    List<String> list = datas.get(i);
                    for (int j = 0; j < list.size(); j++) {
                        stmt.setString(j + 1, list.get(j));
                    }
                    stmt.executeUpdate();
                }
                // 关闭CSV读取器和插入语句
                stmt.close();
            }

            // 关闭数据库连接
            conn.close();

            logger.info("数据导入完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    public static List<String> findCSVFiles(String folderPath) {
        List<String> csvFiles = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    csvFiles.add(file.getAbsolutePath());
                }
            }
        }

        return csvFiles;
    }


    //解析时候的示例 sqlite的示例
    public List<Object> select(Class clazz, Map<String, Object> filters,String url) {
        List<Object> results = new ArrayList<>();
        String tableName = clazz.getSimpleName();
        StringBuilder sql = new StringBuilder("SELECT ");

        // 获取实体类的字段信息
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            String columnName = field.getName();

            // 根据字段类型进行数据类型转换
            if (field.getType() == String.class) {
                sql.append("CAST(").append(columnName).append(" AS TEXT)").append(" AS ").append(columnName);
            } else if (field.getType() == int.class || field.getType() == Integer.class) {
                sql.append("CAST(").append(columnName).append(" AS INTEGER)").append(" AS ").append(columnName);
            } else if (field.getType() == float.class || field.getType() == Float.class) {
                sql.append("CAST(").append(columnName).append(" AS REAL)").append(" AS ").append(columnName);
            } else if (field.getType() == double.class || field.getType() == Double.class) {
                sql.append("CAST(").append(columnName).append(" AS REAL)").append(" AS ").append(columnName);
            } else if (field.getType() == long.class || field.getType() == Long.class) {
                sql.append("CAST(").append(columnName).append(" AS INTEGER)").append(" AS ").append(columnName);
            } else if (field.getType() == java.util.Date.class) {
                sql.append("CAST(").append(columnName).append(" AS TIMESTAMP)").append(" AS ").append(columnName);
            } else if (field.getType() == java.lang.Short.class) {
                sql.append("CAST(").append(columnName).append(" AS SMALLINT)").append(" AS ").append(columnName);
            } else if (field.getType() == java.lang.Boolean.class) {
                sql.append("CAST(").append(columnName).append(" AS BOOLEAN)").append(" AS ").append(columnName);
            } else {
                sql.append(columnName);
            }
            sql.append(",");
        }

        String s = sql.toString();
        int lastIndex = s.lastIndexOf(",");
        if (lastIndex != -1) {
            s = s.substring(0, lastIndex) + sql.substring(lastIndex + 1);
        }
        sql.setLength(0);
        sql.append(s).append(" FROM ").append(tableName);

        if (filters != null && filters.size() > 0) {
            sql.append(" WHERE ");
            int cnt = 0;
            Iterator<Map.Entry<String, Object>> iterator = filters.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();

                if (cnt > 0) {
                    sql.append(" AND ");
                }

                if (value instanceof String) {
                    sql.append(key).append("='").append(value).append("'");
                } else {
                    sql.append(key).append("=").append(value);
                }

                cnt++;
            }
        }

        sql.append(";");

        try {
            // 连接SQLite数据库
            Connection conn = DriverManager.getConnection(url);
            synchronized (conn) {
                PreparedStatement stmt = conn.prepareStatement(sql.toString());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Object obj = mapToClassObject(clazz, rs);
                    results.add(obj);
                }
                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    private Object mapToClassObject(Class clazz, ResultSet rs) throws IllegalAccessException, InstantiationException, SQLException {
        Object obj1 = clazz.newInstance();
        Field[] fds = ReflectUtil.getFieldsDirectly(obj1.getClass(), true);
        for (Field fd : fds) {
            boolean ignored = false;
            Annotation[] annots = fd.getAnnotations();
            for (Annotation annot : annots) {
                String annos = annot.toString();
                if (annos.compareToIgnoreCase("@javax.persistence.Transient()") == 0) {
                    ignored = true;
                    break;
                }
            }
            if (ignored) {
                continue;
            }

            try {
                Object fdv = null;
                if (fd.getType() == java.lang.Float.class) {
                    fdv = rs.getFloat(fd.getName());
                } else if (fd.getType() == java.util.Date.class) {
                    fdv = rs.getDate(fd.getName());
                } else if (fd.getType() == java.lang.Short.class) {
                    fdv = rs.getShort(fd.getName());
                } else if (fd.getType() == java.lang.Boolean.class) {
                    fdv = rs.getBoolean(fd.getName());
                }else if (fd.getType() == java.lang.Long.class) {
                    fdv = rs.getLong(fd.getName());
                }else {
                    fdv = rs.getObject(fd.getName());
                }
                if (fdv != null) {
                    fd.setAccessible(true);
                    fd.set(obj1, fdv);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj1;
    }

}
