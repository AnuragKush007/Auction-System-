import React, {Component, useEffect, useState} from "react";
import LoginForm from "./LoginForm";
import Home from "./Home";
import MainPage from "./MainPage";

const LogInWrapper = (props) => {
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [isOwner, setIsOwner] = useState(false);

    const setLoggedInWrapper = (data) => {
        console.log("IN setLoggedInWrapper: " + data);
        setLoggedIn(data);
    }

    const setIsOwnerWrapper = (data) => {
        console.log("IN LOGINWRAPPER: is owner = " + data);
        setIsOwner(data);
    }

    return (
            isLoggedIn ? <MainPage isOwner={isOwner}/> : <LoginForm setLoggedIn={setLoggedInWrapper} setIsOwner={setIsOwnerWrapper}/>
    );
}

export default LogInWrapper;