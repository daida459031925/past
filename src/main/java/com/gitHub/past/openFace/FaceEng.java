package com.gitHub.past.openFace;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.ErrorInfo;

import java.util.Optional;
import java.util.function.Function;

/**
 * 人脸识别实体类
 */
public class FaceEng {

    private String appId = "GHoPzfNh9BoGMusuSh9hcC3ZPJUkxm7fCpLCg9R3Fyid";
    private String sdkKey = "5emKMboq4F3catQsqGj2kYZNqMAvCDG6CHfi6LQ8xssG";
    private String libPath;

    private FaceEngine faceEngine;

    //初始化人脸识别引擎
    public FaceEng(String appId, String sdkKey, String libPath) {
        this.appId = appId;
        this.sdkKey = sdkKey;
        this.libPath = libPath;
        //需要指定上传位置
        this.faceEngine = new FaceEngine(Optional.ofNullable(libPath).map(String::trim).orElse(""));
    }

    //激活引擎
    public int getActiveOnline(){
        return faceEngine.activeOnline(appId, sdkKey);
    }

    //查看激活文件
    public int getActiveFileInfo(){
        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        return faceEngine.getActiveFileInfo(activeFileInfo);
    }

    //查看激活状态
    public void get(){
        int activeOnline = getActiveOnline();
        int activeFileInfo = getActiveFileInfo();
        Function<Integer,Boolean> function = (i)->
                i != ErrorInfo.MOK.getValue()
                && i != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue();
        if (function.apply(activeOnline) || function.apply(activeFileInfo)){

        }
    }
}
