package com.oess.model.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="teacher_id")
public class Teacher extends User {

}
