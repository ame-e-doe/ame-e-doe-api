package br.com.loveanddonateapi.service;

import java.util.List;

public interface BaseService<Dto, Entity> {

    Entity createOrUpdate(Dto dto);

    Dto getById(Long id);

    List<Dto> getAll();

    void delete(Long id);

}
