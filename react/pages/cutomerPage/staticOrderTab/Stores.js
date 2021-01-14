import React, {useEffect, useState} from 'react';
import TextField from "@material-ui/core/TextField";
import Autocomplete from '@material-ui/lab/Autocomplete'
import axios from 'axios'


const Stores = (props) => {

    const [stores, setStores] = useState([]);
    const [selectedStore,setSelectedStore] = useState(null);

    useEffect( () => {
        const zone = {
            zoneName: props.zoneName, //TODO - change to props.zoneName
        }
        axios.post("http://localhost:8080/sdm/getZoneStores", zone).then(response => {
                let storeArr = []
                for (let i = 0; i < response.data.stores.length; i++) {
                    const storeId = response.data.stores[i].id;
                    const storeName = response.data.stores[i].name;
                    const storeOwner = response.data.stores[i].owner;
                    const storePPK = response.data.stores[i].ppk;

                    const store = {
                        id: storeId,
                        name: storeName,
                        owner: storeOwner,
                        PPK: storePPK,
                    }
                    storeArr.push(store);
                }
                console.log(storeArr);
                setStores(storeArr);
            }
        );
    }, []);

    const storeSelected = (value) => {
        console.log("In StoreSelcted)" + value);
        props.setSelectedStore(value);
    }

    return (
        <Autocomplete
            id="storesComboBox"
            options={stores}
            getOptionLabel={(option) => option.name}
            style={{ width: 300 }}
            renderInput={(params) => <TextField {...params} label="Select Store" variant="outlined" />}
            onInputChange={((event, value) => storeSelected(value))}
        />
    );
}

export default Stores;