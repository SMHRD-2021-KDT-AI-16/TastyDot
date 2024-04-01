package com.project.project3.model;

public class UserCouponVO {
    private String storeName;
    private String userCouponPrice;
    private String userCouponDate;
    private int userCouponImg;
    private int storeIdx;


    public String getUserCouponStoreName() { return storeName; }

    public  String getUserCouponPrice() {
        return userCouponPrice;
    }

    public String getUserCouponDate() {
        return userCouponDate;
    }

    public int getUserCouponImg() {
        return userCouponImg;
    }
//    public static int getStoreIdx() {
//        return storeIdx; // 추가된 getter 메서드
//    }


    public UserCouponVO(String storeName, String userCouponPrice, String userCouponDate, int userCouponImg) {
        this.storeName = storeName;
        this.userCouponPrice = userCouponPrice;
        this.userCouponDate = userCouponDate;
        this.userCouponImg = userCouponImg;
    }
}
