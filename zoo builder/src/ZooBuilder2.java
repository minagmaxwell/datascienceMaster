import java.util.ArrayList;

class Animal {
	String name;
	String species;
	int age;
	Animal(){
		this.name = "unnamed animal";
		this.species = "unnamed specie";
		this.age = 0;
		}
	Animal(String name, String species, int age){
		this.name = name;
		this.species = species;
		this.age = age;
	}
	void printInfo() {
		System.out.println(this.name +", " + this.species +", " + this.age);
	}
}
 
class Panda extends Animal{
	String name;
	String species;
	int age;
	Panda(){
		this.name = "Spot";
		this.species = "Panda";
		this.age = 0;
		}
	Panda(String name, int age){
		super(name, "Panda" ,age);
	}
}
 
class Elephant extends Animal{
	String name;
	String species;
	int age;
	Elephant(){
		this.name = "Elle";
		this.species = "Elephant";
		this.age = 0;
		}
	Elephant(String name, int age){
		super(name, "Elephant" ,age);
	}
}

class Zoo {
	Animal[] animalArray;
	int i;
	Zoo(){
		i = 0;
		animalArray = new Animal[5];
		}
	void addAnimal(Animal a) {
		animalArray[i] = a;
		i +=1; 
	}
	
	void printAllInfo() {
		for(int i = 0;i<animalArray.length;i++) {
			animalArray[i].printInfo();
		}
	}
}

public class ZooBuilder {
    public static void main(String[] args){
    	Animal pandy = new Panda();
    	Animal ele = new Elephant();
        Zoo my_zoo = new Zoo();
        
        my_zoo.addAnimal(pandy);
        my_zoo.addAnimal(ele);
        my_zoo.printAllInfo();
    }
}
