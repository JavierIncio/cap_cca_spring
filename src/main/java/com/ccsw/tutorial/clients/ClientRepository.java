package com.ccsw.tutorial.clients;

import com.ccsw.tutorial.clients.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
