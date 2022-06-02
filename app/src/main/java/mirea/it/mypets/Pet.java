package mirea.it.mypets;

public class Pet {
    public String name;
    public String type;

    public Pet() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Pet(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
