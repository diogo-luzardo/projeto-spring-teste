package com.vendas.vendas.controller;

import com.vendas.vendas.entity.Cliente;
import com.vendas.vendas.jpaRepository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private Clientes clienteRepository;

    @RequestMapping(value = "/hello/{nome}", method = RequestMethod.GET)
    public String helloCliente(@PathVariable("nome") String nomeCliente){
        return String.format("Hello %s", nomeCliente);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody @Valid Cliente cliente){
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable Integer id){
        return clienteRepository.findById(id)
                                .map(clienteExistente -> {
                                    clienteRepository.deleteById(clienteExistente.getId());
                                    return ResponseEntity.ok().build();
                                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){
        return clienteRepository.findById(id)
                                .map(clienteExistente -> {
                                    cliente.setId(clienteExistente.getId());
                                    clienteRepository.save(cliente);
                                    return ResponseEntity.noContent().build();
                                }).orElseGet(()-> new ResponseEntity<Object>(HttpStatus.NOT_FOUND));

    }

    @GetMapping()
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withIgnoreCase()
                                               .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Cliente> lista = clienteRepository.findAll(example);
        return ResponseEntity.ok(lista);
    }
}
