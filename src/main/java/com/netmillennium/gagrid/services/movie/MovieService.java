package com.netmillennium.gagrid.app.movie.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.netmillennium.gagrid.app.movie.model.Movie;

@Service("movieService")
public class MovieService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Movie> getAllMovies() {
		return jdbcTemplate.query("SELECT * FROM movie", new RowMapper<Movie>() {

			public Movie mapRow(ResultSet rs, int arg1) throws SQLException {
				Movie c = new Movie();
			    c.setTitle(rs.getString("title"));
			    c.setGenre(createGenres(rs.getString("genre")));
			    c.setYearReleased(rs.getInt("year_released"));
				return c;
			}

		});
	}
	
	/**
	 * 
	 * @param genres
	 * @return
	 */
	private List<String> createGenres(String genres)
	{
		String[] genresArray = genres.split(":");
		return Arrays.asList(genresArray);
	}
}
