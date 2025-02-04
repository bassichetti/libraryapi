package com.library.libraryapi.controller.mapper;

import com.library.libraryapi.controller.dto.AutorDTO;
import com.library.libraryapi.model.Autor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorMapper INSTANCE = Mappers.getMapper(AutorMapper.class);

    Autor toEntity(AutorDTO dto);

    @InheritInverseConfiguration
    AutorDTO toDTO(Autor entity);

}
