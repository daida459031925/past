package com.gitHub.past.javaLinkLinux;

import java.io.*;
import java.util.*;

public class command {

    private Process p;
    private InputStream is;
    private OutputStream os;

    private BufferedWriter bw;
    private BufferedReader br;

    private ProcessBuilder pb;
    private InputStream stderr;


    public command() {}


    public void settest(String args) {
        try {
            //
            // pb = new processbuilder(args);
            // pb.redirecterrorstream(true);
            // p = pb.start();
            p = Runtime.getRuntime().exec(args);
            os = p.getOutputStream();
            is = p.getInputStream();
            stderr = p.getErrorStream();
        } catch (IOException e) {
            // todo auto-generated catch block
            System.err.println(e.getMessage());
        }
    }


    public void writetest(String test) {
        try {
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(test);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String readtest() {
        StringBuffer sb = new StringBuffer();
        br = new BufferedReader(new InputStreamReader(is));
        String buffer = null;
        try {
            while ((buffer = br.readLine()) != null) {
                sb.append(buffer + "\n");
            }
            System.out.println(p.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public LinkedList<String> dotest(LinkedList<String> args) {
        LinkedList<String> list = new LinkedList<String>();
        for (String s : args) {
            writetest(s);
            list.add(readtest());
        }
        return list;
    }


    public static void main(String[] args) {
        command cmd = new command();
        cmd.settest("cd /");
        System.out.println(cmd.readtest());
        cmd.settest("cd /home");
        cmd.settest("ls");
        System.out.println(cmd.readtest());
    }
}
