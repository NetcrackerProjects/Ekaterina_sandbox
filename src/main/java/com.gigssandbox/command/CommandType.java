package com.gigssandbox.command;

public enum CommandType {
    REGISTER,
    LOG_IN,
    LOG_OUT,
    GET_GIGS,
    GET_GIGS_BY_BAND,
    JOIN_COMMUNITY,
    LEAVE_COMMUNITY,
    JOIN_GIG,
    LEAVE_GIG,
    HELP,
    UNSUPPORTED,
    NOT_ENOUGH_PARAMETERS
}