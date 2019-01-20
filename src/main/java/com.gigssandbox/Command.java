package com.gigssandbox;

import lombok.Getter;

@Getter
public enum Command {
    LOG_IN("log_in"),
    LOG_OUT("log_out_success"),
    GET_GIGS("get_gigs"),
    GET_GIGS_BY_BAND("get_gigs_by_band"),
    JOIN_COMMUNITY("join_community"),
    LEAVE_COMMUNITY("leave_community"),
    JOIN_GIG("join_gig"),
    LEAVE_GIG("leave_gig"),
    UNSUPPORTED("unsupported");

    private String property;

    Command(String property){this.property = property;}

}