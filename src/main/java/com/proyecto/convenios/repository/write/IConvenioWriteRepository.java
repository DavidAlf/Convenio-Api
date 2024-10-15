package com.proyecto.convenios.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.convenios.entity.ConvenioEntity;

public interface IConvenioWriteRepository extends JpaRepository<ConvenioEntity, Integer> {

}