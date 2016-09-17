package com.qq.tuling;


import iqq.im.bean.QQMsg;
import iqq.im.bean.content.TextItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TulingApi {



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
            TulingResponse tulingResponse = new TulingResponse();
            tulingResponse.setType(type);
            tulingResponse.setText(jsonObject.getString("text"));
            switch (type){
                case Text:
                    break;
                case Link:
                    tulingResponse.setUrl(jsonObject.getString("url"));
                    break;
                case News:
                    List<News> newsList = new ArrayList<>();
                    JSONArray list = jsonObject.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject jo = list.getJSONObject(i);
                        newsList.add(new News(jo.getString("article"),jo.getString("source"),jo.getString("icon"),jo.getString("detailurl")));
                    }
                    tulingResponse.setNewsList(newsList);
                    break;
                case Cook:
                    List<Cook> cookList = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jo = jsonArray.getJSONObject(i);
                        cookList.add(new Cook(jo.getString("name"),jo.getString("icon"),jo.getString("info"),jo.getString("detailurl")));
                    }
                    tulingResponse.setCookList(cookList);
                    break;
            }
            return tulingResponse;
        }finally {
            httpPost.abort();
        }
    }
}
