package Main;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	Item item;
	HashMap<Item, ArrayList<Bag>> localMap;
	    
    Node (Item item, HashMap<Item, ArrayList<Bag>> hashmap){
		this.item = item;
		this.localMap = new HashMap<Item, ArrayList<Bag>> (hashmap);
	}
}
