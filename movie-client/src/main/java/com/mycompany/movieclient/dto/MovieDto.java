package com.mycompany.movieclient.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieDto {

    private String imdb;
    private String title;
    private LocalDateTime lastModifiedDate;

}