package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Binary.TypeBinaryConstraint;
import Main.Item;
import Main.Bag;
import Main.Sorting;
import Unary.TypeUnaryConstraint;

public class testSorting {

	@Test
	public void testSorting() {
		fail("Not yet implemented");
	}

	@Test
	public void testSatisfiesUnaryConstraints() {
		fail("Not yet implemented");
	}

	@Test
	public void testTrimMapUnary() {
		//make sure bag and item are allocated in memory and the real thing is always referenced. otherwise, getting a bag with the 'same id' at
		//different address from a hashmap messes up.
		
		Sorting s = new Sorting();
		ArrayList<Bag> b = new ArrayList <Bag>();
		Bag a = new Bag('a', 10);
		
		b.add(a);
		b.add(new Bag ('b', 10));
		b.add(new Bag ('c', 10));
		b.add(new Bag ('d', 10));
		ArrayList<Item> i = new ArrayList <Item>();
		Item c = new Item ('A', 1);
		i.add(c);
		i.add(new Item('B', 1));
		i.add(new Item('C', 1));
		i.add(new Item('D', 1));
		i.add(new Item('E', 1));
		i.add(new Item('F', 1));
		i.add(new Item('G', 1));
		s.setAllBags(b);
		s.setAllItems(i);
		s.initializeMap();
//		ArrayList<Bag> testInclusive = new ArrayList <Bag>();
//		testInclusive.add(a);
		ArrayList<Bag> testExclusive = new ArrayList <Bag>();
		testExclusive.add(a);
//		s.makeUnaryAndAdd(TypeUnaryConstraint.inclusive, c, testInclusive);
		s.makeUnaryAndAdd(TypeUnaryConstraint.exclusive, c, testExclusive);

		s.trimMapUnary();
	}

	@Test
	public void testTrimMapBinary() {
		Sorting s = new Sorting();
		ArrayList<Bag> b = new ArrayList <Bag>();
		Bag a = new Bag('a', 10);
		
		b.add(a);
		b.add(new Bag ('b', 10));
		b.add(new Bag ('c', 10));
		b.add(new Bag ('d', 10));
		ArrayList<Item> i = new ArrayList <Item>();
		Item c = new Item ('A', 1);
		i.add(c);
		i.add(new Item('B', 1));
		i.add(new Item('C', 1));
		i.add(new Item('D', 1));
		i.add(new Item('E', 1));
		i.add(new Item('F', 1));
		i.add(new Item('G', 1));
		s.setAllBags(b);
		s.setAllItems(i);
		s.initializeMap();
		ArrayList<Bag> testExclusive = new ArrayList <Bag>();
		testExclusive.add(a);
//		s.makeUnaryAndAdd(TypeUnaryConstraint.inclusive, c, testInclusive);
		s.makeUnaryAndAdd(TypeUnaryConstraint.exclusive, c, testExclusive);
		s.trimMapUnary();
		s.trimMapBinary();

	}

	@Test
	public void testAddToAllBags() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddToAllItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeFitLimit() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeConstraintAndAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeBinaryAndAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalizeMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testItemWithLeastRange() {
		fail("Not yet implemented");
	}

}
