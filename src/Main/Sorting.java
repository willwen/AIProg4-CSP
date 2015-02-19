package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BinaryOperator;

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

	// /try to put A in Bag a
	public boolean moveIsValid(Item item, Bag bag,
			HashMap<Item, ArrayList<Bag>> map) {
		boolean didMoveHappen = false;
		for (AbsBinaryConstraint b : binaryConstraints) {
			if (b.getType() == TypeBinaryConstraint.nonEqual) {// b: A, B
				// A and B cannot be in the same bag.
				Item i1 = b.getVariables().get(0); // A
				Item i2 = b.getVariables().get(1); // B
				if (i1.equals(item)) {
					if (map.get(i2).size() == 1) {
						if (map.get(i2).get(0).equals(bag)) {
							return false;
						}
					} else {
						ArrayList<Bag> newToInsert = new ArrayList<Bag>(); // new
																			// list
																			// to
																			// put
																			// in
						newToInsert.add(bag); // add this move ( this item will
												// be put into that bag, so you
												// can delete all other options
						map.put(item, newToInsert); // overwrite and put this
													// new move in
						if (map.get(i2).contains(bag))
							map.get(i2).remove(bag); // remove the possibility
														// of putting i2 in that
														// bag.
						return true;
					}

				} else if (i2.equals(item)) {
					if (map.get(i1).size() == 1) {
						if (map.get(i1).get(0).equals(bag)) {
							return false;
						}
					} else {
						ArrayList<Bag> newToInsert = new ArrayList<Bag>(); // new
																			// list
																			// to
																			// put
																			// in
						newToInsert.add(bag); // add this move ( this item will
												// be put into that bag, so you
												// can delete all other options
						map.put(item, newToInsert); // overwrite and put this
													// new move in
						if (map.get(i1).contains(bag))
							map.get(i1).remove(bag); // remove the possibility
														// of
						// putting i2 in that bag.
						return true;
					}
				}

			} else if (b.getType() == TypeBinaryConstraint.mutualEx) {
				// The mutual exclusive binary constraints mean that the pair of
				// assignments cannot both be made if one of the assignments is
				// made.
				// For example, a line "A B x y" means x cannot be assigned to A
				// if y is assigned to B, and vice versa.
				MutualExclusiveBinaryConstraint m = (MutualExclusiveBinaryConstraint) b;
				Item val1 = m.getVariables().get(0);
				Item val2 = m.getVariables().get(1);
				Bag bag1 = m.getValues().get(0);
				Bag bag2 = m.getValues().get(1);
				ArrayList<Bag> bags1FromLocal = map.get(val1); // list of bags
																// possible from
																// local Map A
				ArrayList<Bag> bags2FromLocal = map.get(val2);
				if (item.equals(val1)) {
					if (bag1.equals(bag)) { 
						if (bags2FromLocal.size() == 1) { 
							if (bags2FromLocal.contains(bag2)) {
								return false;
							}
						}
						else {
							ArrayList<Bag> newToInsert = new ArrayList<Bag>();
							newToInsert.add(bag);
							map.put(item, newToInsert);
							if (map.get(val2).contains(bag2))
								map.get(val2).remove(bag2);
							return true;
						}
					} else if (bag2.equals(bag)) { // B is the var we messin with
						if (bags1FromLocal.size() == 1) {
							if (bags1FromLocal.contains(bag2)) {
								return false;
							}
						} else {
							ArrayList<Bag> newToInsert = new ArrayList<Bag>();
							newToInsert.add(bag);
							map.put(item, newToInsert);
							return true;
						}
					}
				} else if (item.equals(val2)) {
					if (bag1.equals(bag)) {
						if (bags2FromLocal.size() == 1) { 
							if (bags2FromLocal.contains(bag1)) { 
								return false;
							}
							 else {
									ArrayList<Bag> newToInsert = new ArrayList<Bag>();
									newToInsert.add(bag);
									map.put(item, newToInsert);
									return true;
								}
						}

					} else if (bag2.equals(bag)) {

						if (bags1FromLocal.size() == 1) {
							if (bags1FromLocal.contains(bag1)) {
								return false;
							} else {
								ArrayList<Bag> newToInsert = new ArrayList<Bag>();
								newToInsert.add(bag);
								map.put(item, newToInsert);
								if (map.get(val1).contains(bag1))
									map.get(val1).remove(bag1);
								return true;
							}
						}
					}
				}
			}

		}
		ArrayList <Bag> newToInsert = new ArrayList<Bag>();
		newToInsert.add(bag);
		map.put(item, newToInsert);

		return true;
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
			ArrayList<Bag> oldPossibleBags = initialMap.get(uc.getItem()); // first
																			// pull
																			// out
																			// all
																			// possible
																			// ones
																			// of
																			// this
																			// item.
			if (uc.getType() == TypeUnaryConstraint.inclusive) {
				ArrayList<Bag> newBagToInsert = new ArrayList<Bag>();

				for (Bag b : uc.getBags()) { // rules "A p q r s"
					// only keep the p, q , r ,s as the possible bags for A
					newBagToInsert.add(b);
				}
				initialMap.put(uc.getItem(), newBagToInsert);
			} else if (uc.getType() == TypeUnaryConstraint.exclusive) {
				ArrayList<Bag> newBagToInsert = new ArrayList<Bag>(
						oldPossibleBags);

				for (Bag b : uc.getBags()) { // rules "A p q r s"
					if (newBagToInsert.contains(b)) {
						newBagToInsert.remove(b);
					}
				}
				initialMap.put(uc.getItem(), newBagToInsert);

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
					if (var2Options.contains(b)) {
						sameBags.add(b);
					}
				}
				initialMap.put(bc.getVariables().get(0), sameBags);
				initialMap.put(bc.getVariables().get(1), sameBags);

				// } else if (bc.getType() == TypeBinaryConstraint.nonEqual) {
				// ArrayList<Bag> var1Options = initialMap.get(bc.getVariables()
				// .get(0));
				// ArrayList<Bag> var2Options = initialMap.get(bc.getVariables()
				// .get(1));
				// ArrayList<Bag> var1NewOptions = new ArrayList<Bag>();
				// ArrayList<Bag> var2NewOptions = new ArrayList<Bag>();
				//
				// for (Bag b : var1Options) {
				// if (!var2Options.contains(b)) {
				// var1NewOptions.add(b);
				// }
				// }
				// for (Bag b : var2Options) {
				// if (!var1Options.contains(b)) {
				// var2NewOptions.add(b);
				// }
				// }
				//
				// initialMap.put(bc.getVariables().get(0), var1NewOptions);
				// initialMap.put(bc.getVariables().get(1), var2NewOptions);
				// } else if (bc.getType() == TypeBinaryConstraint.mutualEx) {
				// MutualExclusiveBinaryConstraint m =
				// (MutualExclusiveBinaryConstraint) bc;
				// Item val1 = m.getVariables().get(0);
				// Item val2 = m.getVariables().get(1);
				// Bag bag1 = m.getValues().get(0);
				// Bag bag2 = m.getValues().get(1);
				// ArrayList<Bag> bag1ToInsert = initialMap.get(val1);
				// ArrayList<Bag> bag2ToInsert = initialMap.get(val2);
				// if (bag1ToInsert.contains(bag1)) {
				// bag2ToInsert.remove(val2);
				// }
				// if (bag2ToInsert.contains(bag1)) {
				// bag1ToInsert.remove(val1);
				// }
				// if (bag1ToInsert.contains(bag1)) {
				// bag2ToInsert.remove(val2);
				// }
				// if (bag2ToInsert.contains(bag2)) {
				// bag1ToInsert.remove(val1);
				// }
				// }

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

	public Item itemWithLeastRange(HashMap<Item, ArrayList<Bag>> map) {

		// Shouldn't minCount assure that the hashmap only considers items with
		// more than 1 bag count? Because a choice removes all but correct bag
		// and leaves on behind?

		int minCount = 2;
		int smallestSeen = 100;
		Item minItem = allItems.get(0);
		boolean allDone = false;
		if (map.get(minItem).size() < 2){
			allDone = true;
		}
			
		for (Item i : allItems) {
			if ((map.get(i).size() >= minCount)
					&& (map.get(i).size() < smallestSeen)) {
				smallestSeen = map.get(i).size();
				minItem = i;
			}
		}
		if(minItem != allItems.get(0))
			allDone = false;
		if(allDone)
			return null;
		return minItem;
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

	public void setUnaryConstraints(
			ArrayList<AbsUnaryConstraint> unaryConstraints) {
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
