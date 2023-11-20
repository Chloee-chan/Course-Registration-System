package main.repository;

import main.model.Account;

public interface AccountRepository {
    Account findByUsername(String username);
}
