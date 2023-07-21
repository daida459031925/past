package com.gitHub.past.JDBC;

import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Data
public class ExportAllTablesToCSV {
    private static final Logger logger = Logger.getLogger("ExportAllTablesToCSV");

    private static final String DB_URL = "jdbc:mysql://localhost:3306/lab_iot";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "MYSQL";
    private static final String ClassForName = "com.mysql.cj.jdbc.Driver";

    private static final List<String> out = new LinkedList<>();
    private static final List<String> notOut = new LinkedList<>();

    private static final String rowSeparate = "{{row87793891}}";
    private static final String colSeparate = "{{col87793891}}";

    private static final List<String> delFlagData = new LinkedList<>();
    private static final String delFlag = "del_flag";


    static {

        //不输出
        notOut.add("sys_oper_log");
        notOut.add("sys_logininfor");
        notOut.add("sys_user_change_log");
        notOut.add("undo_log");
        notOut.add("gen_table");
        notOut.add("gen_table_column");

        delFlagData.add("0");
    }

    public String getCSVFile(String dbUrl, String dbUser, String dbPassword, String classForName, String FilePath){
        addBean(dbUrl, dbUser, dbPassword);
        getCSVFile(beans, classForName, FilePath);
        return FilePath;
    }

    public String getCSVFile(List<Bean> list, String classForName, String FilePath){
        if(Objects.isNull(FilePath)||FilePath.isEmpty()){
            FilePath = "/tmp";
        }
        if(Objects.isNull(classForName)||classForName.isEmpty()){
            classForName = ClassForName;
        }
        try {
            Path directory = Paths.get(FilePath);

            if (Files.exists(directory)) {
                System.out.println("文件夹已存在");
            } else {
                try {
                    Files.createDirectory(directory);
                    System.out.println("文件夹创建成功");
                } catch (IOException e) {
                    System.out.println("创建文件夹时出错: " + e.getMessage());
                }
            }

            for (int v = 0; v < list.size(); v++) {
                Bean bean = list.get(v);
                Class.forName(classForName);
                Connection connection = DriverManager.getConnection(bean.getUrl(), bean.getUser(), bean.getPwd());

                // 获取所有表的名称
                Statement statement = connection.createStatement();
                ResultSet tablesResultSet = statement.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = '"+ bean.getTableName() +"'");

                while (tablesResultSet.next()) {
                    String tableName = tablesResultSet.getString("table_name");
                    if(notOut.contains(tableName)){
                        continue;
                    }
                    // 创建一个 CSV 文件来存储所有表的数据
                    FileWriter csvWriter = new FileWriter(FilePath+"/"+convertStringFormat(tableName)+".csv");
                    // 查询表中的所有数据
                    Statement dataStatement = connection.createStatement();
                    ResultSet dataResultSet = dataStatement.executeQuery("SELECT * FROM " + tableName);

//                    // 写入表的名称作为 CSV 文件的第一行
//                    csvWriter.append(tableName).append("\n");

                    // 写入表的数据到 CSV 文件中
                    ResultSetMetaData metaData = dataResultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    int del_flag = -1;

                    // 写入表的列名作为 CSV 文件的第二行
                    for (int i = 1; i <= columnCount; i++) {
                        csvWriter.append(toCamelCase(metaData.getColumnName(i)));
                        if(i != columnCount){
                            csvWriter.append(colSeparate);
                        }
                        if(delFlag.equals(metaData.getColumnName(i))){
                            del_flag = i;
                        }
                    }
                    csvWriter.append(rowSeparate);

                    // 写入表的数据
                    while (dataResultSet.next()) {
                        if(del_flag > -1 &&
                                !delFlagData.isEmpty() && !delFlagData.contains(dataResultSet.getString(del_flag))){
                            continue;
                        }

                        for (int i = 1; i <= columnCount; i++) {
                            csvWriter.append(dataResultSet.getString(i));
                            if(i != columnCount){
                                csvWriter.append(colSeparate);
                            }
                        }
                        csvWriter.append(rowSeparate);
                    }

                    // 关闭数据结果集和语句
                    dataResultSet.close();
                    dataStatement.close();
                    // 关闭资源
                    csvWriter.flush();
                    csvWriter.close();

                }


                tablesResultSet.close();
                statement.close();
                connection.close();
            }

            logger.info("数据导出成功！");
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        return FilePath;
    }

    private List<Bean> beans;

    public void addBean(String dbUrl, String dbUser, String dbPassword){
        if(Objects.isNull(beans)){
            beans = new LinkedList<>();
        }
        Bean bean = new Bean();
        bean.setPwd(dbPassword);
        bean.setUrl(dbUrl);
        bean.setUser(dbUser);
//        3306/lab_iot?
        String[] split = dbUrl.split(":3306/");
        int i = split[1].indexOf("?");
        String substring = split[1];
        if(i>-1){
            substring = split[1].substring(0, i);
        }
        bean.setTableName(substring);
        beans.add(bean);
    }

    @Data
    class Bean{
        private String url;
        private String user;
        private String pwd;
        private String tableName;
    }

    public static void main(String[] args) {
        ExportAllTablesToCSV exportAllTablesToCSV = new ExportAllTablesToCSV();
        exportAllTablesToCSV.getCSVFile(DB_URL,DB_USER,DB_PASSWORD,ClassForName,"/tmp");
    }

    public static String convertStringFormat(String str) {
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNextChar = true;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (ch == '_') {
                capitalizeNextChar = true;
            } else {
                if (capitalizeNextChar) {
                    sb.append(Character.toUpperCase(ch));
                    capitalizeNextChar = false;
                } else {
                    sb.append(ch);
                }
            }
        }

        return sb.toString();
    }

    public static String toCamelCase(String str) {
        // 将字符串按照空格、下划线和连字符分割成单词数组
        String[] words = str.split("[\\s_-]+");

        StringBuilder result = new StringBuilder();
        // 将第一个单词的首字母小写处理
        result.append(words[0].toLowerCase());

        // 将后续单词的首字母大写处理
        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            // 将单词的首字母转换为大写
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            result.append(capitalizedWord);
        }

        return result.toString();
    }
}

