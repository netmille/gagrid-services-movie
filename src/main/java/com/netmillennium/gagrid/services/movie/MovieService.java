package com.netmillennium.gagrid.services.movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service("movieService")
public class MovieService {

	@Autowired
	@Qualifier("pgJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public List<Movie> getAllMovies(List<String> genres) {
		
		StringBuffer sbClause = new StringBuffer();
		int j=0;
		sbClause.append("(");
		
		for (String genre: genres)
		{
			if (j>0) sbClause.append(",");
			sbClause.append("'");
			sbClause.append(genre);
			sbClause.append("'");
			j=j+1;
		}
		
		sbClause.append(")");
		
		return jdbcTemplate.query("SELECT * FROM movie where genre in " + sbClause.toString(), new RowMapper<Movie>() {

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
