package com.produto.controller;

import com.produto.entity.Produto;
import com.produto.service.ProdutoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ModelAndView cadastro(){
        ModelAndView mv = new ModelAndView("produtos");
        mv.addObject("produto", new Produto());

        return mv;
    }

    @GetMapping("/list")
    public String listarTodos(Model model){
        List<Produto> produtoList = service.getAll();
        model.addAttribute("produto", produtoList);

        return "listproduto";
    }

    @PostMapping("/save")
    public ModelAndView save(@Validated @ModelAttribute("produto") Produto produto, BindingResult result){
        if(result.hasErrors()){
            return new ModelAndView("produtos");
        }

        Produto produtoSave = service.save(produto);

        return new ModelAndView("redirect:/produto/list" + "?=salvo_com_sucesso");
    }

    @GetMapping("/consulta/{id}")
    public ModelAndView consulta(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("produtos");

        Optional<Produto> produtoConsulta = service.getById(id);
        mv.addObject("produto", produtoConsulta);

        return mv;
    }

    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto produtoAtualizado){
        Optional<Produto> produtoOptional = service.getById(id);
        if(!produtoOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Produto produtoExistente = produtoOptional.get();

        produtoExistente.setDescricaoProduto(produtoAtualizado.getDescricaoProduto());
        produtoExistente.setNomeProduto(produtoAtualizado.getNomeProduto());
        produtoExistente.setDe(produtoAtualizado.getDe());
        produtoExistente.setPara(produtoAtualizado.getPara());
        produtoExistente.setQuantidade(produtoAtualizado.getQuantidade());
        produtoExistente.setRefrigerado(produtoAtualizado.getRefrigerado());

        produtoExistente = service.save(produtoExistente);

        return ResponseEntity.ok(produtoExistente);

    }

}
