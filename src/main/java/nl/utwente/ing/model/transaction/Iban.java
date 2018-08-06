package nl.utwente.ing.model.transaction;

import java.util.Arrays;

public class Iban {

    private StateCode stateCode;
    private int[] checkDigits;
    private String bban;

    public Iban(StateCode stateCode, int[] checkDigits, String bban) {
        this.stateCode = stateCode;
        this.checkDigits = checkDigits;
        this.bban = bban;
    }

    @Override
    public String toString() {
        return stateCode + Arrays.toString(checkDigits).replaceAll("\\[|\\]|,|\\s", "") + bban;
    }

    public enum StateCode {

        NL("Netherlands"),
        DE("Germany"),
        GR("Greece"),
        HU("Hungary"),
        IS("Iceland"),
        IT("Italy"),
        MD("Moldova"),
        RO("Romania"),
        GB("United Kingdom");

        private String country;

        StateCode(String country) {
            this.country = country;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
