package com.gitHub.past.characterEncoding;

import java.util.Base64;
import java.util.function.Function;

public class Base64Util {

    //编码
    public static Function<String,String> bianma = (str)-> new String(Base64.getEncoder().encode(str.getBytes()));

    //解码
    public static Function<String,String> jiema = (str)-> new String(Base64.getDecoder().decode(str));
}
