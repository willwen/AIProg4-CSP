package Binary;

import java.util.ArrayList;

import Main.Item;

public class MutualExclusiveBinaryConstraint extends AbsBinaryConstraint {
	ArrayList <Item> values;
	public MutualExclusiveBinaryConstraint(TypeBinaryConstraint type, ArrayList<Item> variables) {
		
		super(type, variables);
		values = new ArrayList<Item>();
	}

}
