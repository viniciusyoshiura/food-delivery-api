package com.mycompany.fooddelivery.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class User {

	@EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateRegister;
    
    @ManyToMany
    @JoinTable(name = "user_groupinge", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "groupinge_id"))
    private Set<Groupinge> groupinges = new HashSet<>();
	
    public boolean passwordEquals(String password) {
        return getPassword().equals(password);
    }

    public boolean passwordNotEquals(String password) {
        return !passwordEquals(password);
    }
    
    public boolean removeGroupinge(Groupinge groupinge) {
        return getGroupinges().remove(groupinge);
    }

    public boolean addGroupinge(Groupinge groupinge) {
        return getGroupinges().add(groupinge);
    }
    
}
