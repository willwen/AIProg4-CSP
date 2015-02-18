package Main;

public class Item {
	int weight;
	char name;
	
	public Item(char name, int weight){
		this.name = name;
		this.weight = weight;
	}
	
//	public int compareTo(Item i){
//		if(this.weight == i.weight
//				&&
//				this.name == i.name)
//			return 0;
//		
//		else
//			return -1;
//		
//	}
	
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
