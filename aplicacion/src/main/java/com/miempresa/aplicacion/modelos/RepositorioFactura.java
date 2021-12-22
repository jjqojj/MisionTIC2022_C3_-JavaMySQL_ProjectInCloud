package com.miempresa.aplicacion.modelos;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;




public interface RepositorioFactura extends CrudRepository<Factura,String> {
    Factura findByNumeroFactura(String numeroFactura);
    
}
