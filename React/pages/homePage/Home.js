import React, {useEffect, useState} from "react";
import {Grid} from "@material-ui/core";
import Header from "./Header";
import Content from "./Content";
import FileUploader from "./FileUploader";
import Box from "@material-ui/core/Box";
import ChooseFileModal from "./ChooseFileModal";
import OnlineUsers from "./OnlineUsers";
import axios from "axios";
import Paper from "@material-ui/core/Paper";
import Alert from "@material-ui/lab/Alert";
import AlertTitle from "@material-ui/lab/AlertTitle";
import ErrorAlert from "../ErrorAlert";
import Switch from "@material-ui/core/Switch";
import {Route, withRouter} from "react-router";
import CustomerHeader from "../cutomerPage/CustomerHeader";


const Home = (props) => {

    const [onlineUsers, setOnlineUsers] = useState([]) // TODO - send a get request every 2 seconds to get the users
    const [zones, setZones] = useState([])


    useEffect( () => {
        const interval = setInterval(() =>{
            axios.get("http://localhost:8080/sdm/getZones")
                .then(response => {
                        if(response.data.length !== zones.length){
                            setZones(response.data)
                        }
                }
                  )
                .catch(error => console.log(error));
            console.log("SUCCESS -set zones");
            console.log(zones);
        },2000);
            return () => clearInterval(interval);
        },[] );

    useEffect(() => {
        const interval = setInterval(() => {
            axios.get("http://localhost:8080/sdm/getOnlineUsers")
                .then(response => setOnlineUsers(response.data))
                .catch(error => console.log(error));
        },2000);
         return () => clearInterval(interval);
        }, []);

    const setZonesWrapper = (data) => {
        console.log(data)
        setZones(data);
    }

    const zoneSelectedHandler = (zoneName) => {
        console.log("OK");
        props.setSelectedZone(zoneName);
        if (props.isOwner) {
            props.history.push('/ownerPage')
        } else {
            props.history.push('/customerPage')
        }
    }

    return (
        <div>
            {console.log("inside Home Page")}
            <Grid  spacing={2} container direction="column" alignItems="stretch">
                <Grid item>
                    <Header isOwner={props.isOwner} setZones={setZonesWrapper}/>
                </Grid>
                <Grid item container>
                    <Grid item xs={2} alignItems="stretch" >
                    </Grid>
                    <Grid item xs={8} alignItems="stretch">
                        <Content zoneSelectedHandler={zoneSelectedHandler} isOwner={props.isOwner} zones={zones} />
                    </Grid>
                    <Grid  border={2} item xs={2} alignItems="stretch" spacing={2} >
                       <Box>
                            <h2>Online Users</h2>
                            <OnlineUsers onlineUsers={onlineUsers}/>
                       </Box>
                    </Grid>
                </Grid>
            </Grid>
        </div>
    );
};

export default withRouter(Home);