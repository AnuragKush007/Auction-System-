import React, {Component, useState} from "react";
import Input from "@material-ui/core/Input";
import Button from "@material-ui/core/Button";
import axios from "axios"
import Alert from '@material-ui/lab/Alert';
import ErrorAlert from "../ErrorAlert";

const FileUploader = (props) => {

    const [file, setFile] = React.useState();
    const [isUploadSuccesses, setIsUploadSuccess] = useState(false);
    const [isError, setIsError] = useState(false);
    const [errorMassage, setErrorMassage] = useState("");

    const createAlert = (errorMessage) => {
        console.log("In Create Alert" + errorMessage);
        setErrorMassage(errorMessage);
        setIsError(true);
    }

    const fileSelectedHandler = (event) => {
        setFile(event.target.files[0]);
        setIsUploadSuccess(true);
        console.log("INE FILE SELECTORHANDLER: isUpload = " +isUploadSuccesses);
    }

    const fileUploadHandler = () => {
        const fd = new FormData();
        fd.append('file',file, file.name )
        axios.post("http://localhost:8080/sdm/uploadFile", fd)
            .then(response => {
                console.log("Respond from upload file " + response.data.isSucceed)
                if ((response.data.isSucceed) === true) {
                    console.log("PASS IF UPLOADE SUCCESS");
                axios.get("http://localhost:8080/sdm/getZones")
                    .then(r => props.setZones(r.data))
                        .catch(error =>  console.log(error))
                              props.closeModal();
                } else {
                    createAlert(response.data.errorMessage);
                  }
                }
             )
            .catch(r =>  createAlert(r.errorMessage) )
    }

   return (
       <div className="FileUploader">
            <input type="file" onChange={fileSelectedHandler} accept= ".xml" />
            <Button size="small" variant="contained" onClick={fileUploadHandler} disabled={!isUploadSuccesses} >Upload</Button>
           {isError && <ErrorAlert setIsError={setIsError} massage={errorMassage}></ErrorAlert>}
       </div>
   );
}

export default FileUploader;