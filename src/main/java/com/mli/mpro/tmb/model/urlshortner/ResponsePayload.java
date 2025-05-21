package com.mli.mpro.tmb.model.urlshortner;

public class ResponsePayload {

    private String shortUrl;
    private String url;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ResponsePayload{" +
                "shortUrl='" + shortUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
