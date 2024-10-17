package cl.villegas.mybatis.service;

import java.util.List;

import cl.villegas.mybatis.model.Document;

public interface DocumentService {
    void delete(long id);
    
    List<Document> findAll();

    Document findById(long id);

    void save(Document document);

    void update(Document document);
}