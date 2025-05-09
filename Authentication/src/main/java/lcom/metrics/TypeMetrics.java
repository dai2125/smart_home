package lcom.metrics;

import com.home.asm.CreatorPrinciple1And3Service;
import com.home.asm.InspectedClass;
import lcom.metrics.algorithms.*;
import lcom.sourceModel.SM_Type;

public class TypeMetrics {
    private double yalcom;
    private SM_Type type;
    private double lcom1, lcom2, lcom3, lcom4, lcom5;

    public TypeMetrics(SM_Type type) {
        this.type = type;
    }

    public void extractMetrics(String targetClass) {
//        System.out.println("type.getName(): " + type.getName() + " targetClass: " + targetClass);
//        System.out.println("type.getName(): " + type.getName());

        InspectedClass inspectedClass = CreatorPrinciple1And3Service.get(type.getName());
        // InspectedClass inspectedClass = ClassService.get(type.getName());

//        System.out.println("targetClass: " + targetClass);
//        System.out.println("inspectedClass.getFullName(): " + inspectedClass.getFullName());
        // System.out.println(444 + " type.getName(): " + type.getName() + ", inspectedClass.getName(): " + inspectedClass.getName() + ", targetClass: " + targetClass);
        if(type.getName().equals(inspectedClass.getName())) {

            ILCOM lcomAlgorithm = new YALCOM();

            if(inspectedClass != null) {
                CohesionService cohesionService = new CohesionService();
                cohesionService.initializeGraph(type);
                inspectedClass.setNumberOfPossibleConnections(cohesionService.getNumberOfPossibleConnections());
                inspectedClass.setNumberOfDirectConnections(cohesionService.getNumberOfDirectConnections());
                inspectedClass.setNumberOfIndirectConnections(cohesionService.getNumberOfIndirectConnections());
//                cohesionService.printGraph();

                if(isNotLcomComputable()) {
                    yalcom = -1.0;
//                    System.out.println("yalcom: " + yalcom);
                    inspectedClass.setYalcom(yalcom);

                    lcomAlgorithm = new LCOM1();
                    lcom1 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom1(lcom1);

                    lcomAlgorithm = new LCOM2();
                    lcom2 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom2(lcom2);

                    lcomAlgorithm = new LCOM3();
                    lcom3 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom3(lcom3);

                    lcomAlgorithm = new LCOM4();
                    lcom4 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom4(lcom4);

                    lcomAlgorithm = new LCOM5();
                    lcom5 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom5(lcom5);


//                    inspectedClass.setLcom1(yalcom);
//                    inspectedClass.setLcom2(yalcom);
//                    inspectedClass.setLcom3(yalcom);
//                    inspectedClass.setLcom4(yalcom);
//                    inspectedClass.setLcom5(yalcom);
                    CreatorPrinciple1And3Service.put(inspectedClass);
                    // ClassService.put(inspectedClass);

                } else {
                    yalcom = lcomAlgorithm.compute(type);
                    inspectedClass.setYalcom(yalcom);

                    lcomAlgorithm = new LCOM1();
                    lcom1 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom1(lcom1);

                    lcomAlgorithm = new LCOM2();
                    lcom2 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom2(lcom2);

                    lcomAlgorithm = new LCOM3();
                    lcom3 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom3(lcom3);

                    lcomAlgorithm = new LCOM4();
                    lcom4 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom4(lcom4);

                    lcomAlgorithm = new LCOM5();
                    lcom5 = lcomAlgorithm.compute(type);
                    inspectedClass.setLcom5(lcom5);
                    //System.out.println("TTTinspectedClass: " + inspectedClass + " has LCOM4 of " + lcom4);
                    //System.out.println("TypeMetrics inspectedClass: " + inspectedClass.getName() + " " + inspectedClass.getLcom1() + ", " + inspectedClass.getLcom2() + ", "+ inspectedClass.getLcom4() + ", " + inspectedClass.getLcom5());
                    CreatorPrinciple1And3Service.put(inspectedClass);
                    // ClassService.put(inspectedClass);
                }
            }

        }
    }

    private boolean isNotLcomComputable() {
        return type.isInterface()
                || type.getFieldList().size() == 0
                || type.getMethodList().size() == 0;
    }

    public double getYalcom() {
        return yalcom;
    }
    public double getLcom1() {
        return lcom1;
    }
    public double getLcom2() {
        return lcom2;
    }
    public double getLcom3() {
        return lcom3;
    }
    public double getLcom4() {
        return lcom4;
    }
    public double getLcom5() {
        return lcom5;
    }
}
