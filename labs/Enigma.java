public class Enigma{

    private String rotorInit[] = {"#GNUAHOVBIPWCJQXDKRYELSZFMT",
        "#EJOTYCHMRWAFKPUZDINSXBGLQV",
        "#BDFHJLNPRTVXZACEGIKMOQSUWY",
        "#NWDKHGXZVRIFJBLMAOPSCYUTQE",
        "#TGOWHLIFMCSZYRVXQABUPEJKND"};


    private Rotor rotors[];
        
    public Enigma(int id1, int id2, int id3, String start){

        rotors = new Rotor[3];
        rotors[0] = new Rotor(rotorInit[id1-1], start.charAt(0));
        rotors[1] = new Rotor(rotorInit[id2-1], start.charAt(1));
        rotors[2] = new Rotor(rotorInit[id3-1], start.charAt(2));
        
    }


    public String decrypt(String message){        
        String output = "";
        for(int i=0;i<message.length();i++){
            output += decryptChar(message.charAt(i));
        }
        return output;

    }

    
    private char decryptChar(char c){
        int outer = rotors[2].indexOf(c);
        char middle = rotors[1].charAt(outer);
        int inner = rotors[2].indexOf(middle);
        char output = rotors[0].charAt(inner);

        rotate();
        return output;
    }

    
    public String encrypt(String message){
        String output = "";
        for(int i=0;i<message.length();i++){
            output += encryptChar(message.charAt(i));
        }
        return output;
    }

    private char encryptChar(char c){
        int inner = rotors[0].indexOf(c);
        char outer = rotors[2].charAt(inner);
        int middle = rotors[1].indexOf(outer);
        char output = rotors[2].charAt(middle);

        rotate();
        return output;
    }

    
    private void rotate(){
        if(rotors[0].rotate()){
            if(rotors[1].rotate()){
                rotors[2].rotate();
            }
        }
    }
    
}
