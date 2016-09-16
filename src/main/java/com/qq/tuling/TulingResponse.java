package com.qq.tuling;

public class TulingResponse {
    private Type type;
    private String text;
    private String url;

    public TulingResponse() {
    }

    public TulingResponse(Type type, String text, String url) {
        this.type = type;
        this.text = text;
        this.url = url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public enum Type{
        Text,Link,News,Cook,UnKnown;

        public static Type valueOf(Integer code){
            switch (code){
                case 100000:return Text;
                case 200000:return Link;
                case 302000:return News;
                case 308000:return Cook;
                default:return UnKnown;
            }
        }
    }
}
