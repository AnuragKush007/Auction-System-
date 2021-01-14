import React, {useEffect, useState} from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Paper from '@material-ui/core/Paper';
import Card from '@material-ui/core/Card';
import Draggable from 'react-draggable';
import DiscountList from "./DiscountList";
import axios from "axios";
import DiscountItem from "./DiscountItem";
import Container from "@material-ui/core/Container";


const PaperComponent = (props) => {
    return (
        <Draggable handle="#draggable-dialog-title" cancel={'[class*="MuiDialogContent-root"]'}>
            <Paper {...props} />
        </Draggable>
    );
}

const DiscountDialog = (props) => {
    const [open, setOpen] = React.useState(false);
    const [storesDiscounts, setStoresDiscounts] = useState([]);

    useEffect( () => {
        axios.get("http://localhost:8080/sdm/getDiscounts").then(response => {
            console.log(response.data.discounts);
            setStoresDiscounts(response.data.discounts);
        })
    }, []);


    const handleClickOpen = () => {
        setOpen(true);
        // TODO - send post requset to get the discounts
    };

    const handleClose = () => {
        //props.onfinish;
        setOpen(false);
    };

    const updateDiscounts = (data) => {
        axios.post("http://localhost:8080/sdm/updateDiscounts", data)
            .then(response => {console.log(response.data.discounts);
                setStoresDiscounts(response.data.discounts)})
                    .catch(error => console.log(error))
    };

    return (
        <div>
            <Button variant="outlined" color="primary" onClick={handleClickOpen}>
                Get Discounts!!!
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                PaperComponent={PaperComponent}
                aria-labelledby="draggable-dialog-title"
            >
                <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
                    Because we love you!
                </DialogTitle>
                    <DialogContent>
                        {console.log("discounts" + storesDiscounts)}
                        {storesDiscounts !== [] ? <DiscountList discounts={storesDiscounts} updateDiscounts={updateDiscounts}></DiscountList>: null}
                    </DialogContent>
                    <DialogActions>
                        <Button autoFocus onClick={handleClose} color="primary">
                            Im done!
                        </Button>
                    </DialogActions>
            </Dialog>
        </div>
    );
}

export default DiscountDialog;