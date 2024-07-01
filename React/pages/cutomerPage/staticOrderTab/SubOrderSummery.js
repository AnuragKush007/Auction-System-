import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {Theme as theme} from "@material-ui/core/styles/createMuiTheme";
import lightBlue from "@material-ui/core/colors/lightBlue";
import withStyles from "@material-ui/core/styles/withStyles";
import Button from "@material-ui/core/Button";
import DiscountDialog from "./discounts/DiscountDialog";

const TAX_RATE = 0.07;

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

const StyledTableRow = withStyles((theme) => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
        },
    },
}))(TableRow);

function ccyFormat(num) {
    return `${num.toFixed(2)}`;
}

function priceRow(qty, unit) {
    return qty * unit;
}

function createRow(desc, qty, unit) {
    const price = priceRow(qty, unit);
    return { desc, qty, unit, price };
}

const subtotal = (items) =>  {
    return items.map(({ price }) => price).reduce((sum, i) => sum + i, 0);
}

const rows = [
    createRow('Paperclips (Box)', 100, 1.15),
    createRow('Paper (Case)', 10, 45.99),
    createRow('Waste Basket', 2, 17.99),
];


const invoiceSubtotal = subtotal(rows);
const invoiceTaxes = TAX_RATE * invoiceSubtotal;
const invoiceTotal = invoiceTaxes + invoiceSubtotal;

const SubOrderSummery = (props) => {
    const classes = useStyles();

    if(props.order !== null) {
        return (
            <TableContainer component={Paper}>
                <Table className={classes.head} aria-label="spanning table">
                    <TableHead>
                        <TableRow>
                            <StyledTableCell align="left" colspan={2}>{props.order.name} </StyledTableCell>
                            <StyledTableCell align="right">ID: {props.order.id}</StyledTableCell>
                            <StyledTableCell align="right">PPK: {props.order.ppk}</StyledTableCell>
                            <StyledTableCell align="right">Location: {props.order.location}</StyledTableCell>
                            <StyledTableCell align="right"></StyledTableCell>
                            <StyledTableCell align="right"></StyledTableCell>

                        </TableRow>
                        <TableRow>
                            <TableCell className={classes.cell}>Name</TableCell>
                            <TableCell className={classes.cell} align="right">ID</TableCell>
                            <TableCell className={classes.cell} align="right">Category</TableCell>
                            <TableCell className={classes.cell} align="right">Amount</TableCell>
                            <TableCell className={classes.cell} align="right">Price</TableCell>
                            <TableCell className={classes.cell} align="right">Total</TableCell>
                            <TableCell className={classes.cell} align="right">From sell</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.items.map((item) => (
                            <TableRow key={item.id}>
                                <TableCell>{item.name}</TableCell>
                                <TableCell align="right">{item.id}</TableCell>
                                <TableCell align="right">{item.purchaseCategory}</TableCell>
                                <TableCell align="right">{item.amount}</TableCell>
                                <TableCell align="right">{item.price}</TableCell>
                                <TableCell align="right">{(item.price * item.amount).toFixed(2)}</TableCell>
                                <TableCell align="right">{item.fromSell}</TableCell>
                            </TableRow>
                        ))}

                        <TableRow>
                            <TableCell rowSpan={4} colSpan={5}/>
                            <TableCell className={classes.cell} colSpan={1}>Total Items</TableCell>
                            <TableCell align="right">{props.order.itemsTypeQuantity.toFixed(2)}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell className={classes.cell} colSpan={1}>Items Price</TableCell>
                            <TableCell align="right">{props.order.itemsCost.toFixed(2)}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell className={classes.cell} colSpan={1}>Delivery</TableCell>
                            <TableCell align="right">{props.order.deliveryCost.toFixed(2)}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell className={classes.cell} colSpan={1}>Total</TableCell>
                            <TableCell
                                align="right">{(props.order.deliveryCost + props.order.deliveryCost).toFixed(2)}</TableCell>
                        </TableRow>
                    </TableBody>
                </Table>

                <DiscountDialog>Check Out</DiscountDialog>
            </TableContainer>
        );
    } else {
        return (
            <div></div>
        );
    }
}

export default SubOrderSummery;