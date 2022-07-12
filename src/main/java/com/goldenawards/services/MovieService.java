package com.goldenawards.services;

import com.goldenawards.entity.Movie;
import com.goldenawards.entity.MovieProducer;
import com.goldenawards.entity.Producer;
import com.goldenawards.repository.MovieProducerRepository;
import com.goldenawards.repository.MovieRepository;
import com.goldenawards.repository.ProducerRepository;
import com.google.common.base.Splitter;
import com.univocity.parsers.common.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

    private static final String YES = "yes";

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MovieProducerRepository movieProducerRepository;

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

            String producers = this.adjustCommas(record.getString("producers"));

            this.saveMovieProducer(movie, producers);
        });
    }

    private void saveMovieProducer(Movie movie, String producers) {
        Splitter.on(",").trimResults().split(producers).forEach(producer -> {
            LOGGER.info("Producer: " + producer);

            Optional<Producer> optProducer = producerRepository.findByNameIgnoreCase(producer);
            MovieProducer movieProducer;

            if (optProducer.isPresent()) {
                movieProducer = MovieProducer.builder().movie(movie).producer(optProducer.get()).build();
            } else {
                Producer newProducer = Producer.builder().name(producer).build();
                producerRepository.save(newProducer);
                movieProducer = MovieProducer.builder().movie(movie).producer(newProducer).build();
            }

            movieProducerRepository.save(movieProducer);
        });
    }

    private String adjustCommas(String producers) {
        LOGGER.info("Producers: " + producers);
        producers = producers.replace(",and ", ", ");
        producers = producers.replace(", and ", ", ");
        producers = producers.replace(" and ", ", ");
        LOGGER.info("Producers: " + producers);
        return producers;
    }
}
