import React, {useEffect, useState} from 'react';
import CustomerHeader from "../CustomerHeader";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import {makeStyles} from "@material-ui/core/styles";
import DatePicker from '../DatePicker'
import Divider from "@material-ui/core/Divider";
import Stores from "./Stores";
import LocationPicker from "../LocationPicker";
import ItemsTable from "./ItemsTable";
import Button from "@material-ui/core/Button";
import DiscountDialog from "./discounts/DiscountDialog";
import SubOrderSummery from "./SubOrderSummery";
import axios from "axios";
import { withAlert  } from 'react-alert'
import CheckOutModal from "../CheckOutModal";
import ErrorAlert from "../../ErrorAlert";
import FeedBacksModal from "../FeedBacksModal";



const useStyles = makeStyles((theme) => ({
    root: {
    },
    bla: {
        width: 200,
        height: 100,
    }}))

const StaticOrder = (props) => {
    const classes = useStyles();

    const [selectedDate, setSelectedDate] = useState(null);
    const [isDateSelected, setIsDateSelected] = useState(false);
    const [selectedStore, setSelectedStore] = useState("");
    const [isStoreSelected, setIsStoreSelected] = useState(false);

    const [selectedLocation, setSelectedLocation] = useState(null);
    const [isLocationSelected, setIsLocationSelected] = useState(false);

    const [selectedItems, setSelectedItems] = useState(null);
    const [isItemsSelected, setIsItemsSelected] = useState(false);


    const [subOrderSummery, setSubOrderSummery] = useState(null);
    const [isCheckedOut, setIsCheckedOut] = useState(false)

    const [isError, setIsError] = useState(false);
    const [errorMassage, setErrorMassage] = useState("");

    const [showFeedBacks, setShowFeedback] = useState(false);
    const [feedBacksStores, setFeedBackStores] = useState([])

    const createAlert = (errorMessage) => {
        console.log("In Create Alert" + errorMessage);
        setErrorMassage(errorMessage);
        setIsError(true);
    }

    const setSelectedStoreWrapper = (storeName) => {
        console.log("In selectedStoreWrapper, selected store = " + storeName);
        setSelectedStore(storeName);
        setIsStoreSelected(true);

        const tempArr = [];
        tempArr.push(storeName);
        console.log("IN selected store: " + tempArr);

        setFeedBackStores(tempArr);
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
        setIsItemsSelected(true);
        checkOut(items);
    }


    useEffect(() => {

    }, [isCheckedOut])

    const checkOut = (items) => {

        console.log(isDateSelected + isLocationSelected + isStoreSelected)
        if (!isDateSelected){
            createAlert("please select date");
        }else if (!isLocationSelected) {
            createAlert("please Select location");
        }else if (!isStoreSelected) {
            createAlert("please Select location");
        }


        if(!(isDateSelected  && isLocationSelected && isStoreSelected)){

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
                orderType: "static",
                storeName: selectedStore,
                xLocation: selectedLocation.x,
                yLocation: selectedLocation.y,
                date: selectedDate,
                itemIdToAmount: itemsMap,
            }

            console.log(data);

            axios.post("http://localhost:8080/sdm/checkout", data).then(response => {
                    if (response.data.isSucceed) {
                        setSubOrderSummery(response.data.stores[0])
                        setIsCheckedOut(true);
                    } else {
                        createAlert("There is allready a store in this location");
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
        setSelectedStore("");
        setIsStoreSelected(false);

        setShowFeedback(true);

    }

    return (
        <div>
         <Grid container justify="center" spacing={2} >

                 <Grid item xs={1} spacing={0}></Grid>

                 <Grid container item className={classes.root} spacing={2} xs={8} direction="row" >
                        <Grid  container xs={12}  diraction="row" alignItems="center" justify={"space-evenly"} >
                            <LocationPicker setSelectedLocation = {setSelectedLocationWrapper}/>
                            <DatePicker setSelectedDate={setSelectedDateWrapper}/>
                            <Stores zoneName={props.zoneName} setSelectedStore={setSelectedStoreWrapper}/>
                        </Grid>
                 </Grid>
                    <Grid item justify={"flex-start"}>
                        {!isCheckedOut ?
                            <ItemsTable createAlert={createAlert} zoneName={props.zoneName} isStatic={props.isStatic} store={selectedStore} setSelectedItems={setSelectedItemsWrapper}></ItemsTable>
                        : <SubOrderSummery order={subOrderSummery} items={selectedItems}></SubOrderSummery>}
                        {isCheckedOut ? <CheckOutModal resetPage={resetPage}></CheckOutModal> : null}
                    </Grid>
                 <Grid item xs={1} ></Grid>
         </Grid>
            {isError && <ErrorAlert setIsError={setIsError} massage={errorMassage}></ErrorAlert>}
            {showFeedBacks && <FeedBacksModal stores={feedBacksStores} setShowFeedBacks={setShowFeedback} ></FeedBacksModal>}
        </div>
    )
}

export default StaticOrder;