package me.whiteship.refactoring._07_divergent_change._26_extract_class;

public class TelephoneNumber {
    private final String areaCode;
    private final String number;

    public TelephoneNumber(String officeAreaCode, String officeNumber) {
        this.areaCode = officeAreaCode;
        this.number = officeNumber;
    }

    public String areaCode() {
        return areaCode;
    }

    public String number() {
        return number;
    }

    @Override
    public String toString() {
        return areaCode + "_" + number;
    }
}