import React from "react";
import { AppBar, Toolbar, Typography } from "@material-ui/core";
import LocalGroceryStoreIcon from '@material-ui/icons/LocalGroceryStore';
import { makeStyles } from "@material-ui/styles";
import SimpleMenu from "./SimpleMenu";

const useStyles = makeStyles(() => ({
    typographyStyles: {
        flex: 1
    }
}));

const Header = (props) => {
    const classes = useStyles();
    return (
        <AppBar position="static">
            <Toolbar>
                <SimpleMenu  isOwner={props.isOwner} setZones={props.setZones}/>
                <Typography align="center" className={classes.typographyStyles}>
                    Super Duper Market
                </Typography>
                <LocalGroceryStoreIcon />
            </Toolbar>
        </AppBar>
    );
};

export default Header;