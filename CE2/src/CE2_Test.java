import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

public class CE2_Test {
	

	@Test
	public void testExecuteCommand() throws IOException{
		
		/********** Test cases for checkContains Function *****************/
		
		List<String> datas = null;
		
		testCheckContains("checkContains","Input string is empty","", datas);
		
		datas = new LinkedList<String>();
		datas.add("TestCase1");
		testCheckContains("checkContains","\"CE2\" not found","CE2",datas);
		testCheckContains("checkContains","\"TestCase1\" word found","TestCase1",datas);

		
		
		/********** Test cases for sortContents Function *****************/
		
		List<String> sortList = null;
		
		testSortContents("TestSortContains","Display List is empty", sortList);
		
		sortList = new LinkedList<String>();
		sortList.add("d");
		sortList.add("e");
		sortList.add("f");
		sortList.add("g");
		sortList.add("a");
		testSortContents("TestSortContains","Display List is sorted", sortList);			
	}

	public void testCheckContains(String description, String expected, 
			String userInput, List<String> datas) throws IOException{
		
		assertEquals(description, expected,CE2.checkContains(userInput,datas));		
	}
	
	public void testSortContents(String description, String expected,
			List<String> datas) throws IOException{
		
		assertEquals(description, expected,CE2.sortContents(datas));		
	}
	
	

	
	
}
