import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Lab1_Tester {

  @Test
  public void test1() {
    System.out.println("simple encryption");
    String expected = "NDU";
    String[] input = {"1", "2", "3", "###", "encrypt", "AAA"};
    assertEquals(expected, Comms.run(input));
  }

  @Test
  public void test2() {
    System.out.println("medium encryption 1");
    String expected = "ACAAFAEOZFWKBQKPXZOGIKXTNPEBDXWQCZ";
    String[] input = {"3", "1", "2", "SAT", "encrypt", "DO#YOUR#BEST#AND#KEEP#ON#KEEPIN#ON"};
    assertEquals(expected, Comms.run(input));
  }

  @Test
  public void test3() {
    System.out.println("medium encryption 2");
    String expected = "CSHIAWDFGDCOE#EZKJHRWAZDDCBCILON#PKUJEXEXSHINZ";
    String[] input = {"5", "2", "4", "EST", "encrypt", "THE#NATIONAL#ANIMAL#OF#SCOTLAND#IS#THE#UNICORN"};
    assertEquals(expected, Comms.run(input));
  }

  @Test
  public void test4() {
    System.out.println("large encryption");
    String expected = "MUWWXBWRWVMAXBOD#OWYYZDYTYXQEAYVPDS#BBCHBXBASGCHUJFMFKRNKHBQXJZ#JABJNSAUWYXDGWXTCTUFHZCHMGBEYOLKRFUBDDENRTXD#U#UZUQCTD#XUOCMGXTURLXFIVGXWCZHWKSBRNKJQESAUORKAYXD#IZTNTHZVDSXRMPQAFLQPGVURHEHDAYXZYAPDYOYOTSJYUZLAXUTVUXLBWMWMRQHWSPMGVZUHWIJOIDYSYFZQJFPTTSZVDSWRETFGLFAVPVDDUFL";
    String in = "HAIL#TO#THE#BUFF#HAIL#TO#THE#BLUE#HAIL#TO#THE#BUFF#AND#BLUE#ALL#OUR#LIVES#WELL#BE#PROUD#TO#SAY#WE#HAIL#FROM#GW#GO#BIG#BLUE#OH#BY#GEORGE#WERE#HAPPY#WE#CAN#SAY#WERE#GW#HERE#TO#SHOW#THE#WAY#SO#RAISE#HIGH#THE#BUFF#RAISE#HIGH#THE#BLUE#LOYAL#TO#GW#YOU#BET#WERE#LOYAL#TO#GW#FIGHT";
    String[] input = {"5", "1", "3", "BUF", "encrypt", in};
    assertEquals(expected, Comms.run(input));
  }

  @Test
  public void test5() {
    System.out.println("simple decryption");
    String expected = "AAA";
    String[] input = {"1", "2", "3", "###", "decrypt", "NDU"};
    assertEquals(expected, Comms.run(input));
  }

  @Test
  public void test6() {
    System.out.println("medium decryption 1");
    String expected = "DO#YOUR#BEST#AND#KEEP#ON#KEEPIN#ON";
    String[] input = {"3", "1", "2", "SAT", "decrypt", "ACAAFAEOZFWKBQKPXZOGIKXTNPEBDXWQCZ"};
    assertEquals(expected, Comms.run(input));
  }

  @Test
  public void test7() {
    System.out.println("medium decryption 2");
    String expected = "THE#NATIONAL#ANIMAL#OF#SCOTLAND#IS#THE#UNICORN";
    String[] input = {"5", "2", "4", "EST", "decrypt", "CSHIAWDFGDCOE#EZKJHRWAZDDCBCILON#PKUJEXEXSHINZ"};
    assertEquals(expected, Comms.run(input));
  }

  @Test
  public void test8() {
    System.out.println("large encryption");
    String in = "MUWWXBWRWVMAXBOD#OWYYZDYTYXQEAYVPDS#BBCHBXBASGCHUJFMFKRNKHBQXJZ#JABJNSAUWYXDGWXTCTUFHZCHMGBEYOLKRFUBDDENRTXD#U#UZUQCTD#XUOCMGXTURLXFIVGXWCZHWKSBRNKJQESAUORKAYXD#IZTNTHZVDSXRMPQAFLQPGVURHEHDAYXZYAPDYOYOTSJYUZLAXUTVUXLBWMWMRQHWSPMGVZUHWIJOIDYSYFZQJFPTTSZVDSWRETFGLFAVPVDDUFL";
    String expected = "HAIL#TO#THE#BUFF#HAIL#TO#THE#BLUE#HAIL#TO#THE#BUFF#AND#BLUE#ALL#OUR#LIVES#WELL#BE#PROUD#TO#SAY#WE#HAIL#FROM#GW#GO#BIG#BLUE#OH#BY#GEORGE#WERE#HAPPY#WE#CAN#SAY#WERE#GW#HERE#TO#SHOW#THE#WAY#SO#RAISE#HIGH#THE#BUFF#RAISE#HIGH#THE#BLUE#LOYAL#TO#GW#YOU#BET#WERE#LOYAL#TO#GW#FIGHT";
    String[] input = {"5", "1", "3", "BUF", "decrypt", in};
    assertEquals(expected, Comms.run(input));
  }

}
