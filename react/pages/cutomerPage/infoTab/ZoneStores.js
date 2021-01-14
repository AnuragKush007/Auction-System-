import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import axios from "axios";

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});


const Store = (props) => {
    const { store } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();
//    (name, id, owner, location, totalOrders, totalItemsIncome, PPK, totalDeliveryIncome, items)

    console.log(store);
    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell>
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="row">{store.name}</TableCell>
                <TableCell align="right">{store.id}</TableCell>
                <TableCell align="right">{store.owner}</TableCell>
                <TableCell align="right">{store.location}</TableCell>
                <TableCell align="right">{store.totalOrders}</TableCell>
                <TableCell align="right">{store.totalItemsIncome.toFixed(2)}</TableCell>
                <TableCell align="right">{store.ppk.toFixed(2)}</TableCell>
                <TableCell align="right">{store.totalDeliveriesIncome.toFixed(2)}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={9}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Typography variant="h6" gutterBottom component="div">
                                Available Items:
                            </Typography>
                            <Table size="small" aria-label="purchases">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Name</TableCell>
                                        <TableCell align="right">ID</TableCell>
                                        <TableCell align="right">Category</TableCell>
                                        <TableCell align="right">Price $</TableCell>
                                        <TableCell align="right">Sells so far</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {store.items.map((item) => (
                                        <TableRow key={item.name}>
                                            <TableCell component="th" scope="row">{item.name}</TableCell>
                                            <TableCell align="right">{item.id}</TableCell>
                                            <TableCell align="right">{item.purchaseCategory}</TableCell>
                                            <TableCell align="right">{item.price.toFixed(2)}</TableCell>
                                            <TableCell align="right">{item.numberOfSells.toFixed(2)}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}



const ZoneStores = (props) => {

    const [selectedZoneName, setSelectedZoneName] = useState(props.zoneName); // TODO - change to props.zoneName
    const [stores, setStores] = useState([]);


    useEffect( () => {
        const zone = {
            zoneName: selectedZoneName,
        }
        axios.post("http://localhost:8080/sdm/getZoneStores", zone)
            .then(response => setStores(response.data.stores))
            .catch(error => console.log(error));
        console.log("SUCCESS -set zones");
    } , []);

    if(stores != []) {
        console.log(stores);
        return (
            <TableContainer component={Paper}>
                <Typography variant="h6" id="tableTitle" component="div">
                    Available Stores
                </Typography>
                <Table aria-label="collapsible table" size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell/>
                            <TableCell>Store Name</TableCell>
                            <TableCell align="right">ID</TableCell>
                            <TableCell align="right">Owner</TableCell>
                            <TableCell align="right">Location</TableCell>
                            <TableCell align="right">Total orders so far</TableCell>
                            <TableCell align="right">Total items income</TableCell>
                            <TableCell align="right">PPK</TableCell>
                            <TableCell align="right">Total delivery income</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {stores.map((store) => (
                            <Store key={store.name} store={store}/> // send single store to object of type Row
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        );
    }
}

export default ZoneStores;

