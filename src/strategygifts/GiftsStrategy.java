package strategygifts;

import data.Gift;

import java.util.ArrayList;
import java.util.HashMap;

public interface GiftsStrategy {
    /**
     *
     * @param budgetMap
     * @return
     */
    HashMap<Integer, ArrayList<Gift>> getGiftsByStrategy(HashMap<Integer, Double> budgetMap);
}
