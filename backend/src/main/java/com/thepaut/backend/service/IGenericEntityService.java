package com.thepaut.backend.service;

import com.thepaut.backend.dto.GenericDto;

import javax.validation.Valid;

public interface IGenericEntityService<CreationDto, UpdateDto extends GenericDto> {

    /**
     * Récupère une entité par son id
     * @param id
     * @return
     */
    GenericDto getEntityById(Long id) ;

    /**
     * On supprime la dernière version de l'entité et on récupère la version précédente
     * @param id
     * @return
     */
    GenericDto rollbackToPreviousVersion(Long id);

    /**
     * On supprime toutes les versions de l'entité qui sont suppérieures au paramètre version et on récupère la version
     * numéro version
     * @param id
     * @param version à récupérer
     * @return
     */
    GenericDto rollbackToVersion(Long id, Long version);

    /**
     * Sauvegarde une nouvelle entité
     * @param creationDto
     * @return
     */
    GenericDto createEntity(@Valid CreationDto creationDto);

    /**
     * Mise à jour de l'entité
     * @param id
     * @param updateDto
     * @return
     */
    GenericDto updateEntity(Long id, @Valid UpdateDto updateDto);

    /**
     * Suppression de l'entité
     * @param id
     * @return
     */
    boolean deleteById(Long id);
}
