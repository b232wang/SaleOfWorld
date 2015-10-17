package com.bluesky.stevenwang.util;
import java.math.*;

public class Lord extends Obs{
	public double lordPower = 1;

	public int a;
	public int b;
	public int c;
	public int d;
	public Lord(){}
	public Lord(String n,int code,int a,int b,int c,int d){
		this.name=n;
		this.code = code;
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;

	}
	public void powerGet(){
		int p = (int)(Math.random( ) * 100);
		if(p < a){
			lordPower = 0.5;
		}else if(p < (a+b)){
			lordPower = 0.75;
		}else if(p<(a+b+c)){
			lordPower = 1.5;
		}else if(p<(a+b+c+d)){
			lordPower = 2;
		}
	}
	
	

}
