package com.husd.framework.fsm;

import java.util.Random;

/**
 * 有限状态机
 * <p>
 * 是表示有限个状态以及在这些状态之间的转移和动作等行为的数学模型。
 * <p>
 * 这个类，演示了有结束状态的情况下，怎么样设计FSM
 *
 * @author hushengdong
 */
public class FSMWorkflow {

    public static void main(String[] args) {

        FSMWorkflow fsmWorkflow = new FSMWorkflow();
        fsmWorkflow.fsm(1);
    }

    //假设我们有一段流程 step1 step2 step3 step4 step5 每次可能从 step1 step2 step3进来
    // 这里我们降低复杂度，每次都从step1进来
    //每个阶段，执行完成之后，可以跳转到不同的分支去，例如：
    // step1成功之后，调用step2
    // step1失败之后，调用step4
    // step2成功，调用step3 失败则跳转 step4
    // step3成功，则跳step5 失败则跳转 step4

    //在这里，我们把step4最为失败的最终节点状态 step5作为成功的最终状态

    //可能还会更复杂，但是会有一个或者几个终点的状态
    //该怎么做呢？ 最简单的方法是写if else 看normal方法
    public void fsm(int start) {

        //定义状态
        boolean end = false;
        int status = start;
        while (!end) {
            switch (status) {
                case 1:
                    //在这里可以根据需要，把状态修改为不同的值，而且只关心这1个就可以了
                    //复杂的if else消失了 流程怎么跳转也很清晰 ，从哪个状态跳到哪个状态
                    // 查看对应的case即可
                    status = step1() ? 2 : 4;
                    break;
                case 2:
                    status = step2() ? 3 : 4;
                    break;
                case 3:
                    status = step3() ? 5 : 4;
                    break;
                case 4:
                    step4();
                    end = true;
                    break;
                case 5:
                    step5();
                    end = true;
                    break;
                default:
                    break;
            }
        }
    }

    public void normal1() {

        //这段代码，会特别难以维护，各种分支
        //各种逻辑，一旦需要调整状态的变化，就更加复杂了
        if (step1()) {
            if (step2()) {
                if (step3()) {
                    step5();
                } else {
                    step4();
                }
            } else {
                step4();
            }
        } else {
            step4();
        }
    }

    //对normal的改良，但是本质是一样的
    //就是强编码，解决问题
    public void normal2() {

        //这段代码，会特别难以维护，各种分支
        //各种逻辑，一旦需要调整状态的变化，就更加复杂了
        //这里仅仅在流程比较简单的时候，才能这么写
        //一旦中间有条件分支，例如step1在执行成功之后，
        //可能会执行step2 ，也可能会执行step3，就不能这么写了
        //本质上和上一个方法normal1()没有任何区别
        if (step1() && step2() && step3()) {
            step5();
        } else {
            step4();
        }
    }

    private boolean step1() {


        boolean res = new Random().nextInt(10) % 2 == 0;
        System.out.println("step 1:" + res);
        return res;
    }

    private boolean step2() {
        boolean res = new Random().nextInt(10) % 2 == 0;
        System.out.println("step 2:" + res);
        return res;
    }

    private boolean step3() {

        boolean res = new Random().nextInt(10) % 2 == 0;
        System.out.println("step 3:" + res);
        return res;
    }

    private boolean step4() {

        boolean res = new Random().nextInt(10) % 2 == 0;
        System.out.println("step 4:" + res);
        return res;
    }

    private boolean step5() {

        boolean res = new Random().nextInt(10) % 2 == 0;
        System.out.println("step 5:" + res);
        return res;
    }
}
