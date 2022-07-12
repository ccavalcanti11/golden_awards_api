package com.goldenawards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "movies")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "release_year")
    private Integer year;

    @Column(length = 256)
    private String title;

    @Column(length = 256)
    private String studios;

    @Column(name = "winner")
    private Boolean isWinner;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<MovieProducer> movieProducer;
}
