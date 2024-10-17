package cl.villegas.mybatis.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document {
    @ApiModelProperty(notes = "ID generado por la base de datos")
    private long id;
    @ApiModelProperty(notes = "El nombre del archivo")
    private String name;
    @ApiModelProperty(notes = "El archivo en bytes")
    private byte[] file;
    @ApiModelProperty(notes = "El tipo de contenido del archivo")
    private String contentType;
}