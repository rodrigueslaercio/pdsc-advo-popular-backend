package com.ifpe.advopopular.controller;

import com.ifpe.advopopular.models.EntidadeGenerica;
import com.ifpe.advopopular.service.ServiceGenerico;
import com.ifpe.advopopular.shared.ItemNaoEcontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ControllerGenerico<T extends EntidadeGenerica, R extends ServiceGenerico> {

    @Autowired
    protected R service;

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<?> add(@RequestBody T obj) {
        T saida = (T) service.add(obj);
        return new ResponseEntity<>(saida, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    protected ResponseEntity<?> update(@RequestBody T obj) {
        try {
            T itemEncontrado = (T) service.update(obj);
            return new ResponseEntity<>(itemEncontrado, HttpStatus.OK);
        } catch (ItemNaoEcontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<?> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    protected ResponseEntity<?> getById(@PathVariable Long id) {
        T itemEncontrado = (T) service.getById(id);
        return new ResponseEntity<>(itemEncontrado, HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
