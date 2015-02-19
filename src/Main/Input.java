package Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import Unary.*;
import Binary.*;

public class Input {

	static int section = 0;

	static LinkedList<FitLimit> fitLimits = new LinkedList<FitLimit>();
	static LinkedList<InclusiveUnaryConstraint> incConstraints = new LinkedList<InclusiveUnaryConstraint>();
	static LinkedList<ExclusiveUnaryConstraint> excConstraints = new LinkedList<ExclusiveUnaryConstraint>();
	static LinkedList<EqualBinaryConstraint> eqlBinConstraints = new LinkedList<EqualBinaryConstraint>();
	static LinkedList<NonEqualBinaryConstraint> nonEqlBinConstraints = new LinkedList<NonEqualBinaryConstraint>();
	static LinkedList<MutualExclusiveBinaryConstraint> mutExcBinConstraints = new LinkedList<MutualExclusiveBinaryConstraint>();
	static Sorting sorter = new Sorting();

	public static void main(String[] args) throws IOException {
		getInput(args);
		
		ArrayList<Item> hand = new ArrayList<Item>(sorter.allItems);
		
		sorter.initializeMap();
		
		sorter.trimMapUnary();
		
		for(Item i : sorter.allItems){
			System.out.print("Item: " + i.name + " ");
			System.out.print("Possible Bags:");
			for(Bag b : sorter.initialMap.get(i)){
				System.out.print(" " + b.name);
			}
			System.out.println();
		}
		System.out.println();

		Item initialItem = sorter.itemWithLeastRange(sorter.initialMap);
		System.out.println("Item with least Range: " + initialItem.name);
		
//		HashMap <Item, ArrayList <Bag>> test = new HashMap <Item , ArrayList<Bag>>(sorter.initialMap);
//		sorter.moveIsValid(initialItem, sorter.initialMap.get(initialItem).get(0), test);
//		
//		for(Item i : sorter.allItems){
//			System.out.print("Item: " + i.name + " ");
//			System.out.print("Possible Bags:");
//			for(Bag b : test.get(i)){
//				System.out.print(" " + b.name);
//			}
//			System.out.println();
//		}
		
		Node tree = new Node(initialItem, sorter.initialMap);
		whileLoop:
		while(!hand.isEmpty()){
			findItemLoop:
			for(Item i : sorter.allItems){
				if( i.name == tree.item.name){
					for(Bag b : sorter.initialMap.get(i)){
						//Willsfunction(i, b, localmap) return true or false if possible placement based on constraints
						if(sorter.moveIsValid(i, b, tree.localMap)){
							// Update Hashmap and assign(clone) to tree.localMap
							
							
							for(Item val : sorter.allItems){
								System.out.print("Item: " + val.name + " ");
								System.out.print("Possible Bags:");
								for(Bag bal : tree.localMap.get(val)){
									System.out.print(" " + bal.name);
								}
								System.out.println();
							}
							System.out.println();
							
							Item returnedItem = sorter.itemWithLeastRange(tree.localMap);
							if(returnedItem == null)
								break whileLoop;
							tree.item = returnedItem;
							hand.remove(i);
							
							break findItemLoop;		
						}						
					}
				}
			}
		}
	}
	


