package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.dtos.FacturaDto;
import com.miempresa.aplicacion.modelos.RepositorioFactura;
import com.miempresa.aplicacion.modelos.Factura;
import com.miempresa.aplicacion.modelos.Producto;
import com.miempresa.aplicacion.modelos.RepositorioProducto;
import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import lombok.RequiredArgsConstructor;
import java.sql.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControladorFactura {
    private final RepositorioFactura repositorioFactura;
    private final RepositorioProducto repositorioProducto;
    private final RepositorioVendedor repositorioVendedor;
    
    
    @GetMapping("/facturas") //path del controlador
    public String getTodaslasFacturas(Model model){
        Iterable<Factura> facturas = repositorioFactura.findAll();
        model.addAttribute("facturas",facturas);
        return "vistaFactura";
    }    
    
    @GetMapping("/facturas/{numeroFactura}") //path del controlador
    public String getFacturaByNumero(@PathVariable String numeroFactura, Model model){
        Factura factura = repositorioFactura.findByNumeroFactura(numeroFactura);
        model.addAttribute("facturas",factura);
        return "vistaFactura";
    }
    
    @GetMapping("/crear/factura")
    public String createFactura(Model model) {
        model.addAttribute("factura",new FacturaDto());
        Iterable<Producto> productos = repositorioProducto.findAll();
        model.addAttribute("productos",productos);
        Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
        model.addAttribute("vendedores",vendedores);
        return "vistaCrearFactura";
 
    }
      
    @PostMapping("/crear/factura")
    public RedirectView procesarFactura(@ModelAttribute FacturaDto facturaDto){
       Producto producto = repositorioProducto.findByCodProducto(facturaDto.getCodigoProducto());
       Vendedor vendedor = repositorioVendedor.findByCodVendedor(facturaDto.getCodigoVendedor());
       System.out.println(producto);
       System.out.println(vendedor);
       if (producto==null || vendedor==null){
           System.out.println("error en el codigo o vendedor de producto");
           return new RedirectView("/crear/factura/",true);
       }
       Iterable<Factura> facturas = repositorioFactura.findAll();
       for( Factura factura:facturas ){
           if (facturaDto.getNumeroFactura().equals(factura.getNumeroFactura())){
               System.out.print(" ya existe la factura");
               return new RedirectView("/crear/factura/",true);
           }
                
       }
       Factura factura = new Factura();
       factura.setNumeroFactura(facturaDto.getNumeroFactura());
       factura.setProducto(producto);
       factura.setVendedor(vendedor);
       factura.setFechaVenta(facturaDto.getFechaVenta());
       factura.setValorFactura(facturaDto.getValorFactura());
       
       
       Factura facturaGuardada = repositorioFactura.save(factura);
       if (facturaGuardada == null){
           System.out.println("fallo en la base de datos");
           return new RedirectView("/crear/factura/",true);
       }
       System.out.println("exito");
       return new RedirectView("/facturas/"+facturaGuardada.getNumeroFactura(),true);
    }    
    @GetMapping("/eliminar/factura")
    public String deleteFactura(Model model) {
        model.addAttribute("factura",new FacturaDto());
        return "vistaEliminarFactura";
 
    }
      
    @PostMapping("/eliminar/factura")
    public RedirectView procesarEliminacionFactura(@ModelAttribute FacturaDto facturaDto){
       Iterable<Factura> facturas = repositorioFactura.findAll();
       for( Factura factura:facturas ){
           if (facturaDto.getNumeroFactura().equals(factura.getNumeroFactura())){
               repositorioFactura.delete(factura);
               return new RedirectView("/facturas/",true);
           }
                
       }

       System.out.println("no existe numero de factura");
       
       
       return new RedirectView("/eliminar/factura",true);
    }    
   
    @GetMapping("/actualizar/factura")
    public String updateFactura(Model model) {
        model.addAttribute("factura",new FacturaDto());
        return "vistaValidarUpdate";
 
    }
      
    @PostMapping("/actualizar/factura")
    public RedirectView procesarUpdateFactura(@ModelAttribute FacturaDto facturaDto){
       Iterable<Factura> facturas = repositorioFactura.findAll();
       for( Factura factura:facturas ){
           if (facturaDto.getNumeroFactura().equals(factura.getNumeroFactura())){
               return new RedirectView("/actualizar/factura/"+facturaDto.getNumeroFactura(),true);
           }
                
       }

       System.out.println("no existe numero de factura");
       
       
       return new RedirectView("/actualizar/factura",true);
    }    
    @GetMapping("/actualizar/factura/{numeroFactura}")
    public String verificarUpdateFactura(@PathVariable String numeroFactura,Model model) {
        
        model.addAttribute("factura",new FacturaDto());
        Iterable<Producto> productos = repositorioProducto.findAll();
        model.addAttribute("productos",productos);
        Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
        model.addAttribute("vendedores",vendedores);
        
        Factura factura=repositorioFactura.findByNumeroFactura(numeroFactura);
        model.addAttribute("facturas",factura);
        return "vistaUpdate";
 
    }
      
    @PostMapping("/actualizar/factura/{numeroFactura}")
    public RedirectView procesarVerificacionUpdateFactura(@PathVariable String numeroFactura,@ModelAttribute FacturaDto facturaDto){
       
       Producto producto = repositorioProducto.findByCodProducto(facturaDto.getCodigoProducto());
       Vendedor vendedor = repositorioVendedor.findByCodVendedor(facturaDto.getCodigoVendedor());
//       System.out.println(facturaDto.getNumeroFactura());
//       System.out.println(vendedor);
       if (producto==null || vendedor==null){
           System.out.println("error en el codigo o vendedor de producto");
           return new RedirectView("/actualizar/factura/"+facturaDto.getNumeroFactura(),true);
       }
       Factura factura = repositorioFactura.findByNumeroFactura(facturaDto.getNumeroFactura());

       factura.setNumeroFactura(facturaDto.getNumeroFactura());
       factura.setProducto(producto);
       factura.setVendedor(vendedor);
       factura.setFechaVenta(facturaDto.getFechaVenta());
       factura.setValorFactura(facturaDto.getValorFactura());
       
       
       Factura facturaGuardada = repositorioFactura.save(factura);
       if (facturaGuardada == null){
           System.out.println("fallo en la base de datos");
           return new RedirectView("/actualizar/factura/"+facturaDto.getNumeroFactura(),true);
       }
       System.out.println("exito");
       return new RedirectView("/facturas/",true);
 
    }    
    
    
}
