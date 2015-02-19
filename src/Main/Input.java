//Ryan Melville , Yuan Wen AI Prog 4
package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import Unary.*;
import Binary.*;
/**
 * Main class, takes in input from command line and creates constraints.
 * passes constraints into Sorting Class.
 * 
 *
 */
public class Input {

	static int section = 0;

	static FitLimit fitLimit;
	static LinkedList<InclusiveUnaryConstraint> incConstraints = new LinkedList<InclusiveUnaryConstraint>();
	static LinkedList<ExclusiveUnaryConstraint> excConstraints = new LinkedList<ExclusiveUnaryConstraint>();
	static LinkedList<EqualBinaryConstraint> eqlBinConstraints = new LinkedList<EqualBinaryConstraint>();
	static LinkedList<NonEqualBinaryConstraint> nonEqlBinConstraints = new LinkedList<NonEqualBinaryConstraint>();
	static LinkedList<MutualExclusiveBinaryConstraint> mutExcBinConstraints = new LinkedList<MutualExclusiveBinaryConstraint>();
	static Sorting sorter = new Sorting();

	public static void main(String[] args) throws IOException {
		
		FileWriter writer = new FileWriter("output.txt");

		getInput(args);

		ArrayList<Item> hand = new ArrayList<Item>(sorter.allItems);

		sorter.initializeMap();

		sorter.trimMapUnary();
		
		System.out.println("Initial HashMap:");

		for (Item i : sorter.allItems) {
			System.out.print("Item: " + i.name + " ");
			System.out.print("Possible Bags:");
			for (Bag b : sorter.initialMap.get(i)) {
				System.out.print(" " + b.name);
			}
			System.out.println();
		}
		System.out.println();

		Item initialItem = sorter.itemWithLeastRange(sorter.initialMap);
		System.out.println("Item with least Range: " + initialItem.name);

		Node tree = new Node(initialItem, sorter.initialMap);
		boolean madeMove = true;

		ArrayList<Bag> emptyBags = new ArrayList<Bag>();

		whileLoop: while (!hand.isEmpty() && madeMove == true) {
			madeMove = false;
			findItemLoop: for (Item i : sorter.allItems) {
				if (i.name == tree.item.name) {
					for (Bag possibleBag : sorter.initialMap.get(i)) {
						if (possibleBag.itemAmount < sorter.fitRule.minItems) {
							emptyBags.add(possibleBag);
						}
					}
					if (emptyBags.size() > 0) {
						for (Bag b : emptyBags) {
							if (sorter.moveIsValid(i, b, tree.localMap)) {
								System.out.println("Assigned Item " + i.name + " to Bag " + b.name);
								System.out.println();
								System.out.println("New HashMap:");
								for (Item val : sorter.allItems) {
									System.out.print("Item: " + val.name + " ");
									System.out.print("Possible Bags:");
									for (Bag bal : tree.localMap.get(val)) {
										System.out.print(" " + bal.name);
									}
									System.out.println();
								}
								System.out.println();

								Item returnedItem = sorter.itemWithLeastRange(tree.localMap);
								
								if (returnedItem == null) {
									madeMove = true;
									break whileLoop;
								}
								tree.item = returnedItem;
								hand.remove(i);
								
								System.out.println("Item with least Range: " + returnedItem.name);

								madeMove = true;

								break findItemLoop;
							}
						}
					} else {
						for (Bag b : sorter.initialMap.get(i)) {
							if (sorter.moveIsValid(i, b, tree.localMap)) {
								// Update Hashmap and assign(clone) to
								// tree.localMap

								for (Item val : sorter.allItems) {
									System.out.print("Item: " + val.name + " ");
									System.out.print("Possible Bags:");
									for (Bag bal : tree.localMap.get(val)) {
										System.out.print(" " + bal.name);
									}
									System.out.println();
								}
								System.out.println();

								Item returnedItem = sorter.itemWithLeastRange(tree.localMap);
								if (returnedItem == null) {
									madeMove = true;
									break whileLoop;
								}
								tree.item = returnedItem;
								hand.remove(i);

								madeMove = true;

								break findItemLoop;
							}
						}
					}
				}
			}
		}

		if (!madeMove) {
			System.out.println("No space left for placing items!");
			writer.append("No solution found");
			System.out.println();
			writer.append('\n');
		} else {

			for (Bag bal : sorter.allBags) {
				System.out.print("Bag: " + bal.name);
				System.out.println("	-> current weight: " + bal.accumWeight);
				System.out.println("	-> max weight: " + bal.maxWeight);
				System.out.println("	-> remaining weight: " + (bal.maxWeight - bal.accumWeight));
				System.out.println("	-> items in bag: " + bal.itemAmount);
			}
			System.out.println();

			for (Bag bag : sorter.allBags) {
				System.out.print(bag.name);
				writer.append(bag.name);
				for (Item item : sorter.allItems) {
					if (tree.localMap.get(item).get(0).equals(bag)) {
						System.out.print(" " + item.name);
						writer.append(' ');
						writer.append(item.name);
					}
				}
				System.out.println();
				writer.append('\n');
				System.out.println("number of items: " + bag.itemAmount);
				writer.append("number of items: " + bag.itemAmount);
				writer.append('\n');
				System.out.println("total weight: " + bag.accumWeight + "/" + bag.maxWeight);
				writer.append("total weight: " + bag.accumWeight + "/" + bag.maxWeight);
				writer.append('\n');
				System.out.println("wasted capacity: " + (bag.maxWeight - bag.accumWeight));
				writer.append("wasted capacity: " + (bag.maxWeight - bag.accumWeight));
				writer.append('\n');
				System.out.println();
				writer.append('\n');
			}
		}

		writer.flush();
		writer.close();

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
				case 1: // Variables (Items)
					Item var = new Item(scanner.next().charAt(0), scanner.nextInt());
					items.add(var);
					System.out.print(var.name + " ");
					System.out.println(var.weight);
					break;
				case 2: // Values (Bags)
					Bag bag = new Bag(scanner.next().charAt(0), scanner.nextInt());
					bags.add(bag);
					System.out.print(bag.name + " ");
					System.out.println(bag.maxWeight);
					break;
				case 3: // Fitting Limits
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

					fitLimit = new FitLimit(lowerLimit, upperLimit);

					sorter.makeFitLimit(lowerLimit, upperLimit);

					System.out.print(lowerLimit + " ");
					System.out.println(upperLimit);
					break;
				case 4: // Unary Inclusive
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

								incConst = new InclusiveUnaryConstraint(TypeUnaryConstraint.inclusive, v, incBags);
								incConstraints.add(incConst);

								sorter.makeUnaryAndAdd(TypeUnaryConstraint.inclusive, v, incBags);

								System.out.println();
							}
						}

					}
					break;
				case 5: // Unary Exclusive
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

								excConst = new ExclusiveUnaryConstraint(TypeUnaryConstraint.exclusive, v, excBags);
								excConstraints.add(excConst);

								sorter.makeUnaryAndAdd(TypeUnaryConstraint.exclusive, v, excBags);

								System.out.println();
							}
						}

					}
					break;
				case 6: // Binary Equals
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

					eqlBinConst = new EqualBinaryConstraint(TypeBinaryConstraint.equal, eqlVariables);
					eqlBinConstraints.add(eqlBinConst);

					sorter.makeBinaryAndAdd(TypeBinaryConstraint.equal, eqlVariables, null);

					System.out.println();

					break;
				case 7: // Binary Not Equals
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

					nonEqlBinConst = new NonEqualBinaryConstraint(TypeBinaryConstraint.nonEqual, nonEqlVariables);
					nonEqlBinConstraints.add(nonEqlBinConst);

					sorter.makeBinaryAndAdd(TypeBinaryConstraint.nonEqual, nonEqlVariables, null);

					System.out.println();

					break;
				case 8: // Binary Mutual Exclusive
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

					mutExBinConst = new MutualExclusiveBinaryConstraint(TypeBinaryConstraint.mutualEx, mutExVariables, mutExValues);
					mutExcBinConstraints.add(mutExBinConst);

					sorter.makeBinaryAndAdd(TypeBinaryConstraint.mutualEx, mutExVariables, mutExValues);

					System.out.println();

					break;
				}

			scanner.close();
		}

		inputFile.close();
		
		System.out.println();

		sorter.setAllItems(items);
		sorter.setAllBags(bags);
	}
}
