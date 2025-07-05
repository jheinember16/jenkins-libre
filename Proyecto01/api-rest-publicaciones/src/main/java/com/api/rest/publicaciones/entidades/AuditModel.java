package com.api.rest.publicaciones.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"fechaCreacion","fechaActualizacion"},
        allowGetters = true
)
public abstract class AuditModel implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false,updatable = false)
    @CreatedDate
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion", nullable = false)
    @LastModifiedDate
    private Date fecha_actualizacion;

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Date fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
}
