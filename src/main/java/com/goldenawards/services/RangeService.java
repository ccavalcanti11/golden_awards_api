package com.goldenawards.services;

import com.goldenawards.data.dto.MovieDetailsDTO;
import com.goldenawards.data.dto.RangeDTO;
import com.goldenawards.entity.Movie;
import com.goldenawards.entity.MovieProducer;
import com.goldenawards.repository.MovieProducerRepository;
import com.goldenawards.repository.ProducerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

@Service
public class RangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RangeService.class);

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MovieProducerRepository movieProducerRepository;

    public RangeDTO getMinMaxRange() {
        this.calculateWinRange();
        RangeDTO rangeDTO = new RangeDTO();
        List<MovieDetailsDTO> min = new ArrayList<>();
        List<MovieDetailsDTO> max = new ArrayList<>();


        this.saveMinRange(min);
        this.saveMaxRange(max);

        rangeDTO.setMin(min);
        rangeDTO.setMax(max);
        return rangeDTO;
    }

    private void saveMinRange(List<MovieDetailsDTO> min) {
        List<MovieProducer> allMinRange = movieProducerRepository.findAllByWinningRangeNotNullOrderByWinningRange();
        ListIterator<MovieProducer> minRangeIterator = allMinRange.listIterator();

        MovieProducer previous = null;

        while (minRangeIterator.hasNext()) {
            MovieProducer movieProducer = minRangeIterator.next();
            if (Objects.nonNull(previous) && previous.getWinningRange() < movieProducer.getWinningRange()) {
                break;
            }
            MovieDetailsDTO movieDetailsDTO = MovieDetailsDTO
                    .builder()
                    .producer(movieProducer.getProducer().getName())
                    .interval(movieProducer.getWinningRange())
                    .previousWin(movieProducer.getLastWinYear())
                    .followingWin(movieProducer.getMovie().getYear())
                    .build();
            min.add(movieDetailsDTO);
            previous = movieProducer;
        }
    }

    private void saveMaxRange(List<MovieDetailsDTO> max) {
        List<MovieProducer> allMaxRange = movieProducerRepository.findAllByWinningRangeNotNullOrderByWinningRangeDesc();

        ListIterator<MovieProducer> maxRangeIterator = allMaxRange.listIterator();
        MovieProducer previous = null;

        while (maxRangeIterator.hasNext()) {
            MovieProducer movieProducer = maxRangeIterator.next();
            if (Objects.nonNull(previous) && previous.getWinningRange() > movieProducer.getWinningRange()) {
                break;
            }
            MovieDetailsDTO movieDetailsDTO = MovieDetailsDTO
                    .builder()
                    .producer(movieProducer.getProducer().getName())
                    .interval(movieProducer.getWinningRange())
                    .previousWin(movieProducer.getLastWinYear())
                    .followingWin(movieProducer.getMovie().getYear())
                    .build();
            max.add(movieDetailsDTO);
            previous = movieProducer;
        }
    }

    private void calculateWinRange() {
        LOGGER.info("calculate win range");

        producerRepository.findAll().forEach(producer -> {
            this.saveMovieProducerRepository(movieProducerRepository.findByProducerAndMovieIsWinnerOrderByMovieYearDesc(producer, Boolean.TRUE));
        });

    }

    private void saveMovieProducerRepository(List<MovieProducer> movieProducers) {
        if (movieProducers.size() > 1) {
            ListIterator<MovieProducer> listIterator = movieProducers.listIterator();
            MovieProducer lastMovieProducer = listIterator.next();
            while (listIterator.hasNext()) {
                Integer lastYear = lastMovieProducer.getMovie().getYear();
                MovieProducer nextMovieProducer = listIterator.next();
                Integer nextYear = nextMovieProducer.getMovie().getYear();

                lastMovieProducer.setWinningRange(lastYear - nextYear);
                lastMovieProducer.setLastWinYear(nextYear);
                movieProducerRepository.save(lastMovieProducer);

                lastMovieProducer = nextMovieProducer;
            }
        }
    }
}
