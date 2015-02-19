//Ryan Melville , Yuan Wen AI Prog 4

package Main;

public class Item {
	int weight;
	char name;
	
	public Item(char name, int weight){
		this.name = name;
		this.weight = weight;
	}
		
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (! (other instanceof Item)) return false;
		Item otherItem = (Item) other;
		if (this.weight == otherItem.weight
				&&
				this.name == otherItem.name) return true;
		else return false;
	}
}
