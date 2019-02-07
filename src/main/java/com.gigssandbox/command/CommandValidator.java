package com.gigssandbox.command;

import com.gigssandbox.exceptions.CommandValidationException;
import java.util.List;

class CommandValidator {
    void validate(CommandType type, List<String> parameters) throws CommandValidationException {
        if (!hasExactCount(type, parameters.size())) {
            throw new CommandValidationException();
        }
    }

    private boolean hasExactCount(CommandType type, int parametersNumber) {
        switch (type) {
            case LOG_IN:
            case REGISTER:
                return parametersNumber == 2;

            case GET_GIGS_BY_BAND:
            case JOIN_COMMUNITY:
            case JOIN_GIG:
                return parametersNumber == 1;

            default:
                return true;
        }
    }
}
