package Unary;
import Main.Item;

import java.util.ArrayList;

import Main.Bag;


public class ExclusiveUnaryConstraints extends AbsUnaryConstraints{
	//	An example line would be "C u v w x". This means that the value assigned to item C must be neither u nor v nor w nor x.
	public ExclusiveUnaryConstraints(TypeUnaryConstraint type, Item i, ArrayList<Bag> b) {
		super(type, i, b);
		// TODO Auto-generated constructor stub
	}


}
