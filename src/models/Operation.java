package models;

import java.util.Date;

public class Operation {
    private int operationId;

    private User user;

    private String firstEquation;

    private String secondEquation;

    private String thirdEquation;

    private double xValue;

    private double yValue;

    private double zValue;

    private Date createdOn;

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstEquation() {
        return firstEquation;
    }

    public void setFirstEquation(String firstEquation) {
        this.firstEquation = firstEquation;
    }

    public String getSecondEquation() {
        return secondEquation;
    }

    public void setSecondEquation(String secondEquation) {
        this.secondEquation = secondEquation;
    }

    public String getThirdEquation() {
        return thirdEquation;
    }

    public void setThirdEquation(String thirdEquation) {
        this.thirdEquation = thirdEquation;
    }

    public double getXValue() {
        return xValue;
    }

    public void setXValue(double xValue) {
        this.xValue = xValue;
    }

    public double getYValue() {
        return yValue;
    }

    public void setYValue(double yValue) {
        this.yValue = yValue;
    }

    public double getZValue() {
        return zValue;
    }

    public void setZValue(double zValue) {
        this.zValue = zValue;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
