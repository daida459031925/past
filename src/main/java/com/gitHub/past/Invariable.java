package com.gitHub.past;

public enum Invariable {
    /**
     * 枚举类型这个必须是一次性创建完成注意 第一个，第二个 每个之间用，号
     * 而返还值最重要的是toString
     */
    UTF_8("UTF-8"),
    ISO_8859_1("ISO-8859-1"),
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
    YYYYMMDD("yyyyMMdd")

    ;


    private final Object value;

    private Invariable(String value) {
        this.value = value;
    }
    private Invariable(Integer value) {
        this.value = value;
    }




    /**
     * 上面重写之类的并不重要重要的是在重写tostring上
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
