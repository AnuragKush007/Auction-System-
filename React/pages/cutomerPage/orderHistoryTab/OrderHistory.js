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

const Item = (props) => {
    const { order } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();

    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell>
                    <IconButton aria-label="expand item" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="item">{order.id}</TableCell>
                <TableCell align="right">{order.date}</TableCell>
                <TableCell align="right">{order.location}</TableCell>
                <TableCell align="right">{order.numOfStores}</TableCell>
                <TableCell align="right">{order.numOfItems}</TableCell>
                <TableCell align="right">{order.totalItemsPrice.toFixed(2)}</TableCell>
                <TableCell align="right">{order.totalDeliveryPrice.toFixed(2)}</TableCell>
                <TableCell align="right">{order.totalOrderPrice.toFixed(2)}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={8}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Typography variant="h6" gutterBottom component="div">
                                Order Items
                            </Typography>
                            <Table size="small" aria-label="purchases">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>ID</TableCell>
                                        <TableCell>Name</TableCell>
                                        <TableCell>Bought From</TableCell>
                                        <TableCell align="right">Category</TableCell>
                                        <TableCell align="right">Price</TableCell>
                                        <TableCell align="right">Amount</TableCell>
                                        <TableCell align="right">Total Price</TableCell>
                                        <TableCell align="right">Is from sell</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {order.items.map((item) => (
                                        <TableRow key={item.id}>
                                            <TableCell component="th" scope="item">{item.id}</TableCell>
                                            <TableCell>{item.name}</TableCell>
                                            <TableCell align="right">{item.storeName}</TableCell>
                                            <TableCell align="right">{item.purchaseCategory}</TableCell>
                                            <TableCell align="right">{item.pricePerUnit.toFixed(2)}</TableCell>
                                            <TableCell align="right">{item.quantity.toFixed(2)}</TableCell>
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


const OrdersHistory = () => {

    const [orders, setOrders] = useState(null);

    useEffect( () => {

        const stam = {
            stam: "stam"
        }

        axios.post("http://localhost:8080/sdm/getCustomerOrdersHistory", stam)
            .then(response => {setOrders(response.data.orders)})
                .catch(error => {console.log(error)})
        },[])

    if(orders !== null) {
        return (
            <TableContainer component={Paper}>
                <Typography variant="h6" id="tableTitle" component="div">
                    Your Order History
                </Typography>
                <Table aria-label="collapsible table" size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell/>
                            <TableCell>ID</TableCell>
                            <TableCell align="right">Date</TableCell>
                            <TableCell align="right">Order Location</TableCell>
                            <TableCell align="right">Total Stores</TableCell>
                            <TableCell align="right">Total Items</TableCell>
                            <TableCell align="right">Total items Price</TableCell>
                            <TableCell align="right">Delivery Price</TableCell>
                            <TableCell align="right">Total Price</TableCell>

                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {orders.map((order) => (
                            <Item key={order.id} order={order}/>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        );
    } else {
        return (
            <div>
                <h2>No orders has been made yet</h2>
            </div>
        );
    }
}

export default OrdersHistory;


