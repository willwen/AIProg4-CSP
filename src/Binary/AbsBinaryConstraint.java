package Binary;

import Main.Item;
import java.util.ArrayList;

//variables = items
//values = bags;

public abstract class AbsBinaryConstraint {
	TypeBinaryConstraint type;
	ArrayList<Item> variables;

	AbsBinaryConstraint(TypeBinaryConstraint type, ArrayList<Item> variables) {
		this.type = type;
		if (type != TypeBinaryConstraint.mutualEx) {
			if (variables.size() == 2)
				this.variables = variables;
			else
				throw new RuntimeException("why more than 2 items?");
		} else {
			if (variables.size() == 4)
				this.variables = variables;
			else
				throw new RuntimeException("why more than 4 items?");

		}
	}

	public TypeBinaryConstraint getType() {
		return type;
	}

	public void setType(TypeBinaryConstraint type) {
		this.type = type;
	}

	public ArrayList<Item> getVariables() {
		return variables;
	}

	public void setVariables(ArrayList<Item> variables) {
		this.variables = variables;
	}

}
