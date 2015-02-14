package Main;
import java.util.ArrayList;


public class Bag {
	char name;
	ArrayList<Item> items;
	int maxWeight;
	int accumWeight;
	
	Bag (char name, int maxWeight){
		this.name = name;
		this.maxWeight = maxWeight;
		this.items = new ArrayList<Item>();
		this.accumWeight = 0;
		
	}
	
	public void addToBag(Item i){
		items.add(i);
		accumWeight += i.weight;
	}
}
