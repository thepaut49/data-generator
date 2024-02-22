package com.thepaut.backend.service;

import com.thepaut.backend.dto.GenericDto;
import com.thepaut.backend.model.data.GenericEntity;
import com.thepaut.backend.model.data.audit.GenericEntityAudit;

import jakarta.validation.Valid;

public interface IGenericEntityService<CreationDto, UpdateDto extends GenericDto, Entity extends GenericEntity,EntityAudit extends GenericEntityAudit> {

    /**
     * Récupère une entité par son id
     * @param path
     * @param id
     * @return
     */
    GenericDto getEntityById(String path, Long id) ;

    /**
     * On supprime la dernière version de l'entité et on récupère la version précédente
     * @param path
     * @param id
     * @return
     */
    GenericDto rollbackToPreviousVersion(String path, Long id);

    /**
     * On supprime toutes les versions de l'entité qui sont suppérieures au paramètre version et on récupère la version
     * numéro version
     * @param path
     * @param id
     * @param version à récupérer
     * @return
     */
    GenericDto rollbackToVersion(String path, Long id, Long version);

    /**
     * Sauvegarde une nouvelle entité
     * @param path
     * @param creationDto
     * @return
     */
    GenericDto createEntity(String path, @Valid CreationDto creationDto);

    /**
     * Mise à jour de l'entité
     * @param path
     * @param id
     * @param updateDto
     * @return
     */
    GenericDto updateEntity(String path, Long id, @Valid UpdateDto updateDto);

    /**
     * Suppression de l'entité
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * Récupération de la version précédente de l'entité
     * @param path de la requête http
     * @param id de l'entité
     * @param version que l'on veut récupérer
     * @return
     */
    Entity getPreviousVersion(String path, Long id, Long version);

    /**
     * Récupération d'une version précise de l'entité
     * @param path de la requête http
     * @param id de l'entité
     * @param version que l'on veut récupérer
     * @return
     */
    Entity getSpecificVersion(String path, Long id, Long version);


    Entity convertAuditToEntity(String path, EntityAudit audit);

    EntityAudit convertEntityToAudit(Entity entity);

    UpdateDto convertEntityToUpdateDto(Entity entity);

    Entity convertUpdateDtoToEntity(UpdateDto updateDto);

   Entity convertCreationDtoToEntity(CreationDto creationDto);
}
