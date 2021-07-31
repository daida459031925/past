package com.gitHub.past.common;


import com.gitHub.past.Invariable;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class SysFun {

    /**
     * 系统打印输出
     */
    public static Consumer<Object> sysPrintln = System.out::println;

    private static Logger logger = Logger.getGlobal();  //java.util.logging.Logger

    public static Consumer<String> loginfo = (string)-> logger.info(string);

//    CPU 密集型：线程数量=cpu核心数量
//    IO 密集型：线程数量=cpu核心数量*2
    public static int nThreads = Runtime.getRuntime().availableProcessors();

//    java反射
//    public class Test {
//        public void method1() {
//            System.out.println("method1() invoked");
//        }
//
//        public void method2() {
//            System.out.println("method2() invoked");
//        }
//
//        public void method3() {
//            System.out.println("method3() invoked");
//        }
//
//        public void invokeMethod(String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//            Test.class.getMethod(methodName,null).invoke(this,null);
//        }
//
//        public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//            new Test().invokeMethod("method2");
//        }
//
//    }

    public static Supplier<String> getOS = ()-> System.getProperty(Invariable.OS.toString()).toLowerCase();

    public static Supplier<Boolean> isLinux = ()-> getOS.get().contains(Invariable.LINUX.toString());
    public static Supplier<Boolean> isMacOS = ()-> {String os = getOS.get();return os.contains(Invariable.MAC.toString()) && os.indexOf(Invariable.OS.toString()) > 0 && !os.contains(Invariable.X.toString());};
    public static Supplier<Boolean> isMacOSX = ()-> {String os = getOS.get();return os.contains(Invariable.MAC.toString()) && os.indexOf(Invariable.OS.toString()) > 0 && os.indexOf(Invariable.X.toString()) > 0;};
    public static Supplier<Boolean> isWindows = ()-> getOS.get().contains(Invariable.WINDOWS.toString());

    public static Supplier<String> thisUserDir = ()-> System.getProperty(Invariable.USERDIR.toString());

}
