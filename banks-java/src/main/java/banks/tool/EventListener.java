package banks.tool;

import banks.account.Account;

import java.util.ArrayList;
import java.util.List;

public class EventListener {
    private List<Passable> delegate;
    public EventListener() {
        delegate = new ArrayList<>();
    }
    public void addDelegate(Account account) {
        delegate.add(account);
    }

    public void invoke() {
        for (Passable instance : delegate) {
            instance.passDay();
        }
    }
}

