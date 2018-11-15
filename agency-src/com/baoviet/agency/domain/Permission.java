package com.baoviet.agency.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * An authority/permission (a security role) used by Spring Security.
 */
@Entity
@Table(name = "AGENT_PERMISSION")
@Getter
@Setter
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 0, max = 64)
    @Id
    @Column(length = 64)
    private String name;

    @Column
    private String description;
}
