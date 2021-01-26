package org.scd.controller;

import org.scd.config.exception.BusinessException;
import org.scd.model.Location;
import org.scd.model.dto.UserLocationDTO;
import org.scd.model.dto.UserLocationFilterDTO;
import org.scd.model.dto.UserLocationUpdateDTO;
import org.scd.service.LocationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Location> addLocation(@RequestBody final UserLocationDTO location) throws BusinessException {
        return ResponseEntity.ok(locationService.addLocation(location));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<Location>> getPosition(@PathVariable("id") final long id) throws BusinessException {
        return ResponseEntity.ok(locationService.getLocationsById(id));
    }

    @PatchMapping(path = "/update/{id}")
    public ResponseEntity<Location> updatePosition(@RequestBody final UserLocationUpdateDTO userLocationUpdateDTO, @PathVariable("id") final long id) throws BusinessException {
        return ResponseEntity.ok(locationService.updateLocationById(userLocationUpdateDTO, id));
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteLocation(@PathVariable("id") final long id) throws BusinessException {
        locationService.deleteLocationById(id);
        return "Location with id:" + id + " deleted";
    }

    @GetMapping("/filterASC")
    public ResponseEntity<List<Location>> filterLocationsAsc(@RequestBody final UserLocationDTO userLocationDTO) throws BusinessException {
        return ResponseEntity.ok(locationService.filterLocationsAsc(userLocationDTO));
    }

    @GetMapping("/filterDESC")
    public ResponseEntity<List<Location>> filterLocationsDesc(@RequestBody final UserLocationDTO userLocationDTO) throws BusinessException {
        return ResponseEntity.ok(locationService.filterLocationsDesc(userLocationDTO));
    }

    @GetMapping("/filterID/{id}")
    public ResponseEntity<List<Location>> filterLocationsID(@PathVariable("id") final long id) throws BusinessException {
        return ResponseEntity.ok(locationService.filterLocationID(id));
    }

    @PostMapping("/sorted_locations/{id}")
    public ResponseEntity<List<Location>> getLocationByIdAndDate(@PathVariable("id") final long id,@RequestBody final UserLocationFilterDTO userLocationFilterDTO) throws BusinessException {
        return ResponseEntity.ok(locationService.getLocationByDate(id, userLocationFilterDTO));
    }

}
