import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import React, {useEffect, useState} from "react";
import axios from "axios";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import withStyles from "@material-ui/core/styles/withStyles";
import {makeStyles} from "@material-ui/core/styles";
import lightBlue from "@material-ui/core/colors/lightBlue";

const useStyles = makeStyles({
    table: {
        minWidth: 700,
    },
    head: {
        backgroundColor: lightBlue,
        color: "white",
    },
    body: {
        fontSize: 14,
    },
    cell: {
        fontWeight: 'bold'
    }
});
const StyledTableCell = withStyles((theme) => ({
    head: {
        backgroundColor: theme.palette.primary.dark,
        color: theme.palette.common.white,
        fontWeight: 'bold'
    },
    body: {
        fontSize: 14,
    },
}))(TableCell);

const CheckOutSummery = (props) => {
    const classes = useStyles();
    const [summery, setSummery] = useState(null);


    // useEffect(() => {
    //     axios.get("http://localhost:8080/sdm/saveOrder")
    //         .then(response => setSummery(response.data))
    //         .catch(error => console.log(error))
    // }, [])

    const Store = (props) => {
        const {store} = props;
        const [open, setOpen] = React.useState(false);


        console.log(store);
        return (
            <React.Fragment>
                <TableRow className={classes.root}>
                    <TableCell>
                        <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                            {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                        </IconButton>
                    </TableCell>
                    <TableCell component="th" scope="row">{store.name}</TableCell>
                    <TableCell align="right">{store.id}</TableCell>
                    <TableCell align="right">{store.location}</TableCell>
                    <TableCell align="right">{store.distanceFromCustomer.toFixed(2)}</TableCell>
                    <TableCell align="right">{store.ppk.toFixed(2)}</TableCell>
                    <TableCell align="right">{store.deliveryCost.toFixed(2)}</TableCell>
                    <TableCell align="right">{store.itemsTypeQuantity.toFixed(2)}</TableCell>
                    <TableCell align="right">{store.itemsCost.toFixed(2)}</TableCell>
                </TableRow>
                <TableRow>
                    <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={9}>
                        <Collapse in={open} timeout="auto" unmountOnExit>
                            <Box margin={1}>
                                <Typography variant="h6" gutterBottom component="div">
                                    Details:
                                </Typography>
                                <Table size="small" aria-label="purchases">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>Name</TableCell>
                                            <TableCell align="right">ID</TableCell>
                                            <TableCell align="right">Category</TableCell>
                                            <TableCell align="right">Amount</TableCell>
                                            <TableCell align="right">Price</TableCell>
                                            <TableCell align="right">Total Price</TableCell>
                                            <TableCell align="right">On Sell</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {store.items.map((item) => (
                                            <TableRow key={item.name}>
                                                <TableCell component="th" scope="row">{item.name}</TableCell>
                                                <TableCell align="right">{item.id}</TableCell>
                                                <TableCell align="right">{item.purchaseCategory}</TableCell>
                                                <TableCell align="right">{item.quantity.toFixed(2)}</TableCell>
                                                <TableCell align="right">{item.pricePerUnit.toFixed(2)}</TableCell>
                                                <TableCell align="right">{item.totalPrice.toFixed(2)}</TableCell>
                                                <TableCell align="right">{item.isChosenOffer ? "Yes" : "No"}</TableCell>
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
                            <TableCell align="right">Location</TableCell>
                            <TableCell align="right">Distance</TableCell>
                            <TableCell align="right">PPK</TableCell>
                            <TableCell align="right">Delivery Price</TableCell>
                            <TableCell align="right">Total items</TableCell>
                            <TableCell align="right">Total items Price</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.summery.stores.map((store) => ( //TODO - stores.map or stores.stores.map???
                            <Store key={store.name} store={store}/> // send single store to object of type Row
                        ))}
                    </TableBody>
                    <TableRow>
                        <TableCell className={classes.cell} colSpan={1}>Items </TableCell>
                        <TableCell align="right">{props.summery.totalItemsPrice.toFixed(2)}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell className={classes.cell} colSpan={1}>Delivery</TableCell>
                        <TableCell align="right">{props.summery.totalDeliveriesPrice.toFixed(2)}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell className={classes.cell} colSpan={1}>Total</TableCell>
                        <TableCell align="right">{props.summery.totalOrderPrice.toFixed(2)}</TableCell>
                    </TableRow>
                </Table>
            </TableContainer>
        );
}

export default CheckOutSummery;