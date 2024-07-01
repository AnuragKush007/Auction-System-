import React, {useEffect, useState} from "react";
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from "axios";
import Typography from "@material-ui/core/Typography";


/**
 * input:
 *      zoneName: string
 * Output:
 *     ZoneItemsDto:
 *          isSucceed: true/false
 *          Set<ItemDto>:
 *                id: Integer
 *                name: string
 *                purchaseCategory: string
 *                availableIn: Integer
 *                averagePrice: Double
 *                totalSells: Double
 *          errorMessage : String
 *
 *
 */
const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

function createData(name, id, purchaseCategory, availableIn, averagePrice, totalSells) {
    return { name, id, purchaseCategory, availableIn, averagePrice, totalSells };
}

const rows = [
    createData('Frozen yoghurt', 159, 6.0, 24, 4.0 , 6),
    createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 5),
    createData('Eclair', 262, 16.0, 24, 6.0, 56),
    createData('Cupcake', 305, 3.7, 67, 4.3, 45),
    createData('Gingerbread', 356, 16.0, 49, 3.9, 78),
];

const ZoneItems = (props) => {
    const classes = useStyles();

    const [selectedZoneName, setSelectedZoneName] = useState(props.zoneName); // TODO - change to props.zoneName
    const [items, setItems] = useState([]);
    useEffect(() => {
        const zone = {
            zoneName: selectedZoneName,
        }

        axios.post("http://localhost:8080/sdm/getZoneItems", zone)
            .then(response => {
                setItems(response.data.items);
                console.log(response.data.items);
            })
            .catch(error => console.log(error));
        console.log("SUCCESS -set zones");
    }, []);

    if (items != []) {
        return (
            <TableContainer component={Paper}>
                <Typography className={classes.title} variant="h6" id="tableTitle" component="div">
                    Available Items
                </Typography>
                <Table className={classes.table} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Name</TableCell>
                            <TableCell align="right">ID</TableCell>
                            <TableCell align="right">Category</TableCell>
                            <TableCell align="right">Available In </TableCell>
                            <TableCell align="right">Average Price</TableCell>
                            <TableCell align="right">Total Sells</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {items.map((item) => (
                            <TableRow key={item.name}>
                                <TableCell component="th" scope="item">{item.name}</TableCell>
                                <TableCell align="right">{item.id}</TableCell>
                                <TableCell align="right">{item.purchaseCategory}</TableCell>
                                <TableCell align="right">{item.availableIn}</TableCell>
                                <TableCell align="right">{item.averagePrice.toFixed(2)}</TableCell>
                                <TableCell align="right">{item.totalSells.toFixed(2)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        );
    }
}

export default ZoneItems;