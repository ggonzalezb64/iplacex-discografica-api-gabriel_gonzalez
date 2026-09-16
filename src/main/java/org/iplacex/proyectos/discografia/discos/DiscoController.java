package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {
    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    //HandlePostDiscoRequest
    @PostMapping(
        value = "/disco", 
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disc){

        if(!artistaRepo.existsById(disc.idArtista)){
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        Object res = discoRepo.insert(disc);
        return new ResponseEntity<>(res, null, HttpStatus.CREATED);
    }    

    //HandleGetDiscosRequest
    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest(){
        return new ResponseEntity<>(discoRepo.findAll(), null, HttpStatus.OK);
    }

    //HandleGetDiscoRequest
    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id){
        Optional<Disco> optDisco = discoRepo.findById(id);

        if(!optDisco.isPresent()){
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optDisco.get(), null, HttpStatus.OK);
    }

    //HandleGetDiscosByArtistaRequest
    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id){
        List<Disco> optDisco = discoRepo.findDiscosByIdArtista(id);

        if(optDisco == null || optDisco.isEmpty()){
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optDisco, null, HttpStatus.OK);
    }


}
