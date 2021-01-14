import React, {useState} from "react";
import TextField from "@material-ui/core/TextField";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Rating from '@material-ui/lab/Rating';

import FavoriteIcon from '@material-ui/icons/Favorite';
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import withStyles from "@material-ui/core/styles/withStyles";
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            width: '25ch',
        },
        width: '30ch',
    },
    paper: {
        width: '30ch',
    }
}));

const StyledRating = withStyles({
    iconFilled: {
        color: '#ff6d75',
    },
    iconHover: {
        color: '#ff3d47',
    },
})(Rating);


const FeedbackFrom = (props) => {
    const classes = useStyles();
    const [feedbackContent, setFeedbackContent] = useState("")
    const [rating, setRating] = useState(5);
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [storeName] = useState(props.store)


    const handleChange = (event) => {
        setFeedbackContent(event.target.value);
    }

    const setRatingHandler = (event) =>{
        setRating(event.target.value);
    }

    const onSubmitHandler = () => {
        const feedback = {
            storeName: storeName,
            score: rating,
            text: feedbackContent
        }
        console.log("Before submit store name= " + storeName)
        console.log(storeName);
        props.addFeedBack(feedback);
        setIsSubmitted(true);
    }

    return (
        <form className={classes.root} noValidate autoComplete="off">
            <Paper variant={"outlined"} className={classes.paper} >
                <Typography component="legend">Did you enjoy shopping at  {props.store} ?</Typography>
                <TextField
                    id="standard-multiline-flexible"
                    label="Your thoughts"
                    multiline
                    rowsMax={5}
                    value={feedbackContent}
                    onChange={handleChange}
                    disable={isSubmitted}
                />
                <Box component="fieldset" mb={3} borderColor="transparent">

                    <StyledRating
                        name="customized-color"
                        defaultValue={5}
                        getLabelText={(value) => `${value} Heart${value !== 1 ? 's' : ''}`}
                        precision={1}
                        icon={<FavoriteIcon fontSize="inherit" />}
                        onChange={setRatingHandler}
                        disable={isSubmitted}

                    />
                </Box>
                <Button variant={"outlined"} onClick={onSubmitHandler} disable={isSubmitted} > Submit </Button>
            </Paper>
        </form>
    )
}

export default FeedbackFrom;