package com.husd.framework.lexical;

/**
 * @author hushengdong
 */
public class SimpleResult {

    private String var;
    private String ge;
    private String val;

    public SimpleResult(String var, String ge, String val) {
        this.var = var;
        this.ge = ge;
        this.val = val;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getGe() {
        return ge;
    }

    public void setGe(String ge) {
        this.ge = ge;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return var + ge + val;
    }
}
