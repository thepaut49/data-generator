package com.thepaut.backend.exception;

import com.thepaut.backend.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundException extends APIException {

    public EntityNotFoundException(Long id, String entityType, String path, String code) {
        super(id, entityType, path, code, String.format(Constants.MESSAGE_RESOURCE_NOT_FOUND, entityType, id));
    }

}
