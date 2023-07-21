package com.gitHub.past.office;

import cn.hutool.core.io.LineHandler;
import cn.hutool.core.lang.func.VoidFunc0;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.gitHub.past.file.FileUtil;
import com.gitHub.past.log.Log;
import com.gitHub.past.web.Result;

import java.io.*;
import java.util.function.Consumer;


/**
 * word转pdf需要将fonts放入linux
 * C:\Windows\fonts =》 /usr/share/Fonts
 */
public class Word2PdfAsposeUtil {

    private static byte[] bytes = new byte[]{
         60,63,120,109,108,32,118,101,114,115,105,111,110,61,34,49,46,48,34,32,101,110,99,111,100,105,110,103,61,34,85,84,70,45,56,34,32,63,62,60,76,105,99,101,110,115,101,62,32,32,32,32,60,68,97,116,97,62,32,32,32,32,32,32
        ,32,32,60,80,114,111,100,117,99,116,115,62,32,32,32,32,32,32,32,32,32,32,32,32,60,80,114,111,100,117,99,116,62,65,115,112,111,115,101,46,84,111,116,97,108,32,102,111,114,32,74,97,118,97,60,47,80,114,111,100,117,99,116,62,32,32,32,32,32,32,32,32,32,32,32,32,60,80,114,111,100,117,99,116,62,65,115,112,111,115,101,46,87,111,114,100,115,32,102,111,114,32,74
        ,97,118,97,60,47,80,114,111,100,117,99,116,62,32,32,32,32,32,32,32,32,60,47,80,114,111,100,117,99,116,115,62,32,32,32,32,32,32,32,32,60,69,100,105,116,105,111,110,84,121,112,101,62,69,110,116,101,114,112,114,105,115,101,60,47,69,100,105,116,105,111,110,84,121,112,101,62,32,32,32,32,32,32,32,32,60,83,117,98,115,99
        ,114,105,112,116,105,111,110,69,120,112,105,114,121,62,50,48,57,57,49,50,51,49,60,47,83,117,98,115,99,114,105,112,116,105,111,110,69,120,112,105,114,121,62,32,32,32,32,32,32,32,32,60,76,105,99,101,110,115,101,69,120,112,105,114,121,62,50,48,57,57,49,50,51,49,60,47,76,105,99,101,110,115,101,69,120,112,105,114,121,62,32,32,32,32,32,32,32,32,60,83,101,114,105,97,108,78,117,109,98,101,114,62,56,98,102,101,49,57,56,99,45,55,102,48,99
        ,45,52,101,102,56,45,56,102,102,48,45,97,99,99,51,50,51,55,98,102,48,100,55,60,47,83,101,114,105,97,108,78,117,109,98,101,114,62,32,32,32,32,60,47,68,97,116,97,62,32,32,32,32,60,83,105,103,110,97,116,117,114,101,62,115,78,76,76,75,71,77,85,100,70,48,114,56,79,49,107,75,105,108,87,65,71,100,103,102,115,50,66,118,74,98,47,50,88,112,56,112,53,105,117,68,86,102,90,88,109,104,112
        ,112,111,43,100,48,82,97,110,49,80,57,84,75,100,106,86,52,65,66,119,65,103,75,88,120,74,51,106,99,81,84,113,69,47,50,73,82,102,113,119,110,80,102,56,105,116,78,56,97,70,90,108,86,51,84,74,80,89,101,68,51,121,87,69,55,73,84,53,53,71,122,54,69,105,106,85,112,67,55,97,75,101,111,111,104,84,98,52,119,50,102,112,111,120,53,56,119,87,111,70,51,83,78,112,54,115,75,54,106
        ,68,102,105,65,85,71,69,72,89,74,57,112,106,85,61,60,47,83,105,103,110,97,116,117,114,101,62,60,47,76,105,99,101,110,115,101,62
    };

    public static boolean getLicense() {
        boolean result = false;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            License aposeLic = new License();
            aposeLic.setLicense(byteArrayInputStream);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean doc2pdf(String inPath, String outPath) {
        Result<Object> result = Result.OK().setFunction((obj) -> {
            // 验证License 若不验证则转化出的pdf文档会有水印产生
            Boolean b = (Boolean) obj;
            if (!b) {
                return false;
            }
            Log.info((o) -> {
                // 新建一个空白pdf文档
                try (FileOutputStream os = new FileOutputStream(outPath);) {
                    Document doc = new Document(inPath); // Address是将要被转化的word文档
                    doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
                    os.flush();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return true;
        }, "转换异常").exec(!getLicense());

        return result.getState().equals(Result.OK);
    }
}