package com.qq.tuling;

public class TulingResponse {
    private TulingApi.Type type;
    private String text;
    private String url;

    public TulingResponse() {
    }

    public TulingResponse(TulingApi.Type type, String text, String url) {
        this.type = type;
        this.text = text;
        this.url = url;
    }

    public TulingApi.Type getType() {
        return type;
    }

    public void setType(TulingApi.Type type) {
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
}
