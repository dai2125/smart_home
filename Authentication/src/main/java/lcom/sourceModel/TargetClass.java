package lcom.sourceModel;

public class TargetClass {

    private static TargetClass instance;
    private String targetClass;

    public TargetClass(String targetClass) {
        this.targetClass = targetClass;
        System.out.println("-TARGET CLASS IS " + targetClass);
    }

    public static void initialize(String targetClass) {
        if(instance == null) {
            instance = new TargetClass(targetClass);
        }
    }

    public static TargetClass getInstance() {
        if(instance == null) {
            throw new IllegalStateException("Target Class is not initialized. Call initialize first");
        }
        return instance;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetClass() {
        return targetClass;
    }
}
