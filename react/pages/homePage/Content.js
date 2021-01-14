import React, {useEffect, useState} from "react";
import { Grid } from "@material-ui/core";
import ZoneCard from "./ZoneCard";

const Content = (props) => {

    const [zoneCards, setZonesCards] = useState(props.zones)

    useEffect(() => {
        if(zoneCards !== null) {
            setZonesCards(props.zones.map( zone => {
                zone.key=zone.zoneName;
                console.log(" In useEffect fo Content, current zone = " + zone.zoneName + zone.availableItems + zone.availableStores + zone.zoneOwner);
                return (
                    <Grid item xs={6} alignItems="stretch">
                        <ZoneCard
                        zoneName={zone.zoneName}
                        zoneOwner={zone.zoneOwner}
                        availableItems={zone.availableItems}
                        availableStores={zone.availableStores}
                        totalOrders={zone.totalOrders}
                        averageOrdersPrice={zone.averageOrdersPrice}
                        errorMessage={zone.errorMessage}
                        zoneSelectedHandler={() => {props.zoneSelectedHandler(zone.zoneName)}}
                        />
                    </Grid>)
            }))
        }
    }, [props.zones]);


    console.log("In content page outside the use effect, zoneCards= " + zoneCards);

    return (
        <Grid container spacing={2}>
            {zoneCards}
        </Grid>
    );
};

export default Content;