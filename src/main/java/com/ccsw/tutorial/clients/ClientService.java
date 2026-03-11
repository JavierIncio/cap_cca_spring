package com.ccsw.tutorial.clients;

import com.ccsw.tutorial.clients.model.Client;
import com.ccsw.tutorial.clients.model.ClientDTO;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    void save(Long id, ClientDTO dto);

    void delete(Long id) throws Exception;

    Client get(Long id);
}
