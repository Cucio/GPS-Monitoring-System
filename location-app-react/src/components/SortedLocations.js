import React, { Component } from "react";
import "./Post.css";
import axios from "axios";

const API_URL = "http://192.168.0.248:8080/locations/sorted_locations";

class PostForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      startDate: "",
      endDate: "",
    };
  }

  changeHandler = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  submitHandler = (e) => {
    e.preventDefault();
    console.log(this.state);
    axios
      .post(API_URL, this.state)
      .then((response) => {
        console.log(response);
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

          <form>
            <div>
              <input
                className="fadeIn second"
                type="text"
                name="email"
                value={email}
                onChange={this.changeHandler}
                placeholder="Email"
              />
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
            </div>
            <input
              type="submit"
              className="fadeIn fourth"
              value="Log In"
              onClick={this.submitHandler}
            />
          </form>
          
        </div>
      </div>
    );
  }
}

export default PostForm;
