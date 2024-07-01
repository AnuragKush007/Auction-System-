import React from 'react';
import Home from "./pages/homePage/Home";
import LoginForm from "./pages/homePage/LoginForm";
import {BrowserRouter, Route} from "react-router-dom";
import LogInWrapper from "./pages/homePage/LoginWrapper";



function App() {
  return (
      <BrowserRouter>
        <div>
          <LogInWrapper/>
        </div>
      </BrowserRouter>
  );
}

export default App;
