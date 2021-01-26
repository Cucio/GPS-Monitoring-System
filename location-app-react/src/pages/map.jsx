import '../App.css';
import React, { useState } from 'react';
import GoogleMapReact from 'google-map-react';
import Marker from '../Marker/Marker'
import './map.css'
import GetList from '../components/GetList.js'
import Locations from '../components/Locations.js'
import { Circle } from 'leaflet';

const API_KEY = 'AIzaSyCTnUMKeIrtZlV8YFHzfsG2XRRnd6HOJl8'



const SimpleMap = (props) => {

  const [center, setCenter] = useState({ lat: 46.75, lng: 23.48 });
  const [zoom, setZoom] = useState(11);
 


  return (
    <div>
      <div className="spliter left" >

      
      <div className="dropdown">
        <div className="dropdown-select">
          <span className="select">Selected User</span>
          <i className="fa fa-caret-down icon"></i>
        </div>
        <div className="dropdown-list">
          <GetList />
        </div>
    </div>
     

        <input
          type="text"
          name="startDate"
          placeholder="Start Date" />

        <input
          type="text"
          name="endDate"
          placeholder="End Date" />


      </div>
      <div className="split right">
        <GoogleMapReact
          bootstrapURLKeys={{ key: API_KEY }}
          defaultZoom={12}
          defaultCenter={{ lat: -34.397, lng: 150.644 }}
        >
          
          {/* <Marker
            lat={center.lat}
            lng={center.lng}
            name="My Marker"
            color="blue"
          />
          <Marker
            lat={46.85}
            lng={23.5}
            name="My Marker"
            color="red"/>
            */}
          <Locations />
        </GoogleMapReact>
      </div>
    </div>
  );


}


export default SimpleMap;