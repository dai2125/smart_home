package lcom.sourceModel;

public class Tc {

    private String targetClass;

    public Tc(String targetClass) {
        this.targetClass = targetClass;
//        System.out.println("--TARGET CLASS IS " + targetClass);
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetClass() {
        return targetClass;
    }
}
