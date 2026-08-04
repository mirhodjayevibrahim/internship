package zoo;

public class Dolphin extends Animal implements Feedable, Trainable {

    public Dolphin(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Dolphin";
    }

    @Override
    public void feed() {
        System.out.println(name + " the Dolphin is eating fish");
    }

    @Override
    public void train() {
        System.out.println(name + " the Dolphin is jumping through hoops");
    }
}
