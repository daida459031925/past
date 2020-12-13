package com.gitHub.past.common;

import com.gitHub.past.Invariable;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**除构造方法外，提供两个public方法 void packageScanner(Class\<\?\> klass)
 *和 void packageScanner(String packageName)
 *扫描出  给定类所在包  或  给定包路径  下的所有的类 （用到递归）
 *用抽象方法提供给工具使用者去决定 如何处理扫描出的类
 *
 * 包扫描一般用作于扫描出该包内所有带有某注解的类，并对之进行处理
 *          包扫描可分为普通包扫描和Jar包扫描
 *
 * 使用这个扫描还是不能解决我在单独这个项目中实现初始化和类的卸载
 */
public abstract class PackageScanner {
//    public PackageScanner() {
//    }

    public abstract void dealClass(Class<?> klass);

    public void packageScanner(Class<?> klass) {
        packageScanner(klass.getPackage().getName());
    }

    public void packageScanner(String packageName) {
        String packagePath = packageName.replace(Invariable.SPOT.toString(), Invariable.XIEGANG.toString());

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url.getProtocol().equals(Invariable.JAR.toString())) {
                    scanPackage(url);
                } else {
                    File file = new File(url.toURI());
                    if (!file.exists()) {
                        continue;
                    }
                    scanPackage(packageName, file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void scanPackage(URL url) throws IOException {
        JarURLConnection jarUrlConnection =  (JarURLConnection) url.openConnection();
        JarFile jarFile = jarUrlConnection.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarName = jarEntry.getName();
            if (jarEntry.isDirectory() || !jarName.endsWith(Invariable.DCLASS.toString())) {
                continue;
            }
            String className = jarName.replace(Invariable.DCLASS.toString(),Invariable.EMPTY.toString())
                    .replaceAll(Invariable.XIEGANG.toString(), Invariable.SPOT.toString());
            try {
                Class<?> klass = Class.forName(className);
                if (klass.isAnnotation()
                        ||klass.isInterface()
                        ||klass.isEnum()
                        ||klass.isPrimitive()) {
                    continue;
                }
                dealClass(klass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void scanPackage(String packageName, File currentfile) {
        File[] files = currentfile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (currentfile.isDirectory()) {
                    return true;
                }
                return pathname.getName().endsWith(Invariable.DCLASS.toString());
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (File file : files) {
            stringBuilder.delete(0,stringBuilder.length());
            if (file.isDirectory()) {
                scanPackage(stringBuilder.append(packageName).append(Invariable.SPOT.toString()).append(file.getName()).toString(), file);
            } else {
                String fileName = file.getName().replace(Invariable.DCLASS.toString(), Invariable.EMPTY.toString());
                String className = stringBuilder.append(packageName).append(Invariable.SPOT.toString()).append(fileName).toString();
                try {
                    Class<?> klass = Class.forName(className);
                    if (klass.isAnnotation()
                            ||klass.isInterface()
                            ||klass.isEnum()
                            ||klass.isPrimitive()) {
                        continue;
                    }
                    dealClass(klass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*使用方法*/
    public static void load(String path) {
        new PackageScanner() {
            @Override
            public void dealClass(Class<?> klass) {
                SysFun.loginfo.accept(klass.getName()+"加载成功");
            }
        }.packageScanner(path);
    }

    public static void load(Class<?> czlss) {
        new PackageScanner() {
            @Override
            public void dealClass(Class<?> klass) {
                SysFun.loginfo.accept(klass.getName()+"加载成功");
            }
        }.packageScanner(czlss);
    }


}
