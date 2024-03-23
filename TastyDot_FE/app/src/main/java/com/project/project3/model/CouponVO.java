package com.project.project3.model;

public class CouponVO {
    private String couponName;
    private String couponUsed;
    private String couponCreate;
    private int CouponNumber;

    public CouponVO(String couponName, String couponUsed, String couponCreate, int couponNumber) {
        this.couponName = couponName;
        this.couponUsed = couponUsed;
        this.couponCreate = couponCreate;
        CouponNumber = couponNumber;
    }

    public String getCouponName() {
        return couponName;
    }

    public String getCouponUsed() {
        return couponUsed;
    }

    public String getCouponCreate() {
        return couponCreate;
    }

    public int getCouponNumber() {
        return CouponNumber;
    }
}
