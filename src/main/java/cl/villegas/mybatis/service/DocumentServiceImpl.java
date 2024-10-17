package cl.villegas.mybatis.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.villegas.mybatis.model.Document;
import cl.villegas.mybatis.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository repository;

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

    @Override
    public List<Document> findAll() {
        return repository.findAll();
    }

    @Override
    public Document findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Document document) {
        repository.save(document);
    }

    @Override
    public void update(Document document) {
        repository.update(document);
    }
}