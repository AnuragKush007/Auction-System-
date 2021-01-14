import React, {useEffect, useState} from 'react';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import MobileStepper from '@material-ui/core/MobileStepper';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';
import SwipeableViews from 'react-swipeable-views';
import { autoPlay } from 'react-swipeable-views-utils';
import Card from '@material-ui/core/Card';
import Discount from './discount'
import DiscountItem from "./DiscountItem";
import bindKeyboard from "react-swipeable-views-utils/lib/bindKeyboard";
import GridList from "@material-ui/core/GridList";
import GridListTile from "@material-ui/core/GridListTile";
import ListSubheader from "@material-ui/core/ListSubheader";
import {withMobileDialog} from "@material-ui/core";
import {keys} from "@material-ui/core/styles/createBreakpoints";

const AutoPlaySwipeableViews = bindKeyboard(SwipeableViews);


const useStyles = makeStyles((theme) => ({
    root: {
        maxWidth: 400,
        flexGrow: 1,
    },
    header: {
        display: 'flex',
        alignItems: 'center',
        height: 50,
        paddingLeft: theme.spacing(4),
        backgroundColor: theme.palette.background.default,
    },
    img: {
        height: 255,
        maxWidth: 400,
        overflow: 'hidden',
        display: 'block',
        width: '100%',
    },
}));

const DiscountList = (props) => {
    const classes = useStyles();
    const theme = useTheme();
    const [activeStep, setActiveStep] = React.useState(0);
    const maxSteps = props.discounts.length;
    const [currentDiscount, setCurrentDiscount] = useState(props.discounts[0])

    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    const setStep = () => {
        setActiveStep(0);
    }

    useEffect( () => {
        setActiveStep(0);
    },[props.discounts])

    if(props.discounts.length > 0) {
        return (
            <div className={classes.root}>
                <Paper square elevation={0} className={classes.header}>
                    {console.log(props.discounts)}
                    {console.log("ACTIVE STEP + " + activeStep)}

                    <Typography>{"From ' " + props.discounts[activeStep].storeName + " ' Store with <3"}</Typography>
                </Paper>
                <DiscountItem setStep={setStep} discount={props.discounts[activeStep]}
                              updateDiscounts={props.updateDiscounts}></DiscountItem>
                <MobileStepper
                    steps={maxSteps}
                    position="static"
                    variant="text"
                    activeStep={activeStep}
                    nextButton={
                        <Button size="small" onClick={handleNext} disabled={activeStep === maxSteps - 1}>
                            Next
                            {theme.direction === 'rtl' ? <KeyboardArrowLeft/> : <KeyboardArrowRight/>}
                        </Button>
                    }
                    backButton={
                        <Button size="small" onClick={handleBack} disabled={activeStep === 0}>
                            {theme.direction === 'rtl' ? <KeyboardArrowRight/> : <KeyboardArrowLeft/>}
                            Back
                        </Button>
                    }
                />
            </div>
        )
    }   else {
            return (
            <h1>No Discount for you you lozer!!!!</h1>
            )
        }

}


export default DiscountList;


