package Binary;
import Main.Item;
import java.util.ArrayList;

//variables = items
//values = bags;

public abstract class AbsBinaryConstraints {
		TypeBinaryConstraint type;
		ArrayList<Item> variables;
		
		AbsBinaryConstraints(TypeBinaryConstraint type, ArrayList<Item> variables){
			this.type = type;
			this.variables = variables;
		}

}
