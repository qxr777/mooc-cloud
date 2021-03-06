package edu.whut.cs.jee.mooc.mclass.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("Teacher")
public class Teacher extends User {

    @Column(name = "title")
    private String title;

    @Column(name = "salary_no")
    private String salaryNo;

}