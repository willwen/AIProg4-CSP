package Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

	public static void main(String[] args) throws IOException {
		getInput(args);
	}

	public static void getInput(String[] args) throws IOException {
		Scanner inputFile = new Scanner(new File(args[0]));
		LinkedList<Item> items = new LinkedList<Item>();
		LinkedList<Bag> bags = new LinkedList<Bag>();

		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();

			Scanner scanner = new Scanner(line);
			if (scanner.hasNext("#####")) {
				section++;
				System.out.print("Section " + section + ":");
				System.out.println(line);
			} else
				switch (section) {
				case 1:
					Item var = new Item(scanner.next().charAt(0),
							scanner.nextInt());
					items.add(var);
					System.out.print(var.name + " ");
					System.out.println(var.weight);
					break;
				case 2:
					Bag bag = new Bag(scanner.next().charAt(0),
							scanner.nextInt());
					bags.add(bag);
					System.out.print(bag.name + " ");
					System.out.println(bag.maxWeight);
					break;
				case 3:
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

					System.out.print(lowerLimit + " ");
					System.out.println(upperLimit);
					break;
				case 4:
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

								System.out.println();
							}
						}

					}
					break;
				case 5:
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

								System.out.println();
							}
						}

					}
					break;
				case 6:
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

					System.out.println();

					break;
				case 7:
					NonEqualBinaryConstraint nonEqlBinConst;
					ArrayList<Item> nonEqlvariables = new ArrayList<Item>();

					scanner.useDelimiter(" ");

					while (scanner.hasNext()) {
						char next = scanner.next().charAt(0);

						for (Item i : items) {
							if (i.name == next) {
								nonEqlvariables.add(i);

								System.out.print(next + " ");
							}
						}
					}

					nonEqlBinConst = new NonEqualBinaryConstraint(
							TypeBinaryConstraint.nonEqual, nonEqlvariables);
					nonEqlBinConstraints.add(nonEqlBinConst);

					System.out.println();

					break;
				case 8:
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

					System.out.println();

					break;
				}

			scanner.close();
		}

		inputFile.close();
	}
}
