package zoo;

public abstract class Animal {

    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " [" + name + "]";
    }
}
