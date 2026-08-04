package zoo;

public class Lion extends Animal implements Feedable {

    public Lion(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Lion";
    }

    @Override
    public void feed() {
        System.out.println(name + " the Lion is eating meat");
    }
}
