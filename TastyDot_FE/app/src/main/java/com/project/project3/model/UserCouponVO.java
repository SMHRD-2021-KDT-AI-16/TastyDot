package com.project.project3.model;

public class UserCouponVO {
    private String userCouponName;
    private String userCouponPrice;
    private String userCouponDate;
    private int userCouponImg;

    public String getUserCouponName() {
        return userCouponName;
    }

    public String getUserCouponPrice() {
        return userCouponPrice;
    }

    public String getUserCouponDate() {
        return userCouponDate;
    }

    public int getUserCouponImg() {
        return userCouponImg;
    }

    public UserCouponVO(String userCouponName, String userCouponPrice, String userCouponDate, int userCouponImg) {
        this.userCouponName = userCouponName;
        this.userCouponPrice = userCouponPrice;
        this.userCouponDate = userCouponDate;
        this.userCouponImg = userCouponImg;
    }
}
