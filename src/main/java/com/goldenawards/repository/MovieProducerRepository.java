package com.goldenawards.repository;

import com.goldenawards.entity.MovieProducer;
import com.goldenawards.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieProducerRepository extends JpaRepository<MovieProducer, Long> {

    List<MovieProducer> findByProducerAndMovieIsWinnerOrderByMovieYearDesc(Producer producer, Boolean isWinner);

    List<MovieProducer> findAllByWinningRangeNotNullOrderByWinningRange();

    List<MovieProducer> findAllByWinningRangeNotNullOrderByWinningRangeDesc();
}
