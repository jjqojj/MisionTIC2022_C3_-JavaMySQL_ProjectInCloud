package com.miempresa.aplicacion.controladores;


import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;




@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControladorVendedor {
    
    private final RepositorioVendedor repositorioVendedor;
    private Vendedor VendedorUptade ;
    
    @GetMapping("/vendedores") //path del controlador
    public String getTodosLosVendedores(Model model){
        Iterable<Vendedor> vendedores= repositorioVendedor.findAll();
        model.addAttribute("vendedores",vendedores);
        return "vistaVendedor";
    }    
    
    @GetMapping("/vendedores/{codigoVendedor}") //path del controlador
    public String getVendedorById(@PathVariable String codigoVendedor, Model model){
        Vendedor vendedores = repositorioVendedor.findByCodVendedor(codigoVendedor);
        model.addAttribute("vendedores",vendedores);
        return "vistaVendedor";
    }
    
    @GetMapping("/crear/vendedor")
    public String CrearVendedor(Model model){
        model.addAttribute("vendedor", new Vendedor());
        return "vistaCrearVendedor";
    }
    @PostMapping("/crear/vendedor")
    public RedirectView procesarVendedor(@ModelAttribute Vendedor vendedor){
      
      Vendedor vendedorGuardado = repositorioVendedor.save(vendedor);
      if(vendedorGuardado == null){
          return new RedirectView ("/crear/Vendedor",true);
      }
        return new RedirectView ("/vendedores/"+vendedorGuardado.getCodVendedor(),true);
    }
    
    @GetMapping("/borrar/vendedor")
    public String EliminarVendedor(Model model) {
       model.addAttribute("vendedor",new Vendedor());
        return "vistaBorrarVendedor";
    }
    
    @PostMapping("/borrar/vendedor")
       public RedirectView procesarEliminacionVendedor(@ModelAttribute Vendedor vendedor){
       Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
       for( Vendedor i:vendedores ){
           if (vendedor.getCodVendedor().equals(i.getCodVendedor())){
               repositorioVendedor.delete(i);
               return new RedirectView("/vendedores/",true);
           }               
       }


       System.out.println("no existe el codigo del vendedor");
       
       
       return new RedirectView("/borrar/vendedor",true);
    }
       
    @GetMapping("/actualizar/vendedor")
    public String SolicitarActualizarVendedor(Model model) {
    model.addAttribute("vendedor",new Vendedor());
    return "vistaActualizarVendedor";
    }
    @PostMapping("/actualizar/vendedor")
    public RedirectView ProcesarSolicitarActualizarVendedor(@ModelAttribute Vendedor vendedor){
       Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
       for( Vendedor i:vendedores ){
           
           if (vendedor.getCodVendedor().equals(i.getCodVendedor())){
                VendedorUptade=i;
               return new RedirectView("/actualizar/vendedor/"+vendedor.getCodVendedor(),true);
           }
  
           
           }
       System.out.println("El vendedor no exite, ingresa otro");
       
       
       return new RedirectView("/actualizar/vendedor",true);
       
       }
    @GetMapping("/actualizar/vendedor/{codVendedor}")
    public String ActualizarVendedor(@PathVariable String codVendedor,Model model) {     
        
        Vendedor vendedor=repositorioVendedor.findByCodVendedor(codVendedor);
        model.addAttribute("vendedor",vendedor);
        return "vistaConfirmarActualizacionVendedor";
 
    }
    @PostMapping("/actualizar/vendedor/{codVendedor}")
    public RedirectView ProcesaActualizarVendedor(@PathVariable String codVendedor,@ModelAttribute Vendedor vendedor){
        Vendedor vendedorUpdate = repositorioVendedor.findByCodVendedor(vendedor.getCodVendedor());
          if (vendedorUpdate==null){
           System.out.println("error en el codigo o vendedor de producto");
           return new RedirectView("/actualizar/vendedor/"+ vendedor.getCodVendedor(),true);
       }
           Vendedor vendedorGuardado = repositorioVendedor.save(vendedor);
           
        if(vendedorGuardado == null){
            System.out.println("Fallo la actualización");
          return new RedirectView ("/actualizar/vendedor",true);
      }
        return new RedirectView ("/vendedores/"+vendedorGuardado.getCodVendedor(),true);
    }    
             
    }
        

    
    
   
    

