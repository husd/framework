package com.husd.framework.fsm;

/**
 * 有限状态机
 * <p>
 * 是表示有限个状态以及在这些状态之间的转移和动作等行为的数学模型。
 * <p>
 * 这个节点，演示了没有结束状态，是1个循环状态的情况下的代码
 *
 * @author hushengdong
 */
public class FSMDoorStatus {

    //简单的使用switch来实现 除了switch之后，还可以使用各个状态之间的白名单来实现
    //例如维护1个map map的key是from，map的value是可以从from变化到的状态的集合
    //Map<unlock,Set<>> Set的内容是 locked open，表示从unlock可以转变状态为 locked open
    //switch的性能，在case的数量很少时，性能会特别好
    //也可以考虑使用数组

    //假设1个门的状态有 open close locked unlock 这4种状态，锁着的门是不能打开的
    //状态的变化如下：
    //open -> close
    //close -> locked
    //locked -> unlock
    //unlock -> locked open
    public boolean doorFsm(DoorStatus from, DoorStatus to, String key) {

        //简单的理解这个状态，不考虑多线程修改
        switch (from.getStatus()) {

            case 0:
                if (DoorStatus.CLOSE == to) return true;
                break;
            case 1:
                if (DoorStatus.LOCKED == to) return true;
                break;
            case 2:
                if (DoorStatus.UNLOCK == to && isKeyWork(key)) return true;
                break;
            case 3:
                if (DoorStatus.LOCKED == to || DoorStatus.OPEN == to) return true;
                break;
            default:
                return false;
        }
        return false;
    }

    private boolean isKeyWork(String key) {

        return true;
    }
}
