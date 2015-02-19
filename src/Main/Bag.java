package Main;


public class Bag {
	char name;
	int maxWeight;
	int accumWeight;
	int itemAmount;
	
	public Bag (char name, int maxWeight){
		this.name = name;
		this.maxWeight = maxWeight;
		this.accumWeight = 0;
		this.itemAmount = 0;
		
	}


	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}



	public int getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public int getAccumWeight() {
		return accumWeight;
	}

	public void setAccumWeight(int accumWeight) {
		this.accumWeight = accumWeight;
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (! (other instanceof Bag)) return false;
		Bag otherBag = (Bag) other;
		if (this.name == otherBag.name
				&&
				this.maxWeight == otherBag.maxWeight) return true;
		else return false;
	}
	
	
}
