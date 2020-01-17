package com.prozy.recycletrash.model;

public class Unggah_model {

    private String url;
    private String jenis;
    private String deskripsi;
    private String urlUserUpload;

    public Unggah_model(){

    }

    public Unggah_model(String url, String jenis, String deskripsi, String urlUserUpload) {
        this.url = url;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.urlUserUpload = urlUserUpload;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getUrlUserUpload() {
        return urlUserUpload;
    }

    public void setUrlUserUpload(String urlUserUpload) {
        this.urlUserUpload = urlUserUpload;
    }
}
