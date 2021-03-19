package bll;

import dao.AbstractDAO;
import dao.ClientDAO;
import model.Client;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that implements the business logic for the Client table/model
 */
public class ClientBLL {
    private final AbstractDAO<Client> dao;

    public ClientBLL() {
        this.dao = new ClientDAO();
    }

    public Client findClientByName(String name) {
        Client c = dao.findByField(name, "name");
        if(c == null) {
            throw new NoSuchElementException("No client with the name " + name + " was found.");
        }
        return c;
    }

    public void insertClient(Client client) {
        dao.insert(client);
    }

    public void deleteClient(Client client) {
        dao.delete(client.getName(), "name");
    }

    public void deleteClient(String name) {
        dao.delete(name, "name");
        (new OrderBLL()).deleteClientOrders(name);
    }

    public void deleteAll() {
        dao.delete(0, "deleted");
        wipe();
    }

    public void wipe() {
        dao.wipe();
    }

    public List<Client> reportClient() {
        return dao.findAll();
    }
}
