package com.ayush.model;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "employees")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstName",nullable = false)

    private String firstName;

    @Column(name = "lastName",nullable = false)

    private String lastName;

    @Column(name = "emailId",nullable = false)
    private String emailId;

}