	public static void getInput(String[] args) throws IOException {
		Scanner inputFile = new Scanner(new File(args[0]));
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Bag> bags = new ArrayList<Bag>();

		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();

			Scanner scanner = new Scanner(line);
			if (scanner.hasNext("#####")) {
				section++;
				System.out.print("Section " + section + ":");
				System.out.println(line);
			} else
				switch (section) {
				case 1:  // Variables (Items)
					Item var = new Item(scanner.next().charAt(0),
							scanner.nextInt());
					items.add(var);
					System.out.print(var.name + " ");
					System.out.println(var.weight);
					break;
				case 2:  // Values (Bags)
					Bag bag = new Bag(scanner.next().charAt(0),
							scanner.nextInt());
					bags.add(bag);
					System.out.print(bag.name + " ");
					System.out.println(bag.maxWeight);
					break;
				case 3:  // Fitting Limits 
					int lowerLimit = -1;
					int upperLimit = 2147483647;
					scanner.useDelimiter(" ");

					while (scanner.hasNextInt()) {
						if (lowerLimit < 0) {
							lowerLimit = scanner.nextInt();
						} else {
							upperLimit = scanner.nextInt();
						}
					}

					FitLimit limit = new FitLimit(lowerLimit, upperLimit);
					fitLimits.add(limit);

					sorter.makeFitLimit(lowerLimit, upperLimit);
					
					System.out.print(lowerLimit + " ");
					System.out.println(upperLimit);
					break;
				case 4:  // Unary Inclusive
					while (scanner.hasNext()) {
						char next = scanner.next().charAt(0);
						for (Item v : items) {
							InclusiveUnaryConstraint incConst;
							scanner.useDelimiter(" ");

							if (v.name == next) {
								System.out.print(v.name + " ");
								ArrayList<Bag> incBags = new ArrayList<Bag>();

								while (scanner.hasNext()) {
									char temp = scanner.next().charAt(0);
									for (Bag b : bags) {
										
										if (b.name == temp) {
											System.out.print(temp + " ");
											incBags.add(b);

										}
									}
								}

								incConst = new InclusiveUnaryConstraint(
										TypeUnaryConstraint.inclusive, v,
										incBags);
								incConstraints.add(incConst);
								
								sorter.makeUnaryAndAdd(TypeUnaryConstraint.inclusive, v, incBags);

								System.out.println();
							}
						}

					}
					break;
				case 5:  // Unary Exclusive
					while (scanner.hasNext()) {
						char next = scanner.next().charAt(0);
						for (Item v : items) {
							ExclusiveUnaryConstraint excConst;
							scanner.useDelimiter(" ");
							
							if (v.name == next) {
								System.out.print(v.name + " ");
								ArrayList<Bag> excBags = new ArrayList<Bag>();

								while (scanner.hasNext()) {
									
									char temp = scanner.next().charAt(0);
									for (Bag b : bags) {
										
										if (b.name == temp) {
											System.out.print(temp + " ");
											excBags.add(b);

										}
									}
								}

								excConst = new ExclusiveUnaryConstraint(
										TypeUnaryConstraint.exclusive, v,
										excBags);
								excConstraints.add(excConst);
								
								sorter.makeUnaryAndAdd(TypeUnaryConstraint.exclusive, v, excBags);

								System.out.println();
							}
						}

					}
					break;
				case 6:  // Binary Equals
					EqualBinaryConstraint eqlBinConst;
					ArrayList<Item> eqlVariables = new ArrayList<Item>();

					scanner.useDelimiter(" ");

					while (scanner.hasNext()) {
						char next = scanner.next().charAt(0);

						for (Item i : items) {
							if (i.name == next) {
								eqlVariables.add(i);

								System.out.print(next + " ");
							}
						}
					}

					eqlBinConst = new EqualBinaryConstraint(
							TypeBinaryConstraint.equal, eqlVariables);
					eqlBinConstraints.add(eqlBinConst);
					
					sorter.makeBinaryAndAdd(TypeBinaryConstraint.equal, eqlVariables, null);

					System.out.println();

					break;
				case 7:  // Binary Not Equals
					NonEqualBinaryConstraint nonEqlBinConst;
					ArrayList<Item> nonEqlVariables = new ArrayList<Item>();

					scanner.useDelimiter(" ");

					while (scanner.hasNext()) {
						char next = scanner.next().charAt(0);

						for (Item i : items) {
							if (i.name == next) {
								nonEqlVariables.add(i);

								System.out.print(next + " ");
							}
						}
					}

					nonEqlBinConst = new NonEqualBinaryConstraint(
							TypeBinaryConstraint.nonEqual, nonEqlVariables);
					nonEqlBinConstraints.add(nonEqlBinConst);
					
					sorter.makeBinaryAndAdd(TypeBinaryConstraint.nonEqual, nonEqlVariables, null);

					System.out.println();

					break;
				case 8:  // Binary Mutual Exclusive
					MutualExclusiveBinaryConstraint mutExBinConst;
					ArrayList<Item> mutExVariables = new ArrayList<Item>();
					ArrayList<Bag> mutExValues = new ArrayList<Bag>();

					scanner.useDelimiter(" ");

					while (scanner.hasNext()) {
						char next = scanner.next().charAt(0);

						
						for (Item i : items) {
							if (i.name == next) {
								mutExVariables.add(i);

								System.out.print(next + " ");
							}
						}
						
						for (Bag b : bags) {
							if (b.name == next) {
								mutExValues.add(b);

								System.out.print(next + " ");
							}
						}
					}

					mutExBinConst = new MutualExclusiveBinaryConstraint(
							TypeBinaryConstraint.mutualEx, mutExVariables, mutExValues);
					mutExcBinConstraints.add(mutExBinConst);
					
					sorter.makeBinaryAndAdd(TypeBinaryConstraint.mutualEx, mutExVariables, mutExValues);

					System.out.println();

					break;
				}
			
			scanner.close();
		}

		inputFile.close();
		
		sorter.setAllItems(items);
		sorter.setAllBags(bags);
	}
}
