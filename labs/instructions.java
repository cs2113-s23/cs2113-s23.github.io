

1. Implement the comparable interface to allow animals to be sorted by weight
2. Create a class Animal with good OOP 
3. Extend the Animal class into a Tiger class and a Fish class. Note that only Tigers have a furColor, and only fish have numFins
4. Complete the toString method to print out the type of each object, along with all its fields
5. Implement good OOP practices as discussed in class
6. Keep track of the number of animals in the zoo without relying on a method
7. Implement the Comparable interface for animals to compare by weight.
8. Compile against and pass all the tests

public class Zoo {

	int[] weights; //all animals have a weight
	String[] furColors; //will have null values for fish
	int[] numFins; //these values are only meaningful for fish
	String[] types; //the type of the animals

	public void feed(int food){
		for(int i = 0; i < weights.length; i++){
			if (types[i].equals("Fish"))
				weights[i] += (int)(food * 0.5 + 11);
			else if (types[i].equals("Tiger"))
				weights[i] += (int)(food / 0.3);
		}
	}

	public void move(int distance){
		for(int i = 0; i < weights.length; i++)
			weights[i] -= 0.1 * distance;
	}


	public String roster(){
		String result = "Here are all the animals: ";
		for(int i = 0; i < types.length; i++)
			result += types[i] + "\n";
		result += "There are " + types.length + " animals total.\n"
		return result;
	}

}