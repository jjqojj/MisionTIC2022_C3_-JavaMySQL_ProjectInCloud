package com.miempresa.aplicacion.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "t_productos")
@Getter
@Setter
public class Producto {

    @Id
    @Column(name = "cod_producto")
    private String codProducto;


    @Column(name = "nombre_producto")
    private String nombreProducto ;
    

    @Column(name = "descripcion_producto")
    private String descripcionProducto;

    @Column(name = "precio_producto")
    private Double precioProducto;

   /* @Column(name="estado_producto")
    private Integer estadoProducto;*/

}
