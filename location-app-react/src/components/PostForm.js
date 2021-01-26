import React, { Component } from "react";
import "./Post.css";
import axios from "axios";
const API_URL = "http://192.168.0.248:8080/users/login";

class PostForm extends Component {
  constructor(props) {
    super(props);
    localStorage.setItem("username", "")
    localStorage.setItem("password", "")
    this.state = {
      email: "",
      password: "",
      user: null
    };
  }

  changeHandler = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  submitHandler = (e) => {
    e.preventDefault();

    console.log(this.state);
    axios
      .post(API_URL, this.state, true)
      .then((response) => {
        console.log(response);
        sessionStorage.setItem("Name", response.data.firstName + " " + response.data.lastName);
        sessionStorage.setItem('id',response.data.id)
        window.location = 'http://@localhost:3000/locations'  // + btoa(atob(localStorage.getItem("username")) + ':' + atob(localStorage.getItem("password"))) 
      })
      .catch((error) => {
        console.log(error);
      });

  };



  render() {
    const { email, password } = this.state;
    return (
      <div className="wrapper fadeInDown">
        <div id="formContent">
          <h2 className="active"> Sign In </h2>
          <h2 className="inactive underlineHover">Sign Up </h2>

          <form  >
            <div>
              <input
                className="fadeIn second"
                type="text"
                name="email"
                value={email}
                onChange={this.changeHandler}
                placeholder="Email"
              />
              {
                localStorage.setItem('username', btoa(email))
                
              }
            </div>
            <div>
              
              <input
                type="text"
                className="fadeIn third"
                name="password"
                placeholder="Password"
                value={password}
                onChange={this.changeHandler}

              />
              {
                localStorage.setItem('password', btoa(password))
              }
            </div>
            <input
              type="submit"
              className="fadeIn fourth"
              value="Log In"
              readOnly = "readOnlu"
              onClick={this.submitHandler}
            />

          </form>

        </div>
      </div>
    );
  }
}

export default PostForm;
