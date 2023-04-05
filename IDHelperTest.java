package CS2043_W23_Team_8;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class IDHelperTest {

	@Test
	void testLoadPeople() {
		ArrayList<Person> l1 = new ArrayList<Person>(3);
		l1.add(new Person(12345679, "Tyler Babineau", false));
		l1.add(new Person(98765432, "Buzz Lightyear", false));
		l1.add(new Person(88888888, "Hung Cao", true));
		
		IDHelper.savePeople(l1);
		
		ArrayList<Person> l2 = IDHelper.loadPeople();
		
		Assert.assertTrue(l2.get(0).equals(l1.get(0)) && l2.get(1).equals(l1.get(1)) && l2.get(2).equals(l1.get(2)));
		
		
		
	}

	@Test
	void testSavePeople() {
		
		ArrayList<Person> l1 = new ArrayList<Person>();
		l1.add(new Person(12345678, "Tyler Babineau", false));
		IDHelper.savePeople(l1);
		
		ArrayList<Person> l2 = IDHelper.loadPeople();
		
		Assert.assertTrue(l2.get(0).compareTo(l1.get(0)) == 0);
	}
	
}
