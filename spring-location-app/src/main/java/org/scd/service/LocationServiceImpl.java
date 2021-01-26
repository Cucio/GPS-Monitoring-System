package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.Location;
import org.scd.model.dto.UserLocationDTO;
import org.scd.model.dto.UserLocationFilterDTO;
import org.scd.model.dto.UserLocationUpdateDTO;
import org.scd.model.security.CustomUserDetails;
import org.scd.repository.LocationRepository;
import org.scd.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private static final long idAdmin = 1;

    public LocationServiceImpl(LocationRepository locationRepository, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Location addLocation(UserLocationDTO userLocationDTO) throws BusinessException {

        Location location = new Location();

        String email = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getEmail();


        if (Objects.isNull(userLocationDTO)) {
            throw new BusinessException(401, "Body NULL!");
        }
        if (Objects.isNull(userLocationDTO.getLatitude())) {
            throw new BusinessException(400, "Latitude is null");
        }
        if (Objects.isNull(userLocationDTO.getLongitude())) {
            throw new BusinessException(400, "Longitude is null");
        }

        if (!Objects.isNull(userRepository.findByEmail(email))) {
            try {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));
                Date date = new Date();
                String currentTime = formatter.format(date);
                date = formatter.parse(currentTime);

                location.setLongitude(userLocationDTO.getLongitude());
                location.setLatitude(userLocationDTO.getLatitude());
                location.setUser(userRepository.findByEmail(email));
                location.setDate(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }


        } else {
            throw new BusinessException(409, "EMAIL DONT EXIST");
        }

        return locationRepository.save(location);
    }


    @Override
    public List<Location> getLocationsById(long id) throws BusinessException {

        long userID = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();

        if (userID == id || userID == idAdmin) {

            if (!userRepository.existsById(id))
                throw new BusinessException(404, "There is no user with this ID");
            if (locationRepository.getLocationsByUserID(id).size() == 0)
                throw new BusinessException(404, "There is no locations for this user");

            return locationRepository.getLocationsByUserID(id);

        } else {
            throw new BusinessException(400, "You dont have the permission to perform this action");
        }

    }

    @Override
    public Location updateLocationById(UserLocationUpdateDTO userLocationUpdateDTO, long id) throws BusinessException {

        long userID = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();

        if (Objects.isNull(locationRepository.findById(id))) {
            throw new BusinessException(404, "Location not found");
        }

        Location location = locationRepository.findById(id);

        if (!Objects.isNull(userLocationUpdateDTO.getLatitude())) {
            location.setLatitude(userLocationUpdateDTO.getLatitude());
        }
        if (!Objects.isNull(userLocationUpdateDTO.getLongitude())) {
            location.setLongitude(userLocationUpdateDTO.getLongitude());
        }
        if (Objects.isNull(userLocationUpdateDTO.getLongitude())
                && Objects.isNull(userLocationUpdateDTO.getLatitude())) {
            throw new BusinessException(400, "Nothing to update");
        }

        if (userID == location.getUser().getId() || userID == idAdmin) {
            locationRepository.updateLocation(id, location.getLatitude(), location.getLongitude(), userID);
        } else {
            throw new BusinessException(400, "You dont have the permission to perform this action");
        }

        return locationRepository.findById(id);
    }

    @Override
    public void deleteLocationById(long id) throws BusinessException {

        long userID = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();

        if (!Objects.isNull(locationRepository.findById(id))) {
            Location location = locationRepository.findById(id);

            if (userID == location.getUser().getId() || userID == idAdmin) {
                locationRepository.deleteById(id);
            } else {
                throw new BusinessException(400, "You dont have the permission to perform this action");
            }
        }

    }

    @Override
    public List<Location> filterLocationsAsc(final UserLocationDTO userLocationDTO) throws BusinessException {
        if (!Objects.isNull(userRepository.findByEmail(userLocationDTO.getEmail()))) {
            return locationRepository.filterLocationsAsc(userRepository.findByEmail(userLocationDTO.getEmail()).getId());
        } else {
            throw new BusinessException(404, "Email not found!");
        }
    }

    @Override
    public List<Location> filterLocationsDesc(UserLocationDTO userLocationDTO) throws BusinessException {
        if (!Objects.isNull(userRepository.findByEmail(userLocationDTO.getEmail()))) {
            return locationRepository.filterLocationsDesc(userRepository.findByEmail(userLocationDTO.getEmail()).getId());
        } else {
            throw new BusinessException(404, "Email not found!");
        }
    }

    @Override
    public List<Location> filterLocationID(long id) throws BusinessException {

        if (id == idAdmin) {
            return locationRepository.getAllLocations();
        } else {
            return locationRepository.filterLocationByUserId(id);
        }
    }

    @Override
    public List<Location> getLocationByDate(long id, UserLocationFilterDTO userLocationFilterDTO) throws BusinessException {


        if (Objects.isNull(userLocationFilterDTO)) {
            throw new BusinessException(401, "Body Null!");
        }

        if (Objects.isNull(userLocationFilterDTO.getStartDate())) {
            throw new BusinessException(400, "Start date NULL!");
        }

        if (Objects.isNull(userLocationFilterDTO.getEndDate())) {
            throw new BusinessException(400, "End date NULL!");
        }

        if (userLocationFilterDTO.getStartDate().compareTo(userLocationFilterDTO.getEndDate()) > 0)
            throw new BusinessException(403, "Start date can't be greater than end date!");
        if (id == idAdmin) {
            return locationRepository.getSortedLocationAdmin(userLocationFilterDTO.getStartDate(), userLocationFilterDTO.getEndDate());
        } else {
            return locationRepository.getSortedLocation(id, userLocationFilterDTO.getStartDate(), userLocationFilterDTO.getEndDate());
        }
    }

}
