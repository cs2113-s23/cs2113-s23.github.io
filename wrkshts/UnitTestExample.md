---
layout: worksheet
permalink: /worksheet/UnitTestExample
showsolution: False
---
```

import static org.junit.Assert.*;
import java.io.*;

public class UnitTestExample {

  private Integer number;
  private String planet = "Earth";
  private String satellite = "moon";

  @Test
  public void test1() {
    System.out.println("This is the first test case and it passes");
    String expectedName = "Earth";
    String resultName = this.planet; // or would be something a method could return, etc
    assertEquals(expectedName, resultName);
  }

  @Test
  public void test2() {
    System.out.println("This is the second test case and it fails");
    String expectedName = "Moon";
    String resultName = this.satellite; // or would be something a method could return, etc
    assertEquals(expectedName, resultName);
  }  

  @Test
  public void test3() {
    System.out.println("This is the third test case and it fails");
    String outputCollected = "";

    String command = "java -jar checkstyle-9.2.1-all.jar -c ./CS1111_checks.xml UnitTestExample.java";
    try {
      Process process = Runtime.getRuntime().exec(command);
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) 
        outputCollected += line;
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String expectedName = "Moon";
    String resultName = line; 
    assertEquals(expectedName, resultName);
  }

  // no main method

}

```