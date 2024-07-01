package sdm.engine.CutomSDMClasses;

public abstract class SDMMember {
    private int ID;
    private String name;

    public SDMMember(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() { return ID ; }
    public String getName() { return name; }
    public void setID(int id) { ID = id ;}
    public void setName(String name) { name = name; }

}
