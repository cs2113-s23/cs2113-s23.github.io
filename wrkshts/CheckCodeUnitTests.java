
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.*;

public class UnitTestExample {

	@Before
	public void before() {
		System.out.println();
	}

	@AfterClass
	public void after() {
		System.out.println();
	}

	@Test
	public void valid_inputs() {
		assertEquals(true, checkCode("Hi9_jf5D3r"));
	}	

    // your other tests here
}
