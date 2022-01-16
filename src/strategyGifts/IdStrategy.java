package strategyGifts;

import data.Child;
import data.Database;
import data.Gift;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class IdStrategy implements GiftsStrategy {

    public IdStrategy() {
    }

    @Override
    public HashMap<Integer, ArrayList<Gift>> getGiftsByStrategy(final HashMap<Integer, Double> budgetMap) {
        HashMap<Integer, ArrayList<Gift>> giftsMap = new HashMap<Integer, ArrayList<Gift>>();
        for (Child child : Database.getDatabase().getInitialChildren()) {
            Double childBudget = budgetMap.get(child.getId());

            ArrayList<Gift> childGifts = new ArrayList<Gift>();

            for (String preference : child.getGiftsPreferences()) {

                ArrayList<Gift> tempGiftsByPreference = new ArrayList<Gift>();
                for (Gift gift : Database.getDatabase().getInitialGifts()) {
                    if ((Objects.equals(gift.getCategory(), preference))
                            && (Double.compare(gift.getPrice(), childBudget) <= 0)
                            && (gift.getQuantity() > 0)) {
                        tempGiftsByPreference.add(gift);
                    }
                }
                tempGiftsByPreference.sort((o1, o2) -> {
                    if (Double.compare(o1.getPrice(), o2.getPrice()) > 0) {
                        return 1;
                    }
                    if (Double.compare(o1.getPrice(), o2.getPrice()) < 0) {
                        return -1;
                    }
                    return 0;
                });
                if (tempGiftsByPreference.size() != 0) {
                    childGifts.add(tempGiftsByPreference.get(0));
                    tempGiftsByPreference.get(0).setQuantity(tempGiftsByPreference.get(0).getQuantity() - 1);
                    childBudget -= tempGiftsByPreference.get(0).getPrice();
                }
            }
            giftsMap.put(child.getId(), childGifts);
        }
        return giftsMap;
    }
}
