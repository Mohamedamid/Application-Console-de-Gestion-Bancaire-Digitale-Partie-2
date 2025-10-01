package com.repository.implement;

import com.model.Client;
import com.model.User;
import com.repository.ClientRepository;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryClientRepository implements ClientRepository {

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getString("full_name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getDouble("salary"),
                        Client.Currency.valueOf(rs.getString("currency"))
                );
                client.setId(rs.getObject("id", UUID.class));
                clients.add(client);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs : " + e.getMessage(), e);
        }

        return clients;
    }

    public Client createClient(Client client) {
        String sql = "INSERT INTO clients (id, full_name, address, email, salary, currency) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psClient = conn.prepareStatement(sql)) {
            psClient.setObject(1, client.getId());
            psClient.setString(2, client.getFullName());
            psClient.setString(3, client.getAddress());
            psClient.setString(4, client.getEmail());
            psClient.setDouble(5, client.getSalary());
            psClient.setString(6, client.getCurrency().name());
            psClient.executeUpdate();

            return client;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating client", e);
        }
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

    public Client editClient(Client client, UUID clientId) {
        String sql = "UPDATE clients SET full_name = ?, address = ?, email = ?, salary = ?, currency = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getFullName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getEmail());
            ps.setDouble(4, client.getSalary());
            ps.setString(5, client.getCurrency().name());
            ps.setObject(6, clientId);
            ps.executeUpdate();

            return client;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error editing client", e);
        }
    }

    public void deleteClient(UUID clientId) {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, clientId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting client", e);
        }
    }
}
