package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.Location;
import org.scd.model.dto.UserLocationDTO;
import org.scd.model.dto.UserLocationFilterDTO;
import org.scd.model.dto.UserLocationUpdateDTO;

import java.util.List;

public interface LocationService {

    Location addLocation(final UserLocationDTO userLocationDTO) throws BusinessException;
    List<Location> getLocationsById(final long id) throws BusinessException;
    Location updateLocationById(UserLocationUpdateDTO userLocationUpdateDTO, long id) throws BusinessException;
    void deleteLocationById(long id) throws BusinessException;
    List<Location> filterLocationsAsc(final UserLocationDTO userLocationDTO) throws BusinessException;
    List<Location> filterLocationsDesc(final UserLocationDTO userLocationDTO) throws BusinessException;
    List<Location> filterLocationID(final long id) throws BusinessException;
    List<Location> getLocationByDate(final long id, UserLocationFilterDTO userLocationFilterDTO) throws BusinessException;
}
