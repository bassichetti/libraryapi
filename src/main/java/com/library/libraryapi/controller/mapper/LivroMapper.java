package com.library.libraryapi.controller.mapper;

import com.library.libraryapi.controller.dto.CadastroLivroDTO;
import com.library.libraryapi.controller.dto.LivroDTO;
import com.library.libraryapi.model.Livro;
import com.library.libraryapi.repository.AutorRepository;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {
    @Autowired
     AutorRepository repository;


    @Mapping(target="autor", expression = "java( repository.findById(dto.idAutor()).orElse(null))")
   public abstract  Livro toEntity(CadastroLivroDTO dto);

    @InheritInverseConfiguration
    public abstract LivroDTO toDTO(Livro livro);


}
