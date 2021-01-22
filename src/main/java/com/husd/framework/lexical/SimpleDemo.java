package com.husd.framework.lexical;

import java.util.ArrayList;
import java.util.List;

/**
 * 词法解析，先解析一个最简单的
 * a=10
 * <p>
 * var ge val
 * <p>
 * var: a
 * ge:  =
 * val: 10
 * <p>
 * 然后要来个状态机
 * <p>
 * 这种是最简单的分析。
 */
public class SimpleDemo {

    public static void main(String[] args) {

        String str = "a1 = 23 bc45   = 6 y89";
        SimpleDemo simpleDemo = new SimpleDemo();
        List<SimpleResult> list = simpleDemo.start(str);
        for (SimpleResult simpleResult : list) {
            System.out.println(simpleResult.toString());
        }
    }

    private List<SimpleResult> start(String str) {

        List<SimpleResult> resultList = new ArrayList<>();
        StringBuilder var = new StringBuilder();
        StringBuilder val = new StringBuilder();
        StringBuilder ge = new StringBuilder();
        SymbolEnum symbolEnum = SymbolEnum.INIT;
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            SymbolEnum temp = getSymbolEnum(c);
            switch (symbolEnum) {
                case INIT:
                    // INIT之后，只能是 INIT 或者 VAR 或者 空格
                    if (temp == SymbolEnum.INIT) {
                    } else if (temp == SymbolEnum.VAR) {
                        var.append(c);
                    } else if (temp == SymbolEnum.SPACE) {
                        //什么都不干
                    } else {
                        throw new RuntimeException("char " + c + " 不该出现");
                    }
                    break;
                case VAR:
                    //VAR之后遇到GE才发生变化 VAR之后可以是 VAR GE VAL
                    if (temp == SymbolEnum.VAR) {
                        var.append(c);
                    } else if (temp == SymbolEnum.GE) {
                        ge.append(c);
                    } else if (temp == SymbolEnum.VAL) {
                        var.append(c);
                        //修正temp，这个时候当前状态，还是VAR
                        temp = SymbolEnum.VAR;
                    } else if (temp == SymbolEnum.SPACE) {
                        // 忽略，但是其实VAR不能再增加了。
                    }
                    break;
                case GE:
                    //GE之后只能是VAL 或者 INIT
                    if (temp == SymbolEnum.VAL) {
                        val.append(c);
                    } else if (temp == SymbolEnum.INIT) {
                        //什么都不管，等第一个val即可。
                    } else if (temp == SymbolEnum.SPACE) {
                        // 什么都不做
                    } else {
                        throw new RuntimeException("char :" + c + " 不该出现");
                    }
                    break;
                case VAL:
                    // VAL 之后，可以是INIT和VAL
                    if (temp == SymbolEnum.VAL) {
                        val.append(c);
                    } else if (temp == SymbolEnum.INIT || temp == SymbolEnum.SPACE) {
                        // 结束了，可以加List了
                        resultList.add(new SimpleResult(var.toString(), ge.toString(), val.toString()));
                        var = new StringBuilder();
                        ge = new StringBuilder();
                        val = new StringBuilder();
                        // 结束了之后，要重制为INIT
                        temp = SymbolEnum.INIT;
                    } else {
                        throw new RuntimeException("char :" + c + " 不该出现");
                    }
                    break;
            }
            if (temp != SymbolEnum.SPACE) {
                symbolEnum = temp;
            }
        }
        if (symbolEnum == SymbolEnum.VAL && validResult(var, ge, val)) {
            resultList.add(new SimpleResult(var.toString(), ge.toString(), val.toString()));
        }
        //这里按说要检查下 最后一个状态是结束 或者 INIT
        return resultList;
    }

    private boolean validResult(StringBuilder var, StringBuilder ge, StringBuilder val) {

        return var.length() > 0 && ge.length() > 0 && val.length() > 0;
    }


    private SymbolEnum getSymbolEnum(char c) {

        if (c == '=') {
            return SymbolEnum.GE;
        }
        //VAL 有可能是VAL 或者 VAR 这个要结合前一个状态判断
        // 例如 a10  =  10 这里比较简单，根据前置的内容，就能判断是否有效。
        // 这里先简单假设，变量命不会出现a10这样的
        if (Character.isLetter(c)) {
            return SymbolEnum.VAR;
        }
        if (Character.isDigit(c)) {
            return SymbolEnum.VAL;
        }
        if (Character.isSpaceChar(c)) {
            return SymbolEnum.SPACE;
        }
        return SymbolEnum.INIT;
    }

    private boolean isGe(char c) {

        return c == '=';
    }
}
