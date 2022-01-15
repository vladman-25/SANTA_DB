package data;

import common.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class InputReader {
    private InputReader() {
    }

    /**
     *
     * @param fileInputPath path of input file
     * @return
     */
    public static void readData(final String fileInputPath) {
        JSONParser jsonParser = new JSONParser();
        int numberOfYears;
        int santaBudget;
        ArrayList<Child> children = new ArrayList<Child>();
        ArrayList<Gift> gifts = new ArrayList<Gift>();
        ArrayList<Change> changes = new ArrayList<Change>();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(fileInputPath));

            numberOfYears =  Integer.parseInt(((JSONObject) jsonObject).
                    get(Constants.NUMBER_OF_YEARS).toString());
            santaBudget = Integer.parseInt(((JSONObject) jsonObject).
                    get(Constants.SANTA_BUDGET).toString());

            JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIAL_DATA);
            JSONArray jsonChildren = (JSONArray) initialData.get(Constants.CHILDREN);
            JSONArray jsonGifts =  (JSONArray) initialData.get(Constants.SANTA_GIFTS_LIST);
            JSONArray jsonChanges = (JSONArray) jsonObject.get(Constants.ANNUAL_CHANGES);

            children = convertJSONArrayToNormalChildren(jsonChildren);

            gifts = convertJSONArrayToGifts(jsonGifts);

            if (jsonChanges != null) {
                for (Object jsonChange : jsonChanges) {
                    changes.add(new Change(
                            Integer.parseInt(((JSONObject) jsonChange).
                                    get(Constants.NEW_SANTA_BUDGET).toString()),
                            convertJSONArrayToGifts((JSONArray) ((JSONObject) jsonChange).
                                    get(Constants.NEW_GIFTS)),
                            convertJSONArrayToNormalChildren((JSONArray) ((JSONObject) jsonChange).
                                    get(Constants.NEW_CHILDREN)),
                            convertJSONArrayTo3varChildren((JSONArray) ((JSONObject) jsonChange).
                                    get(Constants.CHILDREN_UPDATES))
                    ));
                }
            }
            Database.getDatabase().setNumberOfYears(numberOfYears);
            Database.getDatabase().setInitialSantaBudget(santaBudget);
            Database.getDatabase().setInitialChildren(children);
            Database.getDatabase().setInitialGifts(gifts);
            Database.getDatabase().setAnnualChanges(changes);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param array JSON array taht will be transformed
     * @return
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     *
     * @param array JSON array taht will be transformed
     * @return
     */
    public static ArrayList<Gift> convertJSONArrayToGifts(final JSONArray array) {
        if (array != null) {
            ArrayList<Gift> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add(new Gift(
                        (String) ((JSONObject) object).get(Constants.PRODUCT_NAME),
                        Double.parseDouble(((JSONObject) object).get(Constants.PRICE).toString()),
                        (String) ((JSONObject) object).get(Constants.CATEGORY)
                ));
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     *
     * @param array JSON array taht will be transformed
     * @return
     */
    public static ArrayList<Child> convertJSONArrayToNormalChildren(final JSONArray array) {
        if (array != null) {
            ArrayList<Child> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add(new Child.Builder(
                Integer.parseInt(((JSONObject) object).get(Constants.ID).toString()),
                Double.parseDouble(((JSONObject) object).get(Constants.NICE_SCORE).toString()),
            convertJSONArray((JSONArray) ((JSONObject) object).get(Constants.GIFTS_PREFERENCES)))
                .lastNameBuilder((String) ((JSONObject) object).get(Constants.LAST_NAME))
                .firstNameBuilder((String) ((JSONObject) object).get(Constants.FIRST_NAME))
                .ageBuilder(Integer.parseInt(((JSONObject) object).get(Constants.AGE).toString()))
                .cityBuilder((String) ((JSONObject) object).get(Constants.CITY))
                .build());

            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     *
     * @param array JSON array taht will be transformed
     * @return
     */
    public static ArrayList<Child> convertJSONArrayTo3varChildren(final JSONArray array) {
        if (array != null) {
            ArrayList<Child> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add(new Child.Builder(
                        Integer.parseInt(((JSONObject) object).get(Constants.ID).toString()),
                        ((JSONObject) object).get(Constants.NICE_SCORE) != null
                                ? Double.parseDouble(((JSONObject) object).
                                get(Constants.NICE_SCORE).toString()) : -1.0,
                        convertJSONArray((JSONArray) ((JSONObject) object).
                                get(Constants.GIFTS_PREFERENCES)))
                        .build()
                );
            }
            return finalArray;
        } else {
            return null;
        }
    }
}
