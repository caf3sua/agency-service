package com.baoviet.agency.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "ADMIN_ROLE")
@Getter
@Setter
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 0, max = 64)
    @Id
    @Column(length = 64)
    private String name;

    @Column
    private String description;
    
    // name = "user_id" -> table join
    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name = "ADMIN_ROLE_PERMISSION",
        joinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")},
        inverseJoinColumns = {@JoinColumn(name = "permission_name", referencedColumnName = "name")})
    @BatchSize(size = 20)
    private Set<AdminPermission> authorities = new HashSet<>();
}
