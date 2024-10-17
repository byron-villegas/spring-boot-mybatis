package cl.villegas.mybatis.controller;

import java.io.IOException;
import java.util.List;
import cl.villegas.mybatis.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import cl.villegas.mybatis.model.Document;
import cl.villegas.mybatis.service.DocumentServiceImpl;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("documentos")
public class DocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentServiceImpl service;

    @ApiOperation(value = "Elimina un documento mediante su id")
    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "id") long id) {
        service.delete(id);
    }

    @ApiOperation(value = "Retorna una lista de documentos en formato json/xml")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public List<Document> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Obtiene un documento mediante su id")
    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> get(@PathVariable(value = "id") long id) {
        HttpHeaders headers = new HttpHeaders();
        Document document = service.findById(id);

        if (document == null)
            return null;

        headers.setContentType(MediaType.parseMediaType(document.getContentType()));
        headers.add(Constants.HttpHeaders.CONTENT_DISPOSITION_PROPERTY, Constants.HttpHeaders.INLINE_FILENAME_PROPERTY + document.getName());
        return new ResponseEntity<>(document.getFile(), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Registra un documento")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestParam(value = "file") MultipartFile file) {
        String salida = "Documento creado";
        try {
            Document document = new Document(0, file.getOriginalFilename(), file.getBytes(), file.getContentType());
            service.save(document);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            salida = "Documento con problemas";
        }
        return salida;
    }

    @ApiOperation(value = "Actualiza un documento mediante su id")
    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(value = "id") long id, @RequestParam(value = "file") MultipartFile file) {
        try {
            Document document = new Document(id, file.getOriginalFilename(), file.getBytes(), file.getContentType());
            service.update(document);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}