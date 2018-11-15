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
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "ADMIN_USER_PRODUCT_GROUP")
@Getter
@Setter
public class AdminUserProductGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 0, max = 64)
    @Id
    @Column(length = 64)
    private String adminId;

    @Column
    private String groupId;
    
//    // name = "user_id" -> table join
//    @JsonIgnore
//    @ManyToMany(fetch=FetchType.EAGER)
//    @JoinTable(
//        name = "ADMIN_ROLE_PERMISSION",
//        joinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")},
//        inverseJoinColumns = {@JoinColumn(name = "permission_name", referencedColumnName = "name")})
//    @BatchSize(size = 20)
//    private Set<AdminPermission> authorities = new HashSet<>();
}
