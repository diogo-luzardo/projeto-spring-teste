package com.vendas.vendas.controller;

import com.vendas.vendas.entity.Cliente;
import com.vendas.vendas.entity.Produto;
import com.vendas.vendas.jpaRepository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private Produtos produtoRepository;

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto){
        try {
            return produtoRepository.save(produto);
        }catch (Exception e){
            throw new RuntimeException("Could not save");
        }
    }

    @GetMapping("/{id}")
    public Produto findById(@PathVariable Integer id){
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found"));

    }

    @DeleteMapping("/{id}")
    public Produto delete(@PathVariable Integer id){
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "item not found"));

    }

    @GetMapping("/todos")
    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    @PutMapping("/{id}")
    public Produto update(@PathVariable Integer id, @RequestBody @Valid Produto produto){
        return produtoRepository.findById(id)
                .map(produtoBase -> {
                    produto.setId(produtoBase.getId());
                    produtoRepository.save(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "item not found"));

    }

    @GetMapping
    public ResponseEntity<List<Produto>> findFilter(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Produto> lista = produtoRepository.findAll(example);
        return ResponseEntity.ok(lista);
    }


}
