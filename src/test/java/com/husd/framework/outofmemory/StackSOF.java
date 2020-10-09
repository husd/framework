package com.husd.framework.outofmemory;

/**
 * -Xss128k
 *
 * @author hushengdong
 */
public class StackSOF {

    public static void main(String[] args) {

        StackSOF stackSOF = new StackSOF();
        stackSOF.stack();
    }

    private void stack() {
        stack();
    }
}
