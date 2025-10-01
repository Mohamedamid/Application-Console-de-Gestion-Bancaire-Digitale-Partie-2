package com.repository.implement;

import com.model.Transaction;
import com.repository.TransactionRepository;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InMemoryTransactionRepository implements TransactionRepository {
    public void saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, amount, currency, type, status, timestamp, account_id, source, target, tellerid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, transaction.getId());
            pstmt.setBigDecimal(2, transaction.getAmount());
            pstmt.setString(3, transaction.getCurrency().name());
            pstmt.setString(4, transaction.getType().name());
            pstmt.setString(5, transaction.getStatus().name());
            pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(transaction.getTimestamp()));
            pstmt.setObject(7, transaction.getAccountId());
            pstmt.setObject(8, transaction.getSource());
            pstmt.setObject(9, transaction.getTarget());
            pstmt.setObject(10, transaction.getTellerId());
            pstmt.executeUpdate();

            System.out.println("Transaction saved successfully! ID: " + transaction.getId());

        } catch (Exception e) {
            String msg = "Error saving transaction (ID=" + transaction.getId() + "): " + e.getMessage();
            throw new RuntimeException(msg, e);
        }
    }
}
