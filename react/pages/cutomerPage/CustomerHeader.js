import React, {useState} from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import {Toolbar} from "@material-ui/core";
import LocalGroceryStoreIcon from "@material-ui/icons/LocalGroceryStore";
import StaticOrder from "./staticOrderTab/StaticOrder";
import InfoTab from "./infoTab/InfoTab";
import OrderHistoryTab from "./orderHistoryTab/OrderHistoryTab";
import DynamicOrder from "./dynamicOrderTab/DynamicOrder";
import {withRouter} from "react-router";
import Button from "@material-ui/core/Button";

function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`nav-tabpanel-${index}`}
            aria-labelledby={`nav-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box p={3}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `nav-tab-${index}`,
        'aria-controls': `nav-tabpanel-${index}`,
    };
}

function LinkTab(props) {
    return (
        <Tab
            component="a"
            onClick={(event) => {
                event.preventDefault();
            }}
            {...props}
        />
    );
}

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
    },
}));

const CustomerHeader =  (props) => {

    const classes = useStyles();
    const [value, setValue] = React.useState(0);

    const [zone, setZoneName] = useState("");

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const onGoHomeHandler = () => {
        props.history.push('/')
    }

    return ( //TODO - need to pass zone name to all links below
        <div className={classes.root}>
            <AppBar position="static">
                <Typography align="center" className={classes.typographyStyles}>
                    <Button variant={"outlined"} onClick={onGoHomeHandler} >Home</Button>
                </Typography>

                <Typography align="center" className={classes.typographyStyles}>
                    <h2>Super Duper Market</h2>
                </Typography>

                <Tabs
                    variant="fullWidth"
                    value={value}
                    onChange={handleChange}
                    aria-label="nav tabs example"
                >
                    <LinkTab label="Info" href="/zoneInfo" {...a11yProps(0)} />
                    <LinkTab label="Lets Order!" href="/order" {...a11yProps(1)} />
                    <LinkTab label="Buy Smart!" href="/dynamicOrder" {...a11yProps(2)} />
                    <LinkTab label="Order History" href="/orderHistory" {...a11yProps(4)} />
                </Tabs>
            </AppBar>
            <TabPanel value={value} index={0}>
                <InfoTab zoneName={props.zoneName}></InfoTab>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <StaticOrder isStatic={true} zoneName={props.zoneName}/>
            </TabPanel>
            <TabPanel value={value} index={2}>
                <DynamicOrder isStatic={false} zoneName={props.zoneName}>  </DynamicOrder>
            </TabPanel>
            <TabPanel value={value} index={3}>
                <OrderHistoryTab  ></OrderHistoryTab>
            </TabPanel>
        </div>
    );
}

export default withRouter(CustomerHeader);