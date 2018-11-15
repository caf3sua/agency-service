package com.baoviet.agency.dto;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * An authority/permission (a security role) used by Spring Security.
 */
@Getter
@Setter
public class AdminProductGroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String groupId;

    private String groupName;
}
