package com.joycodes.dataserver.repository;

import com.joycodes.dataserver.model.Coin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends CrudRepository<Coin,String> {
}
