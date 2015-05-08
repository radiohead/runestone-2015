package se.uu.it.runestone.teamone.webservice;

public class Robot {

    private int id;
    private int load;
    private int stuck;
    private String content;

    public Robot(){

    }

    public Robot(int id, int load, int stuck, String content) {
        this.id = id;
        this.load = load;
        this.stuck = stuck;
        this.content = content;
        if(id == 2){
            this.content = "ID changed";
        }
    }

    //Getter methods for a robot
    public int getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public int getLoad(){
        return load;
    }
    public int getStuck(){
        return stuck;
    }

    //Setter methods for a robot
    public void setId(int id) {
        this.id = id;
    }
    public void setLoad(int load){
        this.load = load;
    }
    public void setStuck(int stuck){
        this.load = stuck;
    }
}