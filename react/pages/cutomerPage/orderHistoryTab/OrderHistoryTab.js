import React, {useEffect, useState} from "react";
import OrdersHistory from "./OrderHistory";
import Grid from "@material-ui/core/Grid";

const OrderHistoryTab = (props) => {


    return (
        <Grid container spacing={2} justify="center">
            <Grid item xs={2} alignItems="stretch" ></Grid>

            <Grid item container xs={8} spacing={2} diraction={"column"} justify={"flex-start"} alignItems={"stretch"} >

                <OrdersHistory></OrdersHistory>
            </Grid>

            <Grid item xs={2} alignItems="stretch" ></Grid>
        </Grid>


    );
}

export default OrderHistoryTab;