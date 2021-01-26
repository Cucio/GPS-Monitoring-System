import React, { Component } from 'react'
import axios from "axios";
import Marker from '../Marker/Marker'
import '../pages/map.css'
import GoogleMapReact from 'google-map-react';
import { Popup } from 'leaflet';
import { ClearCacheProvider, useClearCacheCtx } from 'react-clear-cache';
import GetList from './GetList'


const API_URL = 'http://localhost:8080/locations/filterID'


const API_KEY = 'AIzaSyCTnUMKeIrtZlV8YFHzfsG2XRRnd6HOJl8'


class Locations extends Component {



    constructor(props) {
        super(props);

        this.state = {
            locations: [],
            startDate: "",
            endDate: ""
        }
    }
    componentDidMount() {

        axios.get(API_URL + "/" + sessionStorage.getItem("id"), {

        }, {
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(atob(localStorage.getItem("username")) + ':' + atob(localStorage.getItem("password")))
            })
        })
            .then(response => {
                console.log(response)
                this.setState({ locations: response.data })
            })
            .catch(error => {
                console.log(error)
            })

    }

    componentWillUnmount() {

    }

    moveRef(){
        window.location.href="/filteredLocations"
    }
    logout() {

        window.location.href = "/"
        localStorage.removeItem("username")
        localStorage.removeItem("password")
        sessionStorage.removeItem("id")
        sessionStorage.removeItem("Name")
    }
    markMap(latitude, longitude) {


        <Marker
            lat={latitude}
            lng={longitude}
            name="My Marker"
            color="red"
        />
        //   console.log("YES")
    }


    changeHandler = (e) => {
        this.setState({ [e.target.name]: e.target.value });
      };

      submitHandler = (e) => {
        e.preventDefault();
    
        console.log(this.state);
        axios.post("http://192.168.0.248:8080/locations/sorted_locations"+ "/" + sessionStorage.getItem("id"),this.state, {

        }, {
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(atob(localStorage.getItem("username")) + ':' + atob(localStorage.getItem("password")))
                
            })
        })
            .then(response => {
                console.log(response)
                this.setState({ locations: response.data })
            })
            .catch(error => {
                console.log(error)
            })
      }

      nothing(){

      }


      requestLocations(){
        console.log(this.state);
        axios.post("http://192.168.0.248:8080/locations/sorted_locations"+ "/" + sessionStorage.getItem("idUSER"),this.state, {

        }, {
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(atob(localStorage.getItem("username")) + ':' + atob(localStorage.getItem("password")))
                
            })
        })
            .then(response => {
                console.log(response)
                this.setState({ locations: response.data })
            })
            .catch(error => {
                console.log(error)
            })
      }

    render() {
        
        const { startDate, endDate, locations } = this.state;

        return (
            <div>
                <div className="spliter left" >

                    <div className="fadeIn">
                        
                        <div className="dropdown ">
                            <div className="dropdown-select">
                                <span className="select">{"Hello " + sessionStorage.getItem("Name").toLocaleUpperCase() }</span>
                                <i className="fa fa-caret-down icon"></i>
                            </div>
                        </div>
                        <div className="dropdown-list">
                            <GetList />
                        </div>
                    </div>



                    <input
                        type="text"
                        className="fadeIn second"
                        name="startDate"
                        value={startDate}
                        onChange = {this.changeHandler}
                        placeholder="Start Date (YYYY-MM-DD)" />

                    {
                        sessionStorage.setItem("startDate", startDate)
                    }

                    <input
                        type="text"
                        className="fadeIn second"
                        name="endDate"
                        value={endDate}
                        onChange = {this.changeHandler}
                        placeholder="End Date (YYYY-MM-DD)" />
                        
                    {
                        sessionStorage.setItem("endDate", endDate)
                    }
                    <input
                        type="text"
                        className="fadeIn search"
                        value="SEARCH"
                        onChange={this.nothing}
                        onClick={this.submitHandler}
                        readOnly="readOnly"
                    />

                    <input
                        type="text"
                        className="fadeIn logout"
                        value="LOG OUT"
                        onChange={this.nothing}
                        onClick={this.logout}
                        readOnly="readOnly"
                    />

                </div>
                <div className="split right">
                    <GoogleMapReact
                        bootstrapURLKeys={{ key: API_KEY }}
                        defaultZoom={12}
                        defaultCenter={{ lat: 45, lng: 25 }}
                    >

                        {
                            locations.length ? locations.map(location => <Marker
                                key={location.id}
                                lat={location.latitude}
                                lng={location.longitude}
                                name={"Latitude:" + parseFloat(location.latitude).toFixed(2) + " Longitude:" + parseFloat(location.longitude).toFixed(2)}
                                color="red"
                            > <Popup> {location.latitude} + ":" + {location.long} </Popup> </Marker>) : null

                        }

                    </GoogleMapReact>
                </div>
            </div>




        )
    }

}

export default Locations;