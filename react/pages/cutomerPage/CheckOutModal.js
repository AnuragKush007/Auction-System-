import React, {useState} from "react";
import FileUploader from "../homePage/FileUploader";
import Button from "@material-ui/core/Button";
import Modal from "@material-ui/core/Modal";
import CheckOutSummery from "./CheckOutSummery";
import {makeStyles} from "@material-ui/core/styles";
import axios from "axios";
import {withRouter} from "react-router";

function getModalStyle() {
    const top = 50 + rand();
    const left = 50 + rand();

    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`,
    };
}


function rand() {
    return Math.round(Math.random() * 20) - 10;
}

const useStyles = makeStyles((theme) => ({
    paper: {
        position: 'absolute',
        width: 900,
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
}));


const CheckOutModal = (props) => {
    const classes = useStyles();
    // getModalStyle is not a pure function, we roll the style only on the first render
    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(false);
    const [details, setDetails] = useState(null)

    const handleOpen = () => {
        setOpen(true);

        const data = {
            isApproved: false,
        }

        axios.post("http://localhost:8080/sdm/saveOrder",data )
            .then(response => setDetails(response.data))
                .catch(error => console.log(error))
    };

    const handleClose = () => {
        setOpen(false);
    };

    const approveOrderHandler = () => {
        const data = {
            isApproved: true,
        }
        axios.post("http://localhost:8080/sdm/saveOrder",data )
            .then(response => setDetails(response.data))
                .catch(error => console.log(error))

        handleClose();

        props.resetPage();
    }

    const cancelOrderHandler = () => {
        handleClose();
    }

    const body = (
        <div style={modalStyle} className={classes.paper}>
            <h2 id="simple-modal-title">Order Summery</h2>
            {details !== null ? <CheckOutSummery summery={details} closeModal={handleClose}/> : null}
            <Button variant={"outlined"} onClick={approveOrderHandler} >Approve </Button>
            <Button variant={"outlined"} onClick={cancelOrderHandler}>Cancel</Button>
        </div>
    );


        return (
            <div>
                <Button aria-controls="simple-menu" type="button" onClick={handleOpen}>
                    CheckOut
                </Button>
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="simple-modal-title"
                    aria-describedby="simple-modal-description"
                >
                    {body}
                </Modal>

            </div>
        );


}
export default withRouter(CheckOutModal);