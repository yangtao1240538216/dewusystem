package com.example.dewu.service;

import com.example.dewu.model.Notify;

public interface NotifyHelper {
    String sendDingDingNotify(Notify notify);

    String sendWechatNotify(Notify notify);
}
