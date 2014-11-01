package em.model;

public class DBTestResult { // TODO rt

    private String result;
    
    public DBTestResult() {
        this(null);
    }
    
    public DBTestResult(String result) {
        super();
        setResult(result);
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
}
