package com.qq;

import com.alibaba.fastjson.JSON;
import com.qq.frame.QrCodeFrame;
import com.qq.tuling.TulingApi;
import com.qq.tuling.TulingResponse;
import iqq.im.*;
import iqq.im.actor.SwingActorDispatcher;

import iqq.im.bean.QQMsg;
import iqq.im.bean.QQUser;

import iqq.im.bean.content.FontItem;
import iqq.im.bean.content.TextItem;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQNotifyEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * QQ自动回复机器人 程序启动
 */
public class Program {

    private static Log log = LogFactory.getLog(Program.class);

    private static QQClient client = new WebQQClient(new QQNotifyListener() {
        @Override
        public void onNotifyEvent(QQNotifyEvent event) {
            log.debug(event);
            switch (event.getType()){
                case CHAT_MSG:
                    QQMsg revMsg = (QQMsg) event.getTarget();
                    revMsg(revMsg);
                    break;
            }
        }
    },new SwingActorDispatcher());

    private static void revMsg(QQMsg revMsg) {
        switch (revMsg.getType()){
            case BUDDY_MSG:
                //发送到图灵api
                HttpClient httpClient = new DefaultHttpClient();

                String qq = String.valueOf(revMsg.getFrom().getQQ());
                String info = revMsg.getText();
                log.info("收到来自"+qq+"的消息："+info);
                try {
                    TulingResponse tulingResponse = TulingApi.getTuling(httpClient,info,qq);
                    switch (tulingResponse.getType()){
                        case Text:
                            sendMsg(revMsg.getFrom(),"[自动回复]:"+tulingResponse.getText());
                            break;
                        case Link:
                            sendMsg(revMsg.getFrom(),"[自动回复]:"+tulingResponse.getUrl());
                            break;
                    }
                } catch (IOException | JSONException e) {
                    log.error("获取图灵消息失败！",e);
                }


        }
    }

    public static void sendMsg(QQUser user,String content){
        log.info("发送一条QQ消息："+content);
        QQMsg sendMsg = new QQMsg();
        sendMsg.setTo(user);
        sendMsg.setType(QQMsg.Type.BUDDY_MSG);
        sendMsg.addContentItem(new TextItem(content));
        sendMsg.addContentItem(new FontItem()); // 使用默认字体
        client.sendMsg(sendMsg, null);
    }

    public static void main(String[] args) {
        log.info("程序启动");
        final QrCodeFrame qrCodeFrame = new QrCodeFrame();
        client.getQRcode(new QQActionListener() {  //获取二维码
            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK){
                    log.debug("获取二维码成功！");
                    final BufferedImage bufferedImage = (BufferedImage) event.getTarget();
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            qrCodeFrame.setQrImage(bufferedImage);
                            qrCodeFrame.setVisible(true);
                        }
                    });
                }else{
                    log.info("获取二维码失败！");
                }
            }
        });

        client.checkQRCode(new QQActionListener() {  // 检查二维码状态
            @Override
            public void onActionEvent(QQActionEvent event) {
                switch (event.getType()){
                    case EVT_OK:
                        //登录成功
                        log.info("登录QQ成功");
                        qrCodeFrame.setVisible(false);
                        client.getBuddyList(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if(event.getType()== QQActionEvent.Type.EVT_OK){
                                    log.info("加载好友列表成功");
                                }
                            }
                        });
                        client.getGroupList(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if(event.getType()== QQActionEvent.Type.EVT_OK){
                                    log.info("加载群列表成功");
                                }
                            }
                        });
                        client.getDiscuzList(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if(event.getType()== QQActionEvent.Type.EVT_OK){
                                    log.info("加载讨论组列表成功");
                                }
                            }
                        });
                        client.getSelfInfo(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                                    System.out.println(JSON.toJSONString(event.getTarget()));
                                    System.out.println("获取个人信息成功");
                                }
                            }
                        });
                        client.beginPollMsg();
                        break;
                    case EVT_ERROR:
                        QQException ex = (QQException) (event.getTarget());
                        QQException.QQErrorCode code = ex.getError();
                        switch (code){
                            case QRCODE_OK:
                            case QRCODE_AUTH:
                                log.info("二维码有效，等待用户扫描。");
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                client.checkQRCode(this);
                                break;
                        }
                        break;
                }
            }
        });
    }
}
