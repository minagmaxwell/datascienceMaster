import java.util.ArrayList;



class Animal {
	String name;
	String species;
	int age;
	Animal(){
		this.name = "Generic Animal";
		this.species = "Generic specie";
		this.age = 0;
		}
	Animal(String name_arg, String species_arg, int age_arg){		
		this.name = name_arg;
		this.species = species_arg;
		this.age = age_arg;
	}
	void printInfo() {
		System.out.println(this.name +", " + species +", " + age);
	}
}
 
class Panda extends Animal{
//	String name ="Spot";
//	String species = "Panda";
//	int age = 0;
	Panda(){
		super("Spot","Panda",0);
		}
	Panda(String name, int age){
		super(name,"Panda" ,age);
//		this.name = name;
//		this.species = "Panda";
//		this.age= age;
	}
}
 
class Elephant extends Animal{

	Elephant(){
		super("Elle","Elephant",0);
		}
	Elephant(String name, int age){
		super(name, "Elephant" ,age); 
	}

}

class Zoo {
	Animal[] animalArray = new Animal[5];
	int current_idx = 0;
	Zoo(){
		}
	void addAnimal(Animal a) {
		animalArray[current_idx] = a;
		current_idx +=1;
	}
	
	void printAllInfo() {
		for(int i = 0;i<animalArray.length;i++) {
			if (animalArray[i] != null)
				animalArray[i].printInfo();
		}
	}
}


public class ZooBuilder {

    public static void main(String[] args){
    	
    	Elephant e1 = null, e2 = null;
        Panda p1 = null, p2 = null;
        Zoo z = null;

        try{ 
            e1 = new Elephant();
            if(e1.name.equals("Elle")){System.out.println("PASS");}else{System.out.println("FAIL");}
            if(e1.species.equals("Elephant")){System.out.println("PASS");}else{System.out.println("FAIL");}
            if(e1.age == 0){System.out.println("PASS");}else{System.out.println("FAIL");}
        } catch (Exception e) {
            System.out.println("FAIL");
        }

        try{
            p1 = new Panda();
            if(p1.name.equals("Spot")){System.out.println("PASS");}else{System.out.println("FAIL");}
            if(p1.species.equals("Panda")){System.out.println("PASS");}else{System.out.println("FAIL");}
            if(p1.age == 0){System.out.println("PASS");}else{System.out.println("FAIL");}
        } catch (Exception e) {
            System.out.println("FAIL");
        }
        
        try{
            e2 = new Elephant("Elephant2", 20);
            if(e2.name.equals("Elephant2")){System.out.println("PASS");}else{System.out.println("FAIL");}
            if(e2.species.equals("Elephant")){System.out.println("PASS");}else{System.out.println("FAIL");}
            if(e2.age == 20){System.out.println("PASS");}else{System.out.println("FAIL");}
        } catch (Exception e) {
            System.out.println("FAIL");
        }
        
        try{
            p2 = new Panda("Panda2", 12);
            if(p2.name.equals("Panda2")){System.out.println("PASS");}else{System.out.println("FAIL");}
            if(p2.species.equals("Panda")){System.out.println("PASS");}else{System.out.println("FAIL");}
            if(p2.age == 12){System.out.println("PASS");}else{System.out.println("FAIL");}
        } catch (Exception e) {
            System.out.println("FAIL");
        }

        System.out.println("--- Testing printInfo function ---");
        try{e1.printInfo();} catch (Exception e) { System.out.println("FAIL"); }
        try{p1.printInfo();} catch (Exception e) { System.out.println("FAIL"); }
        try{e2.printInfo();} catch (Exception e) { System.out.println("FAIL"); }
        try{p2.printInfo();} catch (Exception e) { System.out.println("FAIL"); }
        
        System.out.println("--- Testing printAll function. Should match the above output ---");
        try{
            z = new Zoo();
            z.addAnimal((Animal)e1);
            z.addAnimal((Animal)p1);
            z.addAnimal((Animal)e2);
            z.addAnimal((Animal)p2);
            z.printAllInfo();
        } catch (Exception e) {
            System.out.println("FAIL");
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
    }
}