package com.sample.person.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "custom-generator",
            strategy = GenerationType.IDENTITY)
    @GenericGenerator(
            name = "custom-generator",
            strategy = "com.sample.person.utils.BaseIdentifierGenerator")
    protected String id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    protected Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    protected Timestamp modifiedAt;

}
