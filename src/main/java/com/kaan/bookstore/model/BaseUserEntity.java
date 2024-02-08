
package com.kaan.bookstore.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@MappedSuperclass
@Data
@AllArgsConstructor
public class BaseUserEntity implements UserDetails {

    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String lastname;
    @Column(length = 30, nullable = false, unique = true)
    private String email;
    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;

    @Column(length = 20, unique = true)
    private String username;
    @Column(length = 20)
    private String password;
    
    private boolean isEnabled ;
    private boolean accountNonLocked ;
    private boolean accountNonExpired ;
    private boolean credentialsNonExpired ;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable (name = "user_roles" , joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated (EnumType.STRING)
    private Set<Role> authorities ;
    
}
