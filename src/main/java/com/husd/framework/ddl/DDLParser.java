package com.husd.framework.ddl;

import com.husd.framework.code.DDLColumn;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 把DDL建表语句解析为明细数据
 * <p>
 * 注意： 不要开始就想弄个大而全的代码，先做个最简单的。
 */
public class DDLParser {

    public static DDL parseDDL(String sql) {

        //
        DDL ddl = new DDL();
        List<DDLColumn> columnList = new ArrayList<>();
        DDLColumn ddlColumn = new DDLColumn();
        int colCount = 0;

        String[] array = StringUtils.split(sql, " ");
        // 前一步的关键状态
        KeyStep keyStep = KeyStep.create_table;
        // 当前状态到哪一步了
        TokenType pre = TokenType.INIT;
        for (int i = 0; i < array.length; i++) {
            TokenType current = TokenType.getTokenTypeName(array, i);
            String s = array[i];
            s = TokenType.preDeal(s);
            String err = createErrMsg(array, i);
            switch (current) {
                case INIT:
                    break;
                case CREATE:
                    if (pre != TokenType.INIT) {
                        err(err, s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep, KeyStep.create_table);
                    break;
                case TEMPORARY:
                    if (pre != TokenType.CREATE) {
                        err(err, s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep, KeyStep.create_table);
                    break;
                case TABLE:
                    if (pre != TokenType.CREATE && pre != TokenType.TEMPORARY) {
                        err(err, s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep, KeyStep.create_table);
                    break;
                case IF_NOT_EXISTS:
                    if (pre != TokenType.TABLE) {
                        err(err, s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep, KeyStep.create_table);
                    break;
                case VAR:
                    // var有各种情况，需要根据前面的来判断
                    if (keyStep == KeyStep.create_table) {
                        if (pre == TokenType.TABLE || pre == TokenType.IF_NOT_EXISTS) {
                            current = TokenType.TABLE_NAME;
                            ddl.setTableName(s);
                        } else {
                            err(err, s);
                        }
                    } else if (keyStep == KeyStep.create_col) {
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
                    } else if (keyStep == KeyStep.primary_key) {

                    } else if (keyStep == KeyStep.inx) {

                    } else if (keyStep == KeyStep.table_opt) {
                        if (pre == TokenType.COMMENT) {
                            current = TokenType.COMMENT_VAL;
                            ddl.setTableComment(s);
                        }
                    }
                    break;
                case LEFT_P:
                    if (keyStep == KeyStep.create_table) {
                        if (pre != TokenType.TABLE_NAME) {
                            err(err, s);
                        }
                        keyStep = KeyStep.nextKeyStep(keyStep, KeyStep.create_col);
                    } else if (keyStep == KeyStep.create_col) {
                        if (pre != TokenType.COLUMN_TYPE) {
                            err(err, s);
                        }
                    } else if (keyStep == KeyStep.primary_key) {

                    } else if (keyStep == KeyStep.inx) {

                    } else if (keyStep == KeyStep.table_opt) {

                    }
                    break;
                case RIGHT_P:
                    if (keyStep != KeyStep.inx && keyStep != KeyStep.primary_key) {
                        err(err, s);
                    }
                    //右括号之后进入table_opt
                    keyStep = KeyStep.nextKeyStep(keyStep, KeyStep.table_opt);
                    break;
                case AUTO_INCREMENT:
                    if (colCount != 0) {
                        err(err, s);
                    }
                    // 主键
                    ddlColumn.setId(true);
                    break;
                case COLUMN_TYPE:
                    ddlColumn.setColumnType(s);
                    break;
                case NULL:
                    if (pre != TokenType.COLUMN_TYPE && pre != TokenType.DEFAULT) {
                        err(err, s);
                    }
                    break;
                case NOT_NULL:
                    if (pre != TokenType.COLUMN_TYPE) {
                        err(err, s);
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
                    if (keyStep == KeyStep.create_col) {
                        //
                        if (pre != TokenType.COMMENT) {
                            err(err, s);
                        }
                    }
                    if (keyStep == KeyStep.table_opt) {
                        //
                        if (pre != TokenType.COMMENT) {
                            err(err, s);
                        }
                        ddl.setTableComment(s);
                    }
                    break;
                case TABLE_COMMENT:
                    if (keyStep == KeyStep.table_opt) {
                        int inx2 = s.indexOf("=");
                        ddl.setTableComment(s.substring(inx2 + 1));
                    }
                    break;
                case COMMA:
                    if (keyStep == KeyStep.create_col) {
                        //有逗号，说明当前列就结束了，进入下一个列
                        colCount++;
                        columnList.add(ddlColumn);
                        ddlColumn = new DDLColumn();
                    }
                    if (keyStep == KeyStep.primary_key) {
                        keyStep = KeyStep.nextKeyStep(keyStep, KeyStep.inx);
                    }
                    if (keyStep == KeyStep.inx) {

                    }
                    //先不管错误的逗号这种情况。
                    //err(s);
                    break;
                case PRIMARY_KEY:
                    // primary key前面一定是逗号就行了。
                    if (pre != TokenType.COMMA) {
                        err(err, s);
                    }
                    keyStep = KeyStep.nextKeyStep(keyStep, KeyStep.primary_key);
                    break;
                case DISTRIBUTED_BY_HASH:
                    if (pre != TokenType.RIGHT_P) {
                        err(err, s);
                    }
                    break;
                case PARTITION_BY:
                    break;
            }
            i += (current.getStep() - 1);
            pre = current;
        }
        ddl.setColumnList(columnList);
        if (AutoCodeUtil.DEBUG) {
            System.out.println("生成DDL对象： " + ddl.toString());
        }
        return ddl;
    }

    private static String createErrMsg(String[] array, int i) {

        int start = i - 5;
        int end = i + 5;
        start = Math.max(0, start);
        end = Math.min(end, array.length);
        StringBuilder sb = new StringBuilder();
        for (int j = start; j < end; j++) {
            sb.append(array[j]);
        }
        return sb.toString();
    }

    public static void err(String msg, String s) {

        throw new RuntimeException("错误的 " + s + " 前后内容： " + msg);
    }

}
