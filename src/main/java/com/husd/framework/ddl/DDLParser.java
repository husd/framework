package com.husd.framework.ddl;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 把DDL建表语句解析为明细数据
 *
 * 注意： 不要开始就想弄个大而全的代码，先做个最简单的。
 */
public class DDLParser {

    public static DDL parseDDL(String sql) {

        //
        DDL ddl = new DDL();
        List<DDLColumn> columnList = new ArrayList<>();
        DDLColumn ddlColumn = new DDLColumn();
        int colCount = 0;

        String [] array = StringUtils.split(sql," ");
        // 前一步的关键状态
        KeyStep keyStep = KeyStep.create_table;
        // 当前状态到哪一步了
        TokenType pre = TokenType.INIT;
        for(int i = 0;i<array.length;i++) {
            TokenType current = TokenType.getTokenTypeName(array,i);
            String s = array[i];
            s = TokenType.preDeal(s);
            switch (current) {
                case INIT:
                    break;
                case CREATE:
                    if(pre != TokenType.INIT) {
                        err(s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep,KeyStep.create_table);
                    break;
                case TEMPORARY:
                    if(pre != TokenType.CREATE) {
                        err(s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep,KeyStep.create_table);
                    break;
                case TABLE:
                    if(pre != TokenType.CREATE && pre != TokenType.TEMPORARY) {
                        err(s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep,KeyStep.create_table);
                    break;
                case IF_NOT_EXISTS:
                    if(pre != TokenType.TABLE) {
                        err(s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep,KeyStep.create_table);
                    break;
                case VAR:
                    // var有各种情况，需要根据前面的来判断
                    if(keyStep == KeyStep.create_table) {
                        if(pre == TokenType.TABLE || pre == TokenType.IF_NOT_EXISTS) {
                            current = TokenType.TABLE_NAME;
                            ddl.setTableName(s);
                        } else {
                            err(s);
                        }
                    } else if(keyStep == KeyStep.create_col) {
                        if (pre == TokenType.COMMENT) {
                            current = TokenType.COMMENT_VAL;
                            ddlColumn.setComment(s);
                        } else if (pre == TokenType.DEFAULT) {
                            current = TokenType.DEFAULT_VAL;
                            ddlColumn.setDefaultVal(s);
                        } else {
                            TokenType nextToken = TokenType.getTokenTypeName(array, i + 1);
                            if (nextToken == TokenType.COLUMN_TYPE) {
                                current = TokenType.COL_NAME;
                                ddlColumn.setColumnName(s);
                            }
                        }
                    } else if(keyStep == KeyStep.primary_key) {

                    } else if(keyStep == KeyStep.inx) {

                    } else if(keyStep == KeyStep.table_opt) {
                        if(pre == TokenType.COMMENT) {
                            current = TokenType.COMMENT_VAL;
                            ddl.setTableComment(s);
                        }
                    }
                    break;
                case LEFT_P:
                    if(keyStep == KeyStep.create_table) {
                        if(pre != TokenType.TABLE_NAME) {
                            err(s);
                        }
                        keyStep = KeyStep.nextKeyStep(keyStep,KeyStep.create_col);
                    } else if(keyStep == KeyStep.create_col) {
                        if(pre != TokenType.COLUMN_TYPE) {
                            err(s);
                        }
                    } else if(keyStep == KeyStep.primary_key) {

                    } else if(keyStep == KeyStep.inx) {

                    } else if(keyStep == KeyStep.table_opt) {

                    }
                    break;
                case RIGHT_P:
                    if(keyStep != KeyStep.inx) {
                        err(s);
                    }
                    //右括号之后进入table_opt
                    keyStep = KeyStep.nextKeyStep(keyStep,KeyStep.table_opt);
                    break;
                case AUTO_INCREMENT:
                    if(colCount != 0) {
                        err(s);
                    }
                    // 主键
                    ddlColumn.setId(true);
                    break;
                case COLUMN_TYPE:
                    ddlColumn.setColumnType(s);
                    break;
                case NULL:
                    if (pre != TokenType.COLUMN_TYPE) {
                        err(s);
                    }
                    break;
                case NOT_NULL:
                    if (pre != TokenType.COLUMN_TYPE) {
                        err(s);
                    }
                    ddlColumn.setNotNull(true);
                    break;
                case DEFAULT:
                    break;
                case COMMENT:
                    if (keyStep == KeyStep.create_col) {
                        //
                    }
                    if (keyStep == KeyStep.table_opt) {
                        //
                    }
                    break;
                case COMMENT_VAL:
                    if(keyStep == KeyStep.create_col) {
                        //
                        if(pre != TokenType.COMMENT) {
                            err(s);
                        }
                    }
                    if(keyStep == KeyStep.table_opt) {
                        //
                        if(pre != TokenType.COMMENT) {
                            err(s);
                        }
                    }
                    break;
                case COMMA:
                    if(keyStep == KeyStep.create_col) {
                        //有逗号，说明当前列就结束了，进入下一个列
                        colCount++;
                        columnList.add(ddlColumn);
                        ddlColumn = new DDLColumn();
                    }
                    if(keyStep == KeyStep.primary_key) {
                        keyStep = KeyStep.nextKeyStep(keyStep,KeyStep.inx);
                    }
                    if(keyStep == KeyStep.inx) {

                    }
                    //先不管错误的逗号这种情况。
                    //err(s);
                    break;
                case PRIMARY_KEY:
                    // primary key前面一定是逗号就行了。
                    if(pre != TokenType.COMMA) {
                        err(s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep,KeyStep.primary_key);
                    break;
                case DISTRIBUTED_BY_HASH:
                    if(pre != TokenType.RIGHT_P) {
                        err(s);
                    }
                    break;
                case PARTITION_BY:
                    break;
            }
            i += (current.getStep() - 1);
            pre = current;
        }
        ddl.setColumnList(columnList);
        if(AutoCodeUtil.DEBUG) {
            System.out.println("生成DDL对象： " + ddl.toString());
        }
        return ddl;
    }

    public static void err(String msg) {

        throw new RuntimeException("错误的 " + msg);
    }

}
