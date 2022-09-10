package com.netmillennium.gagrid.services.movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.netmillennium.gagrid.services.movie.Movie;

@Service("movieService")
public class MovieService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Movie> getAllMovies(String clause) {
		return jdbcTemplate.query("SELECT * FROM movie where genre in " + clause, new RowMapper<Movie>() {

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
