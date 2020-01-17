package com.prozy.recycletrash.model;

public class datasetfire {
    String pid,date,time,namabarang,image,kategori,price,nomorhp,jenis,profilname;

    public datasetfire(){

    }

    public datasetfire(String pid, String date, String time, String namabarang, String image, String kategori, String price, String nomorhp, String jenis, String profilname) {
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.namabarang = namabarang;
        this.image = image;
        this.kategori = kategori;
        this.price = price;
        this.nomorhp = nomorhp;
        this.jenis = jenis;
        this.profilname = profilname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNomorhp() {
        return nomorhp;
    }

    public void setNomorhp(String nomorhp) {
        this.nomorhp = nomorhp;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getProfilname() {
        return profilname;
    }

    public void setProfilname(String profilname) {
        this.profilname = profilname;
    }
}
