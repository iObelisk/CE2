import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class CE2_Test {
	
	@Test
	public void testExecuteCommand() throws IOException{
		
		List<String> datas = null;
		
		testCheckContains("checkContains","Input string is empty","", datas);
		
		//testCheckContains("checkContains","\"CE2\" not found","CE2",datas);
		//testCheckContains("checkContains","\"test2\" word found","test2");
		
	}

	public void testCheckContains(String description, String expected, String userInput, List<String> datas) throws IOException{
		
		assertEquals(description, expected,CE2.checkContains(userInput,datas));

		
	}

	
	
}
