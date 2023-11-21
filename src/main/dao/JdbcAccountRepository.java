package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.component.MySQLConnector;
import main.model.Account;
import main.repository.AccountRepository;

public class JdbcAccountRepository implements AccountRepository {

    private final Connection connection;

    public JdbcAccountRepository() {
        this.connection = MySQLConnector.connect();
    }

    @Override
    public Account findByUsername(String username) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT * FROM account WHERE username=?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new Account(
                        result.getString("username"),
                        result.getString("password"),
                        Account.Role.valueOf(result.getString("role")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
