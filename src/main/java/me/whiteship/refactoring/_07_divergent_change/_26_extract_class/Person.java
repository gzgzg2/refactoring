package me.whiteship.refactoring._07_divergent_change._26_extract_class;

public class Person {

    private final String name;

    private final TelephoneNumber telephoneNumber;

    public Person(String name, TelephoneNumber telephoneNumber) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }
    public String name() {
        return name;
    }

    public TelephoneNumber getTelephoneNumber() {
        return telephoneNumber;
    }

    public String telephoneNumber() {
        return this.telephoneNumber.toString();
    }
}
