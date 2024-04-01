package com.project.project3.model;

public class SearchVO {

    private String tv_name;
    private String store_category;
    private float tv_score;
    private int tv_review_cnt;
    private String tv_add;

    private String tv_review; // json의 menu해당
    private String hashtag; // json의 hashtag해당
    private int img_list;

    private String menu;
    private String similarity;


    public SearchVO(String storeName, String address, String menu, String hashtag) {
        this.tv_name = storeName;
        this.tv_add = address;
        this.menu = menu;
        this.hashtag = hashtag;
    }

    public String getTv_name() {
        return tv_name;
    }
    public String getMenu() {
        return menu;
    }

    public String getSimilarity() {
        return similarity;
    }
    public String getStore_category() {
        return store_category;
    }

    public float getTv_score() {
        return tv_score;
    }

    public int getTv_review_cnt() {
        return tv_review_cnt;
    }

    public String getTv_add() {
        return tv_add;
    }

    public String getTv_review() {
        return tv_review;
    }

    public String getHashtag() {
        return hashtag;
    }

    public int getImg_list() {
        return img_list;
    }

    public SearchVO(String tv_name, String tv_add) {
        this.tv_name = tv_name;
        this.tv_add = tv_add;
    }
    public SearchVO(String tv_name, String store_category, float tv_score, int tv_review_cnt, String tv_add, String hashtag) {
        this.tv_name = tv_name;
        this.store_category = store_category;
        this.tv_score = tv_score;
        this.tv_review_cnt = tv_review_cnt;
        this.tv_add = tv_add;
        this.hashtag = hashtag;
    }
}