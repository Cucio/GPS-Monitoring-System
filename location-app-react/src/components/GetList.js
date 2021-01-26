import React, { Component } from 'react'
import axios from "axios";
import '../pages/map.css'

const API_URL = 'http://192.168.0.248:8080/users'

class GetList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            users: []
        }
    }

    componentDidMount() {
        axios.get(API_URL)
            .then(response => {
                console.log(response)
                this.setState({ users: response.data })
            })
            .catch(error => {
                console.log(error)
            })
        
    }


    render() {
        const { users } = this.state
        return (
            <div>
                {
                    users.length ? users.map(user => <div className="dropdown-list_item" key={user.id} > {user.id+ '.' +user.firstName + ' ' + user.lastName}  </div>) : null
                }
            </div>
        )
    }
}

export default GetList;