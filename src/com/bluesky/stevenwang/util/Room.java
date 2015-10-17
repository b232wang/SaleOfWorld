package com.bluesky.stevenwang.util;

public class Room extends Obs
{

	public int ItemsNum;
	public Item[] RoomItems = new Item[1];
	public int ItemN;
	public Lord RoomLord = new Lord();

	public Room(String RoomName,int RoomNum){
		this.name = RoomName;
		this.code = RoomNum;	
	}
	public Room(){}
	public void roomSet(Item[] items){	
		
		ItemN = items.length;
		boolean[] bool = new boolean[ItemN];
		ItemsNum =4+(int)(Math.random()*4);
		RoomItems= new Item[ItemsNum];
		int dd = 0;
		while(dd<ItemsNum){
			int r = (int)(Math.random()*ItemN);
			if(bool[r] == false){
				items[r].SetP();
				RoomItems[dd] = items[r];
				bool[r]=true;
				dd++;
			}
		}
		
		
	}
	
	
}
