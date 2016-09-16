package com.qq.tuling;


import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TulingApi {

    // 我把图灵机器人的新闻资讯、菜谱大全、天气查询、列车查询、航班查询功能都关闭了。
    // 现在这个机器人只回复文字消息。

    private static Log log = LogFactory.getLog(TulingApi.class);

    private static final String key = "3e10777cc66644278496d4c564369821"; // http://www.tuling123.com


    public static TulingResponse getTuling(HttpClient httpClient,String info, String user) throws IOException, JSONException {
        HttpPost httpPost = new HttpPost("http://www.tuling123.com/openapi/api");
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("key",key));
        nameValuePairList.add(new BasicNameValuePair("info",info));
        nameValuePairList.add(new BasicNameValuePair("userid",user));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList,"UTF-8"));
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String json = IOUtils.toString(httpResponse.getEntity().getContent(),"UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            Integer code = jsonObject.getInt("code");
            TulingResponse.Type type = TulingResponse.Type.valueOf(code);
            String text = getString(jsonObject,"text");
            String url = getString(jsonObject,"url");
            return new TulingResponse(type,text,url);
        }finally {
            httpPost.abort();
        }
    }

    private static String getString(JSONObject jsonObject,String s){
        Object o = jsonObject.opt(s);
        if(o==null){
            return null;
        }
        return o.toString();
    }


}
