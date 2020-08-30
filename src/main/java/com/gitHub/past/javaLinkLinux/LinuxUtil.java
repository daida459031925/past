package com.gitHub.past.javaLinkLinux;

import com.gitHub.past.Invariable;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LinuxUtil {

    private Process proc = null;

    private BufferedReader in = null;

    private PrintWriter out = null;

    public Process getProc() {
        return proc;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public LinuxUtil() {
        try {
            Runtime run = Runtime.getRuntime();
            proc = run.exec(Invariable.BIN_BASH.toString(), null, null);
            in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建连接对象Process失败");
        }
    }

    public List<String> execConversation(String commands) throws Exception{
        return execConversation(Arrays.asList(commands));
    }

    /**
     * linux直接访问当前部署的徐桐的linux
     * 必须在关闭的时候执行close();
     *
     * @param commands linux命令
     * @return
     */
    public List<String> execConversation(List<String> commands) throws Exception{
        List<String> rspList = new ArrayList<String>();
        try {
            if (commands == null) {
                throw new RuntimeException("命令不能为null");
            }

            commands = commands.stream().filter(e -> Objects.nonNull(e) && !e.trim().isEmpty()).collect(Collectors.toList());

            if (commands.isEmpty()) {
                throw new RuntimeException("命令不能为空");
            }

            for (String line : commands) {
                out.println(line);
            }

            String rspLine = "";
            try {
                while ((rspLine = in.readLine()) != null) {
                    Logger.getGlobal().info(rspLine);
                    rspList.add(rspLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            return rspList;
        }

    }

    public void close() {
        Optional.ofNullable(proc).ifPresent(e -> {
            try {
                e.waitFor();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        Optional.ofNullable(in).ifPresent(e -> {
            try {
                e.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Optional.ofNullable(out).ifPresent(e -> {
            e.close();
        });

        Optional.ofNullable(proc).ifPresent(e -> {
            e.destroy();
        });
    }

    public void exit() throws Exception{
        // 这个命令必须执行，否则in流不结束。
        execConversation(Invariable.EXIT.toString());
    }
}
