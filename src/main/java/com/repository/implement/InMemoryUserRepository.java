package com.repository.implement;

import com.repository.UserRepository;
import com.util.DBConnection;
import com.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {


    @Override
    public User findByEmail(String email) {


        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role").toUpperCase())
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur : " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role").toUpperCase())
                );
                users.add(user);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs : " + e.getMessage(), e);
        }

        return users;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getRole().toString());
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'utilisateur : " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence de l'utilisateur : " + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void updateByEmail(User user, String oldEmail) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole().toString());
            pstmt.setString(5, oldEmail);
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur : " + e.getMessage(), e);
        }
    }
}
