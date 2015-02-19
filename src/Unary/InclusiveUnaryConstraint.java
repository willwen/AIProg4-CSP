//Ryan Melville , Yuan Wen AI Prog 4

package Unary;
import Main.Item;

import java.util.ArrayList;

import Main.Bag;


public class InclusiveUnaryConstraint extends AbsUnaryConstraint{

	public InclusiveUnaryConstraint(TypeUnaryConstraint type, Item i, ArrayList<Bag> b) {
		super(type, i, b);
	}
	//An example line would be "A p q r s". This means that the value assigned to item A must be either p or q or r or s.

}
