package com.ifpe.advopopular.service;

import com.ifpe.advopopular.models.EntidadeGenerica;
import com.ifpe.advopopular.shared.ItemNaoEcontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class ServiceGenerico<T extends EntidadeGenerica, R extends JpaRepository<T, Long>>{

    @Autowired
    protected R repository;

    public ServiceGenerico(R repository) {
        this.repository = repository;
    }


    public T add(T obj) {
        return repository.save(obj);
    }

    public List<T> getAll() {
        return  repository.findAll();
    }

    public T getById(Long id)  {
        Optional<T> itemEncontrado = repository.findById(id);

        if (itemEncontrado.isPresent()) {
            return itemEncontrado.get();
        } else {
            throw new ItemNaoEcontradoException();
        }
    }

    public T update(T obj) {
        Optional<T> itemEncontrado = repository.findById((Long) obj.getId());

        if (itemEncontrado.isPresent()) {
            return repository.save(itemEncontrado.get());
        } else {
            throw new RuntimeException("Impossível atualiar dados. Item não encontrado");
        }
    }

    public void delete(Long id) {
        Optional<T> itemEncontrado = repository.findById(id);

       try {
           if (itemEncontrado.isPresent()) {
               repository.delete(itemEncontrado.get());
           } else {
               throw new ItemNaoEcontradoException();
           }
       } catch (DataIntegrityViolationException e) {
           throw new RuntimeException("Erro ao excluir. Há outro registro associado a informação passada.");
       } catch (Exception e) {
           throw new RuntimeException("Erro ao excluir.");
       }
    }
}
