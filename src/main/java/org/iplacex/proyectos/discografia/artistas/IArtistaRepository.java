package org.iplacex.proyectos.discografia.artistas;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IArtistaRepository extends MongoRepository<Artista, String>{
    @Query("{ nombre: ?0 }")
    Artista findArtistaByNombre(String nombre) throws Error;
    
}
