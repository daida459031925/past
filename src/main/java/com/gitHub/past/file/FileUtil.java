package com.gitHub.past.file;

import com.gitHub.past.Invariable;
import com.gitHub.past.common.ValidatorUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * 默认情况Files类中的所有方法都会使用UTF-8编码进行操作，当你不愿意这么干的时候可以传递Charset参数进去变更
 */
public class FileUtil {

    /**
     * Path
     * Path用于来表示文件路径和文件，和File对象类似，Path对象并不一定要对应一个实际存在的文件， 它只是一个路径的抽象序列。
     *
     * 要创建一个Path对象有多种方法，首先是final类Paths的两个static方法，如何从一个路径字符串来构造Path对象：
     *
     * Path path1   = Paths.get("/home/biezhi", "a.txt");
     * Path path2   = Paths.get("/home/biezhi/a.txt");
     * URI  u       = URI.create("file:////home/biezhi/a.txt");
     * Path pathURI = Paths.get(u);
     * 通过FileSystems构造
     *
     * Path filePath = FileSystems.getDefault().getPath("/home/biezhi", "a.txt");
     * Path、URI、File之间的转换
     *
     * File file  = new File("/home/biezhi/a.txt");
     * Path p1    = file.toPath();
     * p1.toFile();
     * file.toURI();
     */

    /**
     * 创建路径
     */
    private Path path;
    /**
     * 文件还是文件夹
     */
    private boolean isFile;
    /**
     * 创建对应映射
     */
    private File file;

    /**
     * @param path /xxxx/xxxx/a.txt
     */
    public FileUtil(String path) {
        this.path = Paths.get(path);
        this.file = new File(path);
        this.isFile = file.isFile();
    }

    /**
     *
     * @param path     /xxxx/xxxx
     * @param fileName a.txt
     */
    public FileUtil(String path,String fileName) {
        this.path = Paths.get(path,fileName);
    }


    /**
     * 读文件
     */
    public byte[] readFile() throws IOException {
        return Files.readAllBytes(path);
    }

    /**
     * 读文件
     */
    public List<String> readLineFile() throws IOException {
        return Files.readAllLines(path);
    }

    /**
     * 将流写入
     * @param bytes
     * @throws IOException
     */
    public void writeFile(byte[] bytes) throws IOException {
        writeFile(bytes,false);
    }

    /**
     * 将流写入
     * @param bytes
     * @throws IOException
     */
    public void writeFile(byte[] bytes,boolean tf) throws IOException {
        if(tf){
            //追加模式
            Files.write(path,bytes, StandardOpenOption.APPEND);
        }else {
            Files.write(path,bytes);
        }
    }

    /**
     * 当然Files还有一些其他的常用方法:
     * InputStream ins = Files.newInputStream(path);
     * OutputStream ops = Files.newOutputStream(path);
     * Reader reader = Files.newBufferedReader(path);
     * Writer writer = Files.newBufferedWriter(path);
     */


    /**
     *Files还提供了一些方法让我们创建临时文件/临时目录:
     *
     * Files.createTempFile(dir, prefix, suffix);
     * Files.createTempFile(prefix, suffix);
     * Files.createTempDirectory(dir, prefix);
     * Files.createTempDirectory(prefix);
     * 这里的dir是一个Path对象，并且字符串prefix和suffix都可能为null。 例如调用Files.createTempFile(null, “.txt”)会返回一个类似/tmp/21238719283331124678.txt
     */


    /**
     * 创建文件
     * @throws IOException
     */
    public void createFile() throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    /**
     * 创建文件夹
     * @throws IOException
     */
    public void createDirectory() throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

    /**
     * 读取一个目录下的文件请使用Files.list和Files.walk方法
     */

    /**
     * 复制流内容到指定地方
     * @param in
     * @throws IOException
     */
    public void copy(InputStream in) throws IOException {
        Files.copy(in, path);
    }

    /**
     * 将当前这个文件移动到  目标位置
     * 源文件会被删除
     * @param path
     * @throws IOException
     */
    public void move(Path path) throws IOException {
        Files.move(this.path, path);
    }

