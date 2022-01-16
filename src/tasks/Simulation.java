package tasks;

import common.Constants;
import data.Child;
import data.Database;
import data.Gift;
import enums.Ages;
import result.Result;
import result.ResultArray;
import result.ResultChildren;
import result.ResultGift;
import strategy.StrategyFactory;
import strategygifts.IdStrategy;
import strategygifts.NiceScoreCityStrategy;
import strategygifts.NiceScoreStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Simulation {

    private Simulation() {
    }

    /**
     *
     * @return
     */
    public static Result simulateForInput() {
        Result finalResult = null;
        ArrayList<ResultArray> finalResultArray = new ArrayList<ResultArray>();
        for (int yearNumber = 0; yearNumber <= Database.getDatabase().getNumberOfYears();
             yearNumber++) {

            ArrayList<ResultChildren> intermediateResult = new ArrayList<ResultChildren>();

            Double currentBudget = 0.0;
            if (yearNumber != 0) {
                updateGifts(yearNumber);
                currentBudget = Double.valueOf(Database.getDatabase().getAnnualChanges().
                        get(yearNumber - 1).getNewSantaBudget());
                updateChildren(yearNumber);
            }
            if (yearNumber == 0) {

                currentBudget = Double.valueOf(Database.getDatabase().getInitialSantaBudget());
                ArrayList<Child> newChildren = new ArrayList<Child>();

                for (Child child : Database.getDatabase().getInitialChildren()) {
                    if (Utils.getChildAgeEnum(child) != Ages.YOUNG_ADULT) {
                        newChildren.add(child);
                    }
                }
                Database.getDatabase().setInitialChildren(newChildren);


                for (Child child : Database.getDatabase().getInitialChildren()) {
                    ArrayList<Double> temp = new ArrayList<Double>();
                    temp.add(child.getNiceScore());
                    Database.getDatabase().getScores().put(child.getId(), temp);

                    if (Utils.getChildAgeEnum(child) == Ages.BABY) {
                        Database.getDatabase().getAvgScores()
                                .put(child.getId(), Constants.BABY_SCORE);
                    } else {
                        Database.getDatabase().getAvgScores()
                                .put(child.getId(), child.getNiceScore());
                    }
                }
            }

            calculateAvgScore();
            Double avgScoresSum = 0.0;

            HashMap<Integer, Double> newMapSortedByKey = Database.getDatabase()
                    .getAvgScores().entrySet().stream()
                    .sorted(Map.Entry.<Integer, Double>comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));
            Database.getDatabase().setAvgScores(newMapSortedByKey);

            for (Map.Entry<Integer, Double> entry
                    : Database.getDatabase().getAvgScores().entrySet()) {
                avgScoresSum += entry.getValue();
            }
            Double budgetUnit  = currentBudget / avgScoresSum;

            HashMap<Integer, Double> budgetMap = new HashMap<Integer, Double>();
            for (Map.Entry<Integer, Double> entry
                    : Database.getDatabase().getAvgScores().entrySet()) {
                Double budget = entry.getValue() * budgetUnit;
                for (Child x : Database.getDatabase().getInitialChildren()) {
                    if (x.getId() == entry.getKey()) {
                        budget += x.elfModification(budget);
                    }
                }
                budgetMap.put(entry.getKey(), budget);
            }
            HashMap<Integer, ArrayList<Gift>> finalGiftsOirignal;
            if (yearNumber == 0) {
                finalGiftsOirignal = new IdStrategy().getGiftsByStrategy(budgetMap);
            } else {
                finalGiftsOirignal = switch (Database.getDatabase()
                        .getAnnualChanges().get(yearNumber - 1).getStrategy()) {
                    case "niceScore" -> new NiceScoreStrategy()
                            .getGiftsByStrategy(budgetMap);
                    case "niceScoreCity" -> new NiceScoreCityStrategy()
                            .getGiftsByStrategy(budgetMap);
                    default -> new IdStrategy()
                            .getGiftsByStrategy(budgetMap);
                };

            }

            // yellow elf
            for (Child child : Database.getDatabase().getInitialChildren()) {
                if (child.getElf().equals("yellow")
                        && (finalGiftsOirignal.get(child.getId()).size() == 0)) {
                    ArrayList<Gift> auxGiftList = new ArrayList<Gift>();
                    Gift auxGift = yellowElfGifts(child);
                    if (auxGift != null) {
                        auxGiftList.add(auxGift);
                    }
                    finalGiftsOirignal.put(child.getId(), auxGiftList);
                }
            }
            //

            HashMap<Integer, ArrayList<ResultGift>> finalGifts =
                    new HashMap<Integer, ArrayList<ResultGift>>();
            for (Map.Entry<Integer, ArrayList<Gift>> entry : finalGiftsOirignal.entrySet()) {
                ArrayList<ResultGift> newGiftList = new ArrayList<>();
                for (Gift x : entry.getValue()) {
                    newGiftList.add(new ResultGift(x.getProductName(),
                            x.getPrice(),
                            x.getCategory()));
                }
                finalGifts.put(entry.getKey(), newGiftList);
            }

            for (Child child : Database.getDatabase().getInitialChildren()) {

                ArrayList<Double> newScoreList = new ArrayList<Double>();
                for (Double score : Database.getDatabase().getScores().get(child.getId())) {
                    newScoreList.add(score);
                }


                intermediateResult.add(new ResultChildren(child.getId(),
                                child.getLastName(),
                                child.getFirstName(),
                                child.getCity(),
                                child.getAge(),
                                child.getGiftsPreferences(),
                                Database.getDatabase().getAvgScores().get(child.getId()),
                                newScoreList,
                                budgetMap.get(child.getId()),
                                finalGifts.get(child.getId())));
            }
            ResultArray tempResArr = new ResultArray(intermediateResult);
            finalResultArray.add(tempResArr);
            finalResult = new Result(finalResultArray);

        }
        return finalResult;
    }

    /**
     *
     * @param yearNumber
     */
    public static void updateChildren(final int yearNumber) {
        for (Child child : Database.getDatabase()
                .getAnnualChanges().get(yearNumber - 1).getChildrenUpdates()) {
            for (Child child1 : Database.getDatabase().getInitialChildren()) {
                if (child1.getId() == child.getId()) {
                    if (child.getGiftsPreferences().size() != 0) {
                        ArrayList<String> finalPref = Utils.
                                removeDuplicates(child.getGiftsPreferences());
                        ArrayList<String> temp = Utils.
                                removeDuplicates(child1.getGiftsPreferences());

                        for (String pref : temp) {
                            if (!finalPref.contains(pref)) {
                                finalPref.add(pref);
                            }
                        }
                        child1.setGiftsPreferences(finalPref);
                    }
                    if (child.getNiceScore() != -1.0) {
                        child1.setNiceScore(child.getNiceScore());
                        Database.getDatabase()
                                .getScores().get(child1.getId()).add(child.getNiceScore());
                    }
                    child1.setElf(child.getElf());
                }
            }
        }

        ArrayList<Child> newChildren = new ArrayList<Child>();

        for (Child child : Database.getDatabase().getInitialChildren()) {
            child.setAge(child.getAge() + 1);
            if (Utils.getChildAgeEnum(child) != Ages.YOUNG_ADULT) {
                newChildren.add(child);
            } else {
                HashMap<Integer, ArrayList<Double>> temp =  Database.getDatabase().getScores();
                temp.remove(child.getId());
                Database.getDatabase().setScores(temp);

                HashMap<Integer, Double> temp2 =  Database.getDatabase().getAvgScores();
                temp2.remove(child.getId());
                Database.getDatabase().setAvgScores(temp2);

            }
        }
        Database.getDatabase().setInitialChildren(newChildren);

        for (Child child : Database.getDatabase()
                .getAnnualChanges().get(yearNumber - 1).getNewChildren()) {
            if (Utils.getChildAgeEnum(child) != Ages.YOUNG_ADULT) {
                Database.getDatabase().getInitialChildren().add(child);
                ArrayList<Double> temp = new ArrayList<Double>();
                temp.add(child.getNiceScore());
                Database.getDatabase().getScores().put(child.getId(), temp);

                if (Utils.getChildAgeEnum(child) == Ages.BABY) {
                    Database.getDatabase().getAvgScores().put(child.getId(), Constants.BABY_SCORE);
                } else {
                    Database.getDatabase().getAvgScores().put(child.getId(), child.getNiceScore());
                }

            }
        }
    }

    /**
     *
     */
    public static void calculateAvgScore() {
        for (int i = 0; i < Database.getDatabase().getInitialChildren().size(); i++) {
            Child tempChild = Database.getDatabase().getInitialChildren().get(i);
            StrategyFactory strategyFactory = new StrategyFactory();
            Double score = strategyFactory.createStrategy(i);
            Database.getDatabase().getAvgScores().put(tempChild.getId(), score);
        }
    }

    /**
     *
     * @param yearNumber
     */
    public static void updateGifts(final int yearNumber) {
        ArrayList<Gift> tempGifts = Database.getDatabase().getInitialGifts();
        tempGifts.addAll(Database.getDatabase().getAnnualChanges()
                .get(yearNumber - 1).getNewGifts());
        Database.getDatabase().setInitialGifts(tempGifts);
    }

    /**
     *
     * @param child
     * @return
     */
    public static Gift yellowElfGifts(final Child child) {
        ArrayList<Gift> tempGiftsByPreference = new ArrayList<Gift>();
            for (Gift gift : Database.getDatabase().getInitialGifts()) {
                if (Objects.equals(gift.getCategory(), child.getGiftsPreferences().get(0))) {
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
            if (tempGiftsByPreference.get(0).getQuantity() != 0) {
                tempGiftsByPreference.get(0)
                        .setQuantity(tempGiftsByPreference.get(0).getQuantity() - 1);
                return tempGiftsByPreference.get(0);
            }
        }
        return null;
    }
}
