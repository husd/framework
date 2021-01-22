package com.husd.framework.lexical;

/**
 * @author hushengdong
 * <p>
 * 这个是词法解析里面的枚举，定义了词法的类型
 * <p>
 * 最简单的 a = 10
 * var ge val
 * a   =  10
 * <p>
 * 还有一个初始化的状态
 */
public enum SymbolEnum {
    INIT,
    VAR, // 变量
    GE,  // =
    VAL,  // 数值
    SPACE // 空格
}
