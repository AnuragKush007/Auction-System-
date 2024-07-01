import React, {useEffect, useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import axios from "axios";
import {Redirect, Route} from "react-router-dom"
import Home from "./Home";



const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

const LoginForm = (props) => {
    const classes = useStyles();

    const [owner, setIsOwner] = useState(false);
    const [name, setUserName] = useState('');
    const [isFieldEmpty, setIsFieldEmpty] = useState(false);
    const [isUserExist, setIsUserExist] = useState(false);
    const [errorMassage, setErrorMassage] = useState('');


    const signInHandler= () => {
        if (name === '') {
            setIsFieldEmpty(true);
        } else {
            const info = {
                userName: name,
                isOwner: owner
            }
            console.log(info);
            axios.post("http://localhost:8080/sdm/login", info)
            .then(response => {
                    console.log( "Respond.data.isSuccseed = " + response.data.isSucceed);
                    if(response.data.isSucceed === true){
                        props.setLoggedIn(true);
                    }else {
                        setErrorMassage("Username allready exist please select a different one...")
                    }
            })
                .catch(error => console.log(error.response)
            );
        }
    }


    return(
        <div>
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign in
                </Typography>
                <form className={classes.form} noValidate>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="useName"
                        label="User Name"
                        name="name"
                        autoComplete="name"
                        helperText={errorMassage}
                        autoFocus
                        error={isUserExist || isFieldEmpty}
                        onChange={event => setUserName(event.target.value)}
                    />
                    <FormControlLabel
                        control={<Checkbox value="isOwner" color="primary" onChange={event => {
                            setIsOwner(event.target.checked);
                            props.setIsOwner(event.target.checked);
                        }} />}
                        label="Im a Shop Owner"
                    />
                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={signInHandler}
                    >
                        Sign In
                    </Button>
                </form>
            </div>
            <Box mt={8}>
            </Box>
        </Container>
        </div>
    );
}
export default LoginForm;