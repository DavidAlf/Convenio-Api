package com.proyecto.convenios.repository.read;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.convenios.entity.ConvenioEntity;

public interface IConvenioReadRepository extends JpaRepository<ConvenioEntity, Integer> {

}