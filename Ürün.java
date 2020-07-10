package com.example.up_buy;

public class Ürün {


    String title;
    String image;
    String tanım ;
    String teklifveren;
    String kategori;
    String ürünfiyat;
    String satis;

    public Ürün(String title, String image,String tanım, String teklifveren,String kategori,String ürünfiyat, String satis ) {


        this.title = title;
        this.image = image;
        this.tanım=  tanım ;
        this.teklifveren= teklifveren;
        this.ürünfiyat=ürünfiyat;
        this.satis=satis;

    }

    public Ürün(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTanım(){
        return tanım;
    }

    public void setTanım(String tanım){
        this.tanım=tanım;
    }

    public String getTeklifveren(){
        return teklifveren;
    }

    public void setTeklifveren(){
        this.teklifveren=teklifveren;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getÜrünfiyat() {
        return ürünfiyat;
    }

    public void setÜrünfiyat(String ürünfiyat) {
        this.ürünfiyat = ürünfiyat;
    }

    public String getSatis() {
        return satis;
    }

    public void setSatis(String satis) {
        this.satis = satis;
    }
}
