//Ryan Melville , Yuan Wen AI Prog 4

package Unary;
import Main.Item;

import java.util.ArrayList;

import Main.Bag;

//An example line would be "A p q r s". This means that the value assigned to item A must be either p or q or r or s.


public abstract class AbsUnaryConstraint {
	TypeUnaryConstraint type;
	Item i;
	ArrayList<Bag> bags;
	
	AbsUnaryConstraint(TypeUnaryConstraint type, Item i, ArrayList<Bag> b){
		this.type = type;
		this.i = i;
		bags = b;
				
	}

	public TypeUnaryConstraint getType() {
		return type;
	}

	public void setType(TypeUnaryConstraint type) {
		this.type = type;
	}

	public Item getItem() {
		return i;
	}

	public void setI(Item i) {
		this.i = i;
	}

	public ArrayList<Bag> getBags() {
		return bags;
	}

	public void setBags(ArrayList<Bag> bags) {
		this.bags = bags;
	}
}

