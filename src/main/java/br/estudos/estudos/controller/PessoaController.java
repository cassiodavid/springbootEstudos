package br.estudos.estudos.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.estudos.estudos.buscasBanco.UsuarioBusca;
import br.estudos.estudos.entity.Pessoa;
import br.estudos.estudos.repository.PessoaRepository;

@RestController
public class PessoaController {

    @Autowired
    private PessoaRepository _pessoaRepository;

    @RequestMapping(value = "/pessoa/pesquisa", method = RequestMethod.GET)
    public List<Pessoa> Get(Pageable pageable) {
        // Lembrar Conceitos de Paginação no Spring
        Page<Pessoa> page = _pessoaRepository.findAll(pageable);
        return page.getContent();
    }

    @RequestMapping(value = "/pessoa/pesquisa-dinamica", method = RequestMethod.GET)
    public List<Pessoa> GetPesquisaDinamica(UsuarioBusca usuarioBusca, Pageable pageable) {
        // Lembrar Conceitos JPA , Criteria... bla bla bla
        return _pessoaRepository.findAll(usuarioBusca.toSpec(), pageable).getContent();
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pessoa> GetById(@PathVariable(value = "id") long id) {
        Optional<Pessoa> pessoa = _pessoaRepository.findById(id);
        if (pessoa.isPresent())
            return new ResponseEntity<Pessoa>(pessoa.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/pessoa", method = RequestMethod.POST)
    public Pessoa Post(@Valid @RequestBody Pessoa pessoa) {
        return _pessoaRepository.save(pessoa);
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Pessoa> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Pessoa newPessoa) {
        Optional<Pessoa> oldPessoa = _pessoaRepository.findById(id);
        if (oldPessoa.isPresent()) {
            Pessoa pessoa = oldPessoa.get();
            pessoa.setNome(newPessoa.getNome());
            _pessoaRepository.save(pessoa);
            return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
        Optional<Pessoa> pessoa = _pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            _pessoaRepository.delete(pessoa.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
