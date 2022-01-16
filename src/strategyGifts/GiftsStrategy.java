package strategyGifts;

import data.Gift;

import java.util.ArrayList;
import java.util.HashMap;

public interface GiftsStrategy {
    HashMap<Integer, ArrayList<Gift>> getGiftsByStrategy(final HashMap<Integer, Double> budgetMap);
}
