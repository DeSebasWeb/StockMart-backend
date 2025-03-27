package sl.sistemaInventarios.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String descripcion;
    Double precio;
    Integer cantExistencia;
    String categoria;
//
//    public Producto(Integer id, String descripcion, Double precio, Integer cantExistencia) {
//        this.id = id;
//        this.descripcion = descripcion;
//        this.precio = precio;
//        this.cantExistencia = cantExistencia;
//    }
//
//    public Producto() {
//
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getDescripcion() {
//        return descripcion;
//    }
//
//    public void setDescripcion(String descripcion) {
//        this.descripcion = descripcion;
//    }
//
//    public Double getPrecio() {
//        return precio;
//    }
//
//    public void setPrecio(Double precio) {
//        this.precio = precio;
//    }
//
//    public Integer getCantExistencia() {
//        return cantExistencia;
//    }
//
//    public void setCantExistencia(Integer cantExistencia) {
//        this.cantExistencia = cantExistencia;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Producto producto = (Producto) o;
//        return Objects.equals(id, producto.id) && Objects.equals(descripcion, producto.descripcion) && Objects.equals(precio, producto.precio) && Objects.equals(cantExistencia, producto.cantExistencia);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, descripcion, precio, cantExistencia);
//    }
//
//    @Override
//    public String toString() {
//        return "Producto{" +
//                "id=" + id +
//                ", descripcion='" + descripcion + '\'' +
//                ", precio=" + precio +
//                ", cantExistencia=" + cantExistencia +
//                '}';
//    }
}