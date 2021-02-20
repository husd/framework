//package com.baojia.sms.common;
//
//import com.baojia.sms.config.CacheName;
//import com.baojia.sms.config.SendSmsStatus;
//import com.baojia.sms.config.SmsConstant;
//import com.baojia.sms.dao.SmsMapper;
//import com.baojia.sms.dto.SmsChannelEntry;
//import com.baojia.sms.dto.SmsLog;
//import com.baojia.sms.service.SmsCache;
//import com.guodu.comm.gdpp.message.GDPPSubmitMessage;
//import com.guodu.util.LongHead;
//import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
//import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
//import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
//import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;
//import com.huawei.insa2.util.Args;
//import com.huawei.smproxy.SMProxy;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.math.BigInteger;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by husd on 2015/2/3.
// */
//public class HuaWeiSmsProxy extends SMProxy {
//
//    private Logger logger = Logger.getLogger(HuaWeiSmsProxy.class);
//
//    private String sign;
//
//    private SmsCache smsCache;
//
//    private SmsMapper smsMapper;
//
//    private CacheName cacheName;
//
//    private SmsChannelEntry smsChannelEntry;
//
//    public HuaWeiSmsProxy(Map args) {
//        super(args);
//    }
//
//    public HuaWeiSmsProxy(Args args) {
//        super(args);
//    }
//
//    //收到状态报告或者上行下行短信
//    @Override
//    public CMPPMessage onDeliver(CMPPDeliverMessage msg) {
//        //TODO自己的逻辑代码
//        //1是状态报告 0是上行消息
//        if (msg.getRegisteredDeliver() == 1) {
//            String msgId = String.valueOf(HttpUtil.byte2Long(msg.getStatusMsgId()));
//            String smsLogId = (String) smsCache.getFromCache(cacheName.getMsgIdSmsLogIdCache(), smsChannelEntry.getChannelCode() + msgId);
//            logger.info("返回的缓存key:" + smsChannelEntry.getChannelCode() + msgId);
//            if (StringUtils.isEmpty(smsLogId)) {
//                return super.onDeliver(msg);
//            }
//            SmsLog smsLog = new SmsLog();
//            smsLog.setId(new BigInteger(smsLogId));
//            boolean success = StringUtils.equals(msg.getStat(), SendSmsStatus.GDPP_SUCCESS_CODE);
//            smsLog.setSendStatus(success ? SendSmsStatus.SUCCESS : SendSmsStatus.FAIL);
//            smsLog.setChannelResult(msg.getStat());
//            smsLog.setReceiveTime(new Timestamp(msg.getDoneTime().getTime()));
//            smsMapper.updateUserReceiveTime(smsLog);
//            logger.info(String.format("通道[%s]的状态回调，MsgId是[%s]，对应的sms_log的id是[%s]，返回的报文是[%s]", smsChannelEntry.getName(), msgId, smsLog.getId(), msg.getStat()));
//        } else {
//            //
//        }
//        return super.onDeliver(msg);
//    }
//
//    /**
//     * 发送消息，阻塞直到收到响应或超时。
//     * 返回为收到的消息
//     *
//     * @throws IOException 超时或通信异常。
//     */
//    @Override
//    public CMPPMessage send(CMPPMessage message) throws IOException {
//        return super.send(message);
//    }
//
//    public CMPPSubmitRepMessage sendSms(String mobile, String message, String charSet) {
//        int smsLength = 0;
//        try {
//            smsLength = message.getBytes(charSet).length;
//        } catch (UnsupportedEncodingException e) {
//            logger.error("UnsupportedEncodingException", e);
//        }
////        logger.info("字节长度:" + smsLength + "字符长度:" + message.length());
//        if (smsLength < SmsConstant.SMS_SHORT_LENGTH) {
//            return sendShortSms(mobile, message, charSet);
//        } else {
//            return sendLongSms(mobile, message);
//        }
//    }
//
//    /**
//     * 连接终止的处理，由API使用者实现
//     * SMC连接终止后，需要执行动作的接口
//     */
//    @Override
//    public void onTerminate() {
//        logger.error("连接断开");
//        super.onTerminate();
//    }
//
//    /**
//     * 终止连接。调用之后连接将永久不可用。
//     */
//    @Override
//    public void close() {
//        super.close();
//    }
//
//    private String[] splitMsg(String Text, int len) {
//        int iMax = len;
//        String eStr = "";
//        String[] str;
//        if (Text.length() <= iMax) {
//            str = new String[1];
//            str[0] = Text;
//        } else {
//            int m = iMax - eStr.length();
//            int c = (Text.length() + m - 1) / m;
//            str = new String[c];
//            for (int i = 0; i < c; i++) {
//                if (i == c - 1) {
//                    str[i] = Text.substring(i * m);
//                } else {
//                    str[i] = (Text.substring(i * m, i * m + m) + eStr);
//                }
//            }
//        }
//        return str;
//    }
//
//    private CMPPSubmitRepMessage sendShortSms(String mobile, String content, String charSet) {
//        logger.info("发送短短信开始 ");
//        String[] mobiles = {mobile};
//        byte[] contents = null;
//        try {
//            contents = content.getBytes(charSet);
//        } catch (UnsupportedEncodingException e) {
//            logger.error("发送短信错误，编码不支持", e);
//            return null;
//        }
//        /*具体参数含义请参见发送方法体内*/
//        int pk_Total = 1;
//        int pk_Number = 1;
//        int registered_Delivery = 1;
//        int msg_Level = 1;
//        String service_ID = "";
//        int fee_UserType = 2;
//        String fee_Terminal_Id = "";
//        int tp_PID = 0;
//        int tp_UDHI = 0;
//        int msg_fmt = 15;
//        String msg_Src = smsChannelEntry.getSpId();
//        String fee_Type = "02";
//        String fee_Code = "000";
//        Date valid_Time = null;
//        Date at_Time = null;
//        String src_Terminal_Id = "";
//        String reserve = "";
//        CMPPSubmitMessage m1 = new CMPPSubmitMessage(pk_Total,                        //pk_Total                    msg_Id消息总条数，填1.
//                pk_Number,                        //pk_Number                    相同msg_Id的消息序号      填1.
//                registered_Delivery,            //registered_Delivery     是否要求返回状态报告 不需要填0，需要填1。
//                msg_Level,                        //msg_Level                    消息级别，根据消息实时性填写，值越小实时性越高。填1.
//                service_ID,                    //service_ID               业务类型，填””.
//                fee_UserType,                    //fee_UserType                计费用户类型字段  填0.
//                fee_Terminal_Id,                //fee_Terminal_ID           被计费用户的号码 填””.
//                tp_PID,                        //tp_Pid;                    GSM协议类型  填0.
//                tp_UDHI,                        //tp_Udhi                    GSM协议类型  填0.
//                msg_fmt,                        //msg_fmt                     消息内容的格式：填15.
//                msg_Src,                        //msg_Src                    消息内容来源 填””.
//                fee_Type,                        //fee_Type                    资费类别  填””.
//                fee_Code,                        //fee_Code                    资费代码  填””.
//                valid_Time,                    //valid_Time               Date类型参数，存活有效期 填null
//                at_Time,                        //at_Time                    Date类型参数，定时发送时间 填null,若需要定时发送时，填实际发送的时间.
//                src_Terminal_Id,                //src_Terminal_Id           扩展子号码
//                mobiles,                //dest_Terminal_Id           目的手机号
//                contents,                        //msg_Content                发送内容
//                reserve);
//        CMPPSubmitRepMessage response = null;
//        try {
//            response = (CMPPSubmitRepMessage) send(m1);
//        } catch (IOException e) {
//            logger.error("发送短信错误", e);
//            return null;
//        }
//        return response;
//    }
//
//    private CMPPSubmitRepMessage sendLongSms(String mobile, String content) {
//        logger.info("发送长短信短信开始 ");
//        String[] mobiles = {mobile};
//        int registered_Delivery = 1;
//        int msg_Level = 1;
//        String service_ID = "";
//        int fee_UserType = 2;
//        String fee_Terminal_Id = "";
//        int tp_PID = 0;
//        int tp_UDHI = 0x01;
//        int msg_fmt = 0x08;
//        String msg_Src = smsChannelEntry.getSpId();
//        String fee_Type = "02";
//        String fee_Code = "000";
//        Date valid_Time = new Date(System.currentTimeMillis() + (long) 0xa4cb800);
//        Date at_Time = null;
//        String src_Terminal_Id = "";
//        String reserve = "";
//        byte[] udhiHead = new byte[6];
//        CMPPSubmitRepMessage response = null;
//        List<byte[]> msgList = divideMessage(content);
//        udhiHead[0] = 0x05;
//        udhiHead[1] = 0x00;
//        udhiHead[2] = 0x03;
//        udhiHead[3] = 0x0a;
//        udhiHead[4] = (byte) msgList.size();
//        udhiHead[5] = 0x01;
//        for (int i = 0; i < msgList.size(); i++) {
//            udhiHead[5] = (byte) (i + 1);
//            byte[] msgContent = msgList.get(i);
//            byte[] sendContent = new byte[udhiHead.length + msgContent.length];
//            System.arraycopy(udhiHead, 0, sendContent, 0, udhiHead.length);
//            System.arraycopy(msgContent, 0, sendContent, udhiHead.length, msgContent.length);
//            logger.info("contents length :" + sendContent.length);
//            CMPPSubmitMessage
//                    m1 = new CMPPSubmitMessage(msgList.size(),                        //pk_Total                    msg_Id消息总条数，填1.
//                    i + 1,                        //pk_Number                    相同msg_Id的消息序号      填1.
//                    registered_Delivery,            //registered_Delivery     是否要求返回状态报告 不需要填0，需要填1。
//                    msg_Level,                        //msg_Level                    消息级别，根据消息实时性填写，值越小实时性越高。填1.
//                    service_ID,                    //service_ID               业务类型，填””.
//                    fee_UserType,                    //fee_UserType                计费用户类型字段  填0.
//                    fee_Terminal_Id,                //fee_Terminal_ID           被计费用户的号码 填””.
//                    tp_PID,                        //tp_Pid;                    GSM协议类型  填0.
//                    tp_UDHI,                        //tp_Udhi                    GSM协议类型  填0.
//                    msg_fmt,                        //msg_fmt                     消息内容的格式：填15.
//                    msg_Src,                        //msg_Src                    消息内容来源 填””.
//                    fee_Type,                        //fee_Type                    资费类别  填””.
//                    fee_Code,                        //fee_Code                    资费代码  填””.
//                    valid_Time,                    //valid_Time               Date类型参数，存活有效期 填null
//                    at_Time,                        //at_Time                    Date类型参数，定时发送时间 填null,若需要定时发送时，填实际发送的时间.
//                    src_Terminal_Id,                //src_Terminal_Id           扩展子号码
//                    mobiles,                //dest_Terminal_Id           目的手机号
//                    sendContent,                        //msg_Content                发送内容
//                    reserve);
//            try {
//                response = (CMPPSubmitRepMessage) send(m1);
//            } catch (IOException e) {
//                logger.error("发送短信错误", e);
//                return null;
//            }
//        }
//        return response;
//    }
//
//    private List<byte[]> divideMessage(String msg) {
//        List<byte[]> bytes = new ArrayList<byte[]>();
//        //String newStr = msg.getBytes();
//        byte[] newBytes = new byte[0];
//        try {
//            newBytes = msg.getBytes("UnicodeBigUnmarked");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        logger.info("msg total bytes " + newBytes.length);
//        int length = 134; //字节长度
//        int begin = 0;
//        int remain = newBytes.length - begin;
//        while (remain > 0) {
//            int currentLength = remain > length ? length : remain;
//            byte[] temp = new byte[currentLength];
//            System.arraycopy(newBytes, begin, temp, 0, currentLength);
//            begin = begin + currentLength;
//            remain = newBytes.length - begin;
//            bytes.add(temp);
//        }
//        return bytes;
//    }
//
//    /**
//     * 提供给业务层调用的获取连接状态的方法
//     */
//    /*@Override
//    public String getConnState() {
//        logger.info("连接状态:" + super.getConn().getError());
//        return super.getConn().getError();
//    }*/
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//
//    public SmsCache getSmsCache() {
//        return smsCache;
//    }
//
//    public void setSmsCache(SmsCache smsCache) {
//        this.smsCache = smsCache;
//    }
//
//    public SmsMapper getSmsMapper() {
//        return smsMapper;
//    }
//
//    public void setSmsMapper(SmsMapper smsMapper) {
//        this.smsMapper = smsMapper;
//    }
//
//    public CacheName getCacheName() {
//        return cacheName;
//    }
//
//    public void setCacheName(CacheName cacheName) {
//        this.cacheName = cacheName;
//    }
//
//    public SmsChannelEntry getSmsChannelEntry() {
//        return smsChannelEntry;
//    }
//
//    public void setSmsChannelEntry(SmsChannelEntry smsChannelEntry) {
//        this.smsChannelEntry = smsChannelEntry;
//    }
//
//}