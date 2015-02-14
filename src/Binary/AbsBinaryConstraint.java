package Binary;
import Main.Item;
import java.util.ArrayList;

//variables = items
//values = bags;

public abstract class AbsBinaryConstraint {
		TypeBinaryConstraint type;
		ArrayList<Item> variables;
		
		AbsBinaryConstraint(TypeBinaryConstraint type, ArrayList<Item> variables){
			this.type = type;
			this.variables = variables;
		}

}
