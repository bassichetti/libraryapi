package com.library.libraryapi.controller.mapper;

import com.library.libraryapi.controller.dto.AutorDTO;
import com.library.libraryapi.model.Autor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "dataNascimento", source = "dataNascimento")
    @Mapping(target = "nacionalidade", source = "nacionalidade")
    @Mapping(target = "livros", ignore = true)

    Autor toEntity(AutorDTO dto);

    @InheritInverseConfiguration
    AutorDTO toDTO(Autor entity);

}
