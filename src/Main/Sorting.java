package Main;

import java.util.ArrayList;
import java.util.HashMap;

import Binary.*;
import Unary.*;

public class Sorting {
	ArrayList<Item> allItems = new ArrayList<Item>();
	ArrayList<Bag> allBags = new ArrayList<Bag>();
	FitLimit fitRule;
	ArrayList<AbsUnaryConstraint> unaryConstraints = new ArrayList<AbsUnaryConstraint>();
	ArrayList<AbsBinaryConstraint> binaryConstraints = new ArrayList<AbsBinaryConstraint>();
	HashMap<Item, ArrayList<Bag>> initialMap = new HashMap<Item, ArrayList<Bag>>();
	HashMap<Item, Bag> finalMap = new HashMap<Item, Bag>();

	public Sorting() {

	}

	// can we put this item i in bag b without breaking a binary constraint?

	public boolean satisfiesUnaryConstraints(Item item, Bag goalBag) {
		for (AbsUnaryConstraint c : unaryConstraints) {
			if (c.getType() == TypeUnaryConstraint.inclusive) {
				if (c.getItem().name == item.name) {
					boolean isFound = false;
					for (Bag b : c.getBags()) {
						if (b.getName() == goalBag.name)
							isFound = true;

					}
					if (!isFound)
						return false;

				}

			} else if (c.getType() == TypeUnaryConstraint.exclusive) {
				if (c.getItem().name == item.name) {
					for (Bag b : c.getBags()) {
						if (b.getName() == goalBag.name)
							return false;

					}
				}

			} else
				throw new RuntimeException("how?");

		}
		return true;

	}



	public void trimMapUnary() {
		for (AbsUnaryConstraint uc : unaryConstraints) {
			ArrayList<Bag> oldPossibleBags = initialMap.get(uc.getItem()); //first pull out all possible ones of this item.
			ArrayList<Bag> newBagToInsert = new ArrayList <Bag> ();
			if (uc.getType() == TypeUnaryConstraint.inclusive) {
				for (Bag b : uc.getBags()) { // rules "A p q r s"
					//only keep the p, q , r ,s as the possible bags for A
					newBagToInsert .add(b);
				}
				initialMap.put(uc.getItem(), newBagToInsert);
			} else if (uc.getType() == TypeUnaryConstraint.exclusive) {
				for (Bag b : uc.getBags()) { // rules "A p q r s"
					if (oldPossibleBags.contains(b)) {
						oldPossibleBags.remove(b);
					}
				}
			}
			// initialMap.put(uc.getItem(), newBagToInsert); //overwrite old
			// map.
			// not needed ^.^ because it removes from the orig bag directly
		}
	}
	
	
	/** 
	 * trims the hashmap with binary constraints
	 * 
	 */
	public void trimMapBinary() {
		for (AbsBinaryConstraint bc : binaryConstraints) {
			if (bc.getType() == TypeBinaryConstraint.equal) {
				// ex, E and F . equal constraint
				// if E can go to {a, b , c} and F can go to { c, d, or e}
				// then both can be set to only {c}

				// ASSUME
				// Binary constraints contain two variables (items).
				// from course website.
				ArrayList<Bag> var1Options = initialMap.get(bc.getVariables()
						.get(0));
				ArrayList<Bag> var2Options = initialMap.get(bc.getVariables()
						.get(1));
				ArrayList<Bag> sameBags = new ArrayList<Bag>();
				for (Bag b : var1Options) {
					if(var2Options.contains(b)){
						sameBags.add(b);
					}
				}
				initialMap.put(bc.getVariables().get(0), sameBags);
				initialMap.put(bc.getVariables().get(1), sameBags);

			}
			else if (bc.getType() == TypeBinaryConstraint.nonEqual){
				ArrayList<Bag> var1Options = initialMap.get(bc.getVariables()
						.get(0));
				ArrayList<Bag> var2Options = initialMap.get(bc.getVariables()
						.get(1));
				ArrayList<Bag> var1NewOptions = new ArrayList<Bag>();
				ArrayList<Bag> var2NewOptions = new ArrayList<Bag>();

				for (Bag b : var1Options) {
					if(!var2Options.contains(b)){
						var1NewOptions.add(b);
					}			
				}
				for (Bag b : var2Options) {
					if(!var1Options.contains(b)){
						var2NewOptions.add(b);
					}			
				}
							
				initialMap.put(bc.getVariables().get(0), var1NewOptions);
				initialMap.put(bc.getVariables().get(1), var2NewOptions);
			}
			else if(bc.getType() == TypeBinaryConstraint.mutualEx){
				MutualExclusiveBinaryConstraint m = (MutualExclusiveBinaryConstraint) bc;
				Item val1 = m.getVariables().get(0);
				Item val2 = m.getVariables().get(1);
				Bag bag1 = m.getValues().get(0);
				Bag bag2 = m.getValues().get(1);
				ArrayList <Bag> bag1ToInsert = initialMap.get(val1);
				ArrayList <Bag> bag2ToInsert = initialMap.get(val2);
				if (bag1ToInsert.contains(bag1)){
					bag2ToInsert.remove(val2);
				}
				if (bag2ToInsert.contains(bag1)){
					bag1ToInsert.remove(val1);
				}
				if (bag1ToInsert.contains(bag1)){
					bag2ToInsert.remove(val2);
				}
				if(bag2ToInsert.contains(bag2)){
					bag1ToInsert.remove(val1);
				}
			}
			
		}

	}

