package tasks;

import common.Constants;
import data.Child;
import enums.Ages;

import java.util.ArrayList;

public final class Utils {

    private Utils() {
    }

    /**
     *
     * @param child
     * @return
     */
    public static Ages getChildAgeEnum(final Child child) {
        if (child.getAge() < Constants.BABY_LIMIT) {
            return Ages.BABY;
        }
        if (child.getAge() < Constants.KID_LIMIT) {
            return Ages.KID;
        }
        if (child.getAge() <= Constants.TEEN_LIMIT) {
            return Ages.TEEN;
        }
        return Ages.YOUNG_ADULT;
    }

    /**
     *
     * @param list
     * @return
     */
    public static ArrayList<String> removeDuplicates(final ArrayList<String> list) {


        ArrayList<String> newList = new ArrayList<String>();
        for (String element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }
}
