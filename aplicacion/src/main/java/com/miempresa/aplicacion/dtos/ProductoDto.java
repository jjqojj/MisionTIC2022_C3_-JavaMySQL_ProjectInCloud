/*JK
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miempresa.aplicacion.dtos;

import lombok.Data;

/**
 *
 * @author juanp
 */
@Data
public class ProductoDto {
    private String codigoProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioProducto;
    //private Integer estadoProducto;
}
