import React, {useState , useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import AndroidIcon from '@material-ui/icons/Android';
import green from "@material-ui/core/colors/green";
import Container from "@material-ui/core/Container";
import {Grid} from "@material-ui/core";
import ZoneCard from "./ZoneCard";


const useStyles = makeStyles((theme) => ({
    root: {
        width: '150%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
    avatar: {
        backgroundColor: green[500],
        width: theme.spacing(3),
        height: theme.spacing(3)
    },
}));



    const OnlineUsers= (props) => {
        const classes = useStyles();
        const [users, setUsers] = useState([props.onlineUsers]);



    useEffect( () => {
        console.log(props.onlineUsers);
        setUsers(props.onlineUsers.map( (user) => {
           user.key= user.userName;
           console.log("name: " + user.userName + " role: " + user.userRole)
           return (
               <div>
                   <ListItem>
                       <ListItemAvatar>
                           <Avatar className={classes.avatar}>
                               <AndroidIcon/>
                           </Avatar>
                       </ListItemAvatar>
                       <ListItemText primary={user.userName} secondary={user.userRole}/>
                   </ListItem>
               </div>)
       }))},[props.onlineUsers]);

    return (
        <Container border={2} display="flex" margin={1}>
                {users}
        </Container >
    );
}

export default OnlineUsers;