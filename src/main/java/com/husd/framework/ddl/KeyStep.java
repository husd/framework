package com.husd.framework.ddl;

public enum KeyStep {

    create_table(0),
    create_col(1),
    primary_key(2),
    inx(3),
    table_opt(4);

    private int step;

    KeyStep(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public static KeyStep nextKeyStep(KeyStep curr, KeyStep next) {

        if(next == create_table && curr == create_table) {
            return next;
        }
        if(next.getStep() - curr.getStep() == 1) {
            return next;
        }
        throw new RuntimeException("关键状态变化不正确 curr:" + curr + " next:" + next);
    }
    
}
