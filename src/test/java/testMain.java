
import com.gitHub.past.common.PackageScanner;
import com.gitHub.past.common.SysFun;
import com.gitHub.past.executors.ExecutorsUtil;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;


public class testMain {

    @PreDestroy //结束时候执行一次
    private static void exit(){
        System.out.println("对象销毁时候删除对应的值");
    }

    public void finalize() {

    }

    public static void main(String[] args){
        PackageScanner.load(ExecutorsUtil.class);

//        for(int i =1;i<100000;i++){
//            testMain executors = new testMain();
//            try {
//                executors.clone();
//            } catch (CloneNotSupportedException e) {
//                e.printStackTrace();
//            }
//            SysFun.loginfo.accept(String.valueOf(i));
//        }




//        System.out.println(Invariable.UTF_8);
//        System.out.println(Invariable.UTF_8.name());
//        System.out.println(Invariable.UTF_8.ordinal());
//        System.out.println(Invariable.UTF_8.toString());
//        System.out.println(Invariable.UTF_8.toString() == "UTF-8");
//
//
//        System.out.println(CharEncoding.codeing.apply("%3A%2F",Invariable.UTF_8));
//                    .decode("&#39;&#34;&lt;&gt;",Invariable.UTF_8.toString());

//        List<Integer> integers = Arrays.asList(1, 2, 3);
//        List<Integer> integers1 = Arrays.asList(1, 2, 4);


//        ListUtil listUtil = new ListUtil(integers,integers1,null,null);
//        listUtil.aJianb.

//        LinuxUtil.executeNewFlow("cd /");
//        try {
//            LinuxUtil linuxUtil = new LinuxUtil();
//            linuxUtil.getOut().println("cd / && ls");
//            linuxUtil.getIn().read();
//            while (linuxUtil.getIn().readLine() != null) {
//                Logger.getGlobal().info(linuxUtil.getIn().readLine());
//            }
//            linuxUtil.getOut().println("ls");
//            linuxUtil.getOut().println("ls");
////            linuxUtil.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        executeNewFlow(Arrays.asList("ls"));
    }

    public static List<String> executeNewFlow(List<String> commands) {
        List<String> rspList = new ArrayList<String>();
        Runtime run = Runtime.getRuntime();
        try {
            Process proc = run.exec("/bin/bash", null, null);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            for (String line : commands) {
                out.println(line);
            }
            // out.println("cd /home/test");
            // out.println("pwd");
            // out.println("rm -fr /home/proxy.log");
//            out.println("exit");// 这个命令必须执行，否则in流不结束。
            String rspLine = "";
            while ((rspLine = in.readLine()) != null) {
                System.out.println(rspLine);
                rspList.add(rspLine);
            }
            proc.waitFor();
            in.close();
            out.close();
            proc.destroy();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rspList;
    }
}
