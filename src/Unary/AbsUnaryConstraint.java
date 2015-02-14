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
}

