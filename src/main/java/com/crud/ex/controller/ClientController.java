package com.crud.ex.controller;

import com.crud.ex.entity.Client;
import com.crud.ex.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @GetMapping(
            path = "/{id}"
    )
    public Client getClientByid(@PathVariable Long id){
        return clientRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    @PostMapping
    public ResponseEntity createClient(@RequestBody Client client) throws URISyntaxException {
        Client save = clientRepository.save(client);
        return ResponseEntity.created(new URI("/api/v1/clients/"+save.getId())).body(save);
    }

    @PutMapping("/{id}")
    public ResponseEntity editClient(@PathVariable Long id, @RequestBody Client client){
        Client currentCLient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
        currentCLient.setName(client.getName());
        currentCLient.setEmail(client.getEmail());
        Client save = clientRepository.save(currentCLient);

        return ResponseEntity.ok(save);
    }

    @DeleteMapping(
            path = "/{id}"
    )
    public ResponseEntity deleteClient(@PathVariable Long id){
        Client currentClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
        clientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
