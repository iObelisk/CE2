import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class CE2_Test {
	
	@Test
	public void testExecuteCommand() throws IOException{
		
		testCheckContains("checkContains","\"CE2\" not found","CE2");
		testCheckContains("checkContains","Input string is empty","");
		testCheckContains("checkContains","\"test2\" word found","test2");
		
	}

	public void testCheckContains(String description, String expected, String userInput) throws IOException{
		
		assertEquals(description, expected,CE2.checkContains(userInput));

		
	}

	
	
}