	public void addToAllBags(Bag b) {
		allBags.add(b);
	}

	public void addToAllItems(Item i) {
		allItems.add(i);
	}

	public void makeFitLimit(int min, int max) {
		fitRule = new FitLimit(min, max);
	}

	public void makeUnaryAndAdd(TypeUnaryConstraint type, Item item,
			ArrayList<Bag> b) {

		if (type == TypeUnaryConstraint.inclusive)
			unaryConstraints.add(new InclusiveUnaryConstraint(type, item, b));
		else if (type == TypeUnaryConstraint.exclusive)
			unaryConstraints.add(new ExclusiveUnaryConstraint(type, item, b));
		return;
	}

	public void makeBinaryAndAdd(TypeBinaryConstraint type, ArrayList<Item> i,
			ArrayList<Bag> j) {
		if (type == TypeBinaryConstraint.equal) {
			binaryConstraints.add(new EqualBinaryConstraint(type, i));
		} else if (type == TypeBinaryConstraint.nonEqual) {
			binaryConstraints.add(new EqualBinaryConstraint(type, i));

		} else if (type == TypeBinaryConstraint.mutualEx) {
			binaryConstraints.add(new MutualExclusiveBinaryConstraint(type, i,
					j));

		}
	}

	public void initializeMap() {
		for (Item i : allItems) {
			initialMap.put(i, allBags);
		}
	}

	public Item itemWithLeastRange() {
		int maxCount = 0;
		Item maxItem = allItems.get(0);
		for (Item i : allItems) {
			if (initialMap.get(i).size() > maxCount) {
				maxItem = i;
				maxCount = initialMap.get(i).size();
			}
		}
		return maxItem;
	}

	public ArrayList<Item> getAllItems() {
		return allItems;
	}

	public void setAllItems(ArrayList<Item> allItems) {
		this.allItems = allItems;
	}

	public ArrayList<Bag> getAllBags() {
		return allBags;
	}

	public void setAllBags(ArrayList<Bag> allBags) {
		this.allBags = allBags;
	}

	public FitLimit getFitRule() {
		return fitRule;
	}

	public void setFitRule(FitLimit fitRule) {
		this.fitRule = fitRule;
	}

	public ArrayList<AbsUnaryConstraint> getUnaryConstraints() {
		return unaryConstraints;
	}

	public void setUnaryConstraints(ArrayList<AbsUnaryConstraint> unaryConstraints) {
		this.unaryConstraints = unaryConstraints;
	}

	public ArrayList<AbsBinaryConstraint> getBinaryConstraints() {
		return binaryConstraints;
	}

	public void setBinaryConstraints(
			ArrayList<AbsBinaryConstraint> binaryConstraints) {
		this.binaryConstraints = binaryConstraints;
	}

	public HashMap<Item, ArrayList<Bag>> getInitialMap() {
		return initialMap;
	}

	public void setInitialMap(HashMap<Item, ArrayList<Bag>> initialMap) {
		this.initialMap = initialMap;
	}

	public HashMap<Item, Bag> getFinalMap() {
		return finalMap;
	}

	public void setFinalMap(HashMap<Item, Bag> finalMap) {
		this.finalMap = finalMap;
	}
	
	

}
