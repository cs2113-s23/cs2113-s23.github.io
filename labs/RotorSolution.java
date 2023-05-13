public class Rotor {

    
    private String rotorValues;
    private char startChar;
        
    public Rotor(String v, char c){
        this.rotorValues = new String(v);
        this.startChar = c;
        
        while(!this.rotate());
            
    }
    
    public boolean rotate(){
        rotorValues = rotorValues.charAt(rotorValues.length()-1) + rotorValues.substring(0,rotorValues.length()-1);
        
        if(rotorValues.charAt(0) == startChar)
            return true;

        return false;
               
    }
    

    public int indexOf(char c){
        return rotorValues.indexOf(c);
    }

    public char charAt(int idx){
        return rotorValues.charAt(idx);
    }
}
    
