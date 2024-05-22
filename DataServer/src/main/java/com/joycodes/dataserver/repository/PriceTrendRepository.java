package com.joycodes.dataserver.repository;

import com.joycodes.dataserver.model.PriceTrend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceTrendRepository extends CrudRepository<PriceTrend, String> {

}

