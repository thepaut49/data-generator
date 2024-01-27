package com.thepaut.backend.exception;

import com.thepaut.backend.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityVersionNotFoundException extends APIException {

    public EntityVersionNotFoundException(Long id, Long version, String entityType, String path, String code) {
        super(id, entityType, path, code, String.format(Constants.MESSAGE_VERSION_RESOURCE_NOT_FOUND, version, entityType, id));
    }
}
