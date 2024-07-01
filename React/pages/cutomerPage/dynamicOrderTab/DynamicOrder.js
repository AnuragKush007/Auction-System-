import React, {useEffect, useState} from 'react';
import Grid from "@material-ui/core/Grid";
import {makeStyles} from "@material-ui/core/styles";

import axios from "axios";
import ItemsTable from "../staticOrderTab/ItemsTable";
import SubOrderSummery from "../staticOrderTab/SubOrderSummery";
import LocationPicker from "../LocationPicker";
import DatePicker from '../DatePicker'
import {
    MuiPickersUtilsProvider,
    KeyboardTimePicker,
    KeyboardDatePicker,
} from '@material-ui/pickers';
import DynamicOrderSubSummery from "./DynamicOrderSubSummery";
import CheckOutModal from "../CheckOutModal";
import ErrorAlert from "../../ErrorAlert";



const useStyles = makeStyles((theme) => ({
    root: {
    },
    bla: {
        width: 200,
        height: 100,
    }}))

const DynamicOrder = (props) => {
    const classes = useStyles();

    const [selectedDate, setSelectedDate] = useState(null);
    const [isDateSelected, setIsDateSelected] = useState(false);
    const [selectedLocation, setSelectedLocation] = useState(null);
    const [isLocationSelected, setIsLocationSelected] = useState(false);
    const [selectedItems, setSelectedItems] = useState(null);
    const [isItemsSelected, setIsItemsSelected] = useState(false);


    const [subOrderSummery, setSubOrderSummery] = useState(null);
    const [isCheckedOut, setIsCheckedOut] = useState(false)

    const [isError, setIsError] = useState(false);
    const [errorMassage, setErrorMassage] = useState("");

    const createAlert = (errorMessage) => {
        console.log("In Create Alert" + errorMessage);
        setErrorMassage(errorMessage);
        setIsError(true);
    }

    const setSelectedLocationWrapper = (xyLocation) => {
        console.log("In SelectedLocationWrapper, xyLocation = " + xyLocation.x + xyLocation.y);
        setSelectedLocation(xyLocation);
        setIsLocationSelected(true);
    }

    const setSelectedDateWrapper = (date) => {
        console.log("Selected Date = " + date);
        setSelectedDate(date);
        setIsDateSelected(true);
    }

    const setSelectedItemsWrapper = (items) => {
        console.log(items);
        setSelectedItems(items);
        checkOut(items);
    }

    const checkOut = (items) => {
        console.log("SelctedLocation = " + isLocationSelected);
        console.log("SelctedDate = " + isDateSelected);
        if (!isDateSelected){
            createAlert("please select date");
        }else if (!isLocationSelected) {
            createAlert("please Select location");
        }
        if(!(isDateSelected  && isLocationSelected)) {
            console.log("{please fill all the fields");
            // TODO - set up alert
        } else {
            let itemsMap = [];
            for (let i = 0; i < items.length; i++) {
                const item = {
                    id: items[i].id,
                    quantity: items[i].amount,
                }
                console.log("Item = " + item);
                itemsMap.push(item);
                console.log("ItemMap" + itemsMap);
            }
            console.log(itemsMap)

            const data = {
                zoneName: props.zoneName,
                orderType: "dynamic",
                storeName: "null", // if dynamic then = 0
                xLocation: selectedLocation.x,
                yLocation: selectedLocation.y,
                date: selectedDate,
                itemIdToAmount: itemsMap,
            }
            console.log(data);
            axios.post("http://localhost:8080/sdm/checkout", data).then(response => {
                    if (response.data.isSucceed) {
                        setSubOrderSummery(response.data.stores)
                        setIsCheckedOut(true);
                    } else {
                        createAlert("There is allready a store in this location")
                    }
                }
            );
        }
    }

    const resetPage = () => {
        setSelectedDate(null);
        setIsDateSelected(false);
        setSelectedLocation(null);
        setIsLocationSelected( false);
        setSelectedItems(null);
        setIsItemsSelected(false);
        setSubOrderSummery(null);
        setIsCheckedOut(false);
    }

    return (
        <div>
        <Grid container justify="center" spacing={2} >

            <Grid item xs={1} spacing={0}></Grid>

            {console.log(props.zoneName)}
            <Grid container item className={classes.root} spacing={2} xs={8} direction="row" >
                <Grid  container xs={12}  diraction="row" alignItems="center" justify={"space-evenly"} >
                    <LocationPicker setSelectedLocation = {setSelectedLocationWrapper}/>
                    <DatePicker setSelectedDate={setSelectedDateWrapper}/>
                </Grid>
            </Grid>

            <Grid item justify={"flex-start"}>
                {!isCheckedOut ?
                    <ItemsTable createAlert={createAlert} zoneName={props.zoneName} isStatic={props.isStatic} store={""} setSelectedItems={setSelectedItemsWrapper}></ItemsTable>
                    : <DynamicOrderSubSummery orders={subOrderSummery} setSelectedItems={setSelectedItems}></DynamicOrderSubSummery>}
                {isCheckedOut ? <CheckOutModal resetPage={resetPage}></CheckOutModal> : null}
            </Grid>
            <Grid item xs={1} ></Grid>
        </Grid>
            {isError && <ErrorAlert setIsError={setIsError} massage={errorMassage}></ErrorAlert>}
        </div>
    )
}

export default DynamicOrder;