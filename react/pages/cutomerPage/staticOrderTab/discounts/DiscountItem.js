import React, {useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Button from "@material-ui/core/Button";
import CheckBox from "@material-ui/core/Checkbox";
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Radio from '@material-ui/core/Radio';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormLabel from '@material-ui/core/FormLabel';


import Typography from "@material-ui/core/Typography";
import FormControl from "@material-ui/core/FormControl";

const useStyles = makeStyles({
    root: {
        maxWidth: 345
    }
});




const DiscountItem = (props) => {

    const classes = useStyles();

    const [value, setValue] = React.useState(props.discount.offers[0].thenYouGetId);
    const [selectedThenYouGetItem, setSelectedThenYouGetItem] = useState(props.discount.offers[0].thenYouGetId)

    const handleRadioChange = (event) => {
        console.log("SELECTED THEN U GET ID " + event.target.value);
        setValue(event.target.value);
        setSelectedThenYouGetItem(event.target.value)
    };


    const setOffers = () => {
        console.log(props.discount);
        if(props.discount.operator === "all-or-nothing") { //TODO - add the full all-or-nothing Description
            return (
                <RadioGroup aria-label="quiz" name="quiz" value={value} onChange={handleRadioChange}>
                    <FormControlLabel value={props.discount.offers[0]}
                                      control={<Radio />}
                                      label={
                                                "Get " + props.discount.offers[0].quantity + props.discount.offers[0].thenYouGetName +  " for additional "
                                                + props.discount.offers[0].quantity * props.discount.offers[0].additionalPrice + "! AND " +
                                                + props.discount.offers[1].quantity + props.discount.offers[1].thenYouGetName +
                                                " for additional " + props.discount.offers[1].quantity * props.discount.offers[1].additionalPrice + "!"}/>
                </RadioGroup>
            )
        } else {
            return (
                    <RadioGroup aria-label="quiz" name="quiz" value={value} onChange={handleRadioChange}>
                        <FormControlLabel value={props.discount.offers[0].thenYouGetId}
                                          control={<Radio checked={props.discount.offers[0].thenYouGetId === value}/>}
                                          label={
                                                "Get " + props.discount.offers[0].quantity + props.discount.offers[0].thenYouGetName + " for additional "
                                                + props.discount.offers[0].quantity * props.discount.offers[0].additionalPrice + "!"}/>

                        <FormControlLabel value={props.discount.offers[1].thenYouGetId}
                                          control={<Radio checked={props.discount.offers[1].thenYouGetId === value}/>}
                                          label={
                                                "Get " + props.discount.offers[1].quantity + props.discount.offers[1].thenYouGetName +
                                                " for additional " + props.discount.offers[1].quantity * props.discount.offers[1].additionalPrice + "!"}/>
                    </RadioGroup>
            )

        }
    }

    const discountSelectedHandler = () => {
        let data = {
            storeName: props.discount.storeName,
            storeId: props.discount.storeId,
            discountName: props.discount.name,
            thenYouGetId: selectedThenYouGetItem,
        }
        console.log(data.storeName + " " + data.storeId + " " +data.discountName + " " +data.thenYouGetId)

        console.log("discount operator = " + props.discount.operator)
        if (props.discount.operator === "all-or-nothing") {
            console.log("INSIDE IF " + "discount operator = " + props.discount.operator )
            data.thenYouGetId = 0;
        }

        console.log("DATA.thenUgetID =  " + data.thenYouGetId);
        props.updateDiscounts(data);

        props.setStep();
    }


    return (
        <Card className={classes.root}>
            <CardActionArea>
                <CardMedia
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                        {props.discount.name}
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                        Buy {" " + props.discount.ifYouBuyQuantity + " " + props.discount.ifYouBuyName + " and get:"}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions>
                    {setOffers()}
                <Button size="small" color="primary" onClick={discountSelectedHandler}>
                    I Want This
                </Button>
            </CardActions>
        </Card>
    );
}

export default DiscountItem;
