package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author B
 *
 */
class Bot {
	public String name;
	public Pos curPos;
	public List<Pos> path;
	public Bot() {
		this.name = "";
		this.curPos = new Pos();
		this.path = new ArrayList();
	}

}

class Pos {
	public char row;
	public int col;

	public Pos() {
		this.row = 'Z';
		this.col = -1;
	}
	public Pos(char row,int col) {
		this.row = row;
		this.col = col;
	}

}

class Cell {
	List<String> botNames;
	Bot currentBot;

	public Cell() {
		this.botNames = new ArrayList<String>();
	}

}

class Map {
	
	private static final int CYCLE_STEP = 3;

	private static final int MAP_SIZE = 5;
	
	private List<Bot> bots = new ArrayList();
	
	private int current = 0;
	
	public HashMap<String, List<Cell>> map = new HashMap<>();

	public void init() {
		char key = 'A';
		for (int i = 0; i < MAP_SIZE; i++) {
			List<Cell> value = new ArrayList<Cell>();
			for (int j = 0; j < MAP_SIZE; j++) {
				value.add(new Cell());
			}
			map.put(Character.toString(key), value);
			key++;
		}
	}

	/**
	 * Function to add path of a bot into map
	 * 
	 * @param bot
	 * @param destination
	 */
	public void addPath(Bot bot, int destination) {
		
		if (bot.name.compareTo("E") > 0) {
			char key = (char) (bot.name.charAt(0) - 5);
			List<Cell> lst = this.map.get(Character.toString(key));
			for (int i = MAP_SIZE-1; i >= destination ; i--) {
				lst.get(i).botNames.add(bot.name);
				bot.path.add(new Pos(key,i));
			}
			this.map.replace(Character.toString(key), lst);
			key++;
			while(key <= 'E') {
				List<Cell> value = this.map.get(Character.toString(key));
				value.get(destination).botNames.add(bot.name);
				this.map.replace(Character.toString(key), value);
				key++;
			}
		}else {
			char key = (char) (bot.name.charAt(0));
			List<Cell> lst = this.map.get(Character.toString(key));
			for (int i = 0; i <= destination ; i++) {
				lst.get(i).botNames.add(bot.name);
				bot.path.add(new Pos(key,i));
			}
			this.map.replace(Character.toString(key), lst);
			key++;
			while(key <= 'E') {
				List<Cell> value = this.map.get(Character.toString(key));
				value.get(destination).botNames.add(bot.name);
				this.map.replace(Character.toString(key), value);
				key++;
			}
		}
		if (!this.bots.contains(bot)){
			this.bots.add(bot);
		}
	}

	/**
	 * Function to remove visted pos of a bot in map
	 * 
	 * @param bot
	 * @param destination
	 */
	public void removePath(Bot bot, Pos visited) {

	}

	/**
	 * Function to remove all path of a bot in map
	 * 
	 * @param bot
	 * @param destination
	 */
	public void removeAllPath(Bot bot) {

	}

	public void display() {
		char key = 'A';
		for (int i = 0; i < MAP_SIZE; i++) {
			System.out.print(" "+key+" ");
			List<Cell> row = map.get(Character.toString(key));
			for (int j = 0; j < MAP_SIZE; j++) {
				if (row.get(j).botNames.isEmpty()) {
					System.out.print(" - ");
				} else {
					System.out.print(" "+row.get(j).botNames.get(0)+" ");
				}
			}
			char newkey = (char) (key+5);
			System.out.print(" "+newkey+" ");
			System.out.println();
			key++;
		}
	}
	
	public void run() {
		// 1 turn first
		for (int i = 0; i < bots.size(); i++) {
			for (int j = 0; j < CYCLE_STEP; j++) {
				Bot bot = bots.get(i);
				System.out.println(bot.name +" : "+bot.path.get(j).row + " - "+bot.path.get(j).col);
				bot.curPos = new Pos(bot.path.get(j).row, bot.path.get(j).col);
			}
		}
		
	}
}

public class LogicService {
	public static void main(String args[]) {
		Map m = new Map();
		m.init();
		Bot bot = new Bot();
		bot.name = "H";
		m.addPath(bot , 1);
		Bot bot1 = new Bot();
		bot1.name = "B";
		m.addPath(bot1, 3);
		m.display();
		System.out.println();
		m.run();
	}
}
