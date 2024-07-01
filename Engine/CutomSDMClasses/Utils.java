package sdm.engine.CutomSDMClasses;

import javafx.util.Pair;

import java.text.DecimalFormat;

public abstract class Utils {

    public static double calculateDistance(Pair<Integer, Integer> location1, Pair<Integer, Integer> location2){
        return Math.sqrt(Math.pow(location1.getKey() - location2.getKey(), 2) +
                Math.pow(location1.getValue() - location2.getValue(), 2));
    }

    public static double twoNumbersAfterDot(Double num){
        DecimalFormat format = new DecimalFormat("#.##");
        String numStr = format.format(num);
        return Double.parseDouble(numStr);
    }

}
