package Main;

import java.util.ArrayList;

public class Node {
	Item item;
	ArrayList<Item> children;
	    
    Node (Item item, ArrayList<Item> children){
		this.item = item;
		this.children = children;
	}
}
