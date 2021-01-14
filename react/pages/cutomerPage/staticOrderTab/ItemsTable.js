import React, {useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import axios from "axios";

const useStyles = makeStyles({
    table: {
        minWidth: 300,
        padding: 50,
    },
    paper: {
        padding: 50,
    }
});


const ItemsTable = (props) => {
    const classes = useStyles();

    const [items, setItems] = useState([]);
    const [storeName, setStoreName] = useState(props.store) // TODO - change to props.storeName


    useEffect(() => {
        const data = {
            zoneName: props.zoneName,
            storeName: props.store,
         }

         if(props.isStatic) {
             console.log("In useEffect ItemTable, storename = " + data.storeName);
             if (props.store !== "") {
                 console.log("PASS IF");
                 axios.post("http://localhost:8080/sdm/getItemsByStore", data).then(response => {
                         let itemsArr = [];
                         console.log("In items Table getItemByStore" + response.data.items.items);
                         for (let i = 0; i < response.data.items.length; i++) {
                             const item = {
                                 id: response.data.items[i].id,
                                 name: response.data.items[i].name,
                                 purchaseCategory: response.data.items[i].purchaseCategory,
                                 price: response.data.items[i].price,
                                 amount: 0
                             }
                             itemsArr.push(item);
                         }

                         console.log(itemsArr);
                         setItems(itemsArr);
                     }
                 );
             }
         }
    }, [props.store]);

    useEffect(() => {
        const data = {
            zoneName: props.zoneName,
        }

        if(!props.isStatic) {
            console.log("In useEffect ItemTable dynamic");
                axios.post("http://localhost:8080/sdm/getZoneItems", data).then(response => {
                        let itemsArr = [];
                        console.log("In items Table get Zone Items" + response.data.items);
                        console.log("length" + response.data.items.length)
                        for (let i = 0; i < response.data.items.length; i++) {
                            const item = {
                                id: response.data.items[i].id,
                                name: response.data.items[i].name,
                                purchaseCategory: response.data.items[i].purchaseCategory,
                                price: response.data.items[i].price,
                                amount: 0
                            }
                            console.log(item.name);
                            itemsArr.push(item);
                        }
                        console.log(itemsArr);
                        setItems(itemsArr);
                    }
                );
        }
    }, []);



    const addToCount = (row) => {
        let temp = [...items];
        const oldVal = row.amount;
        if (row.purchaseCategory === "quantity") {
            row.amount = (oldVal + 1)
        } else {
            row.amount = (oldVal + 0.1)
        }
        temp[row.name] = row;
        setItems(temp);
    }

    const subtractFromCount = (row) => {
        if(!(row.amount <= 0)) {
            let temp = [...items];
            const oldVal = row.amount;
            if (row.purchaseCategory === "quantity") {
                row.amount = (oldVal - 1)
            } else {
                row.amount = (oldVal - 0.1)
            }
            temp[row.name] = row;
            setItems(temp);
        }
    }

    const onFinishHandler = () => {

        let selectedItems = [];
        for(let i=0 ; i< items.length; i++)
        {
            if(items[i].amount > 0) {
                const temp = items[i];
                selectedItems.push(temp);
            }
        }
        if(selectedItems.length === 0){
            props.createAlert("please select at least on item");
        }else {
            props.setSelectedItems(selectedItems);
        }
    }

    if(props.selectedStore != "") {
        return (
            <TableContainer className={classes.paper} component={Paper}>
                <Typography className={classes.title} variant="h6" id="tableTitle" component="div">
                    Available Items
                </Typography>
                <Table className={classes.table} aria-label="simple table">

                    <TableHead>
                        <TableRow>
                            <TableCell>Name</TableCell>
                            {props.isStatic && <TableCell align="right">Price</TableCell> }
                            <TableCell align="right"> </TableCell>
                            <TableCell align="right"> </TableCell>
                            <TableCell align="right">Count</TableCell>
                            <TableCell align="right">Total</TableCell>
                        </TableRow>

                    </TableHead>
                    <TableBody>
                        {items.map((item) => (
                            <TableRow key={item.name}>
                                <TableCell component="th" scope="item">{item.name}</TableCell>
                                {props.isStatic &&<TableCell align="right">{item.price}</TableCell>}
                                <TableCell align="right"><Button key={item.name} onClick={() => {
                                    addToCount(item)
                                }}>+</Button></TableCell>
                                <TableCell align="right"><Button key={item.name} onClick={() => {
                                    subtractFromCount(item)
                                }}>-</Button></TableCell>
                                <TableCell align="right">{item.amount.toFixed(2)}</TableCell>
                                {props.isStatic ? <TableCell align="right">{(item.price * item.amount).toFixed(2)}</TableCell> :
                                    <TableCell align="right">???</TableCell>}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
                <Button variant="outlined" onClick={onFinishHandler}>I'm done!</Button>
            </TableContainer>
        );
    }else {
        return(
        <h3>Select a store to see what items there selling</h3>
        );
    }
}

export default ItemsTable;