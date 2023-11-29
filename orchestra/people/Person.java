package people;

public class Person {
    /**
     * Create a Person class in people package
     * String name, create constructor and define getName method following the coursework specification
     */
    public String name = "";
    public Person(String name){
        this.name = name;
    }
    public String getName() {
        System.out.println(name);
        return name;
    }
}
