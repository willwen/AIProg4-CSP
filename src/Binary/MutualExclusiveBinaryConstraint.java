package Binary;

import java.util.ArrayList;

import Main.Bag;
import Main.Item;

public class MutualExclusiveBinaryConstraint extends AbsBinaryConstraint {
	ArrayList<Bag> values;
	public MutualExclusiveBinaryConstraint(TypeBinaryConstraint type, ArrayList<Item> variables, ArrayList<Bag> values) {
		
		super(type, variables);
		values = new ArrayList<Bag>();
	}

}
