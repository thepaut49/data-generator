package com.thepaut.backend.exception;

import com.thepaut.backend.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityAlreadyExistException extends APIException {

    public EntityAlreadyExistException(Long id, String entityType, String path, String code) {
        super(id, entityType, path, code ,String.format(Constants.MESSAGE_RESOURCE_ALREADY_EXIST, entityType, id));
    }

}
