package zoo;

public class Eagle extends Animal implements Trainable {

    public Eagle(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Eagle";
    }

    @Override
    public void train() {
        System.out.println(name + " the Eagle is flying through obstacles");
    }
}
