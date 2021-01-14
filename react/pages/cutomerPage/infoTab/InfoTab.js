import React, {useEffect, useState} from "react";
import ZoneItems from "./ZoneItems";
import ZoneStores from "./ZoneStores";
import axios from "axios";
import OrdersHistory from "../orderHistoryTab/OrderHistory";
import Divider from "@material-ui/core/Divider";
import Grid from "@material-ui/core/Grid";

const InfoTab = (props) => {


    return (
        <Grid container spacing={2} justify="center">
            <Grid item xs={2} alignItems="stretch" ></Grid>

            <Grid item container xs={8} spacing={2} diraction={"column"} justify={"flex-start"} alignItems={"stretch"} >
                    <ZoneStores zoneName={props.zoneName}></ZoneStores>

                    <ZoneItems zoneName={props.zoneName}></ZoneItems>


            </Grid>

            <Grid item xs={2} alignItems="stretch" ></Grid>
        </Grid>


    );
}

export default InfoTab;