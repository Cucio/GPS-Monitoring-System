package org.scd.repository;

import org.scd.model.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

    @Modifying
    @Query(value = "Insert into user_location(date,latitude,longitude) u" + "VALUES(?,?)" +"where u.user_id= ?", nativeQuery = true)
    @Transactional
    void addLocation(@Param("user_id") long id,@Param("date") Date date,@Param("latitude") String latitude, @Param("longitude") String longitude);

    @Modifying
    @Query(value = "Update user_location u set latitude=:latitude,longitude=:longitude where u.id = :id AND u.user_id = :user_id",nativeQuery = true)
    @Transactional
    void updateLocation(@Param("id") long id,@Param("latitude") String latitude,@Param("longitude") String longitude,@Param("user_id") final long user_id);

    @Modifying
    @Query(value = "Delete from user_location u where u.id=?1", nativeQuery = true)
    @Transactional
    void deleteLocation(long id);

    @Modifying
    @Query(value = "Select * from user_location u where u.user_id = ?1 order by u.date ASC", nativeQuery = true)
    @Transactional
    List<Location> filterLocationsAsc(final long id);

    @Modifying
    @Query(value = "Select * from user_location u where u.user_id = ?1 order by u.date DESC", nativeQuery = true)
    @Transactional
    List<Location> filterLocationsDesc(final long id);

    @Modifying
    @Query(value = "Select * from user_location u where u.user_id=:user_id order by u.id ASC", nativeQuery = true)
    @Transactional
    List<Location> filterLocationByUserId(@Param("user_id") long user_id);

    @Modifying
    @Query(value = "Select * from user_location u order by u.id ASC", nativeQuery = true)
    @Transactional
    List<Location> getAllLocations();

    @Modifying
    @Query(value = "Select * from user_location u where u.user_id=?1", nativeQuery = true)
    @Transactional
    List<Location> getLocationsByUserID(@Param("id") long id);

    @Modifying
    @Query(value="SELECT * FROM user_location WHERE user_id=?1 AND date>=?2 AND date<=?3",nativeQuery = true)
    @Transactional
    List<Location> getSortedLocation(final long id, Date startDate, Date endDate);


    @Modifying
    @Query(value="SELECT * FROM user_location WHERE date>=?1 AND date<=?2",nativeQuery = true)
    @Transactional
    List<Location> getSortedLocationAdmin(Date startDate, Date endDate);

    Location deleteById(final long id);

    Location findById(final long id);


}
