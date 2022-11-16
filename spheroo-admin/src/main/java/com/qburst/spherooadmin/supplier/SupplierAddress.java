package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
@ToString
@AttributeOverrides({@AttributeOverride(name = "country",
                    column = @Column(name = "country",nullable = false)),
                    @AttributeOverride(name = "town",
                    column = @Column(name = "town",nullable = false)),
                    @AttributeOverride(name = "district",
                    column = @Column(name = "district",nullable = false)),
                    @AttributeOverride(name = "pinCode",
                    column = @Column(name = "pinCode",nullable = false)),
                    @AttributeOverride(name = "buildNo",
                    column =@Column(name = "buildNo",nullable = false))})
public class SupplierAddress {
    private String country;
    private String town;
    private String district;
    private int pinCode;
    private long buildNo;
}
