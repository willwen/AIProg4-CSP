package Binary;

import java.util.ArrayList;

import Main.Item;

public class MutualExclusiveBinary extends AbsBinaryConstraints {
	ArrayList <Item> values;
	public MutualExclusiveBinary(TypeBinaryConstraint type, ArrayList<Item> variables) {
		
		super(type, variables);
		values = new ArrayList<Item>();
	}

}
