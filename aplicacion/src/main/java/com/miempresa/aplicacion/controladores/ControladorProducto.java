//JK
package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.dtos.ProductoDto;
import com.miempresa.aplicacion.modelos.Producto;
import com.miempresa.aplicacion.modelos.RepositorioProducto;
import java.util.ArrayList;
import java.util.List;

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
public class ControladorProducto {
    
    private final RepositorioProducto repositorioProducto;
    
    @GetMapping("/productos") //path del controlador
    public String getTodosLosProductos(Model model){
        Iterable<Producto> productos = repositorioProducto.findAll();
        model.addAttribute("productos",productos);
        return "vistaProducto";
    }    
    
    @GetMapping("/productos/{codigoProducto}") //path del controlador
    public String getProductoById(@PathVariable String codigoProducto, Model model){
        List<String> listaProducto = new ArrayList<>();
        listaProducto.add(codigoProducto);
        Iterable<Producto> productos = repositorioProducto.findAllById(listaProducto);
        model.addAttribute("productos",productos);
        return "vistaProducto";
    }
    
    @GetMapping ("/crear/producto")
    public String crearProducto(Model model){
        model.addAttribute("producto",new ProductoDto());
        return "vistaCrearProducto";
    }
    @PostMapping("/crear/producto")
    public RedirectView procesarProducto(@ModelAttribute ProductoDto productoDto){
        Producto elem = repositorioProducto.findByCodProducto(productoDto.getCodigoProducto());
        if(elem!=null){
            return new RedirectView ("/crear/producto/error",true);
        }else{
            Producto producto = new Producto();
            producto.setCodProducto(productoDto.getCodigoProducto());
            producto.setNombreProducto(productoDto.getNombreProducto());
            producto.setDescripcionProducto(productoDto.getDescripcionProducto());
            producto.setPrecioProducto(productoDto.getPrecioProducto());
            //producto.setEstadoProducto(1);
            if(repositorioProducto.save(producto)==null){
                return new RedirectView ("/crear/producto/errorBD",true);
            }
        }
        return new RedirectView("/productos",true);
    }
    
    @GetMapping("/actualizar/producto")
    public String buscarProducto(Model model){
       model.addAttribute("producto",new ProductoDto());
       return "vistaActualizarProducto";
    }
    @PostMapping("/actualizar/producto")
    public RedirectView procesarBuscarProducto(@ModelAttribute ProductoDto productoDto){
        Producto producto = repositorioProducto.findByCodProducto(productoDto.getCodigoProducto());
        if(producto!=null){
            return new RedirectView ("/actualizar/producto/"+productoDto.getCodigoProducto(),true);
        }
        return new RedirectView("/actualizar/producto/error");
    }
    
    @GetMapping("/actualizar/producto/{codigoProducto}")
    public String updtProducto (@PathVariable String codigoProducto,Model model){
        Producto producto = repositorioProducto.findByCodProducto(codigoProducto);
        model.addAttribute("producto",producto);        
        return "vistaUpdtProducto";
    }
    
    @PostMapping("/actualizar/producto/{codigoProducto}")
    public RedirectView procesarUpdtProducto(@ModelAttribute ProductoDto productoDto){ 
        Producto producto = repositorioProducto.findByCodProducto(productoDto.getCodigoProducto());
        producto.setCodProducto(productoDto.getCodigoProducto());
        producto.setNombreProducto(productoDto.getNombreProducto());
        producto.setDescripcionProducto(productoDto.getDescripcionProducto());
        producto.setPrecioProducto(productoDto.getPrecioProducto());        
        if(repositorioProducto.save(producto)!=null){
            return new RedirectView ("/productos",true);
        }
        return new RedirectView ("/actualizar/producto/"+producto.getCodProducto(),true);
    }
    
    /*@GetMapping("/borrar/producto")
    public String borrarProducto(Model model){
        model.addAttribute("producto",new ProductoDto());
        return "vistaEliminarProducto";
    }
    @PostMapping("/borrar/producto")
    public RedirectView procesarBorrarProducto(@ModelAttribute ProductoDto productoDto){
        Producto producto = repositorioProducto.findByCodProducto(productoDto.getCodigoProducto());
        if(producto!=null){
            producto.setEstadoProducto(0);
            if(repositorioProducto.save(producto)==null)
              return new RedirectView ("/borrar/producto/errorBD",true);  
        }else{
            return new RedirectView ("/borrar/producto/error",true);
        }
        return new RedirectView ("/productos",true);
    }*/
    @GetMapping("/eliminar/producto")
    public String eliminarProducto(Model model){
        model.addAttribute("producto", new ProductoDto());
        return "vistaBuscarProducto";
    }
    @PostMapping("/eliminar/producto")
    public RedirectView pocesarEliminarProducto(@ModelAttribute ProductoDto productoDto){
        Producto producto = repositorioProducto.findByCodProducto(productoDto.getCodigoProducto());
        if(producto!=null){
            repositorioProducto.delete(producto);
            return new RedirectView ("/productos",true);
            }
        return new RedirectView("/eliminar/producto/error",true);
    }
    
}