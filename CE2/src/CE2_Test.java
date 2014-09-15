import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class CE2_Test {
	
	@Test
	public void testExecuteCommand() throws IOException{
		
		//testAddCmd("add test", "added to null: \"i love dao suan\"" , "i love dao suan");
		
		testCheckContains("checkContains","test not found","test");
		testCheckContains("checkContains","null string entered","");
		
	}

	public void testCheckContains(String description, String expected, String userInput) throws IOException{
		
		assertEquals(description, expected,CE2.checkContains(userInput));

		
	}

	
	
}
