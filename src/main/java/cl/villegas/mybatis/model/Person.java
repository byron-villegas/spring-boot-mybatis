package cl.villegas.mybatis.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @ApiModelProperty(notes = "ID generado por la base de datos")
    private long id;
    @ApiModelProperty(notes = "El rut de la persona")
    private String rut;
    @ApiModelProperty(notes = "Los nombres de la persona")
    private String names;
    @ApiModelProperty(notes = "Los apellidos de la persona")
    private String surnames;
    @ApiModelProperty(notes = "La edad de la persona")
    private byte age;
}