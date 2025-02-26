package com.example.event.Entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Pass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idPass;

    String price;
    Date validFrom;
    Date validUntil;
    @Enumerated(EnumType.STRING)
    private PassType type;

    public long getIdPass() {
        return idPass;
    }

    public void setIdPass(long idPass) {
        this.idPass = idPass;
    }

    public  String getPrice() {
        return price;
    }

    public void setPrice( String price) {
        this.price = price;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public PassType getType() {
        return type;
    }

    public void setType(PassType type) {
        this.type = type;
    }
}
