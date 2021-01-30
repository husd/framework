package com.husd.framework.code;

/**
 * @author hushengdong
 */
public interface JavaAutoCode {

    /**
     * 生成实体类
     *
     * @param javaFile
     * @return
     */
    StringBuilder generateDto(JavaFile javaFile);

    /**
     * 按模版，生成mybatis的接口
     */
    String generateMybatisJava(JavaFile javaFile);

    /**
     * 按模版，生成mybatis的xml
     */
    String generateMybatisXml(JavaFile javaFile);
}
