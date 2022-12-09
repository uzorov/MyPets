package mirea.it.mypets.MYSQL.MYSQL_ENTITYS;

public class Pet {
    private String idApplication;
    private String idPet;
    private String Name;
    private String Age;
    private String Type;

    @Override
    public String toString() {
        return "Уникальный номер='" + idPet + '\n' +
                ", Имя='" + Name + '\n' +
                ", Тип='" + Type + '\n' +
                ", Возраст='" + Age + '\n';
    }

    public Pet(String idApplication, String idPet, String name, String type, String age) {
        this.idApplication = idApplication;
        this.idPet = idPet;
        Name = name;
        Type = type;
        Age = age;
    }

    public Pet() {
    }

    public String getIdApplication() {
        return idApplication;
    }

    public void setIdApplication(String idApplication) {
        this.idApplication = idApplication;
    }

    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
