package br.com.loveanddonateapi.service;

public interface BaseService<Dto> {

    Dto create(Dto dto, String email);

    Dto getById(Long id);

    //List<Dto> getAll(String token);

    void delete(Long id);
}
