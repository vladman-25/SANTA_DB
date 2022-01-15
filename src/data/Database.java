package data;

import java.util.ArrayList;
import java.util.HashMap;

public final class Database {

    private static final Database INSTANCE = new Database();
    private Database() {
    }

    public static Database getDatabase() {
        return INSTANCE;
    }

    private int numberOfYears = 0;
    private int initialSantaBudget = 0;
    private ArrayList<Child> initialChildren = new ArrayList<Child>();
    private ArrayList<Gift> initialGifts = new ArrayList<Gift>();
    private ArrayList<Change> annualChanges = new ArrayList<Change>();

    private HashMap<Integer, ArrayList<Double>> scores = new HashMap<Integer, ArrayList<Double>>();
    private HashMap<Integer, Double> avgScores = new HashMap<Integer, Double>();

    /**
     * get a new database for each test
     */
    public void destroyDatabase() {
        this.numberOfYears = 0;
        this.initialSantaBudget = 0;
        this.initialChildren = new ArrayList<Child>();
        this.initialGifts = new ArrayList<Gift>();
        this.annualChanges = new ArrayList<Change>();
        this.scores = new HashMap<Integer, ArrayList<Double>>();
        this.avgScores = new HashMap<Integer, Double>();
    }


    public HashMap<Integer, ArrayList<Double>> getScores() {
        return scores;
    }

    public void setScores(final HashMap<Integer, ArrayList<Double>> scores) {
        this.scores = scores;
    }

    public HashMap<Integer, Double> getAvgScores() {
        return avgScores;
    }

    public void setAvgScores(final HashMap<Integer, Double> avgScores) {
        this.avgScores = avgScores;
    }

//    public Database(final int numberOfYears,
//                    final int initialSantaBudget,
//                    final ArrayList<Child> initialChildren,
//                    final ArrayList<Gift> initialGifts,
//                    final ArrayList<Change> annualChanges) {
//        this.numberOfYears = numberOfYears;
//        this.initialSantaBudget = initialSantaBudget;
//        this.initialChildren = initialChildren;
//        this.initialGifts = initialGifts;
//        this.annualChanges = annualChanges;
//        this.scores = new HashMap<Integer, ArrayList<Double>>();
//        this.avgScores = new HashMap<Integer, Double>();
//    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(final int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public int getInitialSantaBudget() {
        return initialSantaBudget;
    }

    public void setInitialSantaBudget(final int initialSantaBudget) {
        this.initialSantaBudget = initialSantaBudget;
    }

    public ArrayList<Child> getInitialChildren() {
        return initialChildren;
    }

    public void setInitialChildren(final ArrayList<Child> initialChildren) {
        this.initialChildren = initialChildren;
    }

    public ArrayList<Gift> getInitialGifts() {
        return initialGifts;
    }

    public void setInitialGifts(final ArrayList<Gift> initialGifts) {
        this.initialGifts = initialGifts;
    }

    public ArrayList<Change> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(final ArrayList<Change> annualChanges) {
        this.annualChanges = annualChanges;
    }
}
