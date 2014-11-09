package em.model;

public enum EMVersion {
    
    /** v0.1 */
    V0_1(0, "v0.1");
    
    private final int order;
    private final String pretty;
    
    private EMVersion(int order, String pretty) {
        this.order = order;
        this.pretty = pretty;
    }
    
    public int getOrder() {
        return order;
    }
    
    public String toString() {
        return pretty;
    }
}
