package em.model;

public class Greeting { // TODO rt

    private int id;
    private String content;
    
    public Greeting() {
        this(-1, null);
    }
    
    public Greeting(int id, String content) {
        super();
        setID(id);
        setContent(content);
    }
    
    public int getID() {
        return id;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
