import React from "react";
import CustomerHeader from "./CustomerHeader";
import {Route, withRouter} from "react-router";

const CustomerPage =  (props) => {

    return (
        <div>
            <CustomerHeader zoneName={props.zoneName}/>
        </div>
    )
}

export default withRouter(CustomerPage);