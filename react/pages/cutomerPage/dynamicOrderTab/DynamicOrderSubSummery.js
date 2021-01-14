import React from "react";
import SubOrderSummery from "../staticOrderTab/SubOrderSummery";
import {makeStyles} from "@material-ui/core/styles";
import lightBlue from "@material-ui/core/colors/lightBlue";
import withStyles from "@material-ui/core/styles/withStyles";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import DiscountDialog from "../staticOrderTab/discounts/DiscountDialog";


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





const DynamicOrderSubSummery = (props) => {
    const classes = useStyles();

    if(props.orders !== null) {
        return (
            <TableContainer component={Paper}>
                <Table className={classes.head} aria-label="spanning table">
                    <TableHead>
                        <TableRow>
                            <StyledTableCell align="left" >Name </StyledTableCell>
                            <StyledTableCell align="right">ID</StyledTableCell>
                            <StyledTableCell align="right">PPK</StyledTableCell>
                            <StyledTableCell align="right">Location</StyledTableCell>
                            <StyledTableCell align="right">Distance</StyledTableCell>
                            <StyledTableCell align="right">Delivery Price</StyledTableCell>
                            <StyledTableCell align="right">Total items</StyledTableCell>
                            <StyledTableCell align="right">Total items Price</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.orders.map((store) => (
                            <TableRow key={store.id}>
                                <TableCell>{store.name}</TableCell>
                                <TableCell align="right">{store.id}</TableCell>
                                <TableCell align="right">{store.ppk}</TableCell>
                                <TableCell align="right">{store.location}</TableCell>
                                <TableCell align="right">{store.distanceFromCustomer.toFixed(2)}</TableCell>
                                <TableCell align="right">{store.deliveryCost.toFixed(2)}</TableCell>
                                <TableCell align="right">{(store.itemsTypeQuantity).toFixed(2)}</TableCell>
                                <TableCell align="right">{store.itemsCost.toFixed(2)}</TableCell>
                            </TableRow>
                        ))}
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

export default DynamicOrderSubSummery