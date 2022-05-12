package com.netmillennium.gagrid.app.movie.service;

import java.util.ArrayList;
import java.util.List;

import com.netmillennium.gagrid.app.movie.model.Movie;
import com.netmillennium.gagrid.model.Gene;
import com.netmillennium.gagrid.parameter.IFitnessFunction;



/**
 * This example demonstrates how to create a IFitnessFunction
 *
 * Your IFitness function will vary depending on your particular use case.
 * 
 * For this fitness function, we simply want to calculate the value of
 * 
 * an individual solution relative to other solutions.
 * 
 * 
 * To do this, we simply increase fitness score by number of times
 * 
 * genre is found in list of movies.
 * 
 * In addition, we increase score by fictional IMDB rating.
 * 
 * If there are duplicate movies in selection, we automatically apply a '0'
 * 
 * fitness score.
 * 
 * @author turik.campbell
 * 
 */

public class MovieFitnessFunction implements IFitnessFunction {

    private List<String> generes = null;

    /**
     * 
     * @param List<String>  generes
     */
    public MovieFitnessFunction(List<String> generes) {
        this.generes = generes;
    }

    @Override
    public double evaluate(List<Gene> genes) {

        double score = 0;
        List<String> dups = new ArrayList();
        int badSolution = 1;

        for (int i = 0; i < genes.size(); i++) {
            Movie movie = (Movie) genes.get(i).getVal();
            if (dups.contains(movie.getTitle())) {
                badSolution = 0;
            } else {
                dups.add(movie.getTitle());
            }
            double genreScore = getGenreScore(movie);
            if (genreScore == 0) {
                badSolution = 0;
            }
           // score = (score + movie.getImdbRating()) + (genreScore);
            score = (score) + (genreScore);
        }
        return (score * badSolution);
    }

    /**
     * helper to calculate genre score
     * 
     * @param movie
     * @return
     */
    private double getGenreScore(Movie movie) {
        double genreScore = 0;

        for (String genre :movie.getGenre()) {
        	
            if (generes.contains(genre)) {
                genreScore = genreScore + 1;
            }
        }
        return genreScore;
    }
}
