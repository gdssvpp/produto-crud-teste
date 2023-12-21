package com.produto.service;

import com.produto.entity.Produto;
import com.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> getAll(){
        return repository.findAll();
    }

    public Optional<Produto> getById(Long id){
        return repository.findById(id);
    }


    public Produto save(Produto produto){
        return repository.save(produto);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

}
