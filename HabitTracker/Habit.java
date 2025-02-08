
public class Habit {
    //attributes
    private int id;//database id
    private String name;//habit name .
    private int goal;//number of times a habit is to be performed in a day . 
    private int progress;//number of times habit is performed in a day.

    //constructor
    public Habit(int id,String name , int goal,int progress){
        this.id = id;
        this.name = name;
        this.goal = goal;
        this.progress = progress;
    }
    // methods 
    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }

    public int getGoal(){
        return goal;
    }
    public int getProgress(){
        return progress;
    }

    public void markAsCompleted(){
        if(progress<goal){
            progress++;
        }
        else{
            System.out.println("You have already met today's goal for " + name + "!");
        }
    }

    public void reset(){
        this.progress = 0;
    }
   
    @Override // Overrides the toString method from the Object class to provide a custom string representation of the Habit object
    public String toString () {
        return name + " [Goal: "+ goal + ", Progress: " + progress + "]";
    }
}