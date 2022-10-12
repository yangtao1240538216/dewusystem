package com.example.dewu.service.impl;

import com.dingtalk.api.request.OapiRobotSendRequest;
import com.example.dewu.model.Notify;
import com.example.dewu.service.NotifyHelper;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseWechatHelper implements NotifyHelper {

    public String sendDingDingNotify(Notify notify) {
        return null;
    }

    public String sendWechatNotify(Notify notify) {
        if(notify == null) {
            return "false:notify is null";
        }
        if(notify.getNotifyType().equals("text")) {
            return sendText(notify);
        }else if(notify.getNotifyType().equals("link")) {
            return sendLink(notify);
        }else {
            return sendMarkdown(notify);
        }
    }

    public String sendText(Notify notify){
        return null;
    }

    public String sendLink(Notify notify){
        return null;
    }

    public String sendMarkdown(Notify notify){
        return null;
    }

    public String send(OapiRobotSendRequest request, Notify notify) {
        return null;
    }
}
