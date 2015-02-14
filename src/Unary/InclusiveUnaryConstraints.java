package Unary;
import Main.Item;

import java.util.ArrayList;

import Main.Bag;


public class InclusiveUnaryConstraints extends AbsUnaryConstraints{

	public InclusiveUnaryConstraints(TypeUnaryConstraint type, Item i, ArrayList<Bag> b) {
		super(type, i, b);
		// TODO Auto-generated constructor stub
	}
	//An example line would be "A p q r s". This means that the value assigned to item A must be either p or q or r or s.

}
