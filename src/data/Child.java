package data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import java.util.ArrayList;

@JsonPropertyOrder({ "id", "lastName", "firstName", "city", "age" })

public final class Child {
    private int id;
    private String lastName;
    private String firstName;
    private int age;
    private String city;
    private Double niceScore;
    private ArrayList<String> giftsPreferences;

    public static final class Builder {
        private int id;
        private String lastName;
        private String firstName;
        private int age = 0;
        private String city;
        private Double niceScore;
        private ArrayList<String> giftsPreferences;

        public Builder(final int id,
                     final Double niceScore,
                     final ArrayList<String> giftsPreferences) {
            this.id = id;
            this.niceScore = niceScore;
            this.giftsPreferences = giftsPreferences;
        }

        /**
         *
         * @return
         */
        public Child build() {
            return new Child(this);
        }

        /**
         *
         * @param newLastName
         * @return
         */
        public Builder lastNameBuilder(final String newLastName) {
            this.lastName = newLastName;
            return this;
        }

        /**
         *
         * @param newFirstName
         * @return
         */
        public Builder firstNameBuilder(final String newFirstName) {
            this.firstName = newFirstName;
            return this;
        }

        /**
         *
         * @param newAge
         * @return
         */
        public Builder ageBuilder(final int newAge) {
            this.age = newAge;
            return this;
        }

        /**
         *
         * @param newCity
         * @return
         */
        public Builder cityBuilder(final String newCity) {
            this.city = newCity;
            return this;
        }


    }

    private Child(final Builder builder) {
        this.id = builder.id;
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.age = builder.age;
        this.city = builder.city;
        this.niceScore = builder.niceScore;
        this.giftsPreferences = builder.giftsPreferences;
    }


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public void setNiceScore(final Double niceScore) {
        this.niceScore = niceScore;
    }

    public ArrayList<String> getGiftsPreferences() {
        return giftsPreferences;
    }

    public void setGiftsPreferences(final ArrayList<String> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    @Override
    public String toString() {
        return "ChildData{"
                + "childId=" + getId()
                + ", lastName='" + getLastName() + '\''
                + ", firstName='" + getFirstName() + '\''
                + ", age=" + getAge()
                + ", city='" + getCity() + '\''
                + ", niceScore=" + getNiceScore()
                + ", giftsPrefernces=" + getGiftsPreferences() + "}\n";
    }
}
