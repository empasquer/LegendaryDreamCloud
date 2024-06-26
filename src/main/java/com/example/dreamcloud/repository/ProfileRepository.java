package com.example.dreamcloud.repository;

import com.example.dreamcloud.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createProfile( String profileUsername, String profileFirstName, String profileLastName, String profilePassword, Optional<byte[]> profilePicture) {
        String query = "INSERT INTO profile(profile_username, profile_firstname, profile_lastname, profile_password, profile_picture) VALUES (?,?,?,?,?)";

        //Convert the profilePicture to a byte array if exists
        byte[] pictureData = profilePicture.orElse(null);

        jdbcTemplate.update(query, profileUsername, profileFirstName, profileLastName, profilePassword, pictureData);
    }

    public Profile getProfileFromUsername(String profileUsername) {
        String query = "SELECT * FROM profile WHERE profile_username = ?";
        RowMapper<Profile> rowMapper = new BeanPropertyRowMapper<>(Profile.class);
        try {
            return jdbcTemplate.queryForObject(query, rowMapper, profileUsername);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteProfile(String profileUsername) {
        String query = "DELETE FROM profile WHERE profile_username = ?";
        jdbcTemplate.update(query,profileUsername);
    }

    public void editProfile(String profileUsername, String profileFirstName, String profileLastName, String profilePassword, Optional<byte[]> profilePicture) {
        String query = "UPDATE profile SET profile_firstname=?, profile_lastname=?, profile_password=?, profile_picture=? WHERE profile_username=?";

        //Convert the profilePicture to a byte array if exists
        byte[] pictureData = profilePicture.orElse(null);
        jdbcTemplate.update(query, profileFirstName, profileLastName, profilePassword, pictureData, profileUsername);
    }

}
