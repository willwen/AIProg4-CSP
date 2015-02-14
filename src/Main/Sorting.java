package Main;
import java.util.ArrayList;

import Binary.*;
import Unary.*;


public class Sorting {
	ArrayList <Item> allItems = new ArrayList<Item>();
	ArrayList <Bag> allBags = new ArrayList<Bag>();
	FitLimit fitRule;
	ArrayList <AbsUnaryConstraints> unaryConstraints = new ArrayList<AbsUnaryConstraints>();
	ArrayList <AbsBinaryConstraints> binaryConstraints = new ArrayList<AbsBinaryConstraints>();

	Sorting(){
		
	}
	
	public boolean passesRules(Item item, Bag toBag){
		return false;
	}
	
	public void addToAllBags(Bag b){
		allBags.add(b);
	}
	public void addToAllItems(Item i){
		allItems.add(i);
	}
	public void makeFitLimit(int min, int max){
		fitRule = new FitLimit (min, max);
	}
	public void makeConstraintAndAdd(TypeUnaryConstraint type, Item item, ArrayList<Bag> b){
			
		if(type == TypeUnaryConstraint.inclusive)
			unaryConstraints.add(new InclusiveUnaryConstraints(type, item, b));
		else if(type == TypeUnaryConstraint.exclusive)
			unaryConstraints.add(new ExclusiveUnaryConstraints(type, item, b));
		return;
	}
	public void makeBinaryAndAdd(TypeBinaryConstraint type,  ArrayList<Item> i){
		if (type == TypeBinaryConstraint.equal){
			binaryConstraints.add(new EqualBinaryConstraint(type, i));
		}
		else if (type == TypeBinaryConstraint.nonEqual){
			binaryConstraints.add(new EqualBinaryConstraint(type, i));

		}
		else if (type == TypeBinaryConstraint.mutualEx){
			binaryConstraints.add(new MutualExclusiveBinary(type, i));

		}
	}
	
	
	
	
	
	
}
