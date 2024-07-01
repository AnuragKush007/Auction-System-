// import React from 'react';
// import { makeStyles } from '@material-ui/core/styles';
// import GridList from '@material-ui/core/GridList';
// import GridListTile from '@material-ui/core/GridListTile';
// import GridListTileBar from '@material-ui/core/GridListTileBar';
// import ListSubheader from '@material-ui/core/ListSubheader';
// import IconButton from '@material-ui/core/IconButton';
// import InfoIcon from '@material-ui/icons/Info';
// import Paper from "@material-ui/core/Paper";
// import RemoveCircleOutlineRoundedIcon from '@material-ui/icons/RemoveCircleOutlineRounded';
// import AddCircleOutlineRoundedIcon from '@material-ui/icons/AddCircleOutlineRounded';
// import {Box} from "@material-ui/core";
// import Button from "@material-ui/core/Button";
//
// const useStyles = makeStyles((theme) => ({
//     root: {
//         display: 'flex',
//         flexWrap: 'wrap',
//         justifyContent: 'space-around',
//         overflow: 'hidden',
//         backgroundColor: theme.palette.background.paper,
//     },
//     gridList: {
//         width: 500,
//         height: 600,
//     },
//     icon: {
//         color: 'rgba(255, 255, 255, 0.54)',
//     },
// }));
//
//
//   //The example data is structured as follows:
//
//  //import image from 'path/to/image.jpg';
//
//
// //gets in props: isDynamic, storeName
// const ItemList = (props) => {
//     const classes = useStyles();
//
//
//
//
//
//     const addToCart = () => {
//         //TODO
//     }
//
//     return (
//         <div className={classes.root}>
//             <Paper elevation={3}>
//             <GridList cellHeight={160}  spacing={8}>
//                 <GridListTile key="Subheader" cols={2} style={{ height: 'auto' }}>
//                     <ListSubheader component="div">Our Items:</ListSubheader>
//                 </GridListTile>
//                 {tileData.map((tile) => (
//                     <GridListTile key={tile.img}>
//                         <img src={tile.img} alt={tile.name} />
//                         <GridListTileBar
//                             title={tile.name}
//                             subtitle={<span>Price: {tile.price}</span>}
//                             actionIcon={
//                                 <IconButton aria-label={`Add ${tile.name}`} className={classes.icon}>
//                                     <AddCircleOutlineRoundedIcon onClick={addToCart}/>
//                                 </IconButton>
//                             }
//                         />
//                     </GridListTile>
//                 ))}
//             </GridList>
//             </Paper>
//         </div>
//     );
// }
//
// export default ItemList;