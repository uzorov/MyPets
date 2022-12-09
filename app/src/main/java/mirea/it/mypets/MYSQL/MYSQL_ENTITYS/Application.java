package mirea.it.mypets.MYSQL.MYSQL_ENTITYS;

public class Application {
    private String id;
    private String dateOfReceiving;


    public String getAboutCurrentPet() {
        return AboutCurrentPet;
    }

    public void setAboutCurrentPet(String aboutCurrentPet) {
        AboutCurrentPet = aboutCurrentPet;
    }

    private String dateOfPerfoming;
    private String AboutCurrentPet = "";

    public String getId() {
        return id;
    }

    public Integer getIntegerId() {
        return  Integer.getInteger(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateOfReceiving() {
        return dateOfReceiving;
    }

    public void setDateOfReceiving(String dateOfReceiving) {
        this.dateOfReceiving = dateOfReceiving;
    }

    public String getDateOfPerfoming() {
        return dateOfPerfoming;
    }

    public void setDateOfPerfoming(String dateOfPerfoming) {
        this.dateOfPerfoming = dateOfPerfoming;
    }

    public Application() {
    }

    public Application(String id, String dateOfReceiving, String dateOfPerfoming) {
        this.id = id;
        this.dateOfReceiving = dateOfReceiving;
        this.dateOfPerfoming = dateOfPerfoming;
    }
}
