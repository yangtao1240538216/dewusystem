package com.example.dewu.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.example.dewu.model.Notify;
import com.example.dewu.service.NotifyHelper;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DingDingHelper implements NotifyHelper {

    private final String dingdingUrl = "https://oapi.dingtalk.com/robot/send?access_token=3115b174b8944c10543edb1b0cb2e68e3e062039be8b60e742ec8c0c4040741e";
    private final String dingdingLink = "https://oapi.dingtalk.com/robot/send?access_token=3115b174b8944c10543edb1b0cb2e68e3e062039be8b60e742ec8c0c4040741e";
    private final String dingdingMarkDown = "https://oapi.dingtalk.com/robot/send?access_token=3115b174b8944c10543edb1b0cb2e68e3e062039be8b60e742ec8c0c4040741e";

    private final DingTalkClient clientUrl = new DefaultDingTalkClient(this.dingdingUrl);
    private final DingTalkClient clientLink = new DefaultDingTalkClient(this.dingdingLink);
    private final DingTalkClient clientMarkDown = new DefaultDingTalkClient(this.dingdingMarkDown);

    public String sendDingDingNotify(Notify notify){
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
        // #1. request表示整个消息请求
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // #2. 请求设置消息类别
        request.setMsgtype("text");
        send(request,notify);
        return send(request,notify);
    }

    public String sendLink(Notify notify){
        // #1. request表示整个消息请求
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // #2. 请求设置消息类别
        request.setMsgtype("link");
        send(request,notify);
        return send(request,notify);
    }

    public String sendMarkdown(Notify notify){
        // #1. request表示整个消息请求
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // #2. 请求设置消息类别
        request.setMsgtype("markdown");
        send(request,notify);
        return send(request,notify);
    }

    public String send(OapiRobotSendRequest request, Notify notify) {
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);
        if(request.getMsgtype().equals("text")) {
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(notify.getText());
            request.setText(text);
        }else if(request.getMsgtype().equals("link")){
            OapiRobotSendRequest.Link text = new OapiRobotSendRequest.Link();
            text.setPicUrl(notify.getPicUrl());
            text.setMessageUrl(notify.getLink());
            text.setTitle(notify.getTitle());
            text.setText(notify.getText());
            request.setLink(text);
        }else if(request.getMsgtype().equals("markdown")){
            OapiRobotSendRequest.Markdown text = new OapiRobotSendRequest.Markdown();
            text.setTitle(notify.getTitle());
            text.setText(notify.getText());
            request.setMarkdown(text);
        }else{
            return "send false";
        }

        try {
            if(request.getMsgtype().equals("text")) {
                clientUrl.execute(request);
            }else if(request.getMsgtype().equals("link")){
                clientLink.execute(request);
            }else if(request.getMsgtype().equals("markdown")){
                clientMarkDown.execute(request);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "send success";
    }

    public String sendWechatNotify(Notify notify) {
        return null;
    }
}
