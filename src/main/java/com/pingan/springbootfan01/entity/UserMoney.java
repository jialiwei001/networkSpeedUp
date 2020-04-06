package com.pingan.springbootfan01.entity;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.entity
 *  @文件名:   UserMoney
 *  @创建者:   Administrator
 *  @创建时间:  2020/4/1 20:50
 *  @描述：    TODO
 */
public class UserMoney {
    private static final String TAG = "UserMoney";

    private int id = 1;
    private String username;
    private int testCard;
    private int monthCard;
    private int quarterCard;
    private int halfYearCard;
    private int yearCard;
    private String startTime;
    private String endTime;
    private Double totalAmount;
    private double testPrice;
    private double monthPrice;
    private double quarterPrice;
    private double halfYearPrice;
    private double YearPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTestCard() {
        return testCard;
    }

    public void setTestCard(int testCard) {
        this.testCard = testCard;
    }

    public int getMonthCard() {
        return monthCard;
    }

    public void setMonthCard(int monthCard) {
        this.monthCard = monthCard;
    }

    public int getQuarterCard() {
        return quarterCard;
    }

    public void setQuarterCard(int quarterCard) {
        this.quarterCard = quarterCard;
    }

    public int getHalfYearCard() {
        return halfYearCard;
    }

    public void setHalfYearCard(int halfYearCard) {
        this.halfYearCard = halfYearCard;
    }

    public int getYearCard() {
        return yearCard;
    }

    public void setYearCard(int yearCard) {
        this.yearCard = yearCard;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount() {
        totalAmount = testCard*testPrice + monthCard*monthPrice + quarterCard*quarterPrice + halfYearCard*halfYearPrice + yearCard*YearPrice;
        this.totalAmount = totalAmount;
    }

    public double getTestPrice() {
        return testPrice;
    }

    public void setTestPrice(double testPrice) {
        this.testPrice = testPrice;
    }

    public double getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(double monthPrice) {
        this.monthPrice = monthPrice;
    }

    public double getQuarterPrice() {
        return quarterPrice;
    }

    public void setQuarterPrice(double quarterPrice) {
        this.quarterPrice = quarterPrice;
    }

    public double getHalfYearPrice() {
        return halfYearPrice;
    }

    public void setHalfYearPrice(double halfYearPrice) {
        this.halfYearPrice = halfYearPrice;
    }

    public double getYearPrice() {
        return YearPrice;
    }

    public void setYearPrice(double yearPrice) {
        YearPrice = yearPrice;
    }

    @Override
    public String toString() {
        return "UserMoney{" + "id=" + id + ", username='" + username + '\'' + ", testCard=" + testCard + ", monthCard=" + monthCard + ", quarterCard=" + quarterCard + ", halfYearCard=" + halfYearCard + ", yearCard=" + yearCard + ", startTime=" + startTime + ", endTiem=" + endTime + ", totalAmount=" + totalAmount + ", testPrice=" + testPrice + ", monthPrice=" + monthPrice + ", quarterPrice=" + quarterPrice + ", halfYearPrice=" + halfYearPrice + ", YearPrice=" + YearPrice + '}';
    }
}
