package View;

public abstract class Command {
    private String key, description;

    public Command(String key, String description){
        //  Constructor for abstract class command
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        //  Returns the key
        return key;
    }

    public String getDescription() {
        //  Returns the description of the command
        return description;
    }

    public abstract void execute();

}
