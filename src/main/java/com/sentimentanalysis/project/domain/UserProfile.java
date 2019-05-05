package com.sentimentanalysis.project.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sentimentanalysis.project.domain.enumeration.Sexoption;

import com.sentimentanalysis.project.domain.enumeration.Countrylist;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sexoption sex;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "jhi_organization")
    private String organization;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Countrylist country;

    @OneToMany(mappedBy = "usersearch")
    private Set<Search> usersearches = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfile firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfile lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public UserProfile userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public UserProfile age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public UserProfile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexoption getSex() {
        return sex;
    }

    public UserProfile sex(Sexoption sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sexoption sex) {
        this.sex = sex;
    }

    public Integer getPhone() {
        return phone;
    }

    public UserProfile phone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getOrganization() {
        return organization;
    }

    public UserProfile organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Countrylist getCountry() {
        return country;
    }

    public UserProfile country(Countrylist country) {
        this.country = country;
        return this;
    }

    public void setCountry(Countrylist country) {
        this.country = country;
    }

    public Set<Search> getUsersearches() {
        return usersearches;
    }

    public UserProfile usersearches(Set<Search> searches) {
        this.usersearches = searches;
        return this;
    }

    public UserProfile addUsersearch(Search search) {
        this.usersearches.add(search);
        search.setUsersearch(this);
        return this;
    }

    public UserProfile removeUsersearch(Search search) {
        this.usersearches.remove(search);
        search.setUsersearch(null);
        return this;
    }

    public void setUsersearches(Set<Search> searches) {
        this.usersearches = searches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProfile userProfile = (UserProfile) o;
        if (userProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", age=" + getAge() +
            ", email='" + getEmail() + "'" +
            ", sex='" + getSex() + "'" +
            ", phone=" + getPhone() +
            ", organization='" + getOrganization() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
