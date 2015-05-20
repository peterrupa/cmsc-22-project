/*
  Game object stores all data specific to the whole game.
*/

import java.util.*;

public class Game {
	private String playerName;
	private int foodNumber;
	private int money;
	private Timer timer;
	private ArrayList<Fish> fish = new ArrayList<Fish>();		//arraylist variables are the representations of the entities in the GUI
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private ArrayList<Food> foods = new ArrayList<Food>();

	public Game(String name, int money, int foodNumber, Timer time) {
		this.playerName = name;
		this.money = money;
		this.foodNumber = foodNumber;
		this.timer = time;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public int getFoodNumber() {
		return this.foodNumber;
	}

	public int getMoney() {
		return this.money;
	}

	public ArrayList<Fish> getFish() {
		return this.fish;
	}

	public ArrayList<Coin> getCoins() {
		return this.coins;
	}

	public ArrayList<Food> getFood() {
		return this.foods;
	}
}
