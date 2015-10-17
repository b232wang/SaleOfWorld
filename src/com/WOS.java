package com;
import java.io.*;
import javax.swing.*;
import com.bluesky.stevenwang.util.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;



public class WOS extends JPanel
{
	static int checkStage = 0;

	static JPanel panel;                 // main drawing panel
    static JFrame frame;
    static final int WINDOW_WIDTH = 800; // width of display window
    static final int WINDOW_HEIGHT = 600;// height of display window

    //static int gameStar = 0;
    static int bigStage =0;
    static int gameStage = 0;            // stage of game
    static int anyKeyStage = 0;
    static final int WELCOME_SCREEN = 0;
    static final int MENU = 1;


    static final int INSTRUCTIONS = 2;
    static final int PLAY = 3;
    static final int END_GAME = 4;

	static boolean waitingForKeyPress = false;
	static int playStage = 0;           // stage of actual play
	static final int PART_ONE = 0;
	static final int PART_TWO = 1;
	static final int WINNER = 2;
	//----------------------
	static Image bgImage1;
	static Image bgImage2;
	static Image bgImage3;
	static Image bgImage4;

	static Image win;
	static Image lose;
	//----------------------
	public static Player Player1 = new Player();
	public static Room RoomIn = new Room();
	public static Room[] room = new Room[0];
	public static Item[] items = new Item[0];
	public static GamePackage packet = new GamePackage("NOIC");
	public static int RoomInNum;
	public static int Stage=0;
	public static int numRecord = 0;

	public static int money = 2000;
	public static int days = 30;


