import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import InputLabel from "@material-ui/core/InputLabel";
import Input from "@material-ui/core/Input";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";

const useStyles = makeStyles((theme) => ({
    container: {
        display: "flex",
        flexWrap: "wrap"
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120
    }
}));

const LocationPicker = (props) => {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const [xLocation, setXLocation] = React.useState(0);
    const [yLocation, setYLocation] = React.useState(0);

    const handleChangeX = (event) => {
        setXLocation(Number(event.target.value) || "");
        console.log("X Changed:" + Number(event.target.value))

        if(yLocation !== 0) {
            const xyLocation = {
                x: Number(event.target.value),
                y: yLocation
            }
            console.log(xyLocation);
            props.setSelectedLocation(xyLocation);
        }
    };

    const handleChangeY = (event) => {
        setYLocation(Number(event.target.value) || "");
        console.log("Y Changed:" + Number(event.target.value));

        if (xLocation !== 0) {
            const xyLocation = {
                x: xLocation,
                y: Number(event.target.value),
            }
            console.log(xyLocation);
            props.setSelectedLocation(xyLocation);
        }
    };

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Button onClick={handleClickOpen} border={true} variant="outlined">
                Choose Location
            </Button>
            <Dialog
                disableBackdropClick
                disableEscapeKeyDown
                open={open}
                onClose={handleClose}
            >
                <DialogTitle>Select Your Order Location :)</DialogTitle>
                <DialogContent>
                    <form className={classes.container}>
                        <FormControl className={classes.formControl}>
                            <InputLabel htmlFor="demo-dialog-native">xCoordinate</InputLabel>
                            <Select
                                native
                                value={xLocation}
                                onChange={handleChangeX}
                                input={<Input id="demo-dialog-native" />}
                            >
                                <option aria-label="None" value="" />
                                <option value={1}>1</option>
                                <option value={2}>2</option>
                                <option value={3}>3</option>
                                <option value={4}>4</option>
                                <option value={5}>5</option>
                                <option value={6}>6</option>
                                <option value={7}>7</option>
                                <option value={8}>8</option>
                                <option value={9}>9</option>
                                <option value={10}>10</option>
                                <option value={11}>11</option>
                                <option value={12}>12</option>
                                <option value={13}>13</option>
                                <option value={14}>14</option>
                                <option value={15}>15</option>
                                <option value={16}>16</option>
                                <option value={17}>17</option>
                                <option value={18}>18</option>
                                <option value={19}>19</option>
                                <option value={20}>20</option>
                                <option value={21}>21</option>
                                <option value={22}>22</option>
                                <option value={23}>23</option>
                                <option value={24}>24</option>
                                <option value={25}>25</option>
                                <option value={26}>26</option>
                                <option value={27}>27</option>
                                <option value={28}>28</option>
                                <option value={29}>29</option>
                                <option value={30}>30</option>
                                <option value={31}>31</option>
                                <option value={32}>32</option>
                                <option value={33}>33</option>
                                <option value={34}>34</option>
                                <option value={35}>35</option>
                                <option value={36}>36</option>
                                <option value={37}>37</option>
                                <option value={38}>38</option>
                                <option value={39}>39</option>
                                <option value={40}>40</option>
                                <option value={41}>41</option>
                                <option value={42}>42</option>
                                <option value={43}>43</option>
                                <option value={44}>44</option>
                                <option value={45}>45</option>
                                <option value={46}>46</option>
                                <option value={47}>47</option>
                                <option value={48}>48</option>
                                <option value={49}>49</option>
                                <option value={50}>50</option>

                            </Select>
                        </FormControl>
                        <FormControl className={classes.formControl}>
                            <InputLabel id="demo-dialog-select-label">yCoordinate</InputLabel>
                            <Select
                                native
                                value={yLocation}
                                onChange={handleChangeY}
                                input={<Input id="demo-dialog-native" />}
                            >
                                <option aria-label="None" value="" />
                                <option value={1}>1</option>
                                <option value={2}>2</option>
                                <option value={3}>3</option>
                                <option value={4}>4</option>
                                <option value={5}>5</option>
                                <option value={6}>6</option>
                                <option value={7}>7</option>
                                <option value={8}>8</option>
                                <option value={9}>9</option>
                                <option value={10}>10</option>
                                <option value={11}>11</option>
                                <option value={12}>12</option>
                                <option value={13}>13</option>
                                <option value={14}>14</option>
                                <option value={15}>15</option>
                                <option value={16}>16</option>
                                <option value={17}>17</option>
                                <option value={18}>18</option>
                                <option value={19}>19</option>
                                <option value={20}>20</option>
                                <option value={21}>21</option>
                                <option value={22}>22</option>
                                <option value={23}>23</option>
                                <option value={24}>24</option>
                                <option value={25}>25</option>
                                <option value={26}>26</option>
                                <option value={27}>27</option>
                                <option value={28}>28</option>
                                <option value={29}>29</option>
                                <option value={30}>30</option>
                                <option value={31}>31</option>
                                <option value={32}>32</option>
                                <option value={33}>33</option>
                                <option value={34}>34</option>
                                <option value={35}>35</option>
                                <option value={36}>36</option>
                                <option value={37}>37</option>
                                <option value={38}>38</option>
                                <option value={39}>39</option>
                                <option value={40}>40</option>
                                <option value={41}>41</option>
                                <option value={42}>42</option>
                                <option value={43}>43</option>
                                <option value={44}>44</option>
                                <option value={45}>45</option>
                                <option value={46}>46</option>
                                <option value={47}>47</option>
                                <option value={48}>48</option>
                                <option value={49}>49</option>
                                <option value={50}>50</option>

                            </Select>
                        </FormControl>
                    </form>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleClose} color="primary">
                        Ok
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default LocationPicker;
