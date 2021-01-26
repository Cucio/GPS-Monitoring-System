
import './App.css';
import React, {Component} from 'react';
import PostForm from './components/PostForm.js';
import {BrowserRouter as Router, Route, Switch, Link, Redirect} from 'react-router-dom';
import NotFoundPage from './pages/404'
import SimpleMap from './pages/map'
import GetList from './components/GetList.js';
import Locations from './components/Locations';


class App extends Component {
  
  render() {
    return (
       <Router>
         <Switch>
        <Route exact path="/" component = {PostForm}/>
        <Route exact path="/users" exact component = {GetList}/>
        <Route exact path="/locations" component = {Locations}/>
        <Route path ="/404PageNotFound" component = {NotFoundPage}/>
        <Route path = "/GoogleMap" component = {SimpleMap} />
        <Redirect to ="/404PageNotFound"/>
        </Switch>
      </Router>
      // <div className="App">
      //   <PostForm />
      // </div>
    );
  };
}

export default App;
