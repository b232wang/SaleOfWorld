package com.bluesky.stevenwang.util;

import java.lang.Math;



public class Item extends Obs
{

	public int p;
	public int range = 0;
	public int num =0;
	public int pp=0;

	public void SetP(){
        pp=(p + (int)(Math.random( ) * range));
	}

	public Item(String n,int p,int range){
	    this.name = n;
		this.p = p;
		this.range= range;
	}
    public Item(){}
	
}
