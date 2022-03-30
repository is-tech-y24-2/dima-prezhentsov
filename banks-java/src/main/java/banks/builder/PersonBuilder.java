package banks.builder;

import banks.entity.Person;
import banks.exception.BankException;

public class PersonBuilder {
    private Person person;
    public PersonBuilder() {
        person = null;
    }

    public Person build() {
        if (person != null) {
            return person;
        }

        throw new BankException("person doesn't exist");
    }

    public void setName(String name, String surname) {
        person = new Person(name, surname);
    }

    public void setPassportId(int passportId) {
        if (passportId < 100000 || passportId > 999999) {
            throw new BankException("Invalid passportId");
        }
        person.setPassportId(passportId);
    }

    public void setAddress(String address) {
        person.setAddress(address);
    }

    public void setUserSubscribed(boolean isSubscribed) {
        person.setSubscribed(isSubscribed);
    }
}