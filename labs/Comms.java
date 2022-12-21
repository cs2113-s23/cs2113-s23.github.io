import java.util.Scanner;

/**
 * Creates an Enigma machine based on command-line arguments, and reads the message
 * from the user on STDIN. Decrypts/Encrypts using the enigma class.
 */
public class Comms {

  public static String run(String[] args) {
    
    // Encrypt or decrypt argument
    boolean encrypt = true;
    if( args[4].equals("decrypt") )
      encrypt = false;

    // Rotors
    int id1 = Integer.parseInt(args[0]);
    int id2 = Integer.parseInt(args[1]);
    int id3 = Integer.parseInt(args[2]);
    
    // Get the message from the user, and call the appropriate method
    // Scanner scan = new Scanner(System.in);
    // String message = scan.next();

    String message = args[5];

    // Call the Enigma's constructor to build the machine
    Enigma enigma = new Enigma(id1, id2, id3, args[3]);

    // Encrypt or Decrypt
    if( encrypt )
      return enigma.encrypt(message);
    else
      return enigma.decrypt(message);
  }
}
