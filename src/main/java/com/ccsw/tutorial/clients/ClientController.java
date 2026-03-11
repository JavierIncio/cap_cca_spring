package com.ccsw.tutorial.clients;

import com.ccsw.tutorial.clients.model.Client;
import com.ccsw.tutorial.clients.model.ClientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Client", description = "API of Client")
@RequestMapping(value = "/client")
@RestController
@CrossOrigin(origins = "*")
public class ClientController {
    private final ClientService clientService;
    private final ModelMapper mapper;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper mapper) {
        this.clientService = clientService;
        this.mapper = mapper;
    }

    @Operation(summary = "Find", description = "Method that return a list of Clients")
    @GetMapping()
    public List<ClientDTO> findAll() {
        List<Client> clients = this.clientService.findAll();
        return clients.stream().map(cl -> mapper.map(cl, ClientDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save or Update", description = "Method that saves or updates a Client")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(required = false) Long id, @RequestBody ClientDTO dto) {
        this.clientService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Method that deletes a Client")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        this.clientService.delete(id);
    }
}