	public static void main(String[] args)
	{
		String appf="src/com/bluesky/pic/";
		Toolkit tk = Toolkit.getDefaultToolkit();
		bgImage1=tk.getImage(appf+"bg1.jpg");
		bgImage2=tk.getImage(appf+"bg2.jpg");
		bgImage3=tk.getImage(appf+"bg3.jpg");
		bgImage4=tk.getImage(appf+"bg4.jpg");

		win=tk.getImage(appf+"win.jpg");
		lose=tk.getImage(appf+"lose.jpg");


		panel = new WOS();

        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));  // set size of application window
        frame = new JFrame ("WORLD OF SalE  --- Steven Wang");  // set title of window
        frame.add (panel);
        frame.addKeyListener(new KeyInputHandler());
        frame.addWindowListener(new ExitListener());

        frame.requestFocus();
        frame.pack();
        frame.setVisible(true);
	}
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		if(bigStage==0){
	        if (gameStage == WELCOME_SCREEN) {
	        	g.setColor(Color.yellow);

	            g.fillRect (0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
	            g.drawImage(bgImage1, 0, 0, this);

		        g.setColor(Color.black);
		        g.setFont(new Font("SansSerif", Font.BOLD, 16));   // set font
		        g.drawString("Welcome to ", 360, 250);
		        g.drawString("Press any key to continue.",310,350);

		        g.setColor(Color.blue);
		        g.setFont(new Font("SansSerif", Font.BOLD, 36));   // set font
		        g.drawString("World of Sales!",280,300);  // display
			}else if(gameStage == MENU){
				g.drawImage(bgImage2, 0, 0, this);
		        g.setColor(Color.red);
		        g.setFont(new Font("SansSerif", Font.BOLD, 30));   // set font
		        g.drawString(" 1. Start the game", 150, 200);
		        g.drawString(" 2. Loading package", 200, 250);
		        g.drawString(" 3. Loading game", 250, 300);
			}else if(gameStage == 11){		//过度房间

				g.setColor(new Color(255,182,193));
				g.fillRect (0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

				g.setColor(Color.blue);
			    g.setFont(new Font("SansSerif", Font.BOLD, 22));
				g.drawString(Player1.name+" !", 30, 30);

				g.setColor(Color.black);
			    g.setFont(new Font("SansSerif", Font.BOLD, 18));   // set font
			    g.drawString("You are a hero, you want to join 'Hey, I'm MVP!' .  ... ", 30, 60);
			    g.drawString("But you need 100,000 for admission fee !   ... ", 30, 90);
			    g.drawString("You borrow $5000 from Steven's Bank, you spend $3000 to get a 100 meters Houre.   ...",30,120);
			    g.drawString("And now! You Only Have 30 day !!   ... ",30,150);


			    g.setColor(Color.blue);
			    g.setFont(new Font("SansSerif", Font.BOLD, 22));   // set font
			    g.drawString("* This is a game about Reselling !", 30, 200);
			    g.drawString("* You can only go to one place each day !", 30, 230);
			    g.drawString("* Your health will go down 2 each day!", 30, 260);
			    g.setColor(new Color(199,21,133));
			    g.setFont(new Font("SansSerif", Font.BOLD, 26));
			    g.drawString("# E: end this day", 30, 330);
			    g.drawString("# R: Real estate (Buy warehouse)", 350, 330);
			    g.drawString("# H: Hospital (+3 health)", 30, 390);
			    g.drawString("# B: Bank (Pay for loan)", 350, 390);
			    g.drawString("# L: Record (view record)", 30, 450);
			    g.drawString("# S: Save this game ", 350, 450);
			}else if(gameStage == 40){
				g.drawImage(bgImage4, 0, 0, this);
				g.setColor(Color.blue);
			    g.setFont(new Font("SansSerif", Font.BOLD, 30));   // set font
			    g.drawString("record:", 20, 30);
			    g.setColor(Color.black);
			    g.setFont(new Font("SansSerif", Font.BOLD, 20));
			    for(int i = 0;i<numRecord;i++){
					String a = Player1.record[i][5]==0? "spend":"get";
					String b = Player1.record[i][5]==0? "buy":"sale";
					g.drawString("Day "+(days-Player1.record[i][0])+" :"+Player1.name+" "+a+" $"+(Player1.record[i][2]*Player1.record[i][3])+" "+b+" "+Player1.record[i][3]+" "+items[Player1.record[i][1]].name+" ($"+Player1.record[i][4]+"). ", 20, 60+20*i);
				}
			}

	    }else if(bigStage==1){
	    	g.drawImage(bgImage3, 0, 0, this);
			g.setColor(Color.blue);
			g.setFont(new Font("SansSerif", Font.BOLD, 24));
			for(int i=0;i<80;i++){
				g.drawString("-", 0+i*20, 115);
			}

			g.drawString("Hello "+Player1.name,20,20);
			g.drawString(" / "+days+" days", 50, 50);
			g.drawString("Bank-Loan: ", 20, 80);
			g.drawString("heal: ", 600, 80);
			g.drawString("/  "+Player1.warehouseRoom +" space", 650, 50);
			g.setColor(Color.red);
			for(int i=0;i<79;i++){
				g.drawString("-", 10+i*20, 115);
			}
			g.drawString("$ "+Player1.money, 600, 20);
			g.drawString((30-Player1.days)+"", 20, 50);
			g.drawString("$"+Player1.bankLoan.money, 150, 80); // bank loan num
			g.drawString(""+Player1.heal, 680, 80);
			g.drawString((Player1.warehouseRoom-Player1.getSpace())+"", 600, 50);
			g.setFont(new Font("SansSerif", Font.BOLD, 30));
			g.drawString("-- World OF SalE --", 250, 30);
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("-- by Steven Wang", 320, 55);

		    g.setFont(new Font("SansSerif", Font.BOLD, 40));
		    g.drawString("[H]", 100, 550);
		    g.drawString("[B]", 260, 550);
		    g.drawString("[R]", 420, 550);
		    g.drawString("[E]", 580, 550);



		    if(gameStage == 20){  //  room 房间

			    int app=0;
			    int apd=50;
			    g.setColor(Color.red);
			    g.setFont(new Font("SansSerif", Font.BOLD, 18));
			    g.drawString("Please choose a place", 40, 170);


			    for(int itt = 0; itt<room.length ; itt++){
			    	g.setColor(Color.blue);
				    g.setFont(new Font("SansSerif", Font.BOLD, 32));
			    	g.drawString(room[itt].name, apd+40+itt*220, 250+app);

			    	g.setFont(new Font("SansSerif", Font.BOLD, 30));
			    	g.setColor(Color.black);
			    	g.drawString((itt+1)+":", apd+itt*220, 250+app);
			    	if(itt%3==2){
			    		app+=80;
			    		apd-=660;
			    	}
			    }
			    g.setColor(Color.green);
			    g.setFont(new Font("SansSerif", Font.BOLD, 32));
			    g.drawString("S: Save game", 520, 480);

		    }else if(gameStage == 21){
				g.setColor(Color.black);
				g.setFont(new Font("SansSerif", Font.BOLD, 30));   // set font
				g.drawString(" -- + -- + -- "+room[RoomInNum].name+" -- + -- + --", 200, 150);

				g.setColor(Color.blue);
				g.setFont(new Font("SansSerif", Font.BOLD, 22));   // set font
				for(int i =0;i<RoomIn.RoomItems.length;i++){
					g.setColor(Color.blue);
					g.drawString((i+1)+":", 30, 220+30*i);
					g.drawString(RoomIn.RoomItems[i].name, 100, 220+30*i);
					g.drawString(RoomIn.RoomItems[i].pp+" $", 400, 220+30*i);
					g.drawString(Player1.itemsNum[RoomIn.RoomItems[i].code]+"", 550, 220+30*i);
					g.drawString(Player1.avePrice[RoomIn.RoomItems[i].code]+"",650,220+30*i);
					if(Player1.avePrice[RoomIn.RoomItems[i].code]!=0&&RoomIn.RoomItems[i].pp!=Player1.avePrice[RoomIn.RoomItems[i].code]){
						if(Player1.avePrice[RoomIn.RoomItems[i].code]<RoomIn.RoomItems[i].pp){
							g.setColor(Color.red);
						}else{
							g.setColor(Color.green);
						}
						g.drawString(RoomIn.RoomItems[i].pp+" $", 400, 220+30*i);
					}
				}
				g.setColor(Color.red);
				g.setFont(new Font("SansSerif", Font.BOLD, 20));   // set font
				g.drawString("Code", 30, 190);
				g.drawString("Name", 100, 190);
				g.drawString("Price", 400, 190);
				g.drawString("Stock", 550, 190);
				g.drawString("Average", 650, 190);
				if(RoomIn.RoomLord.lordPower != 1){
					if(RoomIn.RoomLord.lordPower >1){
						g.setColor(Color.red);
					}else if(RoomIn.RoomLord.lordPower <1){
						g.setColor(Color.green);
					}
					g.setFont(new Font("SansSerif", Font.BOLD, 12));
					g.drawString("The price in this room had been time "+ RoomIn.RoomLord.lordPower+" ----- "+RoomIn.RoomLord.name, 80, 450);
				}

		    }//if
		}else if(bigStage ==2){
			if(gameStage == 50){
				g.drawImage(win, 0, 0, this);
				g.setColor(Color.red);
				g.setFont(new Font("SansSerif", Font.BOLD, 40));
				g.drawString("Congratulation ", 280, 220);
				g.setColor(Color.blue);
				g.setFont(new Font("SansSerif", Font.BOLD, 20));
				g.drawString("You get $"+Player1.money+" !! ", 150, 300);
				g.drawString("You had join 'Hey, I'm MVP!' successfully! ", 150, 350);

			}else if(gameStage ==51){
				g.drawImage(lose, 0, 0, this);
				g.setColor(Color.red);
				g.setFont(new Font("SansSerif", Font.BOLD, 40));
				g.drawString("Sorry, you lose", 280, 220);
				g.setColor(Color.blue);
				g.setFont(new Font("SansSerif", Font.BOLD, 20));
				g.drawString("You get $"+Player1.money+" !! ", 150, 300);
				g.drawString("You cannot join 'Hey, I'm MVP!':(  You suck ! ", 150, 350);
			}
			g.setColor(Color.blue);
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("1. Play Again.           2. Quit", 200, 400);
			g.setColor(Color.red);
			g.drawString("Thanks for playing ----- Steven Wang ", 350, 580);

		}

	}

	private static class KeyInputHandler extends KeyAdapter {
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == 27) {
                System.exit(0);
            } else if (waitingForKeyPress == true) {

        // respond to menu selection
            	if(bigStage == 0){
	            	if(gameStage == MENU){
	            		switch (e.getKeyChar()) {
	                    case 49:  getPlayerName();break;
	                    case 50:
	                    	checkStage = 0;
	                    	beginGame(1);
	                    	if(checkStage == 0){
	                    		showMessage("-- Congratulation -- \n Seccess of Loading a Package! ");break;
	                    	}
	                    	break;
	                    case 51:
	                    	checkStage=0;
	                    	beginGame(2);
	                    	if(checkStage==0){
	                    		showMessage("-- Congratulation -- \n Seccess of Loading a Game! ");sG(20);break;
	                    	}
	                    	break;
	                    case 52:  System.exit(0);
	                    } // switch
	            	}
            	}else if(bigStage == 1){
            		if(e.getKeyChar() == 104){
            			Player1.healBody();
            			panel.repaint();
            		}else if(e.getKeyChar() == 114){
        				estate();
        				panel.repaint();
        			}else if(e.getKeyChar() == 98){
        				Player1.payLoan();
        				panel.repaint();
        			}else if(gameStage == 20){
	            		while(true){
	            			if(e.getKeyChar() == 115){
	            				saveFile();
	            				break;
	            			}else if(e.getKeyChar() == 101){
	            				dayPass(0);
	            				break;
	            			}else if(e.getKeyChar()==108){
	            				showRecord();
	            				break;
	            			}else if(e.getKeyChar()<=(50+room.length)&&e.getKeyChar()>=49){

	    	        		    	RoomInNum=e.getKeyChar()-49;
	    	        		    	roomIn();
	    	        		    	sG(21);
	    	        		    	break;

	            			}else{
	            				showMessage("Wrong!");
	            				break;
	            			}

	            		}//while
	            	}else if(gameStage == 21){

	            		while(true){
	            			if(e.getKeyChar()== 101){
	            				dayPass(1);
	            				break;
	            			}else if(e.getKeyChar()<(RoomIn.RoomItems.length+49)&&e.getKeyChar()>48){
		           		    	buyOrSale(RoomIn.RoomItems[e.getKeyChar()-49].code);
		           		    	break;
		           		    }else{
		           		    	showMessage("Wrong!");
	            				break;
		           		    }
	            		}
	            	}

            	}else if(bigStage == 2){
            		switch (e.getKeyChar()) {
                    case 49:  gameReset();sG(20);break;
                    case 50:  System.exit(0);
            		}

            	}
            } else {
            	if(anyKeyStage == 0){
            		showMenu();
            	}else if(anyKeyStage == 1){
            		sG(20);
            	}
            } // else
		}
	}
	private static class ExitListener extends WindowAdapter {

		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
	} // ExitListener

		    // Pause the program for duration milliseconds
	private static void pause(int duration) {
		try {
			Thread.sleep(duration);
		    } catch (InterruptedException e) { }
		}
	private static void showMenu() {// display this stage of the game
		bigStage =0;
		gameStage = MENU;
		playStage = PART_ONE;
		waitingForKeyPress = true;
		panel.repaint();
	}
	private static void sG(int a) {
		if(a == 20){
			bigStage = 1;
		}
		gameStage = a;

		waitingForKeyPress = true;
		panel.repaint();

	}
	private static void getPlayerName(){
        anyKeyStage = 1;
        gameReset();

	    while(true){
			try{

			    Player1.name = JOptionPane.showInputDialog(panel, "-- Enter your name --");
	            if(!Player1.name.equals(null) && !Player1.name.equals("")){
			        break;
	            }
	            showMessage("Plz Enter Ur name \n        THX");

			}catch(Exception e){
				endGame();
			}
		}

			gameStage = 11;
		    waitingForKeyPress = false;
		    panel.repaint();
	}
	public static void gameReset(){
		beginGame(0);
		Player1.money =money;
		Player1.days = days;
		Player1.warehouseRoom = 100;
		Player1.bankLoan=new Bank(5000,0.1);
		Player1.heal=100;
	}
	public static void showRecord(){
		bigStage =0;
		sG(40);
		anyKeyStage = 1;
		waitingForKeyPress = false;


	}

	public static void beginGame(int a1){
		String GP = "";

		if(a1==2){
			readProgress();

		}else{
		if(a1 == 0){
		    GP = packet.name;
		}
		if(a1 == 1){
			File file = new File("src/com/bluesky/stevenwang/packet/");
			File[] lf = file.listFiles();
			String[] load =new String[lf.length];
			for(int i=0; i<lf.length; i++){
			    load[i]=lf[i].getName();
			}
			GP = load[chooseMessage(load,"\n --------Choose a Packet--------")];
			packet.name = GP;
		}
		loadPackage("packet/"+GP+"/Items.txt",1);
        loadPackage("packet/"+GP+"/Rooms.txt",2);
        Player1.itemsSet(items.length);
        Player1.avePriceSet(items.length);
		}

	}


	public static void readProgress(){
		File file = new File("src/com/bluesky/stevenwang/save/");
		File[] lf = file.listFiles();
		String[] load =new String[lf.length];
		for(int i=0; i<lf.length; i++){
		    load[i]=lf[i].getName();
		}
		String GP = load[chooseMessage(load,"\n --------Choose a Packet--------")];
		loadPackage("save/"+GP+"/player.txt",3);
		loadPackage("packet/"+packet.name+"/Items.txt",1);
        loadPackage("packet/"+packet.name+"/Rooms.txt",2);
        Player1.itemsSet(items.length);
        Player1.avePriceSet(items.length);
        Player1.record = new int[100][6];
        loadPackage("save/"+GP+"/items.txt",4);
        loadPackage("save/"+GP+"/record.txt",5);

	}



    public static void roomIn(){
    	RoomIn=room[RoomInNum];
    	RoomIn.roomSet(items);
    	RoomIn.RoomLord.powerGet();
		for(int i =0;i<RoomIn.RoomItems.length;i++){
			RoomIn.RoomItems[i].pp *= RoomIn.RoomLord.lordPower;
		}
    }


	public static void buyOrSale(int k){      // Buy Or Sale
		while(true){
		String[] load2 ={"BUY","SALE","Return"};
		int e=chooseMessage(load2,"\n-F-O-R----S-T-E-V-E-N---- \n \n              ----- "+items[k].name+" ----- \n\n       "+items[k].pp+"each         you have "+Player1.itemsNum[items[k].code]+" right now");
		if(e==2){
			sG(21);
			break;
		}
		Item f = items[k];
		int iteNum = Player1.itemsNum[k];//买前玩家所持有物品数量
		int iteP = Player1.avePrice[k];//买前玩家所持有物品平均价格
		int b =f.pp; //物品价格
		int s= (e==0? Math.min((int)(Player1.money/b),Player1.getSpace()):Player1.itemsNum[k]);
		showMessage(" You can only "+ (e==0?"buy ":"sale ")+s);
		String test = "\n How many do u want "+ (e==0?"buy ":"sale ") + " ? ";
		int num = inputMessage(test,"Wrong number !",s);
		if(num != 0){
			Player1.setRecord(Player1.days,k,b,num,Player1.avePrice[k],numRecord);
			Player1.record[numRecord][5] = (e==0)? 0:1;
			numRecord++;
		}

		if(e==0){
			Player1.itemsNum[k] =Player1.itemsNum[k] + num;
			Player1.money = Player1.money - (num * b);
			showMessage("U speed "+(num*b)+"$ buy "+num+" "+f.name);

			Player1.avePrice[k]= (iteNum*iteP+num*b)/Player1.itemsNum[k];

			break;
		}else{
			Player1.itemsNum[k] =Player1.itemsNum[k] - num;
			Player1.money = Player1.money + (num * b);
			showMessage("U get "+(num*b)+"$ from sale "+num+" "+f.name);
			if(Player1.itemsNum[k]==0){
				Player1.avePrice[k]=0;
			}
			break;
		}
	    }
		sG(21);

	}
	public static void estate(){
		int tt = Player1.warehouseRoom*100;
		int n = JOptionPane.showConfirmDialog(

                null,

                "----- Welcome to the Steven's real estate company ----- \n\n You can only spend $"+tt+" to get 50 more space. \n\n Do you want buy it?",

                "Steven's Estate ",

                JOptionPane.YES_NO_OPTION);
		if(n==0){
			if(Player1.money>=tt){
				Player1.money-=tt;
				Player1.warehouseRoom+=50;
				showMessage("you had spend $"+tt+" buy 50 more space");
				showMessage(" ----- Thank you to patronize -----");
			}else{
				showMessage("You are a fucing pauper ! get out here ! ");
			}
		}

	}
	public static void dayPass(int p){
		if(Player1.days ==0){
			winOrLose();
		}else{
			Player1.days--;
			Player1.heal-=2;
			Player1.bankLoan.money=(int)((1+Player1.bankLoan.interest)*Player1.bankLoan.money);
			if( p == 1){
				sG(20);
			}
			panel.repaint();
			if(Player1.heal <=0){
				showMessage("You die.......");
				bigStage=2;
				sG(51);
			}
		}

	}

	public static void winOrLose(){         //Game end

		 bigStage=2;
		 int tt = Player1.getAccount()>=100000?50:51;
		 sG(tt);
	}

	public static void showMessage(String a){       //showMessage methed
        JOptionPane.showMessageDialog(null,a);
	}

	// 一些游戏工具 by great STEVEN
	public static void loadPackage(String ipa,int kind){    // Item package
        String s = new String();
		String[] a;
		int i = 0;
		int dd = 0;

		try {
			FileInputStream files=new FileInputStream("src/com/bluesky/stevenwang/"+ipa);
			FileInputStream inputf=new FileInputStream("src/com/bluesky/stevenwang/"+ipa);
		    BufferedReader input=new BufferedReader(new InputStreamReader(files));
		    BufferedReader input2=new BufferedReader(new InputStreamReader(inputf));

		    while(input.readLine()!=null){
		        i++;
		    }//while
		    if(kind == 0){  //load package.txt  for package
		    a = new String[1];
		    }
		    if(kind == 1){  //load items.txt   for package
		    items= new Item[i];
		    a = new String[3];
		    }
		    if(kind == 2){ //load room.txt   for package
		    room = new Room[i];
		    a = new String[7];
		    }
		    if(kind == 3){//load player.txt  for save
		    	a=new String[9];
		    }
		    if(kind == 4){//load items.txt for save
		    	a=new String[2];
		    }
		    if(kind == 5){//load record.txt for save
		    	a=new String[6];
		    }
		    while((s = input2.readLine())!=null){
		    a = s.split(",");

		    if(kind == 0){
		    	packet.name= a[0];
		    }
		    if(kind == 1){
		        items[dd] = new Item(a[0].toString(),Integer.parseInt(a[1]),Integer.parseInt(a[2]));
		        items[dd].code=dd;
		    }
		    if(kind == 2){
		    	room[dd] = new Room(a[0].toString(),Integer.parseInt(a[1]));
		    	room[dd].RoomLord = new Lord(a[2].toString(),Integer.parseInt(a[1]),Integer.parseInt(a[3]),Integer.parseInt(a[4]),Integer.parseInt(a[5]),Integer.parseInt(a[6]));

		    }
		    if(kind == 3){
		    	packet.name = a[0];
		    	Player1.name=a[1];
		    	Player1.days=Integer.parseInt(a[2]);
		    	Player1.money=Integer.parseInt(a[3]);
		    	Player1.warehouseRoom=Integer.parseInt(a[4]);
		    	Player1.heal=Integer.parseInt(a[5]);
		    	Player1.bankLoan.money=Integer.parseInt(a[6]);
		    	Player1.bankLoan.interest=Double.parseDouble(a[7]);
		    	numRecord=Integer.parseInt(a[8]);
		    }
		    if(kind == 4){
		    	Player1.itemsNum[dd]=Integer.parseInt(a[0]);
		    	Player1.avePrice[dd]=Integer.parseInt(a[1]);
		    }
		    if(kind == 5){
		    	for(int d = 0; d<6;d++){
		    		Player1.record[dd][d]=Integer.parseInt(a[d]);
		    	}
		    }
		   	dd++;

		    } //while
		    input.close();
		    input2.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Catch wrong " + kind);
			checkStage = 1;
		} //catch

	}

	public static void saveFile(){
		String FileName="";
		while(true){
			try{
		        FileName = JOptionPane.showInputDialog("Plz ender a name about file");
			}catch(Exception e){
			    endGame();
			}

		if(!FileName.equals("")&&!FileName.equals(null)){
			break;
		}
		showMessage("file name can not be nothing");
		}
		File f=new File("src/com/bluesky/stevenwang/save/"+FileName);
        f.mkdirs();
		try{
            BufferedWriter out1 = new BufferedWriter(new FileWriter("src/com/bluesky/stevenwang/save/"+FileName+"/player.txt"));
            out1.write(packet.name+","+Player1.name+","+Player1.days+","+Player1.money+","+Player1.warehouseRoom+","+Player1.heal+","+Player1.bankLoan.money+","+Player1.bankLoan.interest+","+numRecord);
            BufferedWriter out = new BufferedWriter(new FileWriter("src/com/bluesky/stevenwang/save/"+FileName+"/items.txt"));

			for(int i = 0; i <Player1.itemsNum.length;i++){
                out.write(Player1.itemsNum[i]+","+Player1.avePrice[i]);
				out.newLine();
			}
			BufferedWriter out2 = new BufferedWriter(new FileWriter("src/com/bluesky/stevenwang/save/"+FileName+"/record.txt"));
			for(int i = 0;i<numRecord;i++){
				out2.write(Player1.record[i][0]+","+Player1.record[i][1]+","+Player1.record[i][2]+","+Player1.record[i][3]+","+Player1.record[i][4]+","+Player1.record[i][5]);
				out2.newLine();
			}
			out.close();
			out1.close();
			out2.close();
			showMessage("u have been save ur info into "+ FileName);

		}catch(Exception e){
            showMessage("save fail plz try again");
		}
	}
	public static int chooseMessage(String[] a,String test){
		String c =test;
		int i =0;
		while(i == 0){
			try{
		Object b = JOptionPane.showInputDialog(null,
				c, "For THE HORDE",
				JOptionPane.INFORMATION_MESSAGE, null,
				a, a[0]);
		return (find(a,b.toString()));
			}catch(Exception e){
				endGame();
			}
		}
		return -1;
	}
	public static void endGame(){
		int n = JOptionPane.showConfirmDialog(

                null,

                "Do U want close this GAME ? ",

                "For The World SHAMAN Thrall! ",

                JOptionPane.YES_NO_OPTION);
		if(n==0){
			showMessage("Thx for playing  \n              ----Steven Wang");
		    System.exit(-1);
		}
	}

	public static int inputMessage(String test,String b,int ak){  //single input message
		int d = -1;

		String a ="Hello Mr."+Player1.name +"        "+Player1.money+"$              "+Player1.days+"days left!      " + Player1.getSpace() + " space";
		a+=test;
		do{
			try{
        String c = JOptionPane.showInputDialog(a);

		Scanner r = new Scanner(c);
		 if(!r.hasNextInt()) {
         showMessage(b);
		 continue;
	    }
         d = new Integer(c);
		 if(0<=d&&d<=ak){
         break;
		 }
		    showMessage(b);
			}catch(Exception e){
				endGame();
			}

		}while(0>d||d>ak);

		return d;
    }
	public static int find(String[] arr,String str){
		boolean flag=false;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(str)) {
				flag=true;
				return i;
			}
		}
		if (flag==false) {
			return -1;
		}
		return -1;
	}
}
