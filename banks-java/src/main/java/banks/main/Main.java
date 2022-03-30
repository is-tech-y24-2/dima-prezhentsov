package banks.main;

import banks.builder.PersonBuilder;
import banks.entity.Bank;
import banks.entity.Person;

public class Main {
    public static void main(String[] args){
        PersonBuilder pb = new PersonBuilder();
        pb.setName("Dima", "Prezhentsov");
        pb.setUserSubscribed(true);
        Person person = pb.build();
        PersonBuilder pb2 = new PersonBuilder();
        pb2.setName("Dima", "Prezhentsov");
        pb2.setUserSubscribed(true);
        Person person2 = pb2.build();
        Bank bank = new Bank("tink", 1f, 1f, 5, 1f, 1f);
        bank.getPeople().add(person);
        bank.getPeople().add(person2);
        bank.updateConditions(1f, 1f, 5, 1f, 1f);
    }
}
