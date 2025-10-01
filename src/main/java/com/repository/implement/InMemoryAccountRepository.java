package com.repository.implement;

import com.model.Account;
import com.model.Client;
import com.model.User;
import com.repository.AccountRepository;
import com.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryAccountRepository implements AccountRepository {

    public Account createAccount(Account account) {
        String insertSql = "INSERT INTO accounts (id, account_number, balance, type, client_id, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            ps.setObject(1, account.getId());
            ps.setString(2, account.getAccountNumber());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getType().name());
            ps.setObject(5, account.getClientId());
            ps.setBoolean(6, account.isStatus());

            System.out.println("Saving account: " +
                    "\n ID = " + account.getId() +
                    "\n Number = " + account.getAccountNumber() +
                    "\n Balance = " + account.getBalance() +
                    "\n Type = " + account.getType() +
                    "\n ClientId = " + account.getClientId() +
                    "\n Status = " + account.isStatus());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error creating account: " + e.getMessage(), e);
        }
        return account;
    }


    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapToAccount(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while finding accounts: " + e.getMessage(), e);
        }
        return accounts;
    }

    private Account mapToAccount(ResultSet rs) throws SQLException {
        Account account = new Account(
                Account.Type.valueOf(rs.getString("type")),
                rs.getObject("client_id", UUID.class)
        );
        account.setId(rs.getObject("id", UUID.class));
        account.setAccountNumber(rs.getString("account_number"));
        account.setBalance(rs.getDouble("balance"));
        account.setStatus(rs.getBoolean("status"));
        return account;
    }

    public Account findById(UUID accountId) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, accountId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapToAccount(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding account by ID: " + e.getMessage(), e);
        }
        return null;
    }

    public Account findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                UUID id = rs.getObject("id", UUID.class);
                UUID clientId = rs.getObject("client_id", UUID.class);
                Account.Type type = Account.Type.valueOf(rs.getString("type"));
                double balance = rs.getDouble("balance");
                boolean status = rs.getBoolean("status");
                String accNum = rs.getString("account_number");

                Account account = new Account(type, clientId);
                account.setId(id);
                account.setAccountNumber(accNum);
                account.setBalance(balance);
                account.setStatus(status);

                return account;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding account by accountNumber: " + e.getMessage(), e);
        }
        return null;
    }


    public Client findClientById(UUID clientId) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, clientId);
            var rs = ps.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        rs.getString("full_name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getDouble("salary"),
                        Client.Currency.valueOf(rs.getString("currency"))
                );
                client.setId(rs.getObject("id", UUID.class));
                return client;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding client by ID", e);
        }
        return null;
    }

    public Account editAccount(Account account, UUID accountId) {
        String sql = "UPDATE accounts SET account_number = ?, balance = ?, type = ?, client_id = ?, status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getAccountNumber());
            ps.setDouble(2, account.getBalance());
            ps.setString(3, account.getType().name());
            ps.setObject(4, account.getClientId());
            ps.setBoolean(5, account.isStatus());
            ps.setObject(6, accountId);

            ps.executeUpdate();
            return account;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating account: " + e.getMessage(), e);
        }
    }

    public void deleteByIdAccount(UUID idAccount){
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, idAccount);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting account: " + e.getMessage(), e);
        }
    }

    public void editBalance(BigDecimal balance, UUID accountId) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, balance);
            pstmt.setObject(2, accountId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Error: Account with ID " + accountId + " not found.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error updating balance: " + e.getMessage(), e);
        }
    }
}
