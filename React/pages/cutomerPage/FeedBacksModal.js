import React, {useState} from "react";
import FileUploader from "../homePage/FileUploader";
import Button from "@material-ui/core/Button";
import Modal from "@material-ui/core/Modal";
import CheckOutSummery from "./CheckOutSummery";
import {makeStyles} from "@material-ui/core/styles";
import axios from "axios";
import {withRouter} from "react-router";
import FeedbackFrom from "./feedbacks/FeedbackForm";

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


const FeedBacksModal = (props) => {
    const classes = useStyles();
    // getModalStyle is not a pure function, we roll the style only on the first render
    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(true);

    const [feedBacks, setFeedBacks] = useState([]);

    const handleClose = () => {
        setOpen(false);
    };

    const onFinish = () => {
        if(feedBacks !== [] ) {
            const data = {
                feedbacks: feedBacks
            }
            console.log(feedBacks);
            axios.post("http://localhost:8080/sdm/sendFeedbacks", data)
                .then(response => console.log(response.data))
                .catch(error => console.log(error))
        }
        handleClose();
    }

    const addFeedBack = (feedback) => {
        console.log(feedback);
        const tempArr = []
        for(let i =0; i< feedBacks.length ; i++) {
            tempArr.push(feedBacks[i])
        }
        const newFeedBack = {
            storeName : feedback.storeName,
            score : feedback.score,
            text : feedback.text,
        }
        tempArr.push(newFeedBack);
        console.log(tempArr);
        setFeedBacks(tempArr);
    }

    const body = (
        <div style={modalStyle} className={classes.paper}>
            <h2 id="simple-modal-title">Help us to improve!</h2>
            {props.stores.map((store) => {
                console.log("Before send store name: " + store);
                return (
                <FeedbackFrom store={store} addFeedBack={addFeedBack}/>
                )
            })}
            <Button variant={"outlined"} onClick={onFinish} >BYE BYE </Button>
        </div>
    );

    return (
        <div>
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
export default withRouter(FeedBacksModal);