import React, {useState, useEffect} from "react";
import { makeStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Transactions from "./Transactions";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import {MuiPickersUtilsProvider} from "@material-ui/pickers";
import DatePicker from "../cutomerPage/DatePicker";
import axios from "axios";

function rand() {
    return Math.round(Math.random() * 20) - 10;
}

function getModalStyle() {
    const top = 50 + rand();
    const left = 50 + rand();

    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`,
    };
}

const useStyles = makeStyles((theme) => ({
    paper: {
        position: 'absolute',
        width: 600,
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
    load: {
        padding: '20px',

    }
}));




    const tempArr = [
        {
            type: "Cash",
            date: "10.10.1010",
            amount: "150$",
            balanceBefore: "300$",
            balanceAfter: "300$"
        }
    ]

const Account = (props) => {
        const classes = useStyles();
        const [modalStyle] = React.useState(getModalStyle);
        const [open, setOpen] = React.useState(false);

        const [balance, setBalance] = useState(null)
        const [transactions, setTransactions] = useState(null)

        const [selectedBalance, setSelectedBalance] = useState(null);
        const [selectedDate, setSelectedDate] = useState(null);
        const [isDateSelected, setIsDateSelected] = useState(false);
        const [isValidTextField, setIsValidTextField] = useState(false);
        const [errorMassage, setErrorMassage] = useState('select valid number')
        const [newBalance, setNewBalance] = useState(0);



        const handleOpen = () => {
            setOpen(true);
        };

        const handleClose = () => {
            setOpen(false);
        };
        useEffect( () => {
            axios.get("http://localhost:8080/sdm/getAccount")
                .then(response => {
                        setBalance(response.data.balance);
                        setTransactions(response.data.transactions)
                    }
                )
                    .catch(error => console.log(error));
        },[open])

    function isNumeric(selectedBalance) {
        const double = /^[0-9,.\b]+$/;
        return double.test(selectedBalance);
    }

    useEffect( () => {
            if (!isNumeric(selectedBalance)) {
                setIsValidTextField(true);
            }else {
                setIsValidTextField(false);
            }
        }, [selectedBalance])

        const balanceChangedHandler = (event) => {
            setSelectedBalance(event.target.value);
        }

        const loadBalanceHandler = () => {
            console.log(isDateSelected);
            console.log(isValidTextField);
            const data = {
                date: selectedDate,
                amount: selectedBalance,
            }
            setNewBalance(balance + selectedBalance);

            if (isDateSelected && !isValidTextField) {
                axios.post("http://localhost:8080/sdm/loadAccount", data)
                    .then(response => setBalance(newBalance))
                        .catch(error => console.log(error))
                handleClose();
            }
        }

        const setSelectedDateWrapper = (date) => {
            setSelectedDate(date);
            setIsDateSelected(true);
        }

    const body = (
            <div style={modalStyle} className={classes.paper}>
                <h2 id="simple-modal-title">Your Balance: {balance} $</h2>
                <Transactions transactions={transactions}/>
                <div className={classes.load}>
                    {!props.isOwner && <TextField
                        error={isValidTextField}
                        id="outlined-error-helper-text"
                        label="Amount"
                        helperText={errorMassage}
                        variant="outlined"
                        onChange={balanceChangedHandler}
                    />}

                    {!props.isOwner && <Button variant="contained" onClick={loadBalanceHandler}>Load your Balance</Button> }
                </div>

                {!props.isOwner &&<DatePicker setSelectedDate={setSelectedDateWrapper}></DatePicker>}
            </div>
        );

        return (
            <div>
                <Button aria-controls="simple-menu" type="button" onClick={handleOpen}>
                    My Account
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

export default Account;