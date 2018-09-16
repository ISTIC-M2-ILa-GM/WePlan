package fr.istic.gm.weplan.domain.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static fr.istic.gm.weplan.domain.config.PersistsDefinition.CITIES;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.DEPARTMENTS;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.EMAIL;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.EVENTS;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.FIRST_NAME;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.LAST_NAME;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.PASSWORD;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.REGIONS;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.ROLE;
import static fr.istic.gm.weplan.domain.config.PersistsDefinition.USER;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"firstName", "lastName", "email", "password"})
@ToString(callSuper = true)
@Entity
@Table(name = USER)
public class User extends BaseEntity {

    @Column(name = FIRST_NAME)
    private String firstName;

    @Column(name = LAST_NAME)
    private String lastName;

    @Column(name = EMAIL, unique = true, nullable = false)
    private String email;

    @Column(name = PASSWORD)
    private String password;

    @Column(name = ROLE)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    @Column(name = CITIES)
    private List<City> cities;

    @OneToMany
    @Column(name = DEPARTMENTS)
    private List<Department> departments;

    @OneToMany
    @Column(name = REGIONS)
    private List<Region> regions;

    @OneToMany
    @Column(name = EVENTS)
    private List<Event> events;
}
