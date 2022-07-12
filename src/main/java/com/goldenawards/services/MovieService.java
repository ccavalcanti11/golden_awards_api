package com.goldenawards.services;

import com.goldenawards.entity.Movie;
import com.univocity.parsers.common.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    public void getMoviesFromRecords(List<Record> records) {
        records.forEach(record -> {
            LOGGER.info("RECORD: " + record);

            Movie movie = Movie
                    .builder()
                    .year(record.getInt("year"))
                    .title(record.getString("title"))
                    .studios(record.getString("studios"))
                    .isWinner(YES.equalsIgnoreCase(record.getString("winner")) ? Boolean.TRUE : Boolean.FALSE)
                    .build();
            movieRepository.save(movie);

            String producers = this.replaceCommas(record.getString("producers"));

            this.getMovieProducersFromProducers(movie, producers);
        });
    }
}