    /**
     * 删除掉自己 如果是文件夹则需要删除当前文件夹下的所有文件
     * @throws IOException
     */
    public void delete() throws IOException {
        /**
         *listFiles()方法的作用如果file是个文件，则返回的是null，如果file是空目录，返回的是空数组，如果file不是空目录，则返回的是该目录下的文件和目录
         */
        File[] files = file.listFiles();
        Optional.ofNullable(files).ifPresent(v->{
            if(v.length > 0){
                Arrays.stream(v).forEach(e->{
                    FileUtil fileUtil1 = new FileUtil(e.getPath());
                    try {
                        fileUtil1.delete();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            }
        });

        file.delete();
    }

    /**
     * spring boot 通过ClassPathResource获取resource内自定的libs文件内容
     * new ClassPathResource("libs/opencv/x64/opencv_java453.dll");
     * 获取到文件后直接拿到流进行文件加载
     *
     * @param inputStream 需要读取的文件流
     * @param path        指定存放位置必须带有文件名
     */
    public static void load(InputStream inputStream,Path path) throws Exception {
        //根据传入的文件名和文件后缀以及文件存放位置来生成文件
        ValidatorUtils.of(Objects.isNull(inputStream),"至少需要有输入流")
                .isApply(Objects.isNull(path),"至少拥有存放路径")
                .isApply(Pattern.matches(Invariable.SUFFIX.toString(),path.getFileName().toString()),"至少拥有文件名和后缀名")
                .getError();
        //从jar中读取文件流
        Files.copy(inputStream,path);

        //加载库文件
        System.load(String.valueOf(path.toAbsolutePath()));

        //创建完毕删除临时文件   这个地方是删除不掉的，因为文件在  System.load 占用
//        Files.delete(path);
    }

    /**
     * spring boot 通过ClassPathResource获取resource内自定的libs文件内容
     * new ClassPathResource("libs/opencv/x64/opencv_java453.dll");
     * 获取到文件后直接拿到流进行文件加载
     *
     * @param inputStream 需要读取的文件流
     * @param prefix      文件名    xxxx
     * @param suffix      文件后缀  .XXXX
     */
    public static void load(InputStream inputStream,String prefix, String suffix) throws Exception{
        AtomicReference<String> path = new AtomicReference<>();
        Supplier<String> sup = ()->{path.set(prefix+suffix);return path.get(); };
        ValidatorUtils.of(Objects.isNull(prefix),"至少需要有文件名")
                .isApply(Objects.isNull(suffix),"至少需要有后缀名")
                .isApply(Pattern.matches(Invariable.SUFFIX.toString(),sup.get()),"至少拥有文件名和后缀名")
                .getError();
        load(inputStream,Path.of(path.get()));
    }

    /**
     * spring boot 通过ClassPathResource获取resource内自定的libs文件内容
     * new ClassPathResource("libs/opencv/x64/opencv_java453.dll");
     * 获取到文件后直接拿到流进行文件加载
     *
     * @param inputStream 需要读取的文件流
     * @param path        文件名    xxxx/xxxx.XXXX
     */
    public static void load(InputStream inputStream,String path) throws Exception{
        load(inputStream,Optional.ofNullable(path).map(e->Path.of(path)).orElse(null));
    }



    private static Pattern FilePattern = Pattern.compile("[\\s\\.:?<>|]"); //过滤规则
    /**
     *包含特殊字符的文件路劲全部进行空替换
     * 此方法适用于文件路径
     */
    public static String filenameFilter(String str) {
        return str==null?null:FilePattern.matcher(str).replaceAll("");
    }



    public static void main(String[] args) throws Exception {
//        FileUtil fileUtil = new FileUtil("C:/Users/daida/Desktop/l.txt");
//        fileUtil.delete();
        //        byte[] bytes = fileUtil.readFile();
//        Files.getFileStore(Paths.get("/home/sga/apache-tomcat-10.0.0-M5/webapps/HustElective.war"))
//        List<String> list = fileUtil.readLineFile();
//        SysFun.sysPrintln.accept(list);


    }
}
