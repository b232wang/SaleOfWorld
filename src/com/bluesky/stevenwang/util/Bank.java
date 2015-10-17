package com.bluesky.stevenwang.util;

public class Bank extends Obs{
    public int days;
    public int money = 0;
    public double interest = 0;
    public Bank(){}
    public Bank(int money,double interest){
    	this.money = money;
    	this.interest = interest;
    }

}
