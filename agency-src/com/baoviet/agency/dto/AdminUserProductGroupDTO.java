package com.baoviet.agency.dto;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * An authority (a security role) used by Spring Security.
 */
@Getter
@Setter
public class AdminUserProductGroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String adminId;

    private String groupId;
}
