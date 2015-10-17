package com.bluesky.stevenwang.util;
import javax.swing.*;

public class Player extends Obs
{

	public int money;
	public int days;
	public int[] itemsNum = new int[1];
	public int[] avePrice = new int[1];
	public int heal=100;
	public int warehouseRoom = 100;
	public Bank bankLoan = new Bank(1000,0.1);
	public int[][] record = new int[100][6];
	public void setRecord(int day,int code, int p, int num ,int ave ,int numRecord){
		record[numRecord][0] = day; // days
		record[numRecord][1] = code; // code 
		record[numRecord][2] = p; // price
		record[numRecord][3] = num; // number
		record[numRecord][4] = ave; // ave price
		//[5] buy 0 or sales 1
	}
	
	public int getSpace(){
		int p = warehouseRoom;
		for(int i : itemsNum){
			p -= i;
		}
		return p;		
	}
	
	public void itemsSet(int n){		
		itemsNum=new int[n];
		for(int i = 0;i < n;i++){
			itemsNum[i] = 0 ;
		}
	}
	
	public void avePriceSet(int n){		
		avePrice=new int[n];
		for(int i = 0;i < n;i++){
			avePrice[i] = 0 ;
		}
	}
	
	public Player(String n,int m,int d){
	    name = n;
		money = m;
		days = d;
	}
	public Player(){}
	
	public int getAccount(){
		return (money-bankLoan.money);
	}
	public void payLoan(){
		int n = JOptionPane.showConfirmDialog(

                null,

                "--- Welcome to Steven's Bank --- \n\n Do you want to pay off the loan? \n\n your loan is :$"+this.bankLoan.money,

                "Steven's Bank",

                JOptionPane.YES_NO_OPTION);
		if(n==0){
			int t = pay(this.bankLoan.money);
			if(t==1){
				this.bankLoan.money=0;
				JOptionPane.showMessageDialog(null, " YOU had Pay off your bank loan");
			}else{
				JOptionPane.showMessageDialog(null, "Are you Kiding me? ! you dont have enough money !!!");
			}
		}
		
	}
	public int pay(int i){
		if(this.money>=i){
			this.money-=i;
			return 1;
		}else{
			return 0;
		}
	}
	public void healBody(){
		int pay = 1500;
		if(this.heal >= 80){
			pay = 1500;
		}else if(this.heal >= 50){
			pay = 3000;
		}else if(this.heal >= 20){
			pay = 6000;
		}else if(this.heal >= 0){
			pay = 12000;
		}
		
		if(this.heal>97){
			JOptionPane.showMessageDialog(null, "--- Welcome to Steven's Hospital --- \n\n you are very good, you dont need heals");
		}else{
			
			int n = JOptionPane.showConfirmDialog(

	                null,

	                "--- Welcome to Steven's Hospital --- \n\n Do you want to get a heal? \n\n your should spend $"+pay +" to get 3 heal",

	                "Steven's Bank",

	                JOptionPane.YES_NO_OPTION);
			if(n==0){
				int t=pay(pay);
				if(t==1){
					JOptionPane.showMessageDialog(null, "You had spend $"+pay +" to get 3 heal");
				}else{
					JOptionPane.showMessageDialog(null, "YOu dont have enough money");
				}
			}
		}
	}
}
