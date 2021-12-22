package com.miempresa.aplicacion.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorHolaMundo {
    
    @GetMapping("/") //path del controlador
    public String holaMundo(){
        return "Inicio";
    }
    @GetMapping("/Vendedor")
    public String paginaVendedores(){
        return "VistaInicioVendedor";
    }
    @GetMapping("/Productos")
    public String paginaProductos(){
        return "VistaInicioProducto";
    }
    @GetMapping("/Facturas")
    public String paginaFacturas(){
        return "VistaInicioFactura";
    }
}
