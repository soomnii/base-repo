package com.example.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SAMPLE")
public class Sample {
    @Id
    @Column(name = "s_id")
    String id;

    @Column(name = "s_name")
    String name;
}
