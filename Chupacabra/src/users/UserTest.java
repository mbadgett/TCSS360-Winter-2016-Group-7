package users;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/* Tests the VolUser class.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 */
public class UserTest {
	
	User testUser1, testUser2;
	@Before
	public void setUp() throws Exception {
		testUser1= new User("1@mail.com", "a", "b");
		testUser2= new User("2@mail.com", "d", "e");
	}

	@Test
	public void testEqualsIsSameObject() {
		assertTrue(testUser1.equals(testUser1));
	}

	
	@Test
	public void testEqualsIsSameEmail() {
		testUser2.setEmail(testUser1.getEmail());
		assertTrue(testUser1.equals(testUser2));//should be equal as we compare emails
	}
	

	@Test
	public void testEqualsIsDifferentUsers() {		
		assertTrue(!testUser1.equals(testUser2));//should be equal as we compare emails
	}
	
	

}